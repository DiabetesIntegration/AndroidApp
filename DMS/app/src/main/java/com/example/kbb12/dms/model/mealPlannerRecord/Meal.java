package com.example.kbb12.dms.model.mealPlannerRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Meal implements IMeal {
    private String mealName;
    private List<IIngredient> ingredients;
    private List<Double> amounts;

    public Meal() {
        mealName = "";
        ingredients = new ArrayList<>();
        amounts = new ArrayList<>();
    }

    public Meal(String name, List<IIngredient> ingredients, List<Double> amounts) {
        mealName = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.amounts = new ArrayList<>(amounts);
    }

    @Override
    public String getName() {
        return mealName;
    }

    @Override
    public List<IIngredient> getIngredients() {
        return ingredients;
    }

    @Override
    public List<Double> getAmounts() {
        return amounts;
    }
}
