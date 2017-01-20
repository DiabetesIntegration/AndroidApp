package com.example.kbb12.dms.StartUp;

import com.example.kbb12.dms.InsulinModelBuilder.InsulinModel;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel,InsulinModel {

    private String exampleData;

    private List<ModelObserver> observers;

    public UserModel(){
        observers= new ArrayList<>();
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

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
