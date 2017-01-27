package com.example.kbb12.dms.Model;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;
import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;
import com.example.kbb12.dms.Model.Insulin.LongActingDose;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Model.Insulin.ShortActingDose;
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

    private List<LongActingDose> longActingDoses;
    private List<ShortActingDose> shortActingDoses;

    public UserModel(){
        observers= new ArrayList<>();
        longActingDoses=new ArrayList<>();
        shortActingDoses=new ArrayList<>();
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }

    @Override
    public List<InsulinEntry> getInsulinEntries() {
        List<InsulinEntry> entries = new ArrayList<>();
        entries.addAll(longActingDoses);
        entries.addAll(shortActingDoses);
        return entries;
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
}
