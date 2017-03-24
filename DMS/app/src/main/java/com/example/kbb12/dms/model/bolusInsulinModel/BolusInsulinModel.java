package com.example.kbb12.dms.model.bolusInsulinModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class BolusInsulinModel implements IBolusInsulinModel {

    private SQLiteDatabase write;

    public BolusInsulinModel(SQLiteDatabase write) {
        this.write=write;
    }

    @Override
    public void createInsulinToCarbModel(double breakInsulin, double breakCarbs, double lunInsulin,
                                         double lunCarbs, double dinInsulin, double dinCarbs,
                                         double nighInsulin, double nighCarbs) {
        fillICRValues(6, 10, breakInsulin / breakCarbs);
        fillICRValues(11,16,lunInsulin/lunCarbs);
        fillICRValues(17, 22, dinInsulin / dinCarbs);
        fillICRValues(23, 5, nighInsulin / nighCarbs);
    }

    @Override
    public void createInsulinToCarbModel(double ICR) {
        fillICRValues(0,23,ICR);
    }

    @Override
    public void createInsulinSensitivityModel(double ISF) {
        fillISFValues(0, 23, ISF);
    }
    @Override
    public void createInsulinSensitivityModel(double mornISF,double afteISF,double eveISF,double nighISF) {
        fillISFValues(6,11,mornISF);
        fillISFValues(12,17,afteISF);
        fillISFValues(18,22,eveISF);
        fillISFValues(23,5,nighISF);
    }

    private void fillISFValues(int startHour,int endHour,double ISFvalue){
        if(startHour>endHour){
            fillISFValues(startHour,23,ISFvalue);
            fillISFValues(0,endHour,ISFvalue);
            return;
        }
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=?";
        ContentValues args= new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE, ISFvalue);
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_FIVE_TITLE, ISFvalue);
        for(int i=startHour;i<(endHour+1);i++) {
            write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, args,
                    strFilter, new String[]{String.format("%2d:00", i)});
        }
    }
    
    private void fillICRValues(int startHour,int endHour,double ICRvalue){
        if(startHour>endHour){
            fillICRValues(startHour, 23, ICRvalue);
            fillICRValues(0, endHour, ICRvalue);
            return;
        }
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=?";
        ContentValues args= new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE, ICRvalue);
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE, ICRvalue);
        for(int i=startHour;i<(endHour+1);i++) {
            write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, args,
                    strFilter, new String[]{String.format("%2d:00", i)});
        }
    }

    public void log(){
        Cursor cursor =write.rawQuery("Select * from "+
                BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        while (cursor.moveToNext()){
            Log.d("DMS MODEL BOLUS", cursor.getString(0) + "," + cursor.getFloat(1) + "," +
                    cursor.getFloat(2) + "," + cursor.getFloat(3)+","+cursor.getFloat(4));
        }
    }

    @Override
    public Float getICRValue(Calendar time,boolean usingImprovements) {
        int hour =time.get(Calendar.HOUR);
        Cursor cursor = write.rawQuery("Select * from "+
                BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+
                " where "+BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=?",
                new String[]{String.format("%2d:00",hour)});
        cursor.moveToNext();
        if(usingImprovements) {
            return cursor.getFloat(1);
        }else{
            return cursor.getFloat(2);
        }
    }

    @Override
    public Float getISFValue(Calendar time,boolean usingImprovements) {
        int hour =time.get(Calendar.HOUR);
        Cursor cursor = write.rawQuery("Select * from "+
                        BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+
                        " where "+BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE+"=?",
                new String[]{String.format("%2d:00",hour)});
        cursor.moveToNext();
        if(usingImprovements) {
            return cursor.getFloat(3);
        }else{
            return cursor.getFloat(4);
        }
    }

}
