package com.example.kbb12.dms.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kbb12.dms.R;

import java.io.IOException;
import java.util.Arrays;

public class NfcActivity extends AppCompatActivity {

    private NfcAdapter mAdapter;
    private String result;
    private static final String TAG = "NfcActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mAdapter);
    }

    @Override
    public void onPause(){
        super.onPause();
        stopForegroundDispatch(this, mAdapter);
    }

    /**
     * Stops foreground dispatch of NFC intents
     * @param activity The Activity requesting the foreground dispatch.
     * @param adapter The NfcAdapter used for the foreground dispatch.
     */
    public void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * Sets up foreground dispatch for NFC intents for the Activity
     * @param activity The corresponding Activity requesting the foreground dispatch.
     * @param adapter The NfcAdapter used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techListsArray = new String[][] {};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Log.d(TAG, "Tech Discovered");
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NFCReadingTask nfc = new NFCReadingTask(tag);
            nfc.execute();
        }
    }

    private void setNfcResult(String result){
        //ToDo: Do something with the result
        this.result = result;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class NFCReadingTask extends AsyncTask<Tag, Void, String> {

        private Tag tag;
        public NFCReadingTask(Tag tag){
            this.tag = tag;
        }

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            NfcV nfcvTag = NfcV.get(tag);

            Log.d(TAG, "NfcV Tag ID: "+tag.getId());

            try {
                nfcvTag.connect();
            } catch (IOException e) {
                NfcActivity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not connect to sensor", Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }
            String readData = "";
            byte[][] allBytes = new byte[40][8];

            /**
             * For each block:
             *      request that block
             *      set the size to 8 bytes
             *      convert to hex
             *      add to string
             * Then get Current Reading
             * Get 15 readings
             * Get historical readings
             */
            byte[] cmd;

            try {
                // Get system information (0x2B)
                cmd = new byte[] {
                        (byte)0x00, // Flags
                        (byte)0x2B // Command: Get system information
                };
                byte[] systeminfo = nfcvTag.transceive(cmd);

                systeminfo = Arrays.copyOfRange(systeminfo, 2, systeminfo.length - 1);

                byte[] memorySize = { systeminfo[6], systeminfo[5]};
                Log.d(TAG, "Memory Size: " + Integer.parseInt(bytesToHex(memorySize).trim(), 16 ));

                byte[] blocks = { systeminfo[8]};
                Log.d(TAG, "Total number of blocks: " + Integer.parseInt(bytesToHex(blocks).trim(), 16 ));

                for (int i = 3; i < 40; i++) {
                    cmd = new byte[]{
                            (byte) 0x00, // Flags
                            (byte) 0x20, // Command: Read single block
                            (byte) i // block (offset)
                    };

                    byte[] block = nfcvTag.transceive(cmd);
                    Log.d(TAG,"Block: " + i + ", data: " + block.toString());
                    block = Arrays.copyOfRange(block, 1, block.length);
                    block =Arrays.copyOf(block, 8);
                    //Convert to a hex string for parsing later
                    readData = readData + bytesToHex(block);

                }

                return readData;

            } catch (IOException e) {
                NfcActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error sending/receiving data", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(final String success) {
            NfcActivity.this.setNfcResult(success);
        }

        @Override
        protected void onCancelled() {
            //ToDo: Something if cancelled
        }
    }

}
