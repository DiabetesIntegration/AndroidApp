package com.example.kbb12.dms.model;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinMainModel {
    BasalInsulinEntry getLatestBasalRecommendation(Calendar now);
    void takeInsulin(int year,int month,int day,int hour,int minute,double amount,boolean basal);
}