package com.example.kbb12.dms.StartUp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient implements IIngredient {
    /*private String ingredientName;
    private List<String> nutrients;

    public Ingredient(String name, List<String> nutrients) {
        ingredientName = name;
        this.nutrients = nutrients;
    }

    @Override
    public void setIngredientName(String name) {
        ingredientName = name;
    }

    @Override
    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public void setNutritionalValues(List<String> n) {
        nutrients = n;
    }

    @Override
    public List<String> getNutritionalValues() {
        return nutrients;
    }

    @Override
    public String calculateCarbByWeight(String val) {
        double cVal = (Double.parseDouble(val)/Double.parseDouble(totalCarb))*Double.parseDouble(sugarCarb);
        ingredientCarb = cVal + "";
    }

    @Override
    public void calculateCarbByPercentage(String percent) {
        double packetAmount = Double.parseDouble(percent)*Double.parseDouble(packetWeight);
        double cVal = (packetAmount/Double.parseDouble(totalCarb))*Double.parseDouble(sugarCarb);
        ingredientCarb = cVal + "";
    }
    */



    private String ingName, carbAmount, units;
    private List<String> nutrientInfo;

    private String name, carbVal, sugarCarb, totalCarb, packetWeight, ingredientCarb;

    public Ingredient() {
        name = "";
        carbVal = "";
        sugarCarb = "";
        totalCarb = "";
        packetWeight = "";
        ingredientCarb = "";

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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void calculateCarbByWeight(String val) {
        double cVal = (Double.parseDouble(val)/Double.parseDouble(totalCarb))*Double.parseDouble(sugarCarb);
        ingredientCarb = cVal + "";
    }

    @Override
    public void calculateCarbByPercentage(String percent) {
        double packetAmount = Double.parseDouble(percent)*Double.parseDouble(packetWeight);
        double cVal = (packetAmount/Double.parseDouble(totalCarb))*Double.parseDouble(sugarCarb);
        ingredientCarb = cVal + "";
    }

    @Override
    public String getIngredientCarbVal() {
        return ingredientCarb;
    }

    @Override
    public void setSugarValue(String s) {
        sugarCarb = s;
    }

    @Override
    public String getSugarValue() {
        return sugarCarb;
    }

    @Override
    public void setCarbVal(String carbs) {
        carbVal = carbs;
    }

    @Override
    public String getCarbVal() {
        return carbVal;
    }

    @Override
    public void setPacketCarb(String p) {
        totalCarb = p;
    }

    @Override
    public String getPacketCarb() {
        return totalCarb;
    }

    @Override
    public void setPacketWeightValue(String pw) {
        packetWeight = pw;
    }

    @Override
    public String getPacketWeightValue() {
        return packetWeight;
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
        //nutrientInfo.add(i,val);
        //Log.i("tester", nutrientInfo.get(0) + "," + nutrientInfo.get(1) + "," + nutrientInfo.get(2) + "," + nutrientInfo.get(3) + "!");
    }

    @Override
    public void setNutritionalValues(List<String> n) {
        nutrientInfo = n;
    }

    @Override
    public List<String> getNutritionalValues() {
        return nutrientInfo;
    }

    @Override
    public void setAmountUnits(String unit) {
        units = unit;
    }

    @Override
    public String getAmountUnits() {
        return units;
    }

    @Override
    public void setCarbAmount(String val) {
        carbAmount = val;
    }

    //@Override
    //public void calculateCarbAmount(String val) {
    //    if(getAmountUnits().equals("g")) {
    //        setCarbAmountByWeight(val);
    //    }
    //    else {
    //        setCarbAmountByPercent(val);
    //    }
    //}

    //@Override
    public void setCarbAmountByWeight(String weight) {
        double cVal = (Double.parseDouble(weight)/Double.parseDouble(nutrientInfo.get(1)))*Double.parseDouble(nutrientInfo.get(2));
        carbAmount = cVal + "";
    }

    //@Override
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
