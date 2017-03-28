package com.example.kbb12.dms.model.basalInsulinModel;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class BasalInsulinDose implements BasalInsulinEntry {
    private int hour;
    private int minute;
    private double dose;

    public BasalInsulinDose(){
        this.hour=0;
        this.minute=0;
        this.dose=0.0;
    }

    public BasalInsulinDose(int hour, int minute, double dose){
        this.hour=hour;
        this.minute=minute;
        this.dose=dose;
    }

    public BasalInsulinDose clone(){
        return new BasalInsulinDose(hour,minute,dose);
    }

    @Override
    public Integer getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public Integer getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public double getDose() {
        return dose;
    }


    public void setDose(double dose) {
        this.dose = dose;
    }

    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o.getClass().equals(this.getClass())){
            BasalInsulinEntry entry = (BasalInsulinEntry) o;
            return (this.hour==entry.getHour()&&this.minute==entry.getMinute()&&this.dose==entry.getDose());
        }
        return false;
    }

}
