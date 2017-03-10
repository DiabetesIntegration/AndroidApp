package com.example.kbb12.dms.model.insulinTakenRecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class InsulinTakenDatabase extends SQLiteOpenHelper implements InsulinTakenRecord {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" +
                    InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE + " DATE," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE + " VARCHAR(5)," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE + " BOOLEAN," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_FOUR_TITLE + " FLOAT," +
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
        write.execSQL("INSERT INTO " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" + InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE + ", " + InsulinTakenContract.ContentsDefinition.COLUMN_FOUR_TITLE + ")VALUES(\"" + formatDate(day,month,year) + "\", \"" + formatTime(hour,minute) + "\", '" + trueOrFalse + "', " + dose + ")");
    }

    private String formatDate(int day,int month,int year){
        return year+"-"+String.format("%02d",month)+"-"+String.format("%02d",day);
    }

    private String formatTime(int hour,int minute){
        return String.format("%02d",hour).toString()+":"+String.format("%02d", minute);
    }


}
