package com.example.kbb12.dms.baseScreen.model;

import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 03/04/2017.
 */

public class BaseModel implements ErrorReadWriteModel {

    private String errorMessage;
    private ModelObserver observer;

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
    }

    protected void notifyObserver(){
        if(observer!=null) {
            observer.update();
        }
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
    }
}
