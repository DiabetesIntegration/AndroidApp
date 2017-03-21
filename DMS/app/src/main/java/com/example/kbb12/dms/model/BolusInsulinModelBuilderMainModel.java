package com.example.kbb12.dms.model;

/**
 * Created by kbb12 on 21/03/2017.
 */
public interface BolusInsulinModelBuilderMainModel {
    void createInsulinToCarbModel(int breakInsulin,int breakCarbs,int lunInsulin,int lunCarbs,
                                  int dinInsulin,int dinCarbs);
    void createInsulinToCarbModel(double icr);
    void createInsulinSensitivityModel(double mornIsf,double afteIsf,double nighISF);
    void createInsulinSensitivityModel(double ISF);
}
