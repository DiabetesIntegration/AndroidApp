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






    private List<IMeal> meals;
    private int numMeals, currentMealSelected;
    private IMeal meal;
    private String mName;
    private String mAmount;
    private boolean newMeal;


    private List<IIngredient> ingredients;
    private IIngredient ingredient;


    //-------------------------------------------
    //IngredientAmount variables
    private String unit, ingAmount;
    private boolean byWeight;
    //-------------------------------------------

    private List<ModelObserver> observers;

    public UserModel(String exampleData, List<IMeal> meals){
        this.exampleData=exampleData;
        observers= new ArrayList<>();
        this.meals = meals;
        numMeals = this.meals.size();
        currentMealSelected = 0;
        mAmount = "";
        mName = "";
        ingredients = new ArrayList<IIngredient>();



        mealPlanner = new MealPlanner(meals);
        itemNoIng = 0;
        if(!meals.isEmpty()) {
            itemNoMeal = meals.size()-1;
        }
        else {
            itemNoMeal = 0;
        }
        ingList = false;


        //-------------------------------------------
        //IngredientAmount variables
        unit = "g";
        ingAmount="";
        byWeight = true;
        //-------------------------------------------
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
        //List<String> m = new ArrayList<String>();
        //for(int i = 0; i < meals.size(); i++) {
        //    m.add(meals.get(i).getName());
        //    //Log.i("tester", "HI: " + meals.get(i).getName());
        //}
        //return m;
        List<String> m = new ArrayList<String>();
        for(int i = 0; i < mealPlanner.getAllMeals().size(); i++) {
            m.add(mealPlanner.getMeal(i).getMealName() + " - " + mealPlanner.getMeal(i).getCarbsEaten() + " total sugar intake");
        }
        return m;
    }

    @Override
    public void getIngredientsForMeal() {
        //meal = meals.get(index);
        //ingredients = meals.get(index).getIngredients();
        //mName = meals.get(index).getName();
        mealPlanner.setActiveIngredients(mealPlanner.getMeal(itemNoMeal).getAllIngredients());
        Log.i("tester", mealPlanner.getActiveIngredients().size() + ".ACTIVEINGREDIENTSSIZE." + itemNoMeal + ".");
        for(int i = 0; i < mealPlanner.getActiveIngredients().size(); i++) {
            Log.i("tester", mealPlanner.getActiveIngredients().get(i).getIngredientName());
        }
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
        //ingredient = new Ingredient();
        mealPlanner.addCustomIngredient();
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
        //iName = name;
        //i.setName(name);
        //nutrientInfo.add(0,name);
        getCustomIngredient().setIngredientName(name);
    }

    @Override
    public String getItemName() {
    //    return nutrientInfo.get(0);
        return getCustomIngredient().getIngredientName();
    }

    @Override
    public void setCustomCarbVal(String val) {
        //iCarb = name;
        //i.setCarbVal(val);
       // nutrientInfo.add(1,val);
        getCustomIngredient().addCustomNutrition(val,0);
    }

    @Override
    public String getCarbVal() {
    //    return iCarb;
        return getCustomIngredient().getNutritionalValues().get(0);
    }

    @Override
    public void setCustomPacketVal(String val) {
        //iPacket = name;
        //i.setPacketCarb(val);
        getCustomIngredient().addCustomNutrition(val,1);
    }

    @Override
    public String getPacketVal() {
    //    return iPacket;
        return getCustomIngredient().getNutritionalValues().get(1);
    }

    @Override
    public void setCustomSugarVal(String val) {
        //iSugar = name;
        //i.setSugarValue(val);
        getCustomIngredient().addCustomNutrition(val,2);
    }

    @Override
    public String getSugarVal() {
        //    return iSugar;
        return getCustomIngredient().getNutritionalValues().get(2);
    }

    @Override
    public void setCustomPacketWeightVal(String val) {
        //i.setPacketWeightValue(val);
        getCustomIngredient().addCustomNutrition(val,3);
    }

    @Override
    public String getWeightVal() {
        return getCustomIngredient().getNutritionalValues().get(3);
    }


    //------------------------------------------------------------------



    //------------------------------------------------------------------
    //IIngredientAmount methods

    //@Override
    //public boolean isWeight() {
    //    return byWeight;
    //}


    //@Override
    //public void changeUnit(boolean wop) {
    //    byWeight = wop;
    //}

    @Override
    public void setIngredientListView() {
        ingList = true;
    }

    @Override
    public void setIngredientAmount(String amount) {
        mealPlanner.getActiveIngredients().get(itemNoIng).setCarbAmount(amount);
        //mealPlanner.getNewIngredients().get(mealPlanner.getNewIngredients().size()-1).setCarbAmount(amount);
        //mealPlanner.getNewIngredients().get(mealPlanner.getNewIngredients().size() - 1).setCarbAmount(amount);
        //ingAmount = amount;
    }

    @Override
    public String getIngredientAmount() {
        return mealPlanner.getActiveIngredients().get(itemNoIng).getCarbAmount();
        //return mealPlanner.getNewIngredients().get(mealPlanner.getNewIngredients().size() - 1).getCarbAmount();
        //return ingAmount;
    }

    @Override
    public void setUnits(String unit) {
        mealPlanner.setUnits(unit);
        //mealPlanner.getActiveIngredients().get(itemNo).setAmountUnits(unit);
        //this.unit = unit;
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

        /*if(isWeight()) {
            ingredient.calculateCarbByWeight(getIngredientAmount());
        }
        else {
            ingredient.calculateCarbByPercentage(getIngredientAmount());
        }
        ingredients.add(ingredient);
        notifyObservers();*/
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
        //for(int i = 0; i < ingredients.size(); i++) {
        //    ingNames.add(ingredients.get(i).getName());
        //}
        return ingNames;
    }

    @Override
    public void setIngredientItem(int i) {
        itemNoIng = i;
    }

    @Override
    public void setMealName(String name) {
        mealPlanner.setActiveMealName(name);
        //mealPlanner.getMeal(itemNoMeal).setMealName(name);
    }

    @Override
    public String getMealName() {
        return mealPlanner.getActiveMealName();
        //return mealPlanner.getMeal(itemNoMeal).getMealName();
    }

    @Override
    public void createMeal() {
        mealPlanner.getMeal(itemNoMeal).setMealName(getMealName());
        mealPlanner.getMeal(itemNoMeal).setIngredients(mealPlanner.getActiveIngredients());
        //if(newMeal) {
        //    meal = new Meal(getMealName(), ingredients);
        //    meals.add(meal);
        //    numMeals++;
        //    ingredients = new ArrayList<IIngredient>();
        //    notifyObservers();
        //}
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
        //meal.calculateCarbVal(getMealAmount());
        //ingredients = new ArrayList<IIngredient>();
        //notifyObservers();
        //meals.get(currentMealSelected).calculateCarbVal(getMealAmount());
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
