package com.example.kbb12.dms.StartUp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel {

    private String exampleData;

    private List<ModelObserver> observers;

    public UserModel(String exampleData){
        this.exampleData=exampleData;
        observers= new ArrayList<>();
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
