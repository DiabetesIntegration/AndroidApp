package com.example.kbb12.dms.StartUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Meal {
    private String name;
    private double carbVal;
    private double amountEaten;
    private List<Ingredient> meal;

    public Meal(String name) {
        this.name = name;
        carbVal = 0.0;
        amountEaten = 0.0;
        meal = new ArrayList<Ingredient>();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ing) {
        meal.add(ing);
    }

    public void calculateCarbVal(double val) {
        double totCarbs = 0.0;
        for(int i = 0; i < meal.size(); i++) {
            totCarbs += meal.get(i).getCarbVal();
        }
        carbVal = totCarbs*val;
    }

    public double getCarbValue() {
        return carbVal;
    }

    public void setAmountEaten(double percentage) {
        amountEaten = percentage;
    }

    public void calculateAmountAsCarb() {
        carbVal = carbVal*amountEaten;
    }
}
