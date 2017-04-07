package com.example.kbb12.dms.model.database.bolusInsulinModel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by kbb12 on 07/02/2017.
 */
public class BolusInsulinModel implements IBolusInsulinModel {

    private SQLiteDatabase write;

    private static final double STEP_DOWN=0.6;
    private static final double TWO_STEP_DOWN=0.13;

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
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +"=?";
        ContentValues args= new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF, ISFvalue);
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ISF, ISFvalue);
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
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +"=?";
        ContentValues args= new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ICR, ICRvalue);
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ICR, ICRvalue);
        for(int i=startHour;i<(endHour+1);i++) {
            write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, args,
                    strFilter, new String[]{String.format("%2d:00", i)});
        }
    }

    public void log(){
        Cursor cursor =write.rawQuery("Select * from "+
                BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,new String[]{});
        while (cursor.moveToNext()){
            Log.d("DMS MODEL BOLUS",
                    cursor.getString(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME)) +
                            "," + cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ICR)) +
                            "," + cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ICR)) +
                            "," + cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF))+
                            "," + cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ISF)));
        }
    }

    @Override
    public Float getICRValue(Calendar time,boolean usingImprovements) {
        int hour =time.get(Calendar.HOUR);
        Cursor cursor = write.rawQuery("Select * from "+
                BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+
                " where "+BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +"=?",
                new String[]{String.format("%2d:00",hour)});
        cursor.moveToNext();
        if(usingImprovements) {
            return cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ICR));
        }else{
            return cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ICR));
        }
    }

    @Override
    public Float getISFValue(Calendar time,boolean usingImprovements) {
        int hour =time.get(Calendar.HOUR);
        Cursor cursor = write.rawQuery("Select * from "+
                        BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME+
                        " where "+BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +"=?",
                new String[]{String.format("%2d:00",hour)});
        cursor.moveToNext();
        if(usingImprovements) {
            return cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF));
        }else{
            return cursor.getFloat(cursor.getColumnIndex(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ISF));
        }
    }

    @Override
    public void improveICRValue(Calendar time, double percentageChange) {
        if(percentageChange>0.1){
            //Limit percentageChange to 10% for safety
            percentageChange=0.1;
        }
        Float currentValue = getICRValue(time,true);
        Float improvement = currentValue*(float)percentageChange;
        ContentValues args = new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ICR,currentValue+improvement);
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME+"=";
        write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,args,strFilter,
                new String[] {String.format("%2d:00",time.get(Calendar.HOUR))});
    }

    @Override
    public void improveISFValue(Calendar time, double percentageChange) {
        if(percentageChange>0.1){
            //Limit percentageChange to 10% for safety
            percentageChange=0.1;
        }
        Float currentValue = getISFValue(time,true);
        Float improvement = currentValue*(float)percentageChange;
        ContentValues args = new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF,currentValue+improvement);
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME+"=";
        write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,args,strFilter,
                new String[] {String.format("%2d:00",time.get(Calendar.HOUR))});
        Calendar temp =(Calendar) time.clone();
        temp.add(Calendar.HOUR,-1);
        cascadeISFImprove(temp, percentageChange*STEP_DOWN);
        temp.add(Calendar.HOUR,-1);
        cascadeISFImprove(temp,percentageChange*TWO_STEP_DOWN);
        temp =(Calendar) time.clone();
        temp.add(Calendar.HOUR,1);
        cascadeISFImprove(temp, percentageChange*STEP_DOWN);
        temp.add(Calendar.HOUR,1);
        cascadeISFImprove(temp,percentageChange*TWO_STEP_DOWN);
        return;
    }

    private void cascadeISFImprove(Calendar time,double percentageChange){
        if(percentageChange>0.1){
            //Limit percentageChange to 10% for safety
            percentageChange=0.1;
        }
        Float currentValue = getISFValue(time,true);
        Float improvement = currentValue*(float)percentageChange;
        ContentValues args = new ContentValues();
        args.put(BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF,currentValue+improvement);
        String strFilter = BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME+"=";
        write.update(BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME,args,strFilter,
                new String[] {String.format("%2d:00",time.get(Calendar.HOUR))});
    }



}
