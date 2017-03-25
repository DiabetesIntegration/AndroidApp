package com.example.kbb12.dms.model.improvementEngine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.model.database.DatabaseBuilder;
import com.example.kbb12.dms.model.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 25/03/2017.
 */

public class basalImprovement extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseBuilder db = new DatabaseBuilder(context);
        InsulinTakenRecord insulinTakenRecord=db.getInsulinTakenRecord();
        IBasalInsulinModel basalInsulinModel=db.getBasalInsulinModel();
        Calendar eightHoursAgo =Calendar.getInstance();
        eightHoursAgo.add(Calendar.HOUR,-8);
        Calendar thirtyTwoHoursAgo =Calendar.getInstance();
        thirtyTwoHoursAgo.add(Calendar.HOUR,-32);
        List<IInsulinTakenEntry> insulinTaken=insulinTakenRecord.getAllEntries(thirtyTwoHoursAgo,eightHoursAgo);
        boolean failed;
        BasalInsulinEntry matchedDose;
        for(IInsulinTakenEntry entry:insulinTaken){
            failed=false;
            if(entry.getType().equals(TakeInsulinReadModel.InsulinType.BOLUS)){
                failed=true;
            }
            if(!failed){
                matchedDose=findMatchingRecommendation(entry,basalInsulinModel);
            }
            Calendar eightHourAfter=(Calendar) entry.getTime().clone();
            eightHourAfter.add(Calendar.HOUR,8);
            List<IInsulinTakenEntry> insulinTakenDuringTest =
                    insulinTakenRecord.getAllEntries(hourBeforeTaken,eightHourAfter);
            if(insulinTakenDuringTest.size()>1){

            }
            /*
            Match to dose from model. Complete
            Check from an hour before to 8 hours after there was no:
                other insulin taken,
                carbs eaten,
                or exercise
            Find the BG at the time taken and the min and max in the following 8 hrs
                if(max>=orig+x) then raise recommendation by ten%
                if(min<=orig-x) then lower recommendation by ten%
             */
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
