package com.example.kbb12.dms.StartUp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.kbb12.dms.AddIngredient.IAddIngredient;
import com.example.kbb12.dms.CustomIngredient.IAddCustomIngredient;
import com.example.kbb12.dms.MealList.IMealList;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel, IMealList, IAddIngredient, IAddCustomIngredient {

    private String exampleData;

    private List<Meal> meals;

    //-------------------------------------------
    //CustomIngredient variables
    private String iName, iCarb, iPacket, iSugar;
    //-------------------------------------------

    private List<ModelObserver> observers;

    public UserModel(String exampleData, List<Meal> meals){
        this.exampleData=exampleData;
        observers= new ArrayList<>();
        this.meals = meals;

        //-------------------------------------------
        //CustomIngredient variables
        iName = "";
        iCarb = "";
        iPacket = "";
        iSugar = "";
        //-------------------------------------------
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



    //------------------------------------------------------------------
    //IMealList method

    @Override
    public String setEmptyString() {
        if(savedMeals()) {
            return "";
        }
        return "No saved meals yet";
    }

    private boolean savedMeals() {
        if(meals.size() == 0) {
            return false;
        }
        return true;
    }

    //-------------------------------------------------------------------




    //------------------------------------------------------------------
    //IAddCustomIngredient method
    @Override
    public boolean checkEntry(String entry) {
        if(entry.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void setItemName(String name) {
        iName = name;
    }

    @Override
    public String getItemName() {
        return iName;
    }

    @Override
    public void setCarbVal(String name) {
        iCarb = name;
    }

    @Override
    public String getCarbVal() {
        return iCarb;
    }

    @Override
    public void setPacketVal(String name) {
        iPacket = name;
    }

    @Override
    public String getPacketVal() {
        return iPacket;
    }

    @Override
    public void setSugarVal(String name) {
        iSugar = name;
    }

    @Override
    public String getSugarVal() {
        return iSugar;
    }

    //------------------------------------------------------------------
}
