package com.example.kbb12.dms.model;

import android.content.Context;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenDatabase;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinModel;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.startUp.ModelObserver;
import com.example.kbb12.dms.template.ITemplateModel;

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

    private IBasalInsulinModel basalInsulinModel;

    private InsulinTakenRecord insulinTakenRecord;

    public static final int versionNumber=2;

    public UserModel(Context context){
        basalInsulinModel =new BasalInsulinModel(context,versionNumber,"InitialBasalInsulinModel");
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
    public void saveDoses(List<BasalInsulinDose> basicDoses,String basalInsulinBrandName) throws DuplicateDoseException {
        try {
            Calendar currentTime = Calendar.getInstance();
            Calendar lastTaken;
            String date;
            for (BasalInsulinEntry dose : basicDoses) {
                lastTaken=Calendar.getInstance();
                if(dose.getHour()>currentTime.get(Calendar.HOUR_OF_DAY)||(dose.getHour().equals(currentTime.get(Calendar.HOUR_OF_DAY))&&dose.getMinute()>currentTime.get(Calendar.MINUTE))){
                    //If the set time hasn't been today yet then assume the last time it was taken
                    //was yesterday. Otherwise assume it has been taken today.
                    lastTaken.add(Calendar.DAY_OF_YEAR,-1);
                }
                basalInsulinModel.addEntry(dose, basalInsulinBrandName, lastTaken.get(Calendar.DAY_OF_MONTH), lastTaken.get(Calendar.MONTH), lastTaken.get(Calendar.YEAR));
            }
        } catch (DuplicateDoseException e){
            basalInsulinModel.clearValues();
            throw new DuplicateDoseException();
        }
    }

    public List<BasalInsulinEntry> getDoses(){
        return basalInsulinModel.getEntries();
    }


    @Override
    public BasalInsulinEntry getLatestBasalRecommendation(Calendar now) {
        //Get the first time before now
        BasalInsulinEntry mostRecent= basalInsulinModel.getLatestBefore(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)+1);
        //Get what day that dose was last taken on and the recommended time for taking it
        Calendar lastTaken = basalInsulinModel.getLastTakenAprox(mostRecent);
        //If (taken today) or (timeRecommended>now)
        if(sameDay(lastTaken, now)||timeLater(lastTaken,now)){
            return null;
        }
        //Set all before it to taken
        basalInsulinModel.allTakenBefore(mostRecent.getHour(), mostRecent.getMinute(), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH), now.get(Calendar.YEAR));
        //Return it
        return mostRecent;
    }

    @Override
    public void takeInsulin(int year, int month, int day, int hour, int minute, double amount, boolean basal) {
        insulinTakenRecord.addEntry(day,month,year,hour,minute,amount, basal);
    }

    private boolean sameDay(Calendar one,Calendar two){
        return (one.get(Calendar.YEAR)==two.get(Calendar.YEAR))&&(one.get(Calendar.MONTH)==two.get(Calendar.MONTH))&&(one.get(Calendar.DAY_OF_MONTH)==two.get(Calendar.DAY_OF_MONTH));
    }


    private boolean timeLater(Calendar one,Calendar two){
        return (one.get(Calendar.HOUR)>two.get(Calendar.HOUR))||((one.get(Calendar.HOUR)==two.get(Calendar.HOUR))&&(one.get(Calendar.MINUTE)>two.get(Calendar.MINUTE)));
    }
}
