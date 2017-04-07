package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.database.basalInsulinModel.BasalInsulinEntry;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinMainModel {
    BasalInsulinEntry getLatestBasalRecommendation();
    void takeInsulin(Calendar time, double amount, boolean basal);
    int getRecentCarbs();
    double getCurrentICR();
    double getCurrentISF();
    Double getCurrentBG();
    boolean hasTakenBolusInsulinRecently();
}
