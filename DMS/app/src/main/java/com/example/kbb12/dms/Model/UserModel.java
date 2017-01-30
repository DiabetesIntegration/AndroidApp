package com.example.kbb12.dms.Model;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;
import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;
import com.example.kbb12.dms.Model.Insulin.InsulinDose;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel,InsulinReadWriteModel {

    private String exampleData;

    private List<ModelObserver> observers;

    private List<InsulinDose> basicDoses;

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
    public List<InsulinEntry> getInsulinEntries() {
        List<InsulinEntry> entries = new ArrayList<>();
        for(InsulinDose dose:basicDoses){
            entries.add(dose.clone());
        }
        return entries;
    }

    @Override
    public void setType(int position, InsulinEntry.InsulinType type) {
        if(position<basicDoses.size()){
            if(InsulinEntry.InsulinType.NOT_SET.equals(type)){
                basicDoses.remove(position);
            }else {
                basicDoses.get(position).setType(type);
            }
        }else{
            if(InsulinEntry.InsulinType.NOT_SET.equals(type)){
                return;
            }
            basicDoses.add(new InsulinDose(type));
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
