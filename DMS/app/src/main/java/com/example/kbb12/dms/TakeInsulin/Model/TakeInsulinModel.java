package com.example.kbb12.dms.takeInsulin.model;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;
import com.example.kbb12.dms.model.TakeInsulinMainModel;
import com.example.kbb12.dms.startUp.ModelObserver;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinModel implements TakeInsulinReadWriteModel {

    private TakeInsulinMainModel model;
    private Double recommended;
    private Double actual;
    private InsulinType typeTaken;
    private InsulinType typeRecommended;
    private Integer day;
    private Integer month;
    private Integer year;
    private boolean dateToChange;
    private boolean timeToChange;
    private Integer hour;
    private Integer minute;
    private String errorMessage;
    private ModelObserver observer;
    private double targetBG=5.5;

    public TakeInsulinModel(TakeInsulinMainModel model){
        this.model=model;
        Calendar now = Calendar.getInstance();
        day=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        year=now.get(Calendar.YEAR);
        hour=now.get(Calendar.HOUR_OF_DAY);
        minute=now.get(Calendar.MINUTE);
        dateToChange=false;
        timeToChange=false;
        bolusRecommendation();
        if(recommended==0.0) {
            basalRecommendation();
        }
    }

    private void basalRecommendation(){
        //Gets most recent untaken expected dose before now and sets
        //all the entries before that dose to taken because it must
        //now be too late to take them.
        BasalInsulinEntry entry = model.getLatestBasalRecommendation();
        if(null==entry){
            recommended=0.0;
            actual=0.0;
            typeTaken=InsulinType.NOT_SET;
            typeRecommended=InsulinType.NOT_SET;
        }else{
            recommended=entry.getDose();
            actual=entry.getDose();
            typeTaken=InsulinType.BASAL;
            typeRecommended=InsulinType.BASAL;
        }
    }

    private void bolusRecommendation(){
        double correctionDose=0.0;
        double carbInsulin=((double)model.getRecentCarbs())/model.getCurrentICR();
        Double bloodGlucose=model.getCurrentBG();
        if(bloodGlucose==null){
            setError("There is not a recent enough blood glucose value to give accurate" +
                    " recommendations. Please scan your Libre sensor.");
            return;
        }
        if(!inRange(bloodGlucose)) {
            correctionDose=(targetBG-bloodGlucose)*model.getCurrentISF();
        }
        recommended=carbInsulin+correctionDose;
        if(recommended<0){
            recommended=0.0;
            warningMessage();
        }
        actual=recommended;
        typeTaken=InsulinType.BASAL;
        typeRecommended=InsulinType.BASAL;;
    }

    private void warningMessage(){
        setError("We do not calculate you have eaten enough carbs to balance your low blood" +
                " glucose reading. This feature is still in beta though so please use caution" +
                " if eating more carbohydrates.");
    }

    private boolean inRange(double bG){
        return bG>=5.0&&bG<=7.0;
    }

    @Override
    public void setError(String errorMessage) {
        if((!(this.errorMessage==null))&&this.errorMessage.equals(errorMessage)){
            return;
        }
        this.errorMessage=errorMessage;
        notifyObserver();
    }

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
    }

    private void notifyObserver() {
        if(observer!=null) {
            observer.update();
        }
    }

    @Override
    public Double getRecommendedUnits() {
        return recommended;
    }

    @Override
    public InsulinType getRecommendedType() {
        return typeRecommended;
    }

    @Override
    public Double getAmountTaken() {
        return actual;
    }

    @Override
    public int getDayTaken() {
        return day;
    }

    @Override
    public int getMonthTaken() {
        return month;
    }

    @Override
    public int getYearTaken() {
        return year;
    }

    @Override
    public int getHourTaken() {
        return hour;
    }

    @Override
    public int getMinuteTaken() {
        return minute;
    }

    @Override
    public boolean getTimeToChange() {
        return timeToChange;
    }

    @Override
    public boolean getDateToChange() {
        return dateToChange;
    }

    @Override
    public InsulinType getTypeTaken() {
        return typeTaken;
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public void setAmountTaken(Double amountTaken) {
        if(this.actual.equals(amountTaken)){
            return;
        }
        this.actual=amountTaken;
        notifyObserver();
    }

    @Override
    public void setTimeToChange() {
        if(timeToChange){
            return;
        }
        timeToChange=true;
        notifyObserver();
    }

    @Override
    public void setDateToChange() {
        if(dateToChange){
            return;
        }
        dateToChange=true;
        notifyObserver();
    }

    @Override
    public void setTypeTaken(InsulinType typeTaken) {
        if(this.typeTaken.equals(typeTaken)){
            return;
        }
        this.typeTaken=typeTaken;
        notifyObserver();
    }

    @Override
    public void setDateTaken(int day, int month, int year) {
        if(!dateToChange){
            return;
        }
        this.day=day;
        this.month=month;
        this.year=year;
        dateToChange=false;
        notifyObserver();
    }

    @Override
    public void setTimeTaken(int hour, int minute) {
        if(!timeToChange){
            return;
        }
        this.hour=hour;
        this.minute=minute;
        timeToChange=false;
        notifyObserver();
    }

    @Override
    public void takeInsulin(){
        switch (typeTaken){
            case BASAL:
                model.takeInsulin(year,month,day,hour,minute,actual,true);
                break;
            case SHORT_ACTING:
                model.takeInsulin(year,month,day,hour,minute,actual,false);
                break;
            case NOT_SET:
                //This should be unreachable but is here to be extra safe
                setError("You must set which type of insulin you are taking");
                break;
        }
    }
}
