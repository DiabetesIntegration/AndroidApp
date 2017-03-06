package com.example.kbb12.dms.nfc;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lidda on 06/03/2017.
 */

public class CsvCreator {

    private SimpleDateFormat dateFormat;
    private Context context;
    private static final String TAG =  "CsvCreator";

    public CsvCreator(Context context){
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.context = context;
    }
    public File createCsv(Map<Calendar, String> dataMap){
        try {
            File file = new File(context.getFilesDir() + File.separator + "directory" + File.separator + "data.csv");
            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Log.d(TAG, "About to write");
            for(Calendar c:dataMap.keySet()){
                Log.d(TAG, "One object found");
                bufferedWriter.write(dateFormat.format(c.getTime()));
                bufferedWriter.write(", ");
                bufferedWriter.write(dataMap.get(c));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "IOException thrown");
            return null;
        }

    }
}
