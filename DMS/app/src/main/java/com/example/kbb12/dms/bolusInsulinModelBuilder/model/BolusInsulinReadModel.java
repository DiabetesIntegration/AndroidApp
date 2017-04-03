package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.baseScreen.model.ErrorReadModel;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface BolusInsulinReadModel extends ErrorReadModel {
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
