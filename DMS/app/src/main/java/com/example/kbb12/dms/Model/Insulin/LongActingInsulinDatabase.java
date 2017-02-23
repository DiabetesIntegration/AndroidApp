package com.example.kbb12.dms.Model.Insulin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 07/02/2017.
 */
public class LongActingInsulinDatabase extends SQLiteOpenHelper implements ILongActingInsulinDatabase {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME + " (" +
                    LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_ONE_TITLE + " VARCHAR(1000)," +
                    LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_TWO_TITLE + " VARCHAR(5)," +
                    LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    "PRIMARY KEY( "+ LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME;

    private SQLiteDatabase write;

    public LongActingInsulinDatabase(Context context,int versionNumber) {
        super(context, LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME, null, versionNumber);
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
    public void addEntry(LongActingInsulinEntry entry,String brandName) throws DuplicateDoseException {
        Cursor cursor =write.rawQuery("Select * from "+ LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME+" where "+ LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_TWO_TITLE+"=?",new String[]{entry.getHour().toString()+":"+entry.getMinute().toString()});
        if(cursor.moveToNext()){
            throw new DuplicateDoseException();
        }
        cursor.close();
        write.execSQL("INSERT INTO " + LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME + " (" + LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_ONE_TITLE + ", " + LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_TWO_TITLE + ", " + LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_THREE_TITLE +")VALUES(\"" + brandName + "\", \"" + String.format("%02d", entry.getHour()) + ":" + String.format("%02d",entry.getMinute()) + "\", " + entry.getDose() + ")");
        return;
    }

    @Override
    public List<LongActingInsulinEntry> getEntries(){
        List<LongActingInsulinEntry> entries = new ArrayList<>();
        Cursor cursor = write.rawQuery("Select * from "+LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME,new String[]{});
        while(cursor.moveToNext()){
            String time=cursor.getString(1);
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute =Integer.parseInt(time.substring(3,5));
            entries.add(new LongActingInsulinDose(hour,minute,cursor.getFloat(2)));
        }
        return entries;
    }

    @Override
    public void clearValues(){
        write.delete(LongActingInsulinDatabaseContract.ContentsDefinition.TABLE_NAME,LongActingInsulinDatabaseContract.ContentsDefinition.COLUMN_ONE_TITLE+"=\"%\"",new String[]{});
    }


}
