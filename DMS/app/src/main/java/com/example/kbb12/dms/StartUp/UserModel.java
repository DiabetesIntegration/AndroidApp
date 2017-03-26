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
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private boolean actDateToChange;
    private boolean actTimeToChange;

    private List<ModelObserver> observers;

    //private float calories;
    private dbHelper db;

    public UserModel(String exampleData){
        this.exampleData=exampleData;
        observers= new ArrayList<>();

        Calendar now = Calendar.getInstance();
        day=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        year=now.get(Calendar.YEAR);
        hour=now.get(Calendar.HOUR_OF_DAY);
        minute=now.get(Calendar.MINUTE);
        actDateToChange=false;
        actTimeToChange=false;
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

    public void addToCalCount(Context context, float cal){
        db = new dbHelper(context);
        double calories;
        int id;
        String date = getDate();
        if(db.EntryExists(date)){
            Cursor cur = db.getEntry(db.findEntry(date));
            cur.moveToFirst();
            id = cur.getInt(cur.getColumnIndex("id"));
            calories = cur.getDouble(cur.getColumnIndex("calories"));
            calories += cal;
            cur.close();

            db.updateEntry(id, date, calories);
        } else {
            db.insertEntry(date, cal);
        }
    }

    @Override
    public double getCalCount(Context context) {
        double calories = 0;
        db = new dbHelper(context);
        String date = getDate();
        if (db.EntryExists(date)){
            Cursor cur = db.getEntry(db.findEntry(date));
            cur.moveToFirst();
            calories = cur.getDouble(cur.getColumnIndex("calories"));
        }
        return calories;
    }
}
