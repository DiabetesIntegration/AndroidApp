package com.example.kbb12.dms.StartUp;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

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
public class UserModel implements ITemplateModel, IFitnessInfo {

    private String exampleData;

    private List<ModelObserver> observers;

    private float calories;
    private dbHelper db;

    public UserModel(String exampleData){
        this.exampleData=exampleData;
        observers= new ArrayList<>();
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
        float calories;
        int id;
        String date = getDate();
        if(db.EntryExists(date)){
            Cursor cur = db.getEntry(db.findEntry(date));
            cur.moveToFirst();
            id = cur.getInt(cur.getColumnIndex("id"));
            calories = cur.getFloat(cur.getColumnIndex("calories"));
            calories += cal;

            db.updateEntry(id, date, calories);
        } else {
            db.insertEntry(date, cal);
        }
    }

    @Override
    public float getCalCount() {
        return calories;
    }
}
