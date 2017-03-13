package com.example.kbb12.dms.StartUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Meal implements IMeal {
    private String mealName, totalMealCarbs, carbsConsumed, mealAmount;
    private List<IIngredient> ingredients;



    public Meal() {
        mealName = "";
        totalMealCarbs = "";
        carbsConsumed = "";
        mealAmount = "";
        ingredients = new ArrayList<IIngredient>();
    }



    public Meal(String name, List<IIngredient> m) {
        mealName = name;
        ingredients = m;
        totalMealCarbs = calculateTotalCarbs();
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

}
