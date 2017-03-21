package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.errorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface BolusInsulinReadWriteModel extends BolusInsulinReadModel,ErrorReadWriteModel {
    void setKnowsISF(boolean knowsISF);
    void setKnowsICR(boolean knowsICR);
    void setNumBolUnitsPerDay(Integer numBolUnitsPerDay);
    void setHumalogNovolog(boolean humalogNovolog);
    void setBreakInsulin(Integer breakInsulin);
    void setBreakCarbs(Integer breakCarbs);
    void setLunInsulin(Integer lunInsulin);
    void setLunCarbs(Integer lunCarbs);
    void setDinInsulin(Integer dinInsulin);
    void setDinCarbs(Integer dinCarbs);
    void setNumBasBolUnitsPerDay(Integer numBasBolUnitsPerDay);
    void setRapidActing(boolean rapidActing);
    void setMornISF(Double mornISF);
    void setAfteISF(Double aftISF);
    void setNighISF(Double nighISF);
    void saveValues();
}
