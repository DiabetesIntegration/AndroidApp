package com.example.kbb12.dms.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.Model.Insulin.DuplicateDoseException;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDose;

import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface InsulinModel {
    public void saveDoses(List<LongActingInsulinDose> basicDoses,String longActingInsulinBrandName) throws DuplicateDoseException;
}
