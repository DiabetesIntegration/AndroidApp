package com.example.kbb12.dms.StartUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/10/2017.
 */
public class MealPlanner implements IMealPlanner {
    private List<IMeal> savedMeals;
    private List<IIngredient> activeIngredients;
    private String units;
    private String activeMealName;
    //private boolean grams;

    public MealPlanner(List<IMeal> meals) {
        savedMeals = meals;
        activeIngredients = new ArrayList<IIngredient>();
        units = "g";
        activeMealName = "";
        //grams = true;
    }

    @Override
    public boolean addMeal() {
        savedMeals.add(new Meal());
        activeMealName = "";
        activeIngredients.clear();
        return true;
    }

    @Override
    public void setMealName(String name, int i) {
        savedMeals.get(i).setMealName(name);
    }

    @Override
    public boolean addCustomIngredient() {
        activeIngredients.add(new Ingredient());
        return true;
    }

    //@Override
    //public IIngredient getCustomIngredient()
    //{
    //    return activeIngredients.get(activeIngredients.size()-1);
    //}

    @Override
    public boolean addIngredient(String name, List<String> nutrients) {
        activeIngredients.add(new Ingredient(name, nutrients));
        return true;
    }

    @Override
    public boolean removeMeal(int i) {
        savedMeals.remove(i);
        //activeIngredients.clear();
        return true;
    }

    //@Override
    //public boolean removeIngredient(IMeal meal, int i) {
    //    for(int index = 0; index < savedMeals.size(); index++) {
    //        if(savedMeals.get(index).equals(meal)) {
    //            savedMeals.get(index).getIngredients().remove(i);
    //        }
    //    }
    //    return true;
    //}

    @Override
    public boolean removeIngredient(int i) {
        activeIngredients.remove(i);
        return true;
    }

    @Override
    public IMeal getMeal(int i) {
        return savedMeals.get(i);
    }

    @Override
    public List<IMeal> getAllMeals() {
        return savedMeals;
    }

    @Override
    public List<IIngredient> getActiveIngredients() {
        return activeIngredients;
    }

    @Override
    public void setActiveIngredients(List<IIngredient> ing) {
        activeIngredients = new ArrayList<IIngredient>(ing);
    }

    @Override
    public List<IIngredient> getAllIngredients(IMeal meal) {
        return meal.getIngredients();
    }

    @Override
    public void setActiveMealName(String meal) {
        activeMealName = meal;
    }

    @Override
    public String getActiveMealName() {
        return activeMealName;
    }

    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public void setUnits(String unit) {
        units = unit;
    }

    //@Override
    //public void setMeasurementsByGrams(boolean amount) {
    //    grams = amount;
    //}

    //@Override
    //public boolean measurementByGrams() {
    //    return grams;
    //}

    @Override
    public String getCarbsConsumed(int i) {
        savedMeals.get(i).setCarbsEaten();
        return savedMeals.get(i).getCarbsEaten();
    }

    //@Override
    //public boolean editMeal(String name, String amount, int i) {
    //    savedMeals.get(i).setMealName(name);
    //    savedMeals.get(i).calculateCarbVal(amount);
    //    return true;
    //}

    //@Override
    //public boolean editIngredient(IMeal meal, String ingName, List<String> nutrients, int i) {
    //    for(int index = 0; index < savedMeals.size(); index++) {
    //        if(savedMeals.get(index).equals(meal)) {
    //            savedMeals.get(index).getIngredients().get(i).setName(ingName);
    //            savedMeals.get(index).getIngredients().get(i).setNutritionalValues(nutrients);
    //        }
    //    }
    //    return true;
    //}
}
