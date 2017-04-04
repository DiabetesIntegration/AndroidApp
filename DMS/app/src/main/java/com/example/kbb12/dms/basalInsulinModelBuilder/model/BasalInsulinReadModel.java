package com.example.kbb12.dms.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.baseScreen.model.BaseReadModel;

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
