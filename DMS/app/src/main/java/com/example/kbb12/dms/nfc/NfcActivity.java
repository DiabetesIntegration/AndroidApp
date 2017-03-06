package com.example.kbb12.dms.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.DataParser;
import com.example.kbb12.dms.StartUp.DatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class NfcActivity extends AppCompatActivity {

    private NfcAdapter mAdapter;
    private String result;
    private static final String TAG = "NfcActivity";
    private boolean cancelled = false;
    private DataParser dataParser;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        db = new DatabaseHelper(getApplicationContext());

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        dataParser = new DataParser(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/csv");
                Map<Calendar, String> map = db.getAllBasicData();
                Log.d(TAG, "Size:" + map.size());
                CsvCreator csv = new CsvCreator(getApplicationContext());
                File file = csv.createCsv(map);
                Log.d(TAG, file.getName());
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.example.myapp.fileprovider", file);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "text/csv");

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    List<ResolveInfo> resInfoList = getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        getApplicationContext().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    startActivity(intent);
                }



            }
        });

    }

    public static void revokeFileReadPermission(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            String dirpath = context.getFilesDir() + File.separator + "directory";
            File file = new File(dirpath + File.separator + "data.csv");
            Uri uri = FileProvider.getUriForFile(context, "com.example.myapp.fileprovider", file);
            context.revokeUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
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

    @Override
    public void onDestroy(){
        revokeFileReadPermission(getApplicationContext());
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
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{new String[] { NfcV.class.getName() }};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        String action = intent.getAction();
        Log.d(TAG, "Tech Discovered");

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NFCReadingTask nfc = new NFCReadingTask();
        Log.d(TAG, "Tech Discovered");
        nfc.execute(tag);
    }


    private double glucose_Reading(int val) {
        int bitmask = 0x0FFF;
        return ((val & bitmask) / 8.5);
    }

    private double glucose2(int val){
        int bitmask = 0x0FFF;
        return Double.valueOf( Double.valueOf((val & bitmask) / 6)- 37);
    }

    private void setNfcResult(String result){
        //ToDo: Do something with the result
        if(!cancelled && result != null) {
            //Vibrate twice to show data is read.
            final Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(200);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    vibrator.vibrate(200);
                }
            }, 400);
            this.result = result;
            TextView tv = (TextView) findViewById(R.id.pointer);
            tv.setText("Glucose Pointer" + Integer.parseInt(result.substring(4, 6), 16));
            int readings[] = new int[16];
            for (int i = 8, j = 0; i <= 8 + 15 * 12; i += 12, j++) {
                Log.d(TAG, result.substring(i, i + 12));

                final String g = result.substring(i + 2, i + 4) + result.substring(i, i + 2);

                readings[j] = Integer.parseInt(g, 16);
            }

            int i = 0;
            tv = (TextView) findViewById(R.id.textView);
            tv.setText("1Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView2);
            tv.setText("2Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView3);
            tv.setText("3Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView4);
            tv.setText("4Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView5);
            tv.setText("5Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView6);
            tv.setText("6Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView7);
            tv.setText("7Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView8);
            tv.setText("8Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView9);
            tv.setText("9Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView10);
            tv.setText("10Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView11);
            tv.setText("11Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView12);
            tv.setText("12Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView13);
            tv.setText("13Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView14);
            tv.setText("14Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView15);
            tv.setText("15Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            i++;
            tv = (TextView) findViewById(R.id.textView16);
            tv.setText("0Int: " + readings[i] + " glucose1: " + threeSigFigs(glucose_Reading(readings[i])) + " glucose2: " + glucose2(readings[i]));
            dataParser.addRawData(result);
        } else {
            //ToDo: Show Dialog if cancelled
            final Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
        }
    }

    private double threeSigFigs(double d){
        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(3));
        return bd.doubleValue();
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
    private class NFCReadingTask extends AsyncTask<Tag, Boolean, String> {

        public NFCReadingTask(){
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

                for (int i = 3; i < 244; i++) {
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
            cancelled = true;
            //ToDo: Something if cancelled
        }
    }

}
