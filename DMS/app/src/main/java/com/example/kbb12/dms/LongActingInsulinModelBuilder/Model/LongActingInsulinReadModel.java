package com.example.kbb12.dms.LongActingInsulinModelBuilder.Model;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface LongActingInsulinReadModel extends ErrorReadModel {
    List<LongActingInsulinEntry> getDoses();
    boolean isTimeSelected();
    String getLongActingBrandName();
}
