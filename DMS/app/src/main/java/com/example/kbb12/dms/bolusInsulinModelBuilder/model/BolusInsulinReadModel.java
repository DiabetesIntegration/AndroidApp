package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;
import com.example.kbb12.dms.errorHandling.ErrorReadModel;

import java.util.List;

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
    Integer getBreakInsulin();
    Integer getBreakCarbs();
    Integer getLunInsulin();
    Integer getLunCarbs();
    Integer getDinInsulin();
    Integer getDinCarbs();

    Integer getNumBasBolUnitsPerDay();
    boolean isRapidActing();
    Double getMornISF();
    Double getAfteISF();
    Double getNighISF();
}
