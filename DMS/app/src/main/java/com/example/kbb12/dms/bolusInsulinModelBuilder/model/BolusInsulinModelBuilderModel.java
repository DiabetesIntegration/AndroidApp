package com.example.kbb12.dms.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.model.BolusInsulinModelBuilderMainModel;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class BolusInsulinModelBuilderModel implements BolusInsulinReadWriteModel {
    private boolean knowsICR;
    private boolean knowsISF;
    private BolusInsulinModelBuilderMainModel model;
    private String errorMessage;
    private ModelObserver observer;

    private Integer numBolUnitsPerDay;
    private boolean humalogNovolog;
    private Double breakInsulin;
    private Double breakCarbs;
    private Double lunInsulin;
    private Double lunCarbs;
    private Double dinInsulin;
    private Double dinCarbs;
    private Double nighInsulin;
    private Double nighCarbs;

    private Integer numBasBolUnitsPerDay;
    private boolean rapidActing;
    private Double mornIsf;
    private Double afteIsf;
    private Double eveISF;
    private Double nighISF;

    public BolusInsulinModelBuilderModel(BolusInsulinModelBuilderMainModel model){
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
    public Double getICR()
    {
        if(numBolUnitsPerDay==null){
            return null;
        }
        if(humalogNovolog){
            return ((double)numBolUnitsPerDay)/500;
        }else{
            return ((double)numBolUnitsPerDay)/450;
        }
    }

    @Override
    public Double getISF() {
        if(numBasBolUnitsPerDay==null){
            return null;
        }
        if(rapidActing){
            return (1800/((double)numBasBolUnitsPerDay))/18;
        }else{
            return (1500/((double)numBasBolUnitsPerDay))/18;
        }
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
    public Double getBreakInsulin() {
        return breakInsulin;
    }

    @Override
    public Double getBreakCarbs() {
        return breakCarbs;
    }

    @Override
    public Double getLunInsulin() {
        return lunInsulin;
    }

    @Override
    public Double getLunCarbs(){
        return lunCarbs;
    }

    @Override
    public Double getDinInsulin() {
        return dinInsulin;
    }

    @Override
    public Double getDinCarbs() {
        return dinCarbs;
    }

    @Override
    public Double getNighInsulin() {
        return nighInsulin;
    }

    @Override
    public Double getNighCarbs() {
        return nighCarbs;
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
    public Double getEveISF() {
        return eveISF;
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
    public void setNumBolUnitsPerDay(Integer numBolUnitsPerDay) {
        this.numBolUnitsPerDay=numBolUnitsPerDay;
        notifyObserver();
    }

    @Override
    public void setHumalogNovolog(boolean humalogNovolog) {
        this.humalogNovolog=humalogNovolog;
        notifyObserver();
    }

    @Override
    public void setBreakInsulin(Double breakInsulin) {
        this.breakInsulin=breakInsulin;
        notifyObserver();
    }

    @Override
    public void setBreakCarbs(Double breakCarbs) {
        this.breakCarbs=breakCarbs;
        notifyObserver();
    }

    @Override
    public void setLunInsulin(Double lunInsulin) {
        this.lunInsulin=lunInsulin;
        notifyObserver();
    }

    @Override
    public void setLunCarbs(Double lunCarbs) {
        this.lunCarbs=lunCarbs;
        notifyObserver();
    }

    @Override
    public void setDinInsulin(Double dinInsulin) {
        this.dinInsulin=dinInsulin;
        notifyObserver();
    }

    @Override
    public void setDinCarbs(Double dinCarbs) {
        this.dinCarbs=dinCarbs;
        notifyObserver();
    }

    @Override
    public void setNighInsulin(Double nighInsulin) {
        this.nighInsulin=nighInsulin;
        notifyObserver();
    }

    @Override
    public void setNighCarbs(Double nighCarbs) {
        this.nighCarbs=nighCarbs;
        notifyObserver();
    }

    @Override
    public void setNumBasBolUnitsPerDay(Integer numBasBolUnitsPerDay) {
        this.numBasBolUnitsPerDay=numBasBolUnitsPerDay;
        notifyObserver();
    }

    @Override
    public void setRapidActing(boolean rapidActing) {
        this.rapidActing=rapidActing;
        notifyObserver();
    }

    @Override
    public void setMornISF(Double mornISF) {
        this.mornIsf=mornISF;
        notifyObserver();
    }

    @Override
    public void setAfteISF(Double aftISF) {
        this.afteIsf=aftISF;
        notifyObserver();
    }

    @Override
    public void setEveISF(Double eveISF) {
        this.eveISF=eveISF;
    }

    @Override
    public void setNighISF(Double nighISF) {
        this.nighISF=nighISF;
        notifyObserver();
    }

    @Override
    public boolean saveValues() {
        if(knowsICR){
            if(breakInsulin==null||breakCarbs==null||lunInsulin==null||lunCarbs==null||
                    dinInsulin==null||dinCarbs==null||nighInsulin==null||nighCarbs==null){
                setError("You must enter your full insulin to carb ratio for each time scale.\n If " +
                        "you do not know it at different times please enter the same value for each");
                return false;
            }
        }else{
            if(numBolUnitsPerDay==null){
                setError("You must enter your average number of bolus units per day to work out" +
                        " an approximate Insulin to carbohydrate ratio.");
                return false;
            }
        }
        if(knowsISF){
            if(mornIsf==null||afteIsf==null||eveISF==null||nighISF==null){
                setError("You must enter your insulin sensitivity factor for each time scale.\n If " +
                        "you do not know it at different times please enter the same value for each");
                return false;
            }
        }else{
            if(numBasBolUnitsPerDay==null){
                setError("You must enter your total number of basal and bolus units per day to work out" +
                        " an approximate Insulin Sensitivity Factor.");
                return false;
            }
        }
        if(knowsICR){
           model.createInsulinToCarbModel(breakInsulin,breakCarbs,lunInsulin,lunCarbs,dinInsulin,
                   dinCarbs,nighInsulin,nighCarbs);
        }else{
            model.createInsulinToCarbModel(getICR());
        }
        if(knowsISF){
            model.createInsulinSensitivityModel(mornIsf,afteIsf,eveISF,nighISF);
        }else{
            model.createInsulinSensitivityModel(getISF());
        }
        return true;
    }
}
