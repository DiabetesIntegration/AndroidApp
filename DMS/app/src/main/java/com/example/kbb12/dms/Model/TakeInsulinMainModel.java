package com.example.kbb12.dms.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinMainModel {
    LongActingInsulinEntry getLatestLongActingRecommendation(Calendar now);
}
