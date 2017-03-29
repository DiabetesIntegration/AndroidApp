package com.example.kbb12.dms.StartUp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.kbb12.dms.AddIngredient.IAddIngredient;
import com.example.kbb12.dms.CustomIngredient.IAddCustomIngredient;
import com.example.kbb12.dms.CustomListView.IDeleteCustomItem;
import com.example.kbb12.dms.IngredientAmount.IIngredientsAmount;
import com.example.kbb12.dms.IngredientList.IIngredientList;
import com.example.kbb12.dms.MealAmount.IMealAmount;
import com.example.kbb12.dms.MealCarbohydrateValue.IMealCarbohydrateValue;
import com.example.kbb12.dms.MealList.IMealList;
import com.example.kbb12.dms.MealPlannerRecord.SavedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.MealPlannerRecord.SavedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.MealPlannerRecord.TimeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel, IMealList, IAddIngredient, IAddCustomIngredient, IIngredientsAmount, IIngredientList, IMealAmount, IDeleteCustomItem, IMealCarbohydrateValue {

    private String exampleData;

    private IMealPlanner mealPlanner;
    private boolean ingList;
    private String errorMessage;

    private SavedIngredientsRecord savedIngredientsRecord;
    private SavedMealsRecord savedMealsRecord;
    private TimeCarbEatenRecord timeCarbEatenRecord;


    private List<ModelObserver> observers;

    public UserModel(Context context){

        observers= new ArrayList<>();

        DatabaseBuilder db = new DatabaseBuilder(context);
        savedIngredientsRecord = db.getSavedIngredientsRecord();
        savedMealsRecord = db.getSavedMealsRecord();
        timeCarbEatenRecord = db.getTimeCarbEatenRecord();

        mealPlanner = new MealPlanner();
        mealPlanner.setSavedIngredients(getDatabaseIngredients());
        mealPlanner.setSavedMeals(getDatabaseMeals());


        ingList = false;

    }


    private List<IMeal> getDatabaseMeals() {
        List<IMeal> meals = new ArrayList<IMeal>();
        Map<Integer, List<String>> m = savedMealsRecord.getAllMeals();

        List<Integer> idAttribute = new ArrayList<Integer>(m.keySet());
        Collections.sort(idAttribute);

        String mName,tCarb,custom;
        List<String> ings, ingsVals;
        List<String> attributes;
        List<IIngredient> mealIng;
        Meal meal;
        for(int i = 0; i < m.size(); i++) {
            mealIng = new ArrayList<IIngredient>();
            attributes = m.get(idAttribute.get(i));
            mName = attributes.get(0);
            ings = Arrays.asList(attributes.get(1).split(","));
            ingsVals = Arrays.asList(attributes.get(2).split(","));
            tCarb = attributes.get(3);
            custom = attributes.get(4);

            if(custom.equals("0")) {
                for(int j = 0; j < ings.size(); j++) {
                    mealIng.add(mealPlanner.getSavedIngredients().get(Integer.parseInt(ings.get(j))));
                    mealIng.get(mealIng.size()-1).setCarbAmount(ingsVals.get(j));
                }
                meal = new Meal(mName, mealIng);
                meal.setCustomCarbMeal(false);
                meal.setTotalMealCarbs(tCarb);
            }
            else {
                meal = new Meal();
                meal.setMealName(mName);
                meal.setCustomCarbMeal(true);
                meal.setCustomCarbsEaten(tCarb);
            }
            meals.add(meal);
        }
        return meals;
    }


    private List<IIngredient> getDatabaseIngredients() {
        List<IIngredient> ingredients = new ArrayList<IIngredient>();
        Map <Integer, List<String>> ing = savedIngredientsRecord.getAllSavedIngredients();

        List<Integer> idAttribute = new ArrayList<Integer>(ing.keySet());
        Collections.sort(idAttribute);
        String iName;
        String iNutrition[] = new String[3];
        List<String> attributes;
        IIngredient savedIng;
        for(int i = 0; i < ing.size(); i++) {
            attributes = new ArrayList<String>(ing.get(idAttribute.get(i)));
            iNutrition = new String[3];
            iName = attributes.get(0);
            iNutrition[0] = attributes.get(1);
            iNutrition[1] = attributes.get(2);
            iNutrition[2] = attributes.get(3);
            savedIng = new Ingredient(iName, iNutrition);
            ingredients.add(savedIng);
        }

        return ingredients;
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

    //-----------------------------------------------------------------------------------
    //IMealList

    @Override
    public void setNewMeal() {
        IMeal meal = new Meal();
        mealPlanner.setActiveMeal(meal);
        mealPlanner.getMealIngredients().clear();
    }

    @Override
    public void setIngListView() {
        ingList = true;
    }

    @Override
    public List<String> getSavedMeals() {
        List<String> meals  = new ArrayList<String>();
        for(int i = 0; i < mealPlanner.getSavedMeals().size(); i++) {
            if(mealPlanner.getSavedMeals().get(i).getCustomCarbMeal()) {
                meals.add(mealPlanner.getSavedMeals().get(i).getMealName() + " - " + mealPlanner.getSavedMeals().get(i).getCustomCarbsEaten() + "g of carbohydrate in meal (No ingredients)");
            }
            else {
                meals.add(mealPlanner.getSavedMeals().get(i).getMealName() + " - " + mealPlanner.getSavedMeals().get(i).getTotalMealCarbs() + "g of carbohydrate in meal");
            }
        }
        return meals;
    }

    @Override
    public void setMealItem(int i) {
        mealPlanner.setActiveMeal(mealPlanner.getSavedMeals().get(i));
    }

    @Override
    public void getIngredientsForMeal() {
        mealPlanner.setMealIngredients(mealPlanner.getActiveMeal().getAllIngredients());
    }

    @Override
    public boolean customMealAtPosition() {
        return mealPlanner.getActiveMeal().getCustomCarbMeal();
    }

    //-----------------------------------------------------------------------------------
    //IAddIngredient


    @Override
    public void setNewIngredient() {
        IIngredient ing = new Ingredient();
        mealPlanner.setActiveIngredient(ing);
    }

    @Override
    public void removeIngListView() {
        ingList = false;
    }

    @Override
    public void setAddIngredient(boolean ing) {
        mealPlanner.setNewIngredient(ing);
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
    }


    //-----------------------------------------------------------------------------------
    //IAddCustomIngredient

    @Override
    public void setCustomIngredientName(String name) {
        mealPlanner.getActiveIngredient().setIngredientName(name);
    }

    @Override
    public void setCustomIngredientNutrition(String values[]) {
        mealPlanner.getActiveIngredient().addCustomNutrition(values);
    }

    @Override
    public void clearActiveIngreident() {
        mealPlanner.setActiveIngredient(null);
    }


    @Override
    public void getCustomIngErrorMessage(String err) {
        setError(err);
        notifyObservers();
    }

    //------------------------------------------------------------------------------------
    //IIngredientsAmount


    @Override
    public void setUnits(String unit) {
        mealPlanner.getActiveIngredient().setUnit(unit);
        notifyObservers();
    }

    @Override
    public String getUnits() {
        return mealPlanner.getActiveIngredient().getUnit();
    }

    @Override
    public void setCarbValOfIngredient(String amount) {
        if(getUnits().equals("g")) {
            mealPlanner.getActiveIngredient().setCarbAmountByWeight(amount);
        }
        else {
            mealPlanner.getActiveIngredient().setCarbAmountByPercent(amount);
        }
    }

    @Override
    public void setIngredientListView() {
        ingList = true;
    }

    @Override
    public void setIngredientExists(boolean exist) {
        mealPlanner.getActiveIngredient().setExists(exist);
    }

    @Override
    public boolean ingredientExists() {
        return mealPlanner.getActiveIngredient().ingredientExists();
    }

    @Override
    public void addNewIngredient() {
        mealPlanner.addNewIngredient();
    }

    @Override
    public void getIngAmountErrorMessage(String err) {
        setError(err);
        notifyObservers();
    }

    //-----------------------------------------------------------------------------------
    //IIngredientList

    @Override
    public List<String> getIngredientsInMeal() {
        List<String> ingNames = new ArrayList<String>();
        for(int i = 0; i < mealPlanner.getMealIngredients().size(); i++) {
            ingNames.add(mealPlanner.getMealIngredients().get(i).getIngredientName() + " - " + mealPlanner.getMealIngredients().get(i).getCarbAmount() + "g of carbs");
        }
        return ingNames;
    }

    @Override
    public String getMealName() {
        return mealPlanner.getActiveMeal().getMealName();
    }

    @Override
    public boolean fromIngredient() {
        return mealPlanner.isNewIngredient();
    }

    @Override
    public void removeIngredientFromMeal() {
        mealPlanner.removeCreatedIngredient();
    }

    @Override
    public void setMealName(String n) {
        mealPlanner.getActiveMeal().setMealName(n);
    }

    @Override
    public void setIngListView(boolean meal) {
        ingList = meal;
    }

    @Override
    public void setIngredientsForMeal() {
        mealPlanner.getActiveMeal().setIngredients(mealPlanner.getMealIngredients());
    }

    @Override
    public void setIngredientItem(int i) {
        mealPlanner.setActiveIngredient(mealPlanner.getMealIngredients().get(i));
    }

    @Override
    public void setTotalCarbs() {
        mealPlanner.getActiveMeal().setTotalMealCarbs(mealPlanner.getActiveMeal().getTotalCarbs());
    }

    @Override
    public boolean checkMealName() {
        for(int i = 0; i < mealPlanner.getSavedMeals().size(); i++) {
            if(getMealName().equals((mealPlanner.getSavedMeals().get(i).getMealName())) && (!mealPlanner.getActiveMeal().equals(mealPlanner.getSavedMeals().get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void getIngListErrorMessage(String err) {
        setError(err);
        notifyObservers();
    }


    //---------------------------------------------------------------------------------------
    //IMealAmount

    @Override
    public void setMealCarbAmount(String amount) {
        mealPlanner.getActiveMeal().setMealAmount(amount);
        mealPlanner.getActiveMeal().setCarbsEaten();
    }

    @Override
    public boolean mealExists() {
        for(int i = 0; i < mealPlanner.getSavedMeals().size(); i++) {
            if(mealPlanner.getSavedMeals().get(i).equals(mealPlanner.getActiveMeal())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewMeal() {
        mealPlanner.addNewMeal();
        saveMealToDB();
    }

    @Override
    public void setMealListView() {
        ingList = false;
    }

    @Override
    public void saveNewIngredients() {
        saveIngToDB(mealPlanner.addToSavedIngredients(mealPlanner.getMealIngredients()));
        notifyObservers();
    }

    @Override
    public void editMeal() {
        editMealInDB();
    }

    @Override
    public void getMealAmountErrorMessage(String err) {
        setError(err);
        notifyObservers();
    }


    private void saveMealToDB() {
        String ingIndex = "";
        String ingVal = "";
        for(int i = 0; i < mealPlanner.getMealIngredients().size(); i++) {
            for(int j = 0; j < mealPlanner.getSavedIngredients().size(); j++) {
                if(mealPlanner.getMealIngredients().get(i).equals(mealPlanner.getSavedIngredients().get(j))) {
                    ingIndex += j + ",";
                    ingVal +=  mealPlanner.getMealIngredients().get(i).getCarbAmount() + "," ;
                }
            }
        }
        ingIndex = ingIndex.substring(0,ingIndex.length()-1);
        ingVal = ingVal.substring(0,ingVal.length()-1);
        savedMealsRecord.saveMeal(mealPlanner.getActiveMeal().getMealName(), ingIndex, ingVal, mealPlanner.getActiveMeal().getTotalCarbs(),"0");
    }

    private void saveCustomMealToDB() {
        savedMealsRecord.saveMeal(mealPlanner.getActiveMeal().getMealName(), ",", ",", mealPlanner.getActiveMeal().getCustomCarbsEaten(),"1");
    }



    private void saveIngToDB(List<IIngredient> newI) {
        for(int i = 0; i < newI.size(); i++) {
            savedIngredientsRecord.saveIngredient(newI.get(i).getIngredientName(),newI.get(i).getNutritionalValues()[0],newI.get(i).getNutritionalValues()[1],newI.get(i).getNutritionalValues()[2]);
        }
    }



    private void editMealInDB() {
        Map<Integer, List<String>> m = savedMealsRecord.getAllMeals();

        List<Integer> idAttribute = new ArrayList<Integer>(m.keySet());
        Collections.sort(idAttribute);
        Integer index = -1;
        String ingIds = "";
        String ingCarbAmounts = "";

        for(int i = 0; i < m.size(); i++) {
            if(mealPlanner.getActiveMeal().equals(mealPlanner.getSavedMeals().get(i))) {
                index = idAttribute.get(i);
                break;
            }
        }

        for(int i = 0; i < mealPlanner.getMealIngredients().size(); i++) {
            for(int j = 0; j < mealPlanner.getSavedIngredients().size(); j++) {
                if(mealPlanner.getMealIngredients().get(i).equals(mealPlanner.getSavedIngredients().get(j))) {
                    ingIds += j + ",";
                    ingCarbAmounts +=  mealPlanner.getMealIngredients().get(i).getCarbAmount() + "," ;
                }
            }
        }
        ingIds = ingIds.substring(0,ingIds.length()-1);
        ingCarbAmounts = ingCarbAmounts.substring(0,ingCarbAmounts.length()-1);
        savedMealsRecord.editMeal(index,mealPlanner.getActiveMeal().getMealName(),ingIds,ingCarbAmounts,mealPlanner.getActiveMeal().getTotalCarbs(),"0");
    }

    //----------------------------------------------------------------------------------------
    //IDeleteCustomItem

    @Override
    public boolean isIngredientList() {
        return ingList;
    }

    @Override
    public boolean removeIngredient(int index) {
        mealPlanner.getMealIngredients().remove(index);
        notifyObservers();
        return true;
    }

    @Override
    public boolean removeMeal(int index) {
        savedMealsRecord.deleteMeal(mealPlanner.getSavedMeals().get(index));
        mealPlanner.getSavedMeals().remove(index);
        notifyObservers();
        return true;
    }




    //----------------------------------------------------------------------------------------
    //IMealCarbohydrateValue

    @Override
    public void setStraightCarbs(boolean straightCarbs) {
        mealPlanner.getActiveMeal().setCustomCarbMeal(straightCarbs);
    }

    @Override
    public boolean addCarbMeal() {
        for(int i = 0; i < mealPlanner.getSavedMeals().size(); i++) {
            if(mealPlanner.getActiveMeal().getMealName().equals(mealPlanner.getSavedMeals().get(i).getMealName())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void setCarbMealName(String name) {
        mealPlanner.getActiveMeal().setMealName(name);
    }

    @Override
    public void setCarbMealValue(String value) {
        mealPlanner.getActiveMeal().setCustomCarbsEaten(value);
    }

    @Override
    public void addNewCarbMeal() {
        mealPlanner.addNewMeal();
        saveCustomMealToDB();
    }

    @Override
    public void notIngredientList(boolean list) {
        ingList = list;
    }

    @Override
    public void getMealCarbohydrateErrorMessage(String err) {
        setError(err);
        notifyObservers();
    }


    //-----------------------------------------------------------------------------------
    //Error Messages

    @Override
    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getError() {
        return errorMessage;
    }

}
