package com.example.kbb12.dms.model.database.bolusInsulinModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 22/03/2017.
 */
public interface IBolusInsulinModel {
    void createInsulinToCarbModel(double breakInsulin, double breakCarbs, double lunInsulin,
                                  double lunCarbs, double dinInsulin, double dinCarbs,
                                  double nighInsulin,double nighCarbs);
    void createInsulinToCarbModel(double ICR);
    void createInsulinSensitivityModel(double ISF);
    void createInsulinSensitivityModel(double mornISF,double afteISF,double eveISF,double nighISF);
    void log();
    Float getICRValue(Calendar time,boolean usingImprovement);
    Float getISFValue(Calendar time,boolean usingImprovement);
    void improveICRValue(Calendar time,double percentageChange);
    void improveISFValue(Calendar time,double percentageChange);
}
