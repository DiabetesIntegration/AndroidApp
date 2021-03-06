package com.example.kbb12.dms.database.basalInsulinModel;

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
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContract.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME +"=?",new String[]{formatTime(entry.getHour(),entry.getMinute())});
        if(cursor.moveToNext()){
            throw new DuplicateDoseException();
        }
        cursor.close();
        write.execSQL("INSERT INTO " +
                BasalInsulinModelContract.ContentsDefinition.TABLE_NAME + " (" +
                BasalInsulinModelContract.ContentsDefinition.COLUMN_INSULIN_NAME + ", " +
                BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME + ", " +
                BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE + ", "+
                BasalInsulinModelContract.ContentsDefinition.COLUMN_ORIG_DOSE + ", "+
                BasalInsulinModelContract.ContentsDefinition.COLUMN_DATE_LAST_TAKEN +
                ")VALUES(\"" + brandName + "\", \"" +
                formatTime(entry.getHour(),entry.getMinute()) + "\", " + entry.getDose() + ", "+
                entry.getDose()+", \"" + formatDate(day, month, year) + "\")");
    }

    @Override
    public List<BasalInsulinEntry> getEntries(boolean usingImprovement){
        List<BasalInsulinEntry> entries = new ArrayList<>();
        Cursor cursor = write.rawQuery("Select * from "+
                BasalInsulinModelContract.ContentsDefinition.TABLE_NAME,new String[]{});
        while(cursor.moveToNext()){
            String time=
                    cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME));
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute =Integer.parseInt(time.substring(3,5));
            if(usingImprovement) {
                entries.add(new BasalInsulinDose(hour, minute,
                        cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE))));
            }else {
                entries.add(new BasalInsulinDose(hour, minute,
                        cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_ORIG_DOSE))));
            }
        }
        return entries;
    }

    @Override
    public void clearValues(){
        write.delete(BasalInsulinModelContract.ContentsDefinition.TABLE_NAME, BasalInsulinModelContract.ContentsDefinition.COLUMN_INSULIN_NAME +"=\"%\"",new String[]{});
    }

    @Override
    public BasalInsulinEntry getLatestBefore(int hour, int minute,boolean usingImprovement) {
        //Get all the entries less than the given time
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContract.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME +"<=?",new String[]{formatTime(hour, minute)});
        BasalInsulinDose max=null;
        String maxTime="      ";//will be less than any other string
        String time;
        Integer tempHour;
        Integer tempMinute;
        while(cursor.moveToNext()){
            time=cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME));
            if(time.compareTo(maxTime)>=0){
                maxTime=time;
                tempHour = Integer.parseInt(time.substring(0, 2));
                tempMinute =Integer.parseInt(time.substring(3,5));
                if(usingImprovement) {
                    max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE)));
                }else{
                    max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_ORIG_DOSE)));
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
        Cursor cursor =write.rawQuery("Select * from "+ BasalInsulinModelContract.ContentsDefinition.TABLE_NAME+" where "+ BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME +"=?",new String[]{formatTime(mostRecent.getHour(),mostRecent.getMinute())});
        if(cursor.moveToNext()){
            Calendar lastTakenAprox=Calendar.getInstance();
            String dateTaken = cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_DATE_LAST_TAKEN));
            int year = Integer.parseInt(dateTaken.substring(0, 4));
            int month = Integer.parseInt(dateTaken.substring(5,7));
            int day = Integer.parseInt(dateTaken.substring(8,10));
            lastTakenAprox.set(Calendar.YEAR,year);
            lastTakenAprox.set(Calendar.MONTH,month);
            lastTakenAprox.set(Calendar.DAY_OF_MONTH,day);
            String timeSet=cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME));
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
        args.put(BasalInsulinModelContract.ContentsDefinition.COLUMN_DATE_LAST_TAKEN, formatDate(day, month, year));
        String strFilter = BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME +"<=?";
        write.update(BasalInsulinModelContract.ContentsDefinition.TABLE_NAME,args,strFilter,new String[] {formatTime(hour,minute)});
    }

    @Override
    public void improve(BasalInsulinEntry entry, Float improvement) {
        ContentValues args = new ContentValues();
        args.put(BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE,entry.getDose()+improvement);
        String strFilter = BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME+"=?";
        write.update(BasalInsulinModelContract.ContentsDefinition.TABLE_NAME,args,strFilter,
                new String[] {formatTime(entry.getHour(),entry.getMinute())});
    }

    private BasalInsulinDose getLatest(boolean usingImprovement){
        Cursor cursor=write.rawQuery("Select "+ BasalInsulinModelContract.ContentsDefinition.COLUMN_INSULIN_NAME +", max("+ BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME +"), "+ BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE +" from "+ BasalInsulinModelContract.ContentsDefinition.TABLE_NAME,new String[]{});
        BasalInsulinDose max=null;
        if(cursor.moveToNext()){
            String time=cursor.getString(1);
            int tempHour = Integer.parseInt(time.substring(0, 2));
            int tempMinute =Integer.parseInt(time.substring(3,5));
            if(usingImprovement) {
                max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE)));
            }else{
                max = new BasalInsulinDose(tempHour, tempMinute, cursor.getFloat(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_ORIG_DOSE)));
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
                BasalInsulinModelContract.ContentsDefinition.TABLE_NAME,new String[]{});
        while (cursor.moveToNext()){
            Log.d("DMS MODEL BASAL",
                    cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_INSULIN_NAME))+
                            ","+cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_TIME))
                            +","+cursor.getDouble(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_IMPROVED_DOSE))+
                            ","+cursor.getDouble(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_ORIG_DOSE))+
                            ", "+cursor.getString(cursor.getColumnIndex(BasalInsulinModelContract.ContentsDefinition.COLUMN_DATE_LAST_TAKEN)));
        }
    }


}
