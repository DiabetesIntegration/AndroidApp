package com.example.kbb12.dms.individualScreens.takeInsulin.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.TakeInsulinMainModel;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinModel extends BaseModel implements TakeInsulinReadWriteModel {

    private TakeInsulinMainModel model;
    private Double recommended;
    private Double actual;
    private InsulinType typeTaken;
    private InsulinType typeRecommended;
    private Calendar timeTaken;
    private boolean dateToChange;
    private boolean timeToChange;
    private double targetBG=6.0;
    private String calculationDescription;

    public TakeInsulinModel(TakeInsulinMainModel model){
        this.model=model;
        timeTaken = Calendar.getInstance();
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
            calculationDescription+="\nYou are not over-due for a basal insulin dose.";
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
                    " doing so again until this has fully taken effect.";
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
    public Calendar getTimeTaken(){
        return timeTaken;
    }

    @Override
    public boolean isTimeToChange() {
        return timeToChange;
    }

    @Override
    public boolean isDateToChange() {
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
    public void setAmountTaken(Double amountTaken) {
        if(this.actual.equals(amountTaken)){
            return;
        }
        this.actual=amountTaken;
        notifyObserver();
    }

    @Override
    public void setTimeToChange(boolean timeToChange) {
        if(this.timeToChange==timeToChange){
            return;
        }
        this.timeToChange=timeToChange;
        notifyObserver();
    }

    @Override
    public void setDateToChange(boolean dateToChange) {
        if(this.dateToChange==dateToChange){
            return;
        }
        this.dateToChange=dateToChange;
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
        timeTaken.set(Calendar.DAY_OF_MONTH,day);
        timeTaken.set(Calendar.MONTH,month);
        timeTaken.set(Calendar.YEAR,year);
        dateToChange=false;
        notifyObserver();
    }

    @Override
    public void setTimeTaken(int hour, int minute) {
        if(!timeToChange){
            return;
        }
        timeTaken.set(Calendar.HOUR,hour);
        timeTaken.set(Calendar.MINUTE,minute);
        timeToChange=false;
        notifyObserver();
    }

    @Override
    public void takeInsulin(){
        switch (typeTaken){
            case BASAL:
                model.takeInsulin(timeTaken,actual,true);
                break;
            case BOLUS:
                model.takeInsulin(timeTaken,actual,false);
                break;
            case NOT_SET:
                //This should be unreachable but is here to be extra safe
                setError("You must set which type of insulin you are taking");
                break;
        }
    }


}
