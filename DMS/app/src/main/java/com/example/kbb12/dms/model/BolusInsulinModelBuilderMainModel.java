package com.example.kbb12.dms.model;

/**
 * Created by kbb12 on 21/03/2017.
 */
public interface BolusInsulinModelBuilderMainModel {
    void createInsulinToCarbModel(double breakInsulin,double breakCarbs,double lunInsulin,
                                  double lunCarbs, double dinInsulin,double dinCarbs,
                                  double nighInuslin,double nighCarbs);
    void createInsulinToCarbModel(double icr);
    void createInsulinSensitivityModel(double mornIsf,double afteIsf,double eveISF,double nighISF);
    void createInsulinSensitivityModel(double ISF);
}
