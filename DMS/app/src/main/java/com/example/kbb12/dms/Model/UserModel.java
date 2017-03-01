package com.example.kbb12.dms.Model;

import android.content.Context;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.InsulinTakenRecord.InsulinTakenDatabase;
import com.example.kbb12.dms.Model.InsulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.Model.LongActingInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.Model.LongActingInsulinModel.ILongActingInsulinModel;
import com.example.kbb12.dms.Model.LongActingInsulinModel.LongActingInsulinModel;
import com.example.kbb12.dms.Model.LongActingInsulinModel.LongActingInsulinDose;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel,InsulinModel,TakeInsulinMainModel {

    private String exampleData;

    private List<ModelObserver> observers;

    private ILongActingInsulinModel longActingInsulinModel;

    private InsulinTakenRecord insulinTakenRecord;

    public static final int versionNumber=52;

    public UserModel(Context context){
        longActingInsulinModel =new LongActingInsulinModel(context,versionNumber);
        insulinTakenRecord= new InsulinTakenDatabase(context,versionNumber);
        observers= new ArrayList<>();
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    public void registerObserver(ModelObserver observer)
    {
        observers.add(observer);
        notifyObservers();
    }

    private void notifyObservers(){
        for(ModelObserver observer:observers){
            observer.update();
        }
    }

    @Override
    public void saveDoses(List<LongActingInsulinDose> basicDoses,String longActingInsulinBrandName) throws DuplicateDoseException {
        try {
            Calendar currentTime = Calendar.getInstance();
            Calendar lastTaken;
            String date;
            for (LongActingInsulinEntry dose : basicDoses) {
                lastTaken=Calendar.getInstance();
                if(dose.getHour()>currentTime.get(Calendar.HOUR_OF_DAY)||(dose.getHour().equals(currentTime.get(Calendar.HOUR_OF_DAY))&&dose.getMinute()>currentTime.get(Calendar.MINUTE))){
                    //If the set time hasn't been today yet then assume the last time it was taken
                    //was yesterday. Otherwise assume it has been taken today.
                    lastTaken.add(Calendar.DAY_OF_YEAR,-1);
                }
                longActingInsulinModel.addEntry(dose, longActingInsulinBrandName, lastTaken.get(Calendar.DAY_OF_MONTH), lastTaken.get(Calendar.MONTH), lastTaken.get(Calendar.YEAR));
            }
        } catch (DuplicateDoseException e){
            longActingInsulinModel.clearValues();
            throw new DuplicateDoseException();
        }
    }

    public List<LongActingInsulinEntry> getDoses(){
        return longActingInsulinModel.getEntries();
    }


    @Override
    public LongActingInsulinEntry getLatestLongActingRecommendation(Calendar now) {
        //Get the first time before now
        LongActingInsulinEntry mostRecent= longActingInsulinModel.getLatestBefore(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
        //Get what day that dose was last taken on and the recommended time for taking it
        Calendar lastTaken = longActingInsulinModel.getLastTakenAprox(mostRecent);
        //If (taken today) or (timeRecommended>now)
        if(sameDay(lastTaken, now)||timeLater(lastTaken,now)){
            return null;
        }
        //Set all before it to taken
        longActingInsulinModel.allTakenBefore(mostRecent.getHour(), mostRecent.getMinute(), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH), now.get(Calendar.YEAR));
        //Return it
        return mostRecent;
    }

    @Override
    public void takeInsulin(int year, int month, int day, int hour, int minute, double amount, boolean longActing) {
        insulinTakenRecord.addEntry(day,month,year,hour,minute,amount,longActing);
    }

    private boolean sameDay(Calendar one,Calendar two){
        return (one.get(Calendar.YEAR)==two.get(Calendar.YEAR))&&(one.get(Calendar.MONTH)==two.get(Calendar.MONTH))&&(one.get(Calendar.DAY_OF_MONTH)==two.get(Calendar.DAY_OF_MONTH));
    }


    private boolean timeLater(Calendar one,Calendar two){
        return (one.get(Calendar.HOUR)>two.get(Calendar.HOUR))||((one.get(Calendar.HOUR)==two.get(Calendar.HOUR))&&(one.get(Calendar.MINUTE)>two.get(Calendar.MINUTE)));
    }
}
