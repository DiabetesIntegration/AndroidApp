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

    public Meal(String name,int numCarbs){
        mealName=name;
        this.ingredients=new ArrayList<>();
        ingredients.add(new Ingredient.BasicIngredient());
        this.amounts=new ArrayList<>();
        amounts.add((double)numCarbs);
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

    @Override
    public void addIngredient(IIngredient ingredient, double amount) {
        ingredients.add(ingredient);
        amounts.add(amount);
    }

    @Override
    public void setAmountOf(IIngredient ingredient, double amount) {
        for(int i=0;i<ingredients.size();i++){
            if(ingredient.getName().equals(ingredients.get(i).getName())){
                amounts.set(i,amount);
                return;
            }
        }
        ingredients.add(ingredient);
        amounts.add(amount);
    }

    @Override
    public Double getAmountOf(IIngredient ingredient) {
        for(int i=0;i<ingredients.size();i++){
            if(ingredient.getName().equals(ingredient.getName())){
                return amounts.get(i);
            }
        }
        return 0.0;
    }

    @Override
    public void removeIngredient(int index) {
        ingredients.remove(index);
        amounts.remove(index);
    }

    @Override
    public void removeIngredient(IIngredient ingredient) {
        for(int i=0;i<ingredients.size();i++){
            if(ingredients.get(i).getName().equals(ingredient.getName())){
                ingredients.remove(i);
                amounts.remove(i);
                return;
            }
        }
    }

    @Override
    public double getNumCarbs() {
        double totalCarbs=0.0;
        for(int i=0;i<ingredients.size();i++){
            totalCarbs+=(ingredients.get(i).getCarbsPerHundredG()/100)*amounts.get(i);
        }
        return totalCarbs;
    }

    @Override
    public boolean isCustomCarbMeal() {
        return (ingredients.size()==1&&ingredients.get(0).getName().equals("Custom"));
    }
}
