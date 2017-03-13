package com.example.kbb12.dms.StartUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Meal implements IMeal {
    private String name;
    private String carbVal;
    private String amountEaten;
    private List<IIngredient> meal;



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
        this.name = name;
        carbVal = "";
        amountEaten = "";
        meal = m;



        mealName = name;
        ingredients = m;
        totalMealCarbs = calculateTotalCarbs();
        carbsConsumed = "";
        mealAmount = "";


    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(IIngredient ing) {
        meal.add(ing);
    }

    @Override
    public List<IIngredient> getIngredients() {
        return meal;
    }

    public void calculateCarbVal(String percentage) {
        double totCarbs = 0.0;
        for(int i = 0; i < meal.size(); i++) {
            totCarbs += Double.parseDouble(meal.get(i).getCarbVal());
        }
        carbVal = (totCarbs*Double.parseDouble(percentage)) + "";
    }

    public String getCarbValue() {
        return carbVal;
    }

    public void setAmountEaten(String percentage) {
        amountEaten = percentage;
    }

    public void calculateAmountAsCarb() {
        carbVal = (Double.parseDouble(carbVal)*Double.parseDouble(amountEaten)) + "";
    }

    /*
    private String mealName;
    private List<Ingredient> ingredients;

    public Meal(String name) {
        mealName = name;
    }



    @Override
    public String getMealName() {
        return mealName;
    }

    @Override
    public String getTotalCarbs() {
        double totalCarbs = 0.0;
        for(int i = 0; i < ingredients.size(); i++) {
            totalCarbs += Double.parseDouble(ingredients.get(i).g);
        }
        return totalCarbs + "";
    }

    @Override
    public boolean addIngredient(String name, List<String> nutrients) {
        return false;
    }

    @Override
    public boolean removeIngredient(String name) {
        return false;
    }

    @Override
    public boolean editIngredient(String name) {
        return false;
    }*/




















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

    @Override
    public boolean addIngredient(String name, List<String> nutrients) {
        IIngredient i = new Ingredient(name, nutrients);
        ingredients.add(i);
        return true;
    }

    @Override
    public boolean removeIngredient(int i) {
        ingredients.remove(i);
        return true;
    }
}
