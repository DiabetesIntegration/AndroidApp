package com.example.kbb12.dms.model.improvementEngine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kbb12.dms.model.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.model.database.activityRecord.IFitnessEntry;
import com.example.kbb12.dms.model.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.model.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.model.database.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.model.database.DatabaseBuilder;
import com.example.kbb12.dms.model.database.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.model.database.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.model.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by kbb12 on 29/03/2017.
 */

public class BolusImprovement extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseBuilder db = new DatabaseBuilder(context);
        IBolusInsulinModel bolusInsulinModel = db.getBolusInsulinModel();
        InsulinTakenRecord insulinTakenRecord=db.getInsulinTakenRecord();
        TimeCarbEatenRecord carbRecord = db.getTimeCarbEatenRecord();
        BGRecord currentBGRecord = db.getCurrentBGRecord();
        BGRecord historyBGRecord = db.getHistoryBGRecord();
        Calendar fourHoursAgo =Calendar.getInstance();
        fourHoursAgo.add(Calendar.HOUR,-4);
        Calendar twentyEightHoursAgo =Calendar.getInstance();
        twentyEightHoursAgo.add(Calendar.HOUR,-28);
        List<IInsulinTakenEntry> insulinTaken=insulinTakenRecord.getAllEntries(twentyEightHoursAgo,fourHoursAgo);
        List<Double> carbsInTest;
        List<Double> carbsInRecommendation;
        Calendar thirtyMinutesBeforeTaken;
        Calendar hourBeforeTaken;
        Calendar fourHourAfterTaken;
        ActivityRecord activityRecord =db.getActivityRecord();
        List<IFitnessEntry> activityInTest;
        double carbRec;
        double corRec;
        int totalCarbs;
        BGReading bgAtTime;
        BGReading bgAfterTest;
        Calendar temp;
        Calendar endTest;
        double percentageOff;
        for(IInsulinTakenEntry entry:insulinTaken){
            if(entry.getType().equals(TakeInsulinReadModel.InsulinType.BASAL)){
                continue;
            }
            thirtyMinutesBeforeTaken=(Calendar) entry.getTime().clone();
            thirtyMinutesBeforeTaken.add(Calendar.MINUTE,-31);
            carbsInRecommendation=carbRecord.getAllEntries(thirtyMinutesBeforeTaken,entry.getTime());
            hourBeforeTaken=(Calendar) entry.getTime().clone();
            hourBeforeTaken.add(Calendar.HOUR,-1);
            fourHourAfterTaken=(Calendar) entry.getTime().clone();
            fourHourAfterTaken.add(Calendar.HOUR,4);
            carbsInTest=carbRecord.getAllEntries(hourBeforeTaken,fourHourAfterTaken);
            if(carbsInTest.size()>carbsInRecommendation.size()){
                continue;
            }
            activityInTest=activityRecord.getAllEntries(hourBeforeTaken,fourHourAfterTaken);
            if(activityInTest.size()>0){
                continue;
            }
            totalCarbs=0;
            for(Double current:carbsInRecommendation){
                totalCarbs+=current;
            }
            carbRec=totalCarbs*bolusInsulinModel.getICRValue(entry.getTime(),true);
            corRec=0;
            bgAtTime=currentBGRecord.getMostRecentReadingBefore(entry.getTime());
            temp=(Calendar)entry.getTime().clone();
            temp.add(Calendar.MINUTE,-15);
            if(bgAtTime.getTime().before(temp)){
                //Didn't have enough BG data to calculate recommendation
                continue;
            }
            if(bgAtTime.getReading()>7||bgAtTime.getReading()<5){
                corRec=(bgAtTime.getReading()-6.0)/bolusInsulinModel.getISFValue(entry.getTime(),true);
            }
            if(abs(entry.getAmount()-(carbRec+corRec))>0.49){
                //Didn't follow recommendation so don't evaluate recommendation
                continue;
            }
            endTest=(Calendar) entry.getTime().clone();
            endTest.add(Calendar.HOUR,4);
            bgAfterTest=historyBGRecord.getMostRecentReadingBefore(endTest);
            temp=(Calendar) endTest.clone();
            temp.add(Calendar.MINUTE,-30);
            if(bgAfterTest.getTime().before(temp)){
                //Not accurate enough BG data to perform comparison.
                continue;
            }
            if(bgAfterTest.getReading()>7.0||bgAfterTest.getReading()<5.0){
                percentageOff=bgAfterTest.getReading()/6.0-1;
                //Improve individual models on same ratio as they contributed to the recommendation
                bolusInsulinModel.improveISFValue(entry.getTime(),percentageOff*(Math.abs(corRec)/(Math.abs(corRec)+carbRec)));
                bolusInsulinModel.improveICRValue(entry.getTime(),percentageOff*(carbRec/(Math.abs(corRec)+carbRec)));
            }
        }
    }


}
