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
    private List<IIngredient> savedIngredients;
    private String itemSearch;

    public MealPlanner(List<IMeal> meals, List<IIngredient> savedIng) {
        savedMeals = meals;
        activeIngredients = new ArrayList<IIngredient>();
        units = "g";
        activeMealName = "";
        savedIngredients = savedIng;
        itemSearch = "";
    }

    @Override
    public boolean addMeal() {
        savedMeals.add(new Meal());
        activeMealName = "";
        activeIngredients.clear();
        return true;
    }

    @Override
    public boolean addCustomIngredient() {
        activeIngredients.add(new Ingredient());
        return true;
    }

    @Override
    public boolean removeMeal(int i) {
        savedMeals.remove(i);
        return true;
    }

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

    @Override
    public List<IIngredient> getSavedIngredients() {
        return savedIngredients;
    }

    @Override
    public void addToSavedIngredients(List<IIngredient> ingredients) {
        boolean ingExists = false;
        for(int i = 0; i < ingredients.size(); i++) {
            for(int j = 0; j < savedIngredients.size(); j++) {
                if(savedIngredients.get(j).getIngredientName().equals(ingredients.get(i).getIngredientName())) {
                    ingExists = true;
                }
            }
            if(!ingExists) {
                savedIngredients.add(ingredients.get(i));
                ingExists = false;
            }
        }
    }

    @Override
    public void setItemSearch(String search) {
        itemSearch = search;
    }

    @Override
    public String getItemSearch() {
        return itemSearch;
    }

    @Override
    public void addSearchedIngredient(String itemName) {
        for(int i = 0; i < savedIngredients.size(); i++) {
            if(savedIngredients.get(i).getIngredientName().equals(itemName)) {
                activeIngredients.add(new Ingredient(savedIngredients.get(i).getIngredientName(),savedIngredients.get(i).getNutritionalValues()));
            }
        }
    }
}
