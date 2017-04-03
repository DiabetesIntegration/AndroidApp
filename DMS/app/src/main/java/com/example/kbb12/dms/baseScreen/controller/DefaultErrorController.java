package com.example.kbb12.dms.baseScreen.controller;

import com.example.kbb12.dms.baseScreen.model.ErrorReadWriteModel;

/**
 * Created by kbb12 on 30/01/2017.
 */
public class DefaultErrorController implements IErrorController {

    ErrorReadWriteModel model;

    public DefaultErrorController(ErrorReadWriteModel model){
        this.model=model;
    }

    @Override
    public void errorAcknowledged() {
        model.setError(null);
    }
}

