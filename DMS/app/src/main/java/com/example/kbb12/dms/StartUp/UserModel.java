package com.example.kbb12.dms.StartUp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.kbb12.dms.AddIngredient.IAddIngredient;
import com.example.kbb12.dms.CustomIngredient.IAddCustomIngredient;
import com.example.kbb12.dms.CustomListView.IDeleteCustomItem;
import com.example.kbb12.dms.IngredientAmount.IIngredientsAmount;
import com.example.kbb12.dms.IngredientList.IIngredientList;
import com.example.kbb12.dms.MealAmount.IMealAmount;
import com.example.kbb12.dms.MealList.IMealList;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel, IMealList, IAddIngredient, IAddCustomIngredient, IIngredientsAmount, IIngredientList, IMealAmount, IDeleteCustomItem {

    private String exampleData;

    private IMealPlanner mealPlanner;
    private int itemNoIng, itemNoMeal;
    private boolean ingList;


    private List<ModelObserver> observers;

    public UserModel(String exampleData, List<IMeal> meals, List<IIngredient> ing){
        this.exampleData=exampleData;
        observers= new ArrayList<>();

        mealPlanner = new MealPlanner(meals, ing);
        itemNoIng = 0;
        if(!meals.isEmpty()) {
            itemNoMeal = meals.size()-1;
        }
        else {
            itemNoMeal = 0;
        }
        ingList = false;

    }

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    public void registerObserver(ModelObserver observer)
    {
        observers.add(observer);
        notifyObservers();
    }

    private void notifyObservers(){
        for(ModelObserver observer:observers){
            observer.update();
        }
    }


    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);

    }



    //------------------------------------------------------------------
    //IMealList method

    @Override
    public void setMealItem(int i) {
        itemNoMeal = i;
        mealPlanner.setActiveMealName(mealPlanner.getMeal(itemNoMeal).getMealName());
    }

    @Override
    public void setIngListView() {
        ingList = true;
    }

    @Override
    public String setEmptyString() {
        if(savedMeals()) {
            return "";
        }
        return "No saved meals yet";
    }

    private boolean savedMeals() {
        if(mealPlanner.getAllMeals().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getSavedMeals() {
        List<String> m = new ArrayList<String>();
        for(int i = 0; i < mealPlanner.getAllMeals().size(); i++) {
            m.add(mealPlanner.getMeal(i).getMealName() + " - " + mealPlanner.getMeal(i).getCarbsEaten() + " total sugar intake");
        }
        return m;
    }

    @Override
    public void getIngredientsForMeal() {
        mealPlanner.setActiveIngredients(mealPlanner.getMeal(itemNoMeal).getAllIngredients());
        notifyObservers();
    }

    @Override
    public void setNewMeal() {
        mealPlanner.addMeal();
        itemNoMeal = mealPlanner.getAllMeals().size()-1;
    }


    //-------------------------------------------------------------------




    //-------------------------------------------------------------------
    //IAddIngredient methods

    @Override
    public void newCustomIngredient() {
        mealPlanner.addCustomIngredient();
        itemNoIng = mealPlanner.getActiveIngredients().size() - 1;
    }

    @Override
    public void itemSearch(String search) {
        mealPlanner.setItemSearch(search);
        notifyObservers();
    }

    @Override
    public List<String> getSavedIngredients() {
        List<String> itemNames = new ArrayList<String>();
        boolean match = true;
        if(mealPlanner.getItemSearch().equals("")) {
            for(int i = 0; i < mealPlanner.getSavedIngredients().size(); i++) {
                itemNames.add(mealPlanner.getSavedIngredients().get(i).getIngredientName());
            }
        }
        else {
            char enteredSearch[] = mealPlanner.getItemSearch().toCharArray();
            for(int i = 0; i < mealPlanner.getSavedIngredients().size(); i++) {
                if(enteredSearch.length > mealPlanner.getSavedIngredients().get(i).getIngredientName().length()) {
                    continue;
                }
                char ing[] = mealPlanner.getSavedIngredients().get(i).getIngredientName().substring(0, enteredSearch.length).toCharArray();
                for(int j = 0; j < enteredSearch.length; j++) {
                    if(ing[j]!=enteredSearch[j]) {
                        match = false;
                    }
                }
                if(match) {
                    itemNames.add(mealPlanner.getSavedIngredients().get(i).getIngredientName());
                }
                match = true;
            }
        }
        return itemNames;
    }

    @Override
    public void getSavedIngredient(String item) {
        mealPlanner.addSearchedIngredient(item);
        itemNoIng = mealPlanner.getActiveIngredients().size() - 1;
    }

    //-------------------------------------------------------------------






    //------------------------------------------------------------------
    //IAddCustomIngredient method
    @Override
    public boolean checkEntry(String entry) {
        if(entry.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public IIngredient getCustomIngredient() {
        return mealPlanner.getActiveIngredients().get(mealPlanner.getActiveIngredients().size() - 1);
    }

    @Override
    public void setCustomName(String name) {
        getCustomIngredient().setIngredientName(name);
    }


    @Override
    public void setCustomCarbVal(String val) {
        getCustomIngredient().addCustomNutrition(val,0);
    }

    @Override
    public void setCustomPacketVal(String val) {
        getCustomIngredient().addCustomNutrition(val,1);
    }

    @Override
    public void setCustomSugarVal(String val) {
        getCustomIngredient().addCustomNutrition(val,2);
    }

    @Override
    public void setCustomPacketWeightVal(String val) {
        getCustomIngredient().addCustomNutrition(val,3);
    }


    //------------------------------------------------------------------



    //------------------------------------------------------------------
    //IIngredientAmount methods

    @Override
    public void setIngredientListView() {
        ingList = true;
    }

    @Override
    public void setIngredientAmount(String amount) {
        mealPlanner.getActiveIngredients().get(itemNoIng).setCarbAmount(amount);
    }

    @Override
    public String getIngredientAmount() {
        return mealPlanner.getActiveIngredients().get(itemNoIng).getCarbAmount();
    }

    @Override
    public void setUnits(String unit) {
        mealPlanner.setUnits(unit);
        notifyObservers();
    }

    @Override
    public String getUnits() {
        return mealPlanner.getUnits();
    }

    @Override
    public void calculateCarbValOfIngredient(String amount) {
        if(getUnits().equals("g")) {
            mealPlanner.getActiveIngredients().get(itemNoIng).setCarbAmountByWeight(amount);
        }
        else {
            mealPlanner.getActiveIngredients().get(itemNoIng).setCarbAmountByPercent(amount);
        }

    }

    //------------------------------------------------------------------



    //------------------------------------------------------------------
    //IIngredientList methods

    @Override
    public void setIngListView(boolean meal) {
        ingList = meal;
    }

    @Override
    public List<String> getIngredientsInMeal() {
        List<String> ingNames = new ArrayList<String>();
        for(int i = 0; i < mealPlanner.getActiveIngredients().size(); i++) {
            ingNames.add(mealPlanner.getActiveIngredients().get(i).getIngredientName() + " - " + mealPlanner.getActiveIngredients().get(i).getCarbAmount() + "g of sugar" );
        }
        return ingNames;
    }

    @Override
    public void setIngredientItem(int i) {
        itemNoIng = i;
    }

    @Override
    public void setMealName(String name) {
        mealPlanner.setActiveMealName(name);
    }

    @Override
    public String getMealName() {
        return mealPlanner.getActiveMealName();
    }

    @Override
    public void createMeal() {
        mealPlanner.getMeal(itemNoMeal).setMealName(getMealName());
        mealPlanner.getMeal(itemNoMeal).setIngredients(mealPlanner.getActiveIngredients());
    }

    @Override
    public void addNewIngredients() {
        mealPlanner.addToSavedIngredients(mealPlanner.getActiveIngredients());
    }

    //------------------------------------------------------------------



    //------------------------------------------------------------------
    //IMealAmount methods

    @Override
    public String getMealAmount() {
        return mealPlanner.getMeal(itemNoMeal).getMealAmount();
    }

    @Override
    public void setMealAmount(String amount) {
        mealPlanner.getMeal(itemNoMeal).setMealAmount(amount);
    }

    @Override
    public void setMealCarbs() {
        mealPlanner.getMeal(itemNoMeal).getTotalCarbs();
        mealPlanner.getMeal(itemNoMeal).setCarbsEaten();
        notifyObservers();
    }

    //------------------------------------------------------------------

    @Override
    public boolean isIngredientList() {
        return ingList;
    }

    @Override
    public boolean removeIngredient(int index) {
        mealPlanner.removeIngredient(index);
        if(!mealPlanner.getActiveIngredients().isEmpty()) {
            itemNoIng = mealPlanner.getActiveIngredients().size() - 1;
        }
        else {
            itemNoIng = 0;
        }
        notifyObservers();
        return true;
    }

    @Override
    public boolean removeMeal(int index) {
        mealPlanner.removeMeal(index);
        mealPlanner.setActiveMealName("");
        if(!mealPlanner.getAllMeals().isEmpty()) {
            itemNoMeal = mealPlanner.getAllMeals().size() - 1;
        }
        else {
            itemNoMeal = 0;
        }
        notifyObservers();
        return true;
    }

}
