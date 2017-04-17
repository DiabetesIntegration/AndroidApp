package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface BasalInsulinReadModel extends BaseReadModel {
    List<BasalInsulinEntry> getTempDoses();
    boolean isTimeSelected();
    boolean isReadyToDelete();
    String getBasalBrandName();
}
