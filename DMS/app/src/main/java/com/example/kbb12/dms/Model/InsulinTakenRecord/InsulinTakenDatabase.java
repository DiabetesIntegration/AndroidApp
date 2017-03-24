package com.example.kbb12.dms.model.insulinTakenRecord;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class InsulinTakenDatabase extends SQLiteOpenHelper implements InsulinTakenRecord {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" +
                    InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE + " DATE," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE + " BOOLEAN," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    "PRIMARY KEY( "+ InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE+", "+InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InsulinTakenContract.ContentsDefinition.TABLE_NAME;

    private SQLiteDatabase write;

    public InsulinTakenDatabase(Context context, int versionNumber) {
        super(context, InsulinTakenContract.ContentsDefinition.TABLE_NAME, null, versionNumber);
        write=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void addEntry(int day,int month,int year,int hour,int minute,Double dose,boolean basalInsulin){
        String trueOrFalse;
        if(basalInsulin){
            trueOrFalse="TRUE";
        }else{
            trueOrFalse="FALSE";
        }
        write.execSQL("INSERT INTO " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" + InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE + ")VALUES(\"" + formatDateTime(year,month,day,hour,minute) + "\", '" + trueOrFalse + "', " + dose + ")");
    }

    @Override
    public IInsulinTakenEntry getMostRecentBolus() {
        /*
        Get the entry with the maximum time which isn't Basal insulin.
         */
        Cursor cursor = write.rawQuery("Select MAX("
                +InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE+"),"+
                InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE+", "+
                InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE+
                " from "+InsulinTakenContract.ContentsDefinition.TABLE_NAME+
                " where "+InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE+"=?",
                new String[]{"FALSE"});
        if(!cursor.moveToNext()||cursor.getString(0)==null) {
            return null;
        }
        return new InsulinTakenEntry(parseTime(cursor.getString(0)),
                TakeInsulinReadModel.InsulinType.BOLUS,cursor.getFloat(2));
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
