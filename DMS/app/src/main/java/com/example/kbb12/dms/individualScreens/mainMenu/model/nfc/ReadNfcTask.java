package com.example.kbb12.dms.individualScreens.mainMenu.model.nfc;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.kbb12.dms.individualScreens.mainMenu.model.MainMenuReadWriteModel;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by lidda on 23/03/2017.
 */

public class ReadNfcTask extends AsyncTask<Tag, Boolean, String> {


    private static String TAG = "ReadNfcTask";
    private Activity activity;
    private NfcParser parser;
    private MainMenuReadWriteModel model;

    public ReadNfcTask(Activity a,MainMenuReadWriteModel model) {
        activity = a;
        parser = new NfcParser(a,model);
    }


    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    //Buzz once for error
    private void buzzOnce() {
        final Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);
    }

    private void buzzTwice(){
        final Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                vibrator.vibrate(200);
            }
        }, 400);
    }


    @Override
    protected String doInBackground(Tag... params) {
        Tag tag = params[0];


        NfcV nfcvTag = NfcV.get(tag);

        Log.d(TAG, "NfcV Tag ID: "+tag.getId());

        try {
            nfcvTag.connect();
        } catch (IOException e) {
            activity.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    Toast.makeText(activity, "Could not connect to sensor", Toast.LENGTH_LONG).show();//Vibrate twice to show data is read.
                    buzzOnce();
                }
            });
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
                    (byte)0x02, // Flags
                    (byte)0x2B // Command: Get system information
            };
            byte[] systeminfo = nfcvTag.transceive(cmd);

            //systeminfo = Arrays.copyOfRange(systeminfo, 2, systeminfo.length - 1);

            //byte[] memorySize = { systeminfo[6], systeminfo[5]};
            //Log.d(TAG, "Memory Size: " + Integer.parseInt(bytesToHex(memorySize).trim(), 16 ));

            //byte[] blocks = { systeminfo[8]};
            //Log.d(TAG, "Total number of blocks: " + Integer.parseInt(bytesToHex(blocks).trim(), 16 ));

            for (int i = 3; i < 40; i++) {
                Log.d(TAG, i+"");
                cmd = new byte[]{
                        (byte) 0x02, // Flags
                        (byte) 0x20, // Command: Read single block
                        (byte) i // block (offset)
                };
                boolean worked=false;
                byte[] block = null;
                block = nfcvTag.transceive(cmd);
                worked = true;
                Log.d(TAG,"Block: " + i + ", data: " + block.toString());
                block = Arrays.copyOfRange(block, 1, block.length);
                block =Arrays.copyOf(block, 8);
                //Convert to a hex string for parsing later
                readData = readData + bytesToHex(block);

            }
            return readData;


        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Error sending/receiving data", Toast.LENGTH_LONG).show();
                    buzzOnce();
                }
            });
        }

        return null;
    }

    @Override
    protected void onPostExecute(final String success) {
        if(success!=null) {
            try {
                parser.parseNfc(success);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Sensor scanned!", Toast.LENGTH_LONG).show();
                    }
                });
                buzzTwice();
            } catch (SensorTimeException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Sensor not started in this app!", Toast.LENGTH_LONG).show();
                    }
                });
                buzzOnce();
            }
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Sensor not started in this app!", Toast.LENGTH_LONG).show();
                }
            });
            buzzOnce();
        }

    }

    @Override
    protected void onCancelled() {
        //ToDo: Something if cancelled
    }
}
