package com.example.kbb12.dms.database.insulinTakenRecord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class InsulinTakenDatabase implements InsulinTakenRecord {

    private SQLiteDatabase write;
    private DateFormat dateFormat;

    public InsulinTakenDatabase(SQLiteDatabase write) {
        this.write=write;
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss", Locale.getDefault());

    }

    @Override
    public void addEntry(Calendar time,Double dose,boolean basalInsulin){
        String trueOrFalse;
        if(basalInsulin){
            trueOrFalse="TRUE";
        }else{
            trueOrFalse="FALSE";
        }
        write.execSQL("INSERT INTO " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" + InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_BASAL + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT + ")VALUES(\"" + formatDateTime(time) + "\", '" + trueOrFalse + "', " + dose + ")");
    }

    @Override
    public IInsulinTakenEntry getMostRecentBolus() {
        /*
        Get the entry with the maximum time which isn't Basal insulin.
         */
        Cursor cursor = write.rawQuery("Select MAX("
                +InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME +"),"+
                InsulinTakenContract.ContentsDefinition.COLUMN_BASAL +", "+
                InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT +
                " from "+InsulinTakenContract.ContentsDefinition.TABLE_NAME+
                " where "+InsulinTakenContract.ContentsDefinition.COLUMN_BASAL +"=?",
                new String[]{"FALSE"});
        if(!cursor.moveToNext()||cursor.getString(0)==null) {
            return null;
        }
        return new InsulinTakenEntry(parseTime(cursor.getString(0)),
                TakeInsulinReadModel.InsulinType.BOLUS,cursor.getFloat(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT)));
    }

    @Override
    public List<IInsulinTakenEntry> getAllEntries(Calendar from, Calendar to) {
        Cursor cursor = write.rawQuery("Select * from "+
                InsulinTakenContract.ContentsDefinition.TABLE_NAME+" where "+
                InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME+">=? AND "+
                InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME+"<=?",
                new String[]{formatDateTime(from),formatDateTime(to)});
        List<IInsulinTakenEntry> entries =new ArrayList<>();
        TakeInsulinReadModel.InsulinType type;
        while(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_BASAL)).equals("TRUE")){
                type= TakeInsulinReadModel.InsulinType.BASAL;
            }else{
                type= TakeInsulinReadModel.InsulinType.BOLUS;
            }
            entries.add(new InsulinTakenEntry(
                    parseTime(cursor.getString(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME))),
                    type,cursor.getFloat(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT))));
        }
        return entries;
    }


    private String formatDateTime(Calendar timestamp) {
        return dateFormat.format(timestamp.getTime());
    }

    private Calendar parseTime(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            return null;
        }
        return c;
    }



}
