package com.example.kbb12.dms.addFitness.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.AddFitnessMainModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFitnessModel extends BaseModel implements IAddFitness {

    private String activitytype;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int durhour;
    private int durmin;
    private boolean actDateToChange;
    private boolean actTimeToChange;
    private AddFitnessMainModel model;

    public AddFitnessModel(AddFitnessMainModel model){
        activitytype="Walking";
        Calendar now = Calendar.getInstance();
        day=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        year=now.get(Calendar.YEAR);
        hour=now.get(Calendar.HOUR_OF_DAY);
        minute=now.get(Calendar.MINUTE);
        actDateToChange=false;
        actTimeToChange=false;
        durhour=0;
        durmin=0;
        this.model=model;
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println("getDate date value: " + date);
        return format.format(date);
    }


    public void addToCalCount(int cal){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        model.addToCalCount(calendar,cal);
    }

    @Override
    public void setActivityType(String activity) {
        activitytype=activity;
    }

    @Override
    public String getActivityType() {
        return activitytype;
    }

    @Override
    public void setActDateToChange(boolean change) {
        if(actDateToChange==change){
            return;
        }
        actDateToChange = change;
        notifyObserver();
    }

    @Override
    public boolean getActDateToChange() {
        return actDateToChange;
    }

    @Override
    public void setActTimeToChange(boolean change) {
        if(actTimeToChange==change){
            return;
        }
        actTimeToChange=change;
        notifyObserver();
    }

    @Override
    public boolean getActTimeToChange() {
        return actTimeToChange;
    }

    @Override
    public void setDateTaken(int year, int month, int day) {
        if(!actDateToChange){
            return;
        }
        month++;
        System.out.println("datetaken: " + day + " " + month + " " + year);
        this.day=day;
        this.month=month;
        this.year=year;
        actDateToChange=false;
        notifyObserver();
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
    public void setTimeTaken(int hour, int minute) {
        if(!actTimeToChange){
            return;
        }
        System.out.println("timetaken: " + hour + " " + minute);
        this.hour=hour;
        this.minute=minute;
        actTimeToChange=false;
        notifyObserver();
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
    public void setDurHours(int hour) {
        durhour=hour;
    }

    @Override
    public int getDurhours() {
        return durhour;
    }

    @Override
    public void setDurMins(int mins) {
        durmin=mins;
    }

    @Override
    public int getDurMins() {
        return durmin;
    }

    @Override
    public void saveActivity() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);
        model.saveActivity(calendar,activitytype,durhour,durmin);
        resetvals();
        notifyObserver();
    }

    private void resetvals() {
        activitytype="Walking";
        Calendar now = Calendar.getInstance();
        day=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        year=now.get(Calendar.YEAR);
        hour=now.get(Calendar.HOUR_OF_DAY);
        minute=now.get(Calendar.MINUTE);
        actDateToChange=false;
        actTimeToChange=false;
        durhour=0;
        durmin=0;
    }
}