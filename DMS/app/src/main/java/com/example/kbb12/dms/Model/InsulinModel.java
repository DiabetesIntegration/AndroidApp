package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinDose;

import java.util.List;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface InsulinModel {
    public void saveDoses(List<BasalInsulinDose> basicDoses,String basalInsulinBrandName) throws DuplicateDoseException;
}