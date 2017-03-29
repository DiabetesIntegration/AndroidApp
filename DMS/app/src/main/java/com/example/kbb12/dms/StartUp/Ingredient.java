package com.example.kbb12.dms.startUp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient implements IIngredient {
    private String ingName, carbAmount, units;
    private String nutrientInfo[];
    public boolean exists;

    public Ingredient() {

        ingName = "";
        carbAmount = "";
        units = "g";
        nutrientInfo = new String[3];
        for(int i = 0; i < 3; i++) {
            nutrientInfo[i] = "";
        }
        exists = false;
    }

    public Ingredient(String name, String n[]) {
        ingName = name;
        nutrientInfo = n;
        units = "g";
        exists = true;
    }

    @Override
    public boolean equals(Object i) {
        if(i instanceof IIngredient) {
            if(ingName.equals(((IIngredient) i).getIngredientName()) && nutrientInfo.equals(((IIngredient) i).getNutritionalValues())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setIngredientName(String name) {
        ingName = name;
    }

    @Override
    public String getIngredientName() {
        return ingName;
    }

    @Override
    public void addCustomNutrition(String vals[]) {
        //nutrientInfo.set(i,val);
        //nutrientInfo[i] = val;
        nutrientInfo = vals;
    }


    @Override
    public String[] getNutritionalValues() {
        return nutrientInfo;
    }

    @Override
    public void setCarbAmount(String val) {
        carbAmount = val;
    }


    @Override
    public void setCarbAmountByWeight(String weight) {
        double cVal = (Double.parseDouble(weight)/Double.parseDouble(nutrientInfo[1]))*Double.parseDouble(nutrientInfo[0]);
        carbAmount = cVal + "";
    }

    @Override
    public void setCarbAmountByPercent(String percent) {
        double packetAmount = (Double.parseDouble(percent)/100)*Double.parseDouble(nutrientInfo[2]);
        double cVal = (packetAmount/Double.parseDouble(nutrientInfo[1]))*Double.parseDouble(nutrientInfo[0]);
        carbAmount = cVal + "";

    }

    @Override
    public String getCarbAmount() {
        return carbAmount;
    }

    @Override
    public String getUnit() {
        return units;
    }

    @Override
    public void setUnit(String unit) {
        units = unit;
    }

    @Override
    public void setExists(boolean exist) {
        exists = exist;
    }

    @Override
    public boolean ingredientExists() {
        return exists;
    }


}
