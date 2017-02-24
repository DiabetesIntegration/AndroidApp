package com.example.kbb12.dms.Model;

import android.content.Context;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.Model.Insulin.DuplicateDoseException;
import com.example.kbb12.dms.Model.Insulin.ILongActingInsulinDatabase;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDatabase;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDose;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel,LongActingInsulinReadWriteModel {

    private String exampleData;

    private List<ModelObserver> observers;

    private List<LongActingInsulinDose> basicDoses;

    private String errorMessage;

    private LongActingInsulinDose selectedDose;

    private LongActingInsulinDose doseToBeDeleted;

    private String longActingInsulinBrandName;

    private ILongActingInsulinDatabase database;

    public static final int versionNumber=36;

    public UserModel(Context context){
        database=new LongActingInsulinDatabase(context,versionNumber);
        observers= new ArrayList<>();
        basicDoses=new ArrayList<>();
        selectedDose=null;
        doseToBeDeleted=null;
        longActingInsulinBrandName=null;
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }


    public void setError(String errorMessage){
        this.errorMessage=errorMessage;
        notifyObservers();
    }

    public String getError(){
        return errorMessage;
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
    public void setHour(int hour) {
        selectedDose.setHour(hour);
    }

    @Override
    public void setMinute(int minute) {
        selectedDose.setMinute(minute);
    }

    @Override
    public void setSelectedTime(int entryNumber) {
        if(entryNumber>=basicDoses.size()){
            selectedDose=new LongActingInsulinDose();
            basicDoses.add(selectedDose);
        }else{
            selectedDose=basicDoses.get(entryNumber);
        }
        notifyObservers();
    }

    @Override
    public void deselectTime(){
        selectedDose=null;
        notifyObservers();
    }

    @Override
    public boolean isTimeSelected(){
        return (null!=selectedDose);
    }

    @Override
    public void setDoseToBeDeleted(int entryNumber){
        doseToBeDeleted=basicDoses.get(entryNumber);
        notifyObservers();
    }

    @Override
    public void cancelDelete(){
        doseToBeDeleted=null;
        notifyObservers();
    }

    @Override
    public void deleteDose(){
        basicDoses.remove(doseToBeDeleted);
        doseToBeDeleted=null;
        notifyObservers();
    }

    @Override
    public boolean isReadyToDelete(){
        return (null!=doseToBeDeleted);
    }

    @Override
    public String getLongActingBrandName() {
        return longActingInsulinBrandName;
    }

    @Override
    public void setDose(double dose, int entryNumber) {
        basicDoses.get(entryNumber).setDose(dose);
    }

    @Override
    public void setLongActingBrandName(String brandName) {
        longActingInsulinBrandName=brandName;
    }

    @Override
    public List<LongActingInsulinEntry> getTempDoses() {
        List<LongActingInsulinEntry> entries = new ArrayList<>();
        for(LongActingInsulinDose dose:basicDoses){
            entries.add(dose.clone());
        }
        return entries;
    }

    @Override
    public void saveDoses() throws DuplicateDoseException {
        try {
            for (LongActingInsulinEntry dose : basicDoses) {
                database.addEntry(dose,longActingInsulinBrandName);
            }
        } catch (DuplicateDoseException e){
            database.clearValues();
            throw new DuplicateDoseException();
        }
    }

    public List<LongActingInsulinEntry> getDoses(){
        return database.getEntries();
    }


}
