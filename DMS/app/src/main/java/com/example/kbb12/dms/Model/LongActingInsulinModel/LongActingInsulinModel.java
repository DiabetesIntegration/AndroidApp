package com.example.kbb12.dms.Model.LongActingInsulinModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/02/2017.
 */
public class LongActingInsulinModel implements ILongActingInsulinModel {


    private SQLiteDatabase write;

    public LongActingInsulinModel(Context context, int versionNumber,String name) {
        LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME=name;
        SQLiteOpenHelper builder = new DatabaseBuilder(context,versionNumber);
        write=builder.getWritableDatabase();
    }

    @Override
    public void addEntry(LongActingInsulinEntry entry,String brandName,int day,int month,int year) throws DuplicateDoseException {
        Cursor cursor =write.rawQuery("Select * from "+ LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"=?",new String[]{formatTime(entry.getHour(),entry.getMinute())});
        if(cursor.moveToNext()){
            throw new DuplicateDoseException();
        }
        cursor.close();
        write.execSQL("INSERT INTO " + LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" + LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE + ", " + LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE + ", " + LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE + ", "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE + ")VALUES(\"" + brandName + "\", \"" + formatTime(entry.getHour(),entry.getMinute()) + "\", " + entry.getDose() + ", \"" + formatDate(day, month, year) + "\")");
    }

    @Override
    public List<LongActingInsulinEntry> getEntries(){
        List<LongActingInsulinEntry> entries = new ArrayList<>();
        Cursor cursor = write.rawQuery("Select * from "+ LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
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
        write.delete(LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=\"%\"",new String[]{});
    }

    @Override
    public LongActingInsulinEntry getLatestBefore(int hour, int minute) {
        //Get all the entries less than the given time
        Cursor cursor =write.rawQuery("Select * from "+ LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"<?",new String[]{formatTime(hour, minute)});
        LongActingInsulinDose max=null;
        String maxTime="      ";//will be less than any other string
        String time;
        Integer tempHour;
        Integer tempMinute;
        while(cursor.moveToNext()){
            time=cursor.getString(1);
            if(time.compareTo(maxTime)>=0){
                maxTime=time;
                tempHour = Integer.parseInt(time.substring(0, 2));
                tempMinute =Integer.parseInt(time.substring(3,5));
                max=new LongActingInsulinDose(tempHour,tempMinute,cursor.getFloat(2));
            }
        }
        cursor.close();
        if(null==max){
            //No times before that given
            max=getLatest();
        }
        return max;
    }

    @Override
    public Calendar getLastTakenAprox(LongActingInsulinEntry mostRecent) {
        Cursor cursor =write.rawQuery("Select * from "+ LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"=?",new String[]{formatTime(mostRecent.getHour(),mostRecent.getMinute())});
        if(cursor.moveToNext()){
            Calendar lastTakenAprox=Calendar.getInstance();
            String dateTaken = cursor.getString(3);
            int year = Integer.parseInt(dateTaken.substring(0, 4));
            int month = Integer.parseInt(dateTaken.substring(5,7));
            int day = Integer.parseInt(dateTaken.substring(8,10));
            lastTakenAprox.set(Calendar.YEAR,year);
            lastTakenAprox.set(Calendar.MONTH,month);
            lastTakenAprox.set(Calendar.DAY_OF_MONTH,day);
            String timeSet=cursor.getString(1);
            int hour = Integer.parseInt(timeSet.substring(0, 2));
            int minute = Integer.parseInt(timeSet.substring(3,5));
            lastTakenAprox.set(Calendar.HOUR_OF_DAY,hour);
            lastTakenAprox.set(Calendar.MINUTE,minute);
            return lastTakenAprox;
        }
        return null;
    }

    @Override
    public void allTakenBefore(Integer hour, Integer minute, int day, int month, int year) {
        ContentValues args = new ContentValues();
        args.put(LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE, formatDate(day, month, year));
        String strFilter = LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"=?";
        write.update(LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,args,strFilter,new String[] {formatTime(hour,minute)});
    }

    private LongActingInsulinDose getLatest(){
        Cursor cursor=write.rawQuery("Select "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+", max("+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"), "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE+" from "+ LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        LongActingInsulinDose max=null;
        if(cursor.moveToNext()){
            String time=cursor.getString(1);
            int tempHour = Integer.parseInt(time.substring(0, 2));
            int tempMinute =Integer.parseInt(time.substring(3,5));
            max=new LongActingInsulinDose(tempHour,tempMinute,cursor.getFloat(2));
        }
        return max;
    }

    private String formatDate(int day,int month,int year){
        return year+"-"+String.format("%02d",month)+"-"+String.format("%02d",day);
    }

    private String formatTime(int hour,int minute){
        return String.format("%02d",hour).toString()+":"+String.format("%02d", minute);
    }


}
