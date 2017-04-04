package com.example.kbb12.dms.startUp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/10/2017.
 */
public class MealPlanner implements com.example.kbb12.dms.startUp.IMealPlanner {
    private List<IMeal> savedMeals;
    private List<IIngredient> activeIngredients;
    private List<IIngredient> savedIngredients;
    private IIngredient activeIngredient;
    private IMeal activeMeal;
    private boolean newIngredient;
    private String itemSearch;

    public MealPlanner() {
        savedMeals = new ArrayList<>();
        activeIngredients = new ArrayList<>();
        savedIngredients = new ArrayList<>();
        itemSearch = "";
        newIngredient = false;
    }

    @Override
    public void setSavedMeals(List<IMeal> meals) {
        savedMeals = new ArrayList<IMeal>(meals);
    }

    @Override
    public void setSavedIngredients(List<IIngredient> ingredients) {
        savedIngredients = new ArrayList<IIngredient>(ingredients);
    }

    @Override
    public void setActiveMeal(IMeal m) {
        activeMeal = m;
    }

    @Override
    public IMeal getActiveMeal() {
        return activeMeal;
    }

    @Override
    public void setActiveIngredient(IIngredient i) {
        activeIngredient = i;
    }

    @Override
    public IIngredient getActiveIngredient() {
        return activeIngredient;
    }

    @Override
    public boolean addNewMeal() {
        savedMeals.add(getActiveMeal());
        return true;
    }

    @Override
    public boolean addNewIngredient() {
        activeIngredients.add(getActiveIngredient());
        return true;
    }

    @Override
    public List<IIngredient> getMealIngredients() {
        return activeIngredients;
    }

    @Override
    public void setMealIngredients(List<IIngredient> ing) {
        activeIngredients = new ArrayList<>(ing);
    }

    @Override
    public void removeCreatedIngredient() {
        activeIngredients.remove(activeIngredients.get(activeIngredients.size()-1));
    }

    @Override
    public void setNewIngredient(boolean nIng) {
        newIngredient = nIng;
    }

    @Override
    public boolean isNewIngredient() {
        return newIngredient;
    }

    @Override
    public List<IMeal> getSavedMeals() {
        return savedMeals;
    }

    @Override
    public List<IIngredient> addToSavedIngredients(List<IIngredient> ingredients) {
        boolean ingExists;
        List<IIngredient> newIngs = new ArrayList<IIngredient>();
        for(int i = 0; i < ingredients.size(); i++) {
            ingExists = false;
            for(int j = 0; j < savedIngredients.size(); j++) {
                if(savedIngredients.get(j).getIngredientName().equals(ingredients.get(i).getIngredientName())) {
                    ingExists = true;
                }
            }
            if(!ingExists) {
                savedIngredients.add(ingredients.get(i));
                newIngs.add(ingredients.get(i));
            }
        }
        return newIngs;
    }

    @Override
    public List<IIngredient> getSavedIngredients() {
        return savedIngredients;
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
        IIngredient ingredient;
        for(int i = 0; i < savedIngredients.size(); i++) {
            if(savedIngredients.get(i).getIngredientName().equals(itemName)) {
                ingredient = new Ingredient(savedIngredients.get(i).getIngredientName(),savedIngredients.get(i).getNutritionalValues());
                Log.i("tester",ingredient.getIngredientName() + "," + ingredient.getNutritionalValues()[0] + "," + ingredient.getNutritionalValues()[1] + "," + ingredient.getNutritionalValues()[2] + " HERE");
                setActiveIngredient(ingredient);
                activeIngredients.add(getActiveIngredient());
            }
        }
    }

}
