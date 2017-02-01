package com.example.kbb12.dms.Model;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
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

    private String longActingInsulinBrandName;

    public UserModel(){
        observers= new ArrayList<>();
        basicDoses=new ArrayList<>();
        selectedDose=null;
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
    public List<LongActingInsulinEntry> getDoses() {
        List<LongActingInsulinEntry> entries = new ArrayList<>();
        for(LongActingInsulinDose dose:basicDoses){
            entries.add(dose.clone());
        }
        return entries;
    }


}
