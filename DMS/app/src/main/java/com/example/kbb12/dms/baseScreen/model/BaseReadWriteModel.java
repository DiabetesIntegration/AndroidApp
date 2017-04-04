package com.example.kbb12.dms.baseScreen.model;

import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 30/01/2017.
 */
public interface BaseReadWriteModel extends BaseReadModel {
    void setError(String errorMessage);
    void registerObserver(ModelObserver observer);
}
