package com.example.kbb12.dms.model.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.database.DatabaseBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class BluetoothService extends Service{
    private static final String tag="BluetoothService";

    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    private static final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private boolean mAllowInsecureConnections = true;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    private int mNewState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device


    private BluetoothAdapter mAdapter = null;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public void onCreate(){
        //This will only run if bluetooth is enabled so don't do any checking for it
        super.onCreate();
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e(tag, "Launching Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(tag, "OnStartCommandRun");
        if (!mAdapter.isEnabled()) {
            //If bluetooth isn't enabled, return
            return START_STICKY;
        }
        BluetoothDevice foundDevice = null;
        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        // for each paired device
        for(BluetoothDevice device : pairedDevices){
            //Check if paired devices matches the address of it
            Log.d(tag, "NAME: " + device.getName());
            if(device.getName().equals("HC-06")){
                Log.d(tag, tag + device.getAddress());
                foundDevice = mAdapter.getRemoteDevice(device.getAddress());
            }
        }
        if(foundDevice!=null){
            connect(foundDevice);

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public synchronized void connect(BluetoothDevice device) {
        Log.d(tag, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        mState = STATE_CONNECTING;
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.d(tag, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        mState = STATE_CONNECTED;
    }

    private void getData(){

    }
    /**
     * Performs a connection
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if ( mAllowInsecureConnections ) {
                    Method method;

                    method = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class } );
                    tmp = (BluetoothSocket) method.invoke(device, 1);
                }
                else {
                    tmp = device.createRfcommSocketToServiceRecord( SerialPortServiceClass_UUID );
                }
            } catch (Exception e) {
                Log.e(tag, "create() failed", e);
                finished();
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(tag, "BEGIN mConnectThread");
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                Log.e(tag, "Connection failed");
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(tag, "unable to close() socket during connection failure", e2);
                    finished();
                }
                // Start the service over to restart listening mode
                //BluetoothSerialService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(tag, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        public ConnectedThread(BluetoothSocket socket) {
            Log.d(tag, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(tag, "temp sockets not created", e);
                finished();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(tag, "BEGIN mConnectedThread");
            byte[] buffer = new byte[4096];
            PrintWriter printWriter = new PrintWriter(mmOutStream);
            Scanner scanner = new Scanner(mmInStream);
            String moreString = "Send\n";
            String returned;
            BluetoothParser bp = new BluetoothParser(getApplicationContext());

            // Keep listening to the InputStream while connected

            try {
                Thread.sleep(2000);
                mmOutStream.write(moreString.getBytes());
                //  Wait a second Read from the InputStream
                int i=0;
                while(i<2000) {
                    if (scanner.hasNextLine()) {
                        returned = scanner.nextLine();
                        Log.d(tag, returned);
                        mmOutStream.write(returned.getBytes());
                        bp.parseNfc(returned);
                        break;
                    }
                        i++;
                }
                mmSocket.close();
            } catch (Exception e) {
                try {
                    mmInStream.close();
                    mmOutStream.close();

                }catch (Exception e1){
                    Log.e(tag, "Cannot close");
                    finished();
                }
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(tag, "close() of connect socket failed", e);
            }
        }
    }

    private void finished() {
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

    }

    private class BluetoothParser{

        private SharedPreferences sharedPreferences;
        private RawBGRecord rawdb;
        private BGRecord currentdb, historydb;
        private static final String TAG = "NfcParser";
        private Context context;

        public BluetoothParser(Context context){
            this.context = context;
            sharedPreferences = getSharedPreferences(getString(R.string.sensor_prefs),Context.MODE_PRIVATE);
            DatabaseBuilder db = new DatabaseBuilder(context);
            rawdb = db.getRawBGRecord();
            currentdb = db.getCurrentBGRecord();
            historydb = db.getHistoryBGRecord();

        }

        private int getCurrentSensorTime(){
            return sharedPreferences.getInt(getString(R.string.sensor_current_time), Integer.MAX_VALUE);
        }

        private Calendar getSensorStartTime(){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sharedPreferences.getLong(getString(R.string.sensor_start_time), 0));
            return cal;
        }

        private void saveCurrentSensorTime(int currentTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.sensor_current_time), currentTime);
            editor.apply();
        }

        private void saveSensorStartTime(Calendar startTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(getString(R.string.sensor_start_time), startTime.getTimeInMillis());
            editor.apply();
        }

        private void setLastScan(){
            Calendar cal = Calendar.getInstance();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(context.getString(R.string.last_scan), cal.getTimeInMillis());
            editor.apply();
        }

        private Calendar getSLastscan(){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sharedPreferences.getLong(context.getString(R.string.last_scan), 0));
            return cal;
        }

        private double linearConversion(int val) {
            int bitmask = 0x0FFF;
            return ((val & bitmask) / 153.0);
        }

        public void parseNfc(String result){
            Log.e("BLUETOOTH", result);
            boolean newSensor = false;
            Calendar now = Calendar.getInstance();

            rawdb.addRawData(result, now);

            //Get relevant pointers
            int glucosePointer = Integer.parseInt(result.substring(4, 6), 16);
            int elapsedMinutes = Integer.parseInt(result.substring(586,588) + result.substring(584,586),16);
            Log.e("BLUETOOTH", elapsedMinutes+"");
            int historyPointer = Integer.parseInt(result.substring(6, 8), 16);
            int readings[] = new int[16];
            int historicalReadings[] = new int[32];
            Map<Calendar, Double> historyMap = new HashMap<>();

            for (int i = 8, j = 0; i < 8 + (16 * 12); i += 12, j++) {
                final String g = result.substring(i + 2, i + 4) + result.substring(i, i + 2);
                readings[j] = Integer.parseInt(g, 16);
            }

            for(int i = 200, j = 0; i <= 200 + (31 * 12); i +=12, j++){
                final String g = result.substring(i + 2, i + 4) + result.substring(i, i + 2);
                historicalReadings[j] = Integer.parseInt(g, 16);
            }

            //TODO: More new sensor error checking FOR OLD SENSOR!!!
            //If a new sensor
            long timeSinceStart = ((now.getTimeInMillis()/1000/60)-(getSensorStartTime().getTimeInMillis()/1000/60));
            Log.d(TAG, "tss: "+timeSinceStart + " em: " + elapsedMinutes);
            //0.5 should be more than enough
            if(getCurrentSensorTime()>elapsedMinutes||(Math.abs(timeSinceStart-elapsedMinutes)/timeSinceStart)>0.05){
                if(elapsedMinutes<65){
                    //This can be assumed to be a new sensor
                    Calendar temp = Calendar.getInstance();
                    temp.add(Calendar.MINUTE, (0-elapsedMinutes));
                    saveSensorStartTime(temp);
                    newSensor = true;
                    Log.e("BLUETOOTH", "NEW SENSOR");
                } else {
                    //TODO: Throw an error
                    Log.d(TAG, "tss: "+timeSinceStart + " em: " + elapsedMinutes);
                    Log.e(TAG, "Will throw error");
                    //Dont throw error here, we're about to disconnect anyway
                }
            }
            //TODO: SAVE CURRENT READING IN DB
            double currentReading = linearConversion(readings[((glucosePointer+15)%16)]);
            currentdb.insertReading(now, currentReading);


            saveCurrentSensorTime(elapsedMinutes);
            long tss = getMinutesSinceSensorStart();
            Calendar mostRecent = Calendar.getInstance();
            //This should be the time of the most recent history reading
            mostRecent.add(Calendar.MINUTE,0-((int)tss%15));
            Calendar c2;

            //Now get all the times
            for(int i = (historyPointer+31)%32, j = 0; j<32; j++, i=((i+31)%32)){
                c2 = Calendar.getInstance();
                c2.setTime(mostRecent.getTime());
                mostRecent.add(Calendar.MINUTE, -15);
                historyMap.put(c2, linearConversion(historicalReadings[i]));
            }
            Calendar last = historydb.getMostRecentReading().getTime();

            for(Calendar c: historyMap.keySet()){
                Log.d(TAG, last.getTimeInMillis() + " and " + c.getTimeInMillis() + "reading: " + historyMap.get(c));
                //Only add to the history database if the reading is after the most recent one
                if(newSensor||last==null||(last!=null&&historyMap.get(c)>0.01&&c.after(getSLastscan()))){
                    historydb.insertReading(c, historyMap.get(c));
                }
            }
            setLastScan();


        }

        private long getMinutesSinceSensorStart() {
            Calendar now = Calendar.getInstance();
            Calendar startTime = getSensorStartTime();
            long diff = now.getTimeInMillis() - startTime.getTimeInMillis();
            long minutes = diff/(1000*60);
            return minutes;
        }
    }
}