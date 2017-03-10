package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.errorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface BolusInsulinReadWriteModel extends BolusInsulinReadModel,ErrorReadWriteModel {
    void setKnowsISF(boolean knowsISF);
    void setKnowsICR(boolean knowsICR);
    void setNumBolUnitsPerDay(int numBolUnitsPerDay);
    void setHumalogNovolog(boolean humalogNovolog);
    void setBreakInsulin(int breakInsulin);
    void setBreakCarbs(int breakCarbs);
    void setLunInsulin(int lunInsulin);
    void setLunCarbs(int lunCarbs);
    void setDinInsulin(int dinInsulin);
    void setDinCarbs(int dinCarbs);
    void setNumBasBolUnitsPerDay(int numBasBolUnitsPerDay);
    void setRapidActing(boolean rapidActing);
    void setMornISF(double mornISF);
    void setAfteISF(double aftISF);
    void setNighISF(double nighISF);
}
