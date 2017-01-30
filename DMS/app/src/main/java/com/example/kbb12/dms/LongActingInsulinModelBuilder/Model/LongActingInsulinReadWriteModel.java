package com.example.kbb12.dms.LongActingInsulinModelBuilder.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface LongActingInsulinReadWriteModel extends LongActingInsulinReadModel,ErrorReadWriteModel {
    public void setBrand(String name);
}
