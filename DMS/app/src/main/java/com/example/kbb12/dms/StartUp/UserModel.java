package com.example.kbb12.dms.StartUp;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.kbb12.dms.AddFitness.IAddFitness;
import com.example.kbb12.dms.FitnessInfo.IFitnessInfo;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel, IFitnessInfo, IAddFitness {

    private String exampleData;
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

    private List<ModelObserver> observers;

    //private float calories;
    private dbHelper db;

    public UserModel(String exampleData){
        this.exampleData=exampleData;
        observers= new ArrayList<>();

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

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    public void registerObserver(ModelObserver observer)
    {
        observers.add(observer);
        notifyObservers();
    }

    private void notifyObservers(){
        for(ModelObserver observer:observers){
            observer.update();
        }
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        Format format = new SimpleDateFormat("dd.MM.yyyy");
        Date date = calendar.getTime();
        return format.format(date);
    }

    public String formatDate() {
        String tday = "0";
        String tmonth = "0";
        String date;
        if(day<10){
            tday = "0" + day;
        }
        if(month<10){
            tmonth = "0" + month;
        }
        date = tday + "." + tmonth + "." + year;
        return date;
    }

    public String formatTime() {
        String thours = "0";
        String tmins = "0";
        String time;
        if(hour<10){
            thours = "0" + hour;
        }
        if(minute<10){
            tmins = "0" + minute;
        }
        time = thours + ":" + tmins;
        return time;
    }

    public void addToCalCount(Context context, int cal){
        db = new dbHelper(context);
        int calories;
        int id;
        //String date = getDate();
        String date = formatDate();
        if(db.DailyEntryExists(date)){
            Cursor cur = db.getDailyEntry(db.findDailyEntry(date));
            cur.moveToFirst();
            id = cur.getInt(cur.getColumnIndex("id"));
            calories = cur.getInt(cur.getColumnIndex("calories"));
            calories += cal;
            cur.close();

            db.updateDailyEntry(id, date, calories);
        } else {
            db.insertDailyEntry(date, cal);
        }
    }

    public void addActivityToDB(Context context, int calories){
        db = new dbHelper(context);
        String date = formatDate();
        String time = formatTime();

        db.insertActivityEntry(date, time, calories, activitytype, durhour, durmin);
    }

    @Override
    public int getCalCount(Context context) {
        int calories = 0;
        db = new dbHelper(context);
        String date = getDate();
        if (db.DailyEntryExists(date)){
            Cursor cur = db.getDailyEntry(db.findDailyEntry(date));
            cur.moveToFirst();
            calories = cur.getInt(cur.getColumnIndex("calories"));
        }
        return calories;
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
    public void setActDateToChange() {
        if(actDateToChange){
            return;
        }
        actDateToChange=true;
        notifyObservers();
    }

    @Override
    public boolean getActDateToChange() {
        return actDateToChange;
    }

    @Override
    public void setActTimeToChange() {
        if(actTimeToChange){
            return;
        }
        actTimeToChange=true;
        notifyObservers();
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
        this.day=day;
        this.month=month;
        this.year=year;
        actDateToChange=false;
        notifyObservers();
    }

    /*@Override
    public String getDateTaken() {
        return null;
    }*/

   /* @Override
    public void setDayTaken(int day) {
        this.day=day;
    }

    @Override
    public void setMonthTaken(int month) {
        this.month=month;
    }

    @Override
    public void setYearTaken(int year) {
        this.year=year;
    }*/

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
        this.hour=hour;
        this.minute=minute;
        actTimeToChange=false;
        notifyObservers();
    }

    /*@Override
    public String getTimeTaken() {
        return null;
    }*/

    /*@Override
    public void setHourTaken(int hour) {

    }

    @Override
    public void setMinuteTaken(int minute) {

    }*/

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
    public void saveActivity(Context context, String activity, int year, int month, int day, int hours, int mins, int durhour, int durmin, double weight) {
        db = new dbHelper(context);

        int calories = calculateCalories(activity, weight, durhour, durmin);

        addActivityToDB(context, calories);
        addToCalCount(context, calories);

        resetvals();
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

    public int calculateCalories(String activity, double weight, int hours, int minutes){
        int length = (hours*60) + minutes;
        int calories = 0;
        switch (activity){
            case "Walking":
                calories = (int) ((0.055*length*weight)+0.5d);
                break;
            case "Running":
                calories = (int) ((0.183*length*weight)+0.5d);
                break;
            case "Cycling":
                calories = (int) ((0.133*length*weight)+0.5d);
                break;
        }
        return calories;
    }
}
