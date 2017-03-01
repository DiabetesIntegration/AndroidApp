package com.example.kbb12.dms.LongActingInsulinModelBuilder.Model;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.LongActingInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.Model.LongActingInsulinModel.LongActingInsulinDose;
import com.example.kbb12.dms.Model.InsulinModel;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class LongActingInsulinModel implements LongActingInsulinReadWriteModel {

    private LongActingInsulinDose selectedDose;

    private LongActingInsulinDose doseToBeDeleted;

    private String longActingInsulinBrandName;

    private List<LongActingInsulinDose> basicDoses;

    private InsulinModel model;

    private ModelObserver observer;

    private String errorMessage;

    public LongActingInsulinModel(InsulinModel model){
        basicDoses=new ArrayList<>();
        selectedDose=null;
        doseToBeDeleted=null;
        longActingInsulinBrandName=null;
        this.model=model;
    }

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
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
    public void deselectTime(){
        selectedDose=null;
        notifyObserver();
    }

    @Override
    public void setSelectedTime(int entryNumber) {
        if(entryNumber>=basicDoses.size()){
            selectedDose=new LongActingInsulinDose();
            basicDoses.add(selectedDose);
        }else{
            selectedDose=basicDoses.get(entryNumber);
        }
        notifyObserver();
    }

    @Override
    public void setDoseToBeDeleted(int entryNumber){
        doseToBeDeleted=basicDoses.get(entryNumber);
        notifyObserver();
    }

    @Override
    public void cancelDelete(){
        doseToBeDeleted=null;
        notifyObserver();
    }

    @Override
    public void deleteDose(){
        basicDoses.remove(doseToBeDeleted);
        doseToBeDeleted=null;
        notifyObserver();
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
    public void saveDoses() throws DuplicateDoseException {
        model.saveDoses(basicDoses,longActingInsulinBrandName);
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
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
    public boolean isTimeSelected(){
        return (null!=selectedDose);
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
    public String getError() {
        return errorMessage;
    }

    private void notifyObserver(){
        if(null!=observer){
            observer.update();
        }
    }
}

