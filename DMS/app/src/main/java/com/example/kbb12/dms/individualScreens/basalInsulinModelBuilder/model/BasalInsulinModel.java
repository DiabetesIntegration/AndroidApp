package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.BasalInsulinModelBuilderMainModel;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.database.basalInsulinModel.DuplicateDoseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class BasalInsulinModel extends BaseModel implements BasalInsulinReadWriteModel {

    private BasalInsulinDose selectedDose;

    private BasalInsulinDose doseToBeDeleted;

    private String basalInsulinBrandName;

    private List<BasalInsulinDose> basicDoses;

    private BasalInsulinModelBuilderMainModel model;

    public BasalInsulinModel(BasalInsulinModelBuilderMainModel model){
        basicDoses=new ArrayList<>();
        selectedDose=null;
        doseToBeDeleted=null;
        basalInsulinBrandName =null;
        this.model=model;
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

}

