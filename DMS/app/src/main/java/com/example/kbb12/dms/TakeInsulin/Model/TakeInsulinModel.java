package com.example.kbb12.dms.takeInsulin.model;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
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
    private String calculationDescription;

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
        calculationDescription="";
        if(!bolusRecommendation()) {
            basalRecommendation();
        }
    }

    private void basalRecommendation(){
        //Gets most recent untaken expected dose before now and sets
        //all the entries before that dose to taken because it must
        //now be too late to take them.
        BasalInsulinEntry entry = model.getLatestBasalRecommendation();
        if(null==entry){
            calculationDescription+="\nYou are not over-due for a basal insulin dose";
            recommended=0.0;
            actual=0.0;
            typeTaken=InsulinType.NOT_SET;
            typeRecommended=InsulinType.NOT_SET;
        }else{
            calculationDescription+="\nYou are over-due for your "+entry.getHour()+
                    ":"+entry.getMinute()+" dose of basal insulin.";
            recommended=entry.getDose();
            actual=entry.getDose();
            typeTaken=InsulinType.BASAL;
            typeRecommended=InsulinType.BASAL;
        }
    }

    /*
    Sets up the bolus recommendation and returns true if a bolus recommendation is given. Returns
    false if there isn't a bolus recommendation to be given.
     */
    private boolean bolusRecommendation(){
        double correctionDose=0.0;
        double carbInsulin=((double)model.getRecentCarbs())*model.getCurrentICR();
        Double bloodGlucose=model.getCurrentBG();
        if(model.hasTakenBolusInsulinRecently()){
            calculationDescription+="You have injected bolus insulin recently. We do not recommend" +
                    "doing so again until this has fully taken effect.";
            recommended=0.0;
            actual=0.0;
            return false;
        }
        if(bloodGlucose==null){
            recommended=0.0;
            actual=0.0;
            calculationDescription+="There is not a recent enough blood glucose value to" +
                    " give accurate bolus recommendations. Please scan your Libre sensor and return.";
            return false;
        }
        if(!inRange(bloodGlucose)) {
            calculationDescription+=String.format("Your current blood glucose %.2fmmol/l is outwith the " +
                    "recommended range of 4-7mmol/l",model.getCurrentBG());
            calculationDescription+=String.format("\nYour current Insulin Sensitivity Factor is %.2f",model.getCurrentISF());
            correctionDose=(bloodGlucose-targetBG)/model.getCurrentISF();
            calculationDescription+=String.format("\nGiving a correction dose of:%.2f\n",correctionDose);
        }
        recommended=carbInsulin+correctionDose;
        if(recommended<0){
            recommended=0.0;
            actual=0.0;
            calculationDescription="We do not calculate that "+model.getRecentCarbs()+
                    " carbohydrates is enough to balance your low blood glucose reading of "+
                    bloodGlucose+"mmol/l";
            return false;
        }
        recommended=Double.parseDouble(String.format("%.2f",recommended));
        actual=recommended;
        typeTaken=InsulinType.BOLUS;
        typeRecommended=InsulinType.BOLUS;
        calculationDescription+=String.format("Current Insulin to Carb ratio= 1:%.2f",(1/model.getCurrentICR()));
        calculationDescription+="\nCarbs eaten recently: "+model.getRecentCarbs();
        calculationDescription+=String.format("\nCarbohydrate Correction=%.2f",carbInsulin);
        return recommended!=0.0;
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
    public String getCalculationDescription() {
        return calculationDescription;
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
        Calendar time =Calendar.getInstance();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month);
        time.set(Calendar.DAY_OF_MONTH,day);
        time.set(Calendar.HOUR,hour);
        time.set(Calendar.MINUTE,minute);
        switch (typeTaken){
            case BASAL:
                model.takeInsulin(time,actual,true);
                break;
            case BOLUS:
                model.takeInsulin(time,actual,false);
                break;
            case NOT_SET:
                //This should be unreachable but is here to be extra safe
                setError("You must set which type of insulin you are taking");
                break;
        }
    }


}
