package com.example.kbb12.dms.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.model.BasalInsulinModelBuilderMainModel;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.startUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class BasalInsulinModel implements BasalInsulinReadWriteModel {

    private BasalInsulinDose selectedDose;

    private BasalInsulinDose doseToBeDeleted;

    private String basalInsulinBrandName;

    private List<BasalInsulinDose> basicDoses;

    private BasalInsulinModelBuilderMainModel model;

    private ModelObserver observer;

    private String errorMessage;

    public BasalInsulinModel(BasalInsulinModelBuilderMainModel model){
        basicDoses=new ArrayList<>();
        selectedDose=null;
        doseToBeDeleted=null;
        basalInsulinBrandName =null;
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
            selectedDose=new BasalInsulinDose();
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
    public void setBasalBrandName(String brandName) {
        basalInsulinBrandName =brandName;
    }

    @Override
    public void cancelSelection() {
        if(selectedDose!=null&&selectedDose.getHour()==0&&selectedDose.getMinute()==0&&selectedDose.getDose()==0.0){
            basicDoses.remove(selectedDose);
        }
        selectedDose=null;
    }

    @Override
    public void saveDoses() throws DuplicateDoseException {
        model.saveDoses(basicDoses, basalInsulinBrandName);
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
    }

    @Override
    public List<BasalInsulinEntry> getTempDoses() {
        List<BasalInsulinEntry> entries = new ArrayList<>();
        for(BasalInsulinDose dose:basicDoses){
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
    public String getBasalBrandName() {
        return basalInsulinBrandName;
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

