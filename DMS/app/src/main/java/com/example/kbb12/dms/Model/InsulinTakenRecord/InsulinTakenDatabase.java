package com.example.kbb12.dms.model.insulinTakenRecord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class InsulinTakenDatabase implements InsulinTakenRecord {

    private SQLiteDatabase write;

    public InsulinTakenDatabase(SQLiteDatabase write) {
        this.write=write;
    }

    @Override
    public void addEntry(int day,int month,int year,int hour,int minute,Double dose,boolean basalInsulin){
        String trueOrFalse;
        if(basalInsulin){
            trueOrFalse="TRUE";
        }else{
            trueOrFalse="FALSE";
        }
        write.execSQL("INSERT INTO " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" + InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_BASAL + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT + ")VALUES(\"" + formatDateTime(year,month,day,hour,minute) + "\", '" + trueOrFalse + "', " + dose + ")");
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
        if(!cursor.moveToNext()||cursor.getString(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME))==null) {
            return null;
        }
        return new InsulinTakenEntry(parseTime(cursor.getString(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_DATE_TIME))),
                TakeInsulinReadModel.InsulinType.BOLUS,cursor.getFloat(cursor.getColumnIndex(InsulinTakenContract.ContentsDefinition.COLUMN_AMOUNT)));
    }

    private Calendar parseTime(String dateTime){
        Calendar time = Calendar.getInstance();
        time.set(Calendar.YEAR,Integer.parseInt(dateTime.substring(0,4)));
        time.set(Calendar.MONTH,Integer.parseInt(dateTime.substring(5,7)));
        time.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateTime.substring(8,10)));
        time.set(Calendar.HOUR,Integer.parseInt(dateTime.substring(11,13)));
        time.set(Calendar.MINUTE,Integer.parseInt(dateTime.substring(14,16)));
        return time;
    }

    private String formatDateTime(int year,int month,int day,int hour,int minute){
        return String.format("%04d-%02d-%02d-%02d-%02d",year,month,day,hour,minute);
    }


}
