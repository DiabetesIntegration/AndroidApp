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

    @Override
    public List<InsulinEntry> getInsulinEntries() {
        List<InsulinEntry> entries = new ArrayList<>();
        for(InsulinDose dose:basicDoses){
            entries.add(dose.clone());
        }
        return entries;
    }

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    @Override
    public void setType(int position, InsulinEntry.InsulinType type) {
        if(position<basicDoses.size()){
            basicDoses.get(position).setType(type);
        }else{
            basicDoses.add(new InsulinDose(type));
        }
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
