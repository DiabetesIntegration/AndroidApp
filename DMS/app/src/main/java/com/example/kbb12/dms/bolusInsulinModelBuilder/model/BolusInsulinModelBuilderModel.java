package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.model.InsulinModel;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class BolusInsulinModelBuilderModel implements BolusInsulinReadWriteModel {
    private boolean knowsICR;
    private boolean knowsISF;
    private InsulinModel model;
    private String errorMessage;
    private ModelObserver observer;

    private Integer numBolUnitsPerDay;
    private boolean humalogNovolog;
    private Integer breakInsulin;
    private Integer breakCarbs;
    private Integer lunInsulin;
    private Integer lunCarbs;
    private Integer dinInsulin;
    private Integer dinCarbs;

    private Integer numBasBolUnitsPerDay;
    private boolean rapidActing;
    private Double mornIsf;
    private Double afteIsf;
    private Double nighISF;

    public BolusInsulinModelBuilderModel(InsulinModel model){
        this.model=model;
        this.knowsICR=false;
        this.knowsISF=false;
        this.errorMessage=null;
        this.observer=null;
        numBolUnitsPerDay=null;
        humalogNovolog=false;
        breakInsulin=null;
        breakCarbs=null;
        lunInsulin=null;
        lunCarbs=null;
        dinInsulin=null;
        dinCarbs=null;
        numBasBolUnitsPerDay=null;
        rapidActing=false;
        mornIsf=null;
        afteIsf=null;
        nighISF=null;
    }

    @Override
    public boolean knowsISF() {
        return knowsISF;
    }

    @Override
    public boolean knowsICR() {
        return knowsICR;
    }

    @Override
    public Double getICR() {
        return null;
    }

    @Override
    public Double getISF() {
        return null;
    }

    @Override
    public Integer getNumBolUnitsPerDay() {
        return numBolUnitsPerDay;
    }

    @Override
    public boolean isHumalogNovolog() {
        return humalogNovolog;
    }

    @Override
    public Integer getBreakInsulin() {
        return breakInsulin;
    }

    @Override
    public Integer getBreakCarbs() {
        return breakCarbs;
    }

    @Override
    public Integer getLunInsulin() {
        return lunInsulin;
    }

    @Override
    public Integer getLunCarbs(){
        return lunCarbs;
    }

    @Override
    public Integer getDinInsulin() {
        return dinInsulin;
    }

    @Override
    public Integer getDinCarbs() {
        return dinCarbs;
    }

    @Override
    public Integer getNumBasBolUnitsPerDay() {
        return numBasBolUnitsPerDay;
    }

    @Override
    public boolean isRapidActing() {
        return rapidActing;
    }

    @Override
    public Double getMornISF() {
        return mornIsf;
    }

    @Override
    public Double getAfteISF() {
        return afteIsf;
    }

    @Override
    public Double getNighISF() {
        return nighISF;
    }

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
    }

    private void notifyObserver(){
        if(observer!=null){
            observer.update();;
        }
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public void setKnowsISF(boolean knowsISF) {
        this.knowsISF=knowsISF;
        notifyObserver();
    }

    @Override
    public void setKnowsICR(boolean knowsICR) {
        this.knowsICR=knowsICR;
        notifyObserver();
    }

    @Override
    public void setNumBolUnitsPerDay(int numBolUnitsPerDay) {
        this.numBolUnitsPerDay=numBolUnitsPerDay;
        notifyObserver();
    }

    @Override
    public void setHumalogNovolog(boolean humalogNovolog) {
        this.humalogNovolog=humalogNovolog;
        notifyObserver();
    }

    @Override
    public void setBreakInsulin(int breakInsulin) {
        this.breakInsulin=breakInsulin;
        notifyObserver();
    }

    @Override
    public void setBreakCarbs(int breakCarbs) {
        this.breakCarbs=breakCarbs;
        notifyObserver();
    }

    @Override
    public void setLunInsulin(int lunInsulin) {
        this.lunInsulin=lunInsulin;
        notifyObserver();
    }

    @Override
    public void setLunCarbs(int lunCarbs) {
        this.lunCarbs=lunCarbs;
        notifyObserver();
    }

    @Override
    public void setDinInsulin(int dinInsulin) {
        this.dinInsulin=dinInsulin;
        notifyObserver();
    }

    @Override
    public void setDinCarbs(int dinCarbs) {
        this.dinCarbs=dinCarbs;
        notifyObserver();
    }

    @Override
    public void setNumBasBolUnitsPerDay(int numBasBolUnitsPerDay) {
        this.numBasBolUnitsPerDay=numBasBolUnitsPerDay;
        notifyObserver();
    }

    @Override
    public void setRapidActing(boolean rapidActing) {
        this.rapidActing=rapidActing;
        notifyObserver();
    }

    @Override
    public void setMornISF(double mornISF) {
        this.mornIsf=mornISF;
        notifyObserver();
    }

    @Override
    public void setAfteISF(double aftISF) {
        this.afteIsf=aftISF;
        notifyObserver();
    }

    @Override
    public void setNighISF(double nighISF) {
        this.nighISF=nighISF;
        notifyObserver();
    }
}
