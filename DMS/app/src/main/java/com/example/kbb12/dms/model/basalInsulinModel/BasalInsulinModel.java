package com.example.kbb12.dms.model.basalInsulinModel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/02/2017.
 */
public class BasalInsulinModel implements IBasalInsulinModel {

    private SQLiteDatabase write;

    public BasalInsulinModel(SQLiteDatabase write) {
        this.write=write;
    }


    @Override
    public void addEntry(BasalInsulinEntry entry,String brandName,int day,int month,int year) throws DuplicateDoseException {
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"=?",new String[]{formatTime(entry.getHour(),entry.getMinute())});
        if(cursor.moveToNext()){
            throw new DuplicateDoseException();
        }
        cursor.close();
        write.execSQL("INSERT INTO " +
                BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE + ", " +
                BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE + ", " +
                BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE + ", "+
                BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE + ", "+
                BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FIVE_TITLE +
                ")VALUES(\"" + brandName + "\", \"" +
                formatTime(entry.getHour(),entry.getMinute()) + "\", " + entry.getDose() + ", "+
                entry.getDose()+", \"" + formatDate(day, month, year) + "\")");
    }

    @Override
    public List<BasalInsulinEntry> getEntries(boolean usingImprovement){
        List<BasalInsulinEntry> entries = new ArrayList<>();
        Cursor cursor = write.rawQuery("Select * from "+
                BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        while(cursor.moveToNext()){
            String time=cursor.getString(1);
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute =Integer.parseInt(time.substring(3,5));
            if(usingImprovement) {
                entries.add(new BasalInsulinDose(hour, minute, cursor.getFloat(2)));
            }else {
                entries.add(new BasalInsulinDose(hour, minute, cursor.getFloat(3)));
            }
        }
        return entries;
    }

    @Override
    public void clearValues(){
        write.delete(BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=\"%\"",new String[]{});
    }

    @Override
    public BasalInsulinEntry getLatestBefore(int hour, int minute,boolean usingImprovement) {
        //Get all the entries less than the given time
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"<=?",new String[]{formatTime(hour, minute)});
        BasalInsulinDose max=null;
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
                if(usingImprovement) {
                    max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(2));
                }else{
                    max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(3));
                }
            }
        }
        cursor.close();
        if(null==max){
            //No times before that given
            max=getLatest(usingImprovement);
        }
        return max;
    }

    @Override
    public Calendar getLastTakenAprox(BasalInsulinEntry mostRecent) {
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"=?",new String[]{formatTime(mostRecent.getHour(),mostRecent.getMinute())});
        if(cursor.moveToNext()){
            Calendar lastTakenAprox=Calendar.getInstance();
            String dateTaken = cursor.getString(4);
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
        args.put(BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FIVE_TITLE, formatDate(day, month, year));
        String strFilter = BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"<=?";
        write.update(BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,args,strFilter,new String[] {formatTime(hour,minute)});
    }

    private BasalInsulinDose getLatest(boolean usingImprovement){
        Cursor cursor=write.rawQuery("Select "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+", max("+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+"), "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE+" from "+ BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        BasalInsulinDose max=null;
        if(cursor.moveToNext()){
            String time=cursor.getString(1);
            int tempHour = Integer.parseInt(time.substring(0, 2));
            int tempMinute =Integer.parseInt(time.substring(3,5));
            if(usingImprovement) {
                max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(2));
            }else{
                max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(3));
            }
        }
        return max;
    }

    private String formatDate(int day,int month,int year){
        return year+"-"+String.format("%02d",month)+"-"+String.format("%02d",day);
    }

    private String formatTime(int hour,int minute){
        return String.format("%02d",hour).toString()+":"+String.format("%02d", minute);
    }

    public void log(){
        Cursor cursor =write.rawQuery("Select * from "+
                BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        while (cursor.moveToNext()){
            Log.d("DMS MODEL BASAL",cursor.getString(0)+","+cursor.getString(1)+","+
                    cursor.getDouble(2)+","+cursor.getDouble(3)+", "+cursor.getString(4));
        }
    }


}
