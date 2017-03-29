package com.example.kbb12.dms.ErrorHandling;

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