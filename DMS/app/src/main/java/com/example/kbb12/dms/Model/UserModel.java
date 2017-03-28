package com.example.kbb12.dms.model;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.model.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.model.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.model.database.DatabaseBuilder;
import com.example.kbb12.dms.model.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
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
public class UserModel implements ITemplateModel,BasalInsulinModelBuilderMainModel,
        TakeInsulinMainModel,BolusInsulinModelBuilderMainModel, IBloodGlucoseModel {

    private String exampleData;

    private List<ModelObserver> observers;

    private IBasalInsulinModel basalInsulinModel;

    private IBolusInsulinModel bolusInsulinModel;

    private InsulinTakenRecord insulinTakenRecord;

    private RawBGRecord rawBGRecord;

    private BGRecord historyBGRecord;

    private BGRecord currentBGRecord;

    private boolean usingImprovements=true;

    public UserModel(Context context){
        DatabaseBuilder db = new DatabaseBuilder(context);
        basalInsulinModel =db.getBasalInsulinModel();
        bolusInsulinModel= db.getBolusInsulinModel();
        insulinTakenRecord= db.getInsulinTakenRecord();
        rawBGRecord = db.getRawBGRecord();
        historyBGRecord = db.getHistoryBGRecord();
        currentBGRecord = db.getCurrentBGRecord();
        observers= new ArrayList<>();
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }

    @Override
    public void addRawData(Calendar c, String data){
        rawBGRecord.addRawData(data, c);
    }

    @Override
    public void addHistoryReading(Calendar c, double reading){
        historyBGRecord.insertReading(c, reading);
    }

    @Override
    public void addCurrentReading(Calendar c, double reading){
        historyBGRecord.insertReading(c, reading);
    }

    @Override
    public BGReading getMostRecentHistoryReading() {
        return historyBGRecord.getMostRecentReading();
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
        return basalInsulinModel.getEntries(usingImprovements);
    }


    @Override
    public BasalInsulinEntry getLatestBasalRecommendation() {
        Calendar now = Calendar.getInstance();
        //Get the first time before now
        BasalInsulinEntry mostRecent= basalInsulinModel.getLatestBefore(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)+1,usingImprovements);
        //Get what day that dose was last taken on and the recommended time for taking it
        Calendar lastTaken = basalInsulinModel.getLastTakenAprox(mostRecent);
        //If (taken today) or (timeRecommended>now)
        if(sameDay(lastTaken, now)||timeLater(lastTaken,now)){
            return null;
        }
        basalInsulinModel.allTakenBefore(mostRecent.getHour(),mostRecent.getMinute(),now.get(Calendar.DAY_OF_MONTH),
        now.get(Calendar.MONTH),now.get(Calendar.YEAR));
        //Return it
        return mostRecent;
    }


    @Override
    public void takeInsulin(Calendar time, double amount, boolean basal) {
        if(basal){
            //If they're taking basal insulin mark all the times before and including now as taken.
            time.add(Calendar.MINUTE,5);
            basalInsulinModel.allTakenBefore(time.get(Calendar.HOUR),time.get(Calendar.MINUTE)+5,
                    time.get(Calendar.DAY_OF_MONTH),time.get(Calendar.MONTH),time.get(Calendar.YEAR));
        }
        try {
            insulinTakenRecord.addEntry(time, amount, basal);
        }catch (SQLiteConstraintException e){
            //Do nothing the entry has already been added.
        }
    }

    @Override
    public int getRecentCarbs() {
        /*
        TODO This should add up all of the carbs in the last half hour and return the result
         */
        return 10;
    }

    @Override
    public double getCurrentICR() {
        return bolusInsulinModel.getICRValue(Calendar.getInstance(),usingImprovements);
    }

    @Override
    public double getCurrentISF() {
        return bolusInsulinModel.getISFValue(Calendar.getInstance(),usingImprovements);
    }

    @Override
    public Double getCurrentBG() {
        BGReading reading =currentBGRecord.getMostRecentReading();
        Calendar fifteenMinutesAgo = Calendar.getInstance();
        fifteenMinutesAgo.add(Calendar.MINUTE,-15);
        if(reading==null||reading.getTime().getTimeInMillis()<fifteenMinutesAgo.getTimeInMillis()){
            return null;
        }
        return reading.getReading();
    }

    @Override
    public boolean hasTakenBolusInsulinRecently() {
        IInsulinTakenEntry lastTaken =insulinTakenRecord.getMostRecentBolus();
        if(lastTaken==null){
            //never been taken
            return false;
        }
        Calendar now =Calendar.getInstance();
        now.add(Calendar.HOUR,-4);
        return (now.getTimeInMillis()<lastTaken.getTime().getTimeInMillis());
    }

    private boolean sameDay(Calendar one,Calendar two){
        return (one.get(Calendar.YEAR)==two.get(Calendar.YEAR))&&(one.get(Calendar.MONTH)==two.get(Calendar.MONTH))&&(one.get(Calendar.DAY_OF_MONTH)==two.get(Calendar.DAY_OF_MONTH));
    }


    private boolean timeLater(Calendar one,Calendar two){
        return (one.get(Calendar.HOUR)>two.get(Calendar.HOUR))||((one.get(Calendar.HOUR)==two.get(Calendar.HOUR))&&(one.get(Calendar.MINUTE)>two.get(Calendar.MINUTE)));
    }

    @Override
    public void createInsulinToCarbModel(double breakInsulin, double breakCarbs, double lunInsulin,
                                         double lunCarbs, double dinInsulin, double dinCarbs,
                                         double nighInsulin,double nighCarbs ) {
        bolusInsulinModel.createInsulinToCarbModel(breakInsulin,breakCarbs,lunInsulin,lunCarbs,
                dinInsulin,dinCarbs,nighInsulin,nighCarbs);
    }

    @Override
    public void createInsulinToCarbModel(double icr) {
        bolusInsulinModel.createInsulinToCarbModel(icr);
    }

    @Override
    public void createInsulinSensitivityModel(double mornIsf, double afteIsf, double eveISF, double nighISF) {
        bolusInsulinModel.createInsulinSensitivityModel(mornIsf, afteIsf, eveISF, nighISF);
    }

    @Override
    public void createInsulinSensitivityModel(double ISF) {
        bolusInsulinModel.createInsulinSensitivityModel(ISF);
    }

    public void logModels(){
        basalInsulinModel.log();
        bolusInsulinModel.log();
    }
}
