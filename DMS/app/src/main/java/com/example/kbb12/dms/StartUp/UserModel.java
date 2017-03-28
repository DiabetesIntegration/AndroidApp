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

    public void removeObserver(ModelObserver observer){
        observers.remove(observer);
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println("getDate date value: " + date);
        return format.format(date);
    }

    public String formatDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println("getDate date value: " + date);
        return format.format(date);
    }

    public String formatDateTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = cal.getTime();
        String datetime = format.format(date);
        return datetime;
    }

    public void addToCalCount(Context context, int cal){
        db = new dbHelper(context);
        int calories;
        int id;
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
        String datetime = formatDateTime();

        db.insertActivityEntry(datetime, calories, activitytype, durhour, durmin);
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
    public void setActDateToChange(boolean change) {
        if(actDateToChange==change){
            return;
        }
        actDateToChange = change;
        notifyObservers();
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
        month++;
        System.out.println("datetaken: " + day + " " + month + " " + year);
        this.day=day;
        this.month=month;
        this.year=year;
        actDateToChange=false;

        notifyObservers();
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
        notifyObservers();
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
    public void saveActivity(Context context, double weight) {
        db = new dbHelper(context);

        int calories = calculateCalories(activitytype, weight, durhour, durmin);

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