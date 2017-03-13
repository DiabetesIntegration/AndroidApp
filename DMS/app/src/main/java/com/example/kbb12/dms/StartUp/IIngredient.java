package com.example.kbb12.dms.StartUp;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IIngredient {

    public void setName(String name);

    public String getName();

    public void calculateCarbByWeight(String val);

    public void calculateCarbByPercentage(String percent);

    public String getIngredientCarbVal();

    public void setSugarValue(String s);

    public String getSugarValue();

    public void setCarbVal(String carbs);

    public String getCarbVal();

    public void setPacketCarb(String p);

    public String getPacketCarb();

    public void setPacketWeightValue(String pw);

    public String getPacketWeightValue();







    public void setIngredientName(String name);
    public String getIngredientName();
    public void addCustomNutrition(String val, int i);
    public void setNutritionalValues(List<String> n);
    public List<String> getNutritionalValues();
    public void setAmountUnits(String unit);
    public String getAmountUnits();
    public void setCarbAmount(String val);
    //public void calculateCarbAmount(String val);
    public void setCarbAmountByWeight(String weight);
    public void setCarbAmountByPercent(String percent);
    public String getCarbAmount();
    }
