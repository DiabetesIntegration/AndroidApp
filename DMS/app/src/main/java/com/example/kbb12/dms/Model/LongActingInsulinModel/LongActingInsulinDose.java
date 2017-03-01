package com.example.kbb12.dms.Model.LongActingInsulinModel;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class LongActingInsulinDose implements LongActingInsulinEntry {
    private int hour;
    private int minute;
    private double dose;

    public LongActingInsulinDose(){
        this.hour=0;
        this.minute=0;
        this.dose=0.0;
    }

    public LongActingInsulinDose(int hour,int minute,double dose){
        this.hour=hour;
        this.minute=minute;
        this.dose=dose;
    }

    public LongActingInsulinDose clone(){
        return new LongActingInsulinDose(hour,minute,dose);
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
            LongActingInsulinEntry entry = (LongActingInsulinEntry) o;
            return (this.hour==entry.getHour()&&this.minute==entry.getMinute()&&this.dose==entry.getDose());
        }
        return false;
    }

}
