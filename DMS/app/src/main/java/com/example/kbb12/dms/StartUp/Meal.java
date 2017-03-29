package com.example.kbb12.dms.StartUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Meal implements IMeal {
    private String mealName, totalMealCarbs, carbsConsumed, mealAmount;
    private List<IIngredient> ingredients;
    private boolean isCustom;



    public Meal() {
        mealName = "";
        totalMealCarbs = "";
        carbsConsumed = "";
        mealAmount = "";
        ingredients = new ArrayList<IIngredient>();
        isCustom = false;
    }



    public Meal(String name, List<IIngredient> m) {
        mealName = name;
        ingredients = new ArrayList<IIngredient>(m);
        totalMealCarbs = "";
        carbsConsumed = "";
        mealAmount = "";
    }

    private String calculateTotalCarbs() {
        double tCarbs = 0.0;
        for(int i = 0; i < ingredients.size(); i++) {
            tCarbs += Double.parseDouble(ingredients.get(i).getCarbAmount());
        }
        return tCarbs + "";
    }



    @Override
    public void setMealName(String name) {
        mealName = name;
    }

    @Override
    public String getMealName() {
        return mealName;
    }

    @Override
    public String getTotalCarbs() {
        totalMealCarbs = calculateTotalCarbs();
        return totalMealCarbs;
    }

    @Override
    public void setTotalMealCarbs(String val) {
        totalMealCarbs = val;
    }

    @Override
    public String getTotalMealCarbs() {
        return totalMealCarbs;
    }

    @Override
    public void setCarbsEaten() {
        carbsConsumed = ((Double.parseDouble(getMealAmount())/100)*Double.parseDouble(totalMealCarbs)) + "";
    }

    @Override
    public String getCarbsEaten() {
        return carbsConsumed;
    }

    @Override
    public void setMealAmount(String amount) {
        mealAmount = amount;
    }

    @Override
    public String getMealAmount() {
        return mealAmount;
    }

    @Override
    public List<IIngredient> getAllIngredients() {
        return ingredients;
    }

    @Override
    public boolean setIngredients(List<IIngredient> ing) {
        ingredients = new ArrayList<IIngredient>(ing);
        return true;
    }




    @Override
    public void setCustomCarbMeal(boolean carb) {
        isCustom = carb;
    }

    @Override
    public boolean getCustomCarbMeal() {
        return isCustom;
    }

    @Override
    public void setCustomCarbsEaten(String amount) {
        carbsConsumed = amount;
    }

    @Override
    public String getCustomCarbsEaten() {
        return carbsConsumed;
    }

}
