package com.example.kbb12.dms.enterWeight.model;

import com.example.kbb12.dms.enterWeight.IEnterWeight;
import com.example.kbb12.dms.model.EnterWeightMainModel;
import com.example.kbb12.dms.startUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 29-Mar-17.
 */

public class EnterWeightModel implements IEnterWeight{

    private String errorMessage;
    EnterWeightMainModel model;

    private List<ModelObserver> observers;

    public EnterWeightModel(EnterWeightMainModel model){
        observers=new ArrayList<>();
        this.model=model;
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

    public void removeObserver(ModelObserver observer){
        observers.remove(observer);
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObservers();
    }
}
