package com.example.kbb12.dms.nfc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.model.IBloodGlucoseModel;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.startUp.ModelHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lidda on 23/03/2017.
 */

public class NfcParser {

    private IBloodGlucoseModel userModel;
    private SharedPreferences sharedPreferences;
    private Activity activity;
    private static final String TAG = "NfcParser";

    public NfcParser(Activity activity){
        sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.sensor_prefs), Context.MODE_PRIVATE);
        this.activity = activity;
        userModel = ModelHolder.model;
    }

    private int getCurrentSensorTime(){
        return sharedPreferences.getInt(activity.getString(R.string.sensor_current_time), Integer.MAX_VALUE);
    }

    private Calendar getSensorStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sharedPreferences.getLong(activity.getString(R.string.sensor_start_time), 0));
        return cal;
    }

    private void saveCurrentSensorTime(int currentTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(activity.getString(R.string.sensor_current_time), currentTime);
        editor.commit();
    }

    private void saveSensorStartTime(Calendar startTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(activity.getString(R.string.sensor_start_time), startTime.getTimeInMillis());
        editor.commit();
    }

    private double linearConversion(int val) {
        int bitmask = 0x0FFF;
        return ((val & bitmask) / 153.0);
    }

    public void parseNfc(String result) throws SensorTimeException {
        Calendar now = Calendar.getInstance();
        Log.e(TAG, result.length() + result);

        boolean newSensor = false;
        userModel.addRawData(now, result);

        //Get relevant pointers
        int glucosePointer = Integer.parseInt(result.substring(4, 6), 16);
        int elapsedMinutes = Integer.parseInt(result.substring(586,588) + result.substring(584,586),16);
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
            } else {
                //TODO: Throw an error
                Log.d(TAG, "tss: "+timeSinceStart + " em: " + elapsedMinutes);
                Log.e(TAG, "Will throw error");
                throw new SensorTimeException();
            }
        }
        //TODO: SAVE CURRENT READING IN DB
        double currentReading = linearConversion(readings[((glucosePointer+15)%16)]);
        userModel.addCurrentReading(now, currentReading);
        Log.d(TAG, "CR: "+ currentReading);


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
        BGReading last = userModel.getMostRecentHistoryReading();
        for(Calendar c: historyMap.keySet()){
            Log.d(TAG, c.toString() + historyMap.get(c));
            //Only add to the history database if the reading is after the most recent one
            if(newSensor||last==null||(last!=null&&c.after(last.getTime())&&historyMap.get(c)>0.01)){
                userModel.addHistoryReading(c, historyMap.get(c));
                Log.d(TAG, c.get(Calendar.DAY_OF_MONTH) +"");
            }
        }

    }

    private long getMinutesSinceSensorStart() {
        Calendar now = Calendar.getInstance();
        Calendar startTime = getSensorStartTime();
        long diff = now.getTimeInMillis() - startTime.getTimeInMillis();
        long minutes = diff/(1000*60);
        return minutes;
    }
}
