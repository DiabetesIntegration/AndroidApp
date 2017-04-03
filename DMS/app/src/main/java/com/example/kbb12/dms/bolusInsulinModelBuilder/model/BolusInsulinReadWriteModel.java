package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.baseScreen.model.ErrorReadWriteModel;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface BolusInsulinReadWriteModel extends BolusInsulinReadModel,ErrorReadWriteModel {
    void setKnowsISF(boolean knowsISF);
    void setKnowsICR(boolean knowsICR);
    void setNumBolUnitsPerDay(Integer numBolUnitsPerDay);
    void setHumalogNovolog(boolean humalogNovolog);
    void setBreakInsulin(Double breakInsulin);
    void setBreakCarbs(Double breakCarbs);
    void setLunInsulin(Double lunInsulin);
    void setLunCarbs(Double lunCarbs);
    void setDinInsulin(Double dinInsulin);
    void setDinCarbs(Double dinCarbs);
    void setNighInsulin(Double dinInsulin);
    void setNighCarbs(Double dinInsulin);
    void setNumBasBolUnitsPerDay(Integer numBasBolUnitsPerDay);
    void setRapidActing(boolean rapidActing);
    void setMornISF(Double mornISF);
    void setAfteISF(Double aftISF);
    void setEveISF(Double eveISF);
    void setNighISF(Double nighISF);
    boolean saveValues();
}
