package com.example.kbb12.dms.StartUp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient implements IIngredient {
    private String ingName, carbAmount, units;
    private List<String> nutrientInfo;

    public Ingredient() {

        ingName = "";
        carbAmount = "";
        units = "g";
        nutrientInfo = new ArrayList<String>();
        for(int i = 0; i < 4; i++) {
            nutrientInfo.add("");
        }
    }

    public Ingredient(String name, List<String> n) {
        ingName = name;
        nutrientInfo = n;
        units = "g";
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
    public void addCustomNutrition(String val, int i) {
        nutrientInfo.set(i,val);
    }


    @Override
    public List<String> getNutritionalValues() {
        return nutrientInfo;
    }

    @Override
    public void setCarbAmount(String val) {
        carbAmount = val;
    }


    @Override
    public void setCarbAmountByWeight(String weight) {
        double cVal = (Double.parseDouble(weight)/Double.parseDouble(nutrientInfo.get(1)))*Double.parseDouble(nutrientInfo.get(2));
        carbAmount = cVal + "";
    }

    @Override
    public void setCarbAmountByPercent(String percent) {
        double packetAmount = (Double.parseDouble(percent)/100)*Double.parseDouble(nutrientInfo.get(3));
        double cVal = (packetAmount/Double.parseDouble(nutrientInfo.get(1)))*Double.parseDouble(nutrientInfo.get(2));
        carbAmount = cVal + "";

    }

    @Override
    public String getCarbAmount() {
        return carbAmount;
    }


}
