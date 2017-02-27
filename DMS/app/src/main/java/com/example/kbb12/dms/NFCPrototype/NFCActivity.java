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
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kbb12.dms.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NFCActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private String readings;

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

        //activity in the foreground/resume
        start4groundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        //needs to be halted before onpause is called
        stop4groundDispatch(this, nfcAdapter);

        super.onPause();
    }

    private void start4groundDispatch(Activity activity, NfcAdapter nAdapt) {
        Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[1];
        String[][] techLists = new String[][]{};

        intentFilter[0] = new IntentFilter();
        intentFilter[0].addAction(nAdapt.ACTION_NDEF_DISCOVERED);
        intentFilter[0].addCategory(Intent.CATEGORY_DEFAULT);

        nAdapt.enableForegroundDispatch(activity, pendingIntent, intentFilter, techLists);
    }

    private void stop4groundDispatch(Activity activity, NfcAdapter nAdapt) {
        nAdapt.disableForegroundDispatch(activity);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);

        //where the stuff gets done:

        String intAct = intent.getAction();

        if (intent.hasExtra(nfcAdapter.EXTRA_TAG)){
            // nfc intent

            Tag tag = intent.getParcelableExtra(nfcAdapter.EXTRA_TAG);

            new NfcReadTask().execute(tag);
        }

    }

    private class NfcReadTask extends AsyncTask<Tag, void, String>{


        @Override
        protected String doInBackground(Tag... tags) {

            Tag tag = tags[0];
            NfcV nfcvTag = NfcV.get(tag);

            try{
                nfcvTag.connect();
            } catch (IOException e) {
                //make toast to inform user connection couldnt be made?
                return null;
            }

            readings = "";

            //assuming the size of the block is 8 bytes
            byte[][] blocks = new byte[13][8];

            try {
                for (int i = 3; i < 16; i++) {

                    byte[] request = new byte[]{
                            (byte) 0x02,
                            (byte) 0x20,
                            (byte) i
                    };

                    byte[] readBlock = nfcvTag.transceive(request);
                    int length = readBlock[1];

                    for (int j=0; j<length; j++){
                        blocks[i-2][j] = readBlock[j+2];
                    }

                }
            } catch (IOException e){
                //do a thing
            }

            return null;
        }
    }

}
