package com.example.kbb12.dms.NFCPrototype;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kbb12.dms.R;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class NFCActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_LONG).show();
        } else {
            finish();
            /*make this toast in main activity?
            Toast.makeText(this, "NFC is not enabled", Toast.LENGTH_LONG).show();
            */
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        start4groundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop4groundDispatch(this, nfcAdapter);
    }

    private void start4groundDispatch(Activity activity, NfcAdapter nAdapt) {
        Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};
        String[][] techLists = new String[][]{};

        nAdapt.enableForegroundDispatch(activity, pendingIntent, intentFilter, techLists);
    }

    private void stop4groundDispatch(Activity activity, NfcAdapter nAdapt) {
        nAdapt.disableForegroundDispatch(activity);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //do stuff

        if (intent.hasExtra(nfcAdapter.EXTRA_TAG)){
            // nfc intent

            Tag tag = intent.getParcelableExtra(nfcAdapter.EXTRA_TAG);
            NdefMessage ndefMessage = createNdefMessage(MESSAGE_CONTENT_FOR_TAG);

            writeNdefMessage(tag, ndefMessage);
        }

    }

    private NdefRecord createRecord(String text) {
        String format = "UTF-8";
        try{
            String language = "en";
            byte[] textBytes = text.getBytes();
            byte[] languageBytes = language.getBytes(format);
            int textLength = textBytes.length;
            int languageLength = languageBytes.length;

            byte[] payload = new byte[1+textLength+languageLength];
            payload[0] = (byte) languageLength;

            System.arraycopy(textBytes, 0, payload, 1+languageLength, textLength);
            System.arraycopy(languageBytes, 0, payload, 1, languageLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        } catch(UnsupportedEncodingException e){
            Log.e("createRecord", e.getMessage());
        }
        return null;
    }



    private NdefMessage createNdefMessage(String message){
        NdefRecord ndefRecord = createRecord(message);
        NdefMessage ndefmessage = new NdefMessage(new NdefRecord[] {ndefRecord});
        return ndefmessage;
    }

    private void writeNdefMessage(Tag tag, NdefMessage message){
        try{
            if(tag == null){
                Toast.makeText(this, "Tag is null", Toast.LENGTH_LONG).show();
                return;
            }

            Ndef ndef = Ndef.get(tag);

            if (ndef == null){
                createTag(tag, message);
            } else{
                ndef.connect();

                if(!ndef.isWritable()){
                    Toast.makeText(this, "Tag cannot be written", Toast.LENGTH_LONG).show();
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(message);
                ndef.close();

                Toast.makeText(this, "Tag written successfully", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Log.e("writeNdef", e.getMessage());
        }
    }

    private void createTag(Tag tag, NdefMessage message){
        try{
            NdefFormatable formatable = NdefFormatable.get(tag);

            if (formatable == null){
                //toast for bad tag?
            }

            formatable.connect();
            formatable.format(message);
            formatable.close();

            Toast.makeText(this, "Tag written successfully", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Log.e("createTag", e.getMessage());
        }
    }
}
