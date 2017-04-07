package com.example.kbb12.dms.reusableFunctionality.baseScreen.controller;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by kbb12 on 30/01/2017.
 */
public class DefaultErrorController implements IErrorController {

    BaseReadWriteModel model;

    public DefaultErrorController(BaseReadWriteModel model){
        this.model=model;
    }

    @Override
    public void errorAcknowledged() {
        model.setError(null);
    }
}

