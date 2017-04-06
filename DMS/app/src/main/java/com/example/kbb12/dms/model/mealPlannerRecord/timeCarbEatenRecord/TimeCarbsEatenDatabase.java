package com.example.kbb12.dms.model.mealPlannerRecord.timeCarbEatenRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class TimeCarbsEatenDatabase implements TimeCarbEatenRecord {
    private SQLiteDatabase write;
    private SimpleDateFormat dateFormat;

    public TimeCarbsEatenDatabase(SQLiteDatabase write) {
        this.write = write;
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    }

    @Override
    public List<Double> getAllEntries(Calendar from, Calendar to) {
        Cursor cursor = write.rawQuery("Select * from "+
                        TimeCarbEatenContract.ContentsDefinition.TABLE_NAME+" where "+
                        TimeCarbEatenContract.ContentsDefinition.COLUMN_NAME_DATETIME+">=? AND "+
                        TimeCarbEatenContract.ContentsDefinition.COLUMN_NAME_DATETIME+"<=?",
                new String[]{getDateTime(from),getDateTime(to)});
        List<Double> entries =new ArrayList<>();
        while(cursor.moveToNext()){
            entries.add(cursor.getDouble(cursor.getColumnIndex(
                    TimeCarbEatenContract.ContentsDefinition.COLUMN_NAME_CARBCONSUMED)));
        }
        return entries;
    }

    @Override
    public long addCarbsEaten(Integer rawData, Calendar timestamp) {
        ContentValues values = new ContentValues();
        values.put(TimeCarbEatenContract.ContentsDefinition.COLUMN_NAME_CARBCONSUMED, rawData);
        values.put(TimeCarbEatenContract.ContentsDefinition.COLUMN_NAME_DATETIME, getDateTime(timestamp));

        // insert row
        long id = write.insert(TimeCarbEatenContract.ContentsDefinition.TABLE_NAME, null, values);

        return id;
    }


    private String getDateTime(Calendar timestamp) {
        return dateFormat.format(timestamp.getTime());
    }


    private Calendar parseCalendar(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            return null;
        }
        return c;
    }


}
