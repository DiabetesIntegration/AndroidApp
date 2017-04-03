package com.example.kbb12.dms.fitnessInfo;

import com.example.kbb12.dms.model.FitnessInfoMainModel;
import com.example.kbb12.dms.startUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class FitnessInfoModel implements IFitnessInfo {

    private List<ModelObserver> observers;
    private FitnessInfoMainModel model;

    public FitnessInfoModel(FitnessInfoMainModel model){
        this.model=model;
        this.observers= new ArrayList<>();
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
    public int getCalCount() {
        return model.getCalCount();
    }

}