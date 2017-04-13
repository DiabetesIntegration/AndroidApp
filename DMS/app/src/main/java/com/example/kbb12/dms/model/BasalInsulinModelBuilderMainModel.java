package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.database.basalInsulinModel.DuplicateDoseException;

import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface BasalInsulinModelBuilderMainModel {
    public void saveDoses(List<BasalInsulinDose> basicDoses,String basalInsulinBrandName) throws DuplicateDoseException;
}
