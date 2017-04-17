package com.example.kbb12.dms.model.improvementEngine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kbb12.dms.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.database.activityRecord.IFitnessEntry;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.database.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.DatabaseBuilder;
import com.example.kbb12.dms.database.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 25/03/2017.
 */

public class BasalImprovement extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseBuilder db = new DatabaseBuilder(context);
        InsulinTakenRecord insulinTakenRecord=db.getInsulinTakenRecord();
        IBasalInsulinModel basalInsulinModel=db.getBasalInsulinModel();
        BGRecord historyBGModel = db.getHistoryBGRecord();
        ActivityRecord activityRecord =db.getActivityRecord();
        TimeCarbEatenRecord carbRecord = db.getTimeCarbEatenRecord();
        Calendar eightHoursAgo =Calendar.getInstance();
        eightHoursAgo.add(Calendar.HOUR,-8);
        Calendar thirtyTwoHoursAgo =Calendar.getInstance();
        thirtyTwoHoursAgo.add(Calendar.HOUR,-32);
        List<IInsulinTakenEntry> insulinTaken=insulinTakenRecord.getAllEntries(thirtyTwoHoursAgo,eightHoursAgo);
        BasalInsulinEntry matchedDose;
        for(IInsulinTakenEntry entry:insulinTaken){
            if(entry.getType().equals(TakeInsulinReadModel.InsulinType.BOLUS)){
                continue;
            }
            matchedDose=findMatchingRecommendation(entry,basalInsulinModel);
            if(matchedDose==null){
                continue;
            }
            Calendar eightHourAfter=(Calendar) entry.getTime().clone();
            eightHourAfter.add(Calendar.HOUR,8);
            Calendar hourBeforeTaken =(Calendar) entry.getTime().clone();
            hourBeforeTaken.add(Calendar.HOUR,-1);
            List<IInsulinTakenEntry> insulinTakenDuringTest =
                        insulinTakenRecord.getAllEntries(hourBeforeTaken,eightHourAfter);
            if(insulinTakenDuringTest.size()>1){
                continue;
            }
            List<Double> carbsEatenDuringTest = carbRecord.getAllEntries(hourBeforeTaken,
                                                            eightHourAfter);
            carbsEatenDuringTest.remove(null);
            if(carbsEatenDuringTest.size()>0){
                continue;
            }
            List<IFitnessEntry> activityDuringTest = activityRecord.getAllEntries(hourBeforeTaken,
                                                            eightHourAfter);
            if(activityDuringTest.size()>0){
                continue;
            }
            List<BGReading> bgReadings=historyBGModel.getReadingsBetween(hourBeforeTaken,eightHourAfter);
                //Checking there is actually a fair amount of readings before improving based on them.
            if(bgReadings.size()<30){
                continue;
            }
            BGReading max=null;
            BGReading min=null;
            BGReading closest=null;
            for(BGReading current:bgReadings){
                if(max==null||max.getReading()<current.getReading()){
                    max=current;
                }
                if(min==null||min.getReading()>current.getReading()){
                    min=current;
                }
                if(closest==null||
                        Math.abs(entry.getTime().getTimeInMillis()-closest.getTime().getTimeInMillis())>
                                Math.abs(entry.getTime().getTimeInMillis()-current.getTime().getTimeInMillis())){
                    closest=current;
                }
            }
            if(max.getReading()>closest.getReading()+1.5){
                basalInsulinModel.improve(matchedDose,(float)0.5);
            }else if(min.getReading()<closest.getReading()-1.5){
                basalInsulinModel.improve(matchedDose,(float) -0.5);
            }
        }
    }

    private BasalInsulinEntry findMatchingRecommendation(IInsulinTakenEntry entry,
                                                         IBasalInsulinModel basalInsulinModel){
        Calendar hourBeforeTaken = (Calendar) entry.getTime().clone();
        hourBeforeTaken.add(Calendar.HOUR,-1);
        Calendar hourAfterTaken = (Calendar) entry.getTime().clone();
        hourAfterTaken.add(Calendar.HOUR,1);
        List<BasalInsulinEntry> basalModelEntries=basalInsulinModel.getEntries(true);
        BasalInsulinEntry matchedDose=null;
        for(BasalInsulinEntry basalInsulinEntry:basalModelEntries){
            if(basalEntryAfter(hourBeforeTaken,basalInsulinEntry)&&
                    basalEntryBefore(hourAfterTaken,basalInsulinEntry)&&
                    basalInsulinEntry.getDose()==(double) entry.getAmount()){
                matchedDose=basalInsulinEntry;
                break;
            }
        }
        return matchedDose;
    }

    private boolean basalEntryAfter(Calendar time,BasalInsulinEntry entry){
        if(entry.getHour()>time.get(Calendar.HOUR)){
            return true;
        }
        if(((Integer)time.get(Calendar.HOUR)).equals(entry.getHour())){
            if(entry.getMinute()>entry.getMinute()){
                return true;
            }
        }
        return false;
    }

    private boolean basalEntryBefore(Calendar time,BasalInsulinEntry entry){
        if(entry.getHour()<time.get(Calendar.HOUR)){
            return true;
        }
        if(((Integer)time.get(Calendar.HOUR)).equals(entry.getHour())){
            if(entry.getMinute()<entry.getMinute()){
                return true;
            }
        }
        return false;
    }
}
