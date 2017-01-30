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

    public UserModel(){
        observers= new ArrayList<>();
        basicDoses=new ArrayList<>();
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

    @Override
    public List<LongActingInsulinEntry> getInsulinEntries() {
        List<LongActingInsulinEntry> entries = new ArrayList<>();
        for(LongActingInsulinDose dose:basicDoses){
            entries.add(dose.clone());
        }
        return entries;
    }

    @Override
    public void setType(int position, LongActingInsulinEntry.InsulinType type) {
        if(position<basicDoses.size()){
            if(LongActingInsulinEntry.InsulinType.NOT_SET.equals(type)){
                basicDoses.remove(position);
            }else {
                basicDoses.get(position).setType(type);
            }
        }else{
            if(LongActingInsulinEntry.InsulinType.NOT_SET.equals(type)){
                return;
            }
            basicDoses.add(new LongActingInsulinDose(type));
        }
        notifyObservers();
    }

    @Override
    public void setBrand(int entryNumber,String name){
        assert entryNumber<basicDoses.size();
        basicDoses.get(entryNumber).setBrandName(name);
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
}
