package com.example.kbb12.dms.StartUp;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by lidda on 01/03/2017.
 */
public class DataParser {

    DatabaseHelper db;

    public DataParser(Context context){
        db = new DatabaseHelper(context);
    }


    /**
     * Adds data to the raw data table
     * @param data the raw data string containing blocks 3-40
     */
    public void addRawData(String data){
        db.addRawData(data, Calendar.getInstance());
    }


}
