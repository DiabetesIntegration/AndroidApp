package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface BolusInsulinReadModel extends BaseReadModel {
    boolean knowsISF();
    boolean knowsICR();
    Double getICR();
    Double getISF();

    Integer getNumBolUnitsPerDay();
    boolean isHumalogNovolog();
    Double getBreakInsulin();
    Double getBreakCarbs();
    Double getLunInsulin();
    Double getLunCarbs();
    Double getDinInsulin();
    Double getDinCarbs();
    Double getNighInsulin();
    Double getNighCarbs();

    Integer getNumBasBolUnitsPerDay();
    boolean isRapidActing();
    Double getMornISF();
    Double getAfteISF();
    Double getEveISF();
    Double getNighISF();
}
