package com.example.kbb12.dms.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.kbb12.dms.customIngredient.model.AddCustomIngredientReadWriteModel;
import com.example.kbb12.dms.ingredientAmount.IIngredientsAmount;
import com.example.kbb12.dms.ingredientList.IIngredientList;
import com.example.kbb12.dms.mealAmount.IMealAmount;
import com.example.kbb12.dms.model.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.model.mealPlannerRecord.savedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.model.mealPlannerRecord.scanningItemsRecord.ScannedItemRecord;
import com.example.kbb12.dms.model.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.model.activityRecord.ActivityRecord;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.model.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.model.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.model.dailyFitnessInfo.DailyFitnessInfoRecord;
import com.example.kbb12.dms.model.database.DatabaseBuilder;
import com.example.kbb12.dms.model.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.startUp.IIngredient;
import com.example.kbb12.dms.startUp.IMeal;
import com.example.kbb12.dms.startUp.IMealPlanner;
import com.example.kbb12.dms.startUp.Ingredient;
import com.example.kbb12.dms.startUp.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements BasalInsulinModelBuilderMainModel,
        TakeInsulinMainModel,BolusInsulinModelBuilderMainModel, IBloodGlucoseModel,
        AddFitnessMainModel,FitnessInfoMainModel, EnterWeightMainModel, AddCustomIngredientReadWriteModel,
        IIngredientsAmount, IIngredientList, IMealAmount, MealCarbohydrateMainModel,
        MealListMainModel,AddIngredientMainModel{

    private IBasalInsulinModel basalInsulinModel;

    private IBolusInsulinModel bolusInsulinModel;

    private InsulinTakenRecord insulinTakenRecord;

    private DailyFitnessInfoRecord dailyFitnessInfoRecord;

    private ActivityRecord activityRecord;

    private RawBGRecord rawBGRecord;

    private BGRecord historyBGRecord;

    private BGRecord currentBGRecord;

    private boolean usingImprovements=true;

    private SharedPreferences sharPrefEdit;

    private SavedIngredientsRecord savedIngredientsRecord;
    private SavedMealsRecord savedMealsRecord;
    private TimeCarbEatenRecord timeCarbEatenRecord;
    private ScannedItemRecord scannedItemRecord;

    private IMeal activeMeal;

    private IMealPlanner mealPlanner;

    public UserModel(Context context,SharedPreferences sharPrefEdit){
        DatabaseBuilder db = new DatabaseBuilder(context);
        basalInsulinModel =db.getBasalInsulinModel();
        bolusInsulinModel= db.getBolusInsulinModel();
        insulinTakenRecord= db.getInsulinTakenRecord();
        rawBGRecord = db.getRawBGRecord();
        historyBGRecord = db.getHistoryBGRecord();
        currentBGRecord = db.getCurrentBGRecord();
        dailyFitnessInfoRecord=db.getDailyFitnessInfoRecord();
        activityRecord=db.getActivityRecord();
        observers= new ArrayList<>();
        savedIngredientsRecord = db.getSavedIngredientsRecord();
        savedMealsRecord = db.getSavedMealsRecord();
        timeCarbEatenRecord = db.getTimeCarbEatenRecord();
        scannedItemRecord = db.getScannedItemRecord();
        mealPlanner = new com.example.kbb12.dms.startUp.MealPlanner();
        mealPlanner.setSavedIngredients(getDatabaseIngredients());
        mealPlanner.setSavedMeals(getDatabaseMeals());

        for(Calendar c : rawBGRecord.getAllBasicData().keySet()){
            Log.d("Record", rawBGRecord.getAllBasicData().get(c));
            Log.d("EM: ", rawBGRecord.getAllBasicData().get(c).substring(586,588) + rawBGRecord.getAllBasicData().get(c).substring(584,586));
        }
        ingList = false;
        this.sharPrefEdit=sharPrefEdit;
        activeMeal =null;
        setUpScanningExamples();
    }

    private void setUpScanningExamples(){
        if(scannedItemRecord.getAllSavedItems().isEmpty()) {
            scannedItemRecord.saveItem("Napolina Fusilli Pasta", "72", "100", "1000");
            scannedItemRecord.saveItem("Napolina Chopped Tomatoes", "3.6", "100", "100");
            scannedItemRecord.saveItem("Dolmio Tomato and Basil Sauce", "8.4", "100", "100");
        }
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }

    @Override
    public void addRawData(Calendar c, String data){
        rawBGRecord.addRawData(data, c);
    }

    @Override
    public void addHistoryReading(Calendar c, double reading){
        historyBGRecord.insertReading(c, reading);
        notifyObservers();
    }

    @Override
    public void addCurrentReading(Calendar c, double reading){
        currentBGRecord.insertReading(c, reading);
        notifyObservers();
    }

    @Override
    public BGReading getMostRecentHistoryReading() {
        return historyBGRecord.getMostRecentReading();
    }

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    @Override
    public void saveDoses(List<BasalInsulinDose> basicDoses,String basalInsulinBrandName) throws DuplicateDoseException {
        try {
            Calendar currentTime = Calendar.getInstance();
            Calendar lastTaken;
            String date;
            for (BasalInsulinEntry dose : basicDoses) {
                lastTaken=Calendar.getInstance();
                if(dose.getHour()>currentTime.get(Calendar.HOUR_OF_DAY)||(dose.getHour().equals(currentTime.get(Calendar.HOUR_OF_DAY))&&dose.getMinute()>currentTime.get(Calendar.MINUTE))){
                    //If the set time hasn't been today yet then assume the last time it was taken
                    //was yesterday. Otherwise assume it has been taken today.
                    lastTaken.add(Calendar.DAY_OF_YEAR,-1);
                }
                basalInsulinModel.addEntry(dose, basalInsulinBrandName, lastTaken.get(Calendar.DAY_OF_MONTH), lastTaken.get(Calendar.MONTH), lastTaken.get(Calendar.YEAR));
            }
        } catch (DuplicateDoseException e){
            basalInsulinModel.clearValues();
            throw new DuplicateDoseException();
        }
    }

    public List<BasalInsulinEntry> getDoses(){
        return basalInsulinModel.getEntries(usingImprovements);
    }


    @Override
    public BasalInsulinEntry getLatestBasalRecommendation() {
        Calendar now = Calendar.getInstance();
        //Get the first time before now
        BasalInsulinEntry mostRecent= basalInsulinModel.getLatestBefore(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)+1,usingImprovements);
        //Get what day that dose was last taken on and the recommended time for taking it
        Calendar lastTaken = basalInsulinModel.getLastTakenAprox(mostRecent);
        //If (taken today) or (timeRecommended>now)
        if(sameDay(lastTaken, now)||timeLater(lastTaken,now)){
            return null;
        }
        basalInsulinModel.allTakenBefore(mostRecent.getHour(),mostRecent.getMinute(),now.get(Calendar.DAY_OF_MONTH),
        now.get(Calendar.MONTH),now.get(Calendar.YEAR));
        //Return it
        return mostRecent;
    }


    @Override
    public void takeInsulin(Calendar time, double amount, boolean basal) {
        if(basal){
            //If they're taking basal insulin mark all the times before and including now as taken.
            time.add(Calendar.MINUTE,5);
            basalInsulinModel.allTakenBefore(time.get(Calendar.HOUR),time.get(Calendar.MINUTE)+5,
                    time.get(Calendar.DAY_OF_MONTH),time.get(Calendar.MONTH),time.get(Calendar.YEAR));
        }
        try {
            insulinTakenRecord.addEntry(time, amount, basal);
        }catch (SQLiteConstraintException e){
            //Do nothing the entry has already been added.
        }
    }

    @Override
    public int getRecentCarbs() {
        int totalCarbs=0;
        Calendar halfAnHourAgo = Calendar.getInstance();
        halfAnHourAgo.add(Calendar.MINUTE,-31);
        Calendar oneMinuteFromNow = Calendar.getInstance();
        oneMinuteFromNow.add(Calendar.MINUTE,+1);
        List<Double> recentCarbs = timeCarbEatenRecord.getAllEntries(halfAnHourAgo,oneMinuteFromNow);
        for(Double current:recentCarbs){
            totalCarbs+=current;
        }
        return totalCarbs;
    }

    @Override
    public double getCurrentICR() {
        return bolusInsulinModel.getICRValue(Calendar.getInstance(),usingImprovements);
    }

    @Override
    public double getCurrentISF() {
        return bolusInsulinModel.getISFValue(Calendar.getInstance(),usingImprovements);
    }

    @Override
    public Double getCurrentBG() {
        BGReading reading =currentBGRecord.getMostRecentReading();
        Calendar fifteenMinutesAgo = Calendar.getInstance();
        fifteenMinutesAgo.add(Calendar.MINUTE,-15);
        if(reading==null||reading.getTime().before(fifteenMinutesAgo)){
            return null;
        }
        return reading.getReading();
    }

    @Override
    public boolean hasTakenBolusInsulinRecently() {
        IInsulinTakenEntry lastTaken =insulinTakenRecord.getMostRecentBolus();
        if(lastTaken==null){
            //never been taken
            return false;
        }
        Calendar now =Calendar.getInstance();
        now.add(Calendar.HOUR,-4);
        return (now.getTimeInMillis()<lastTaken.getTime().getTimeInMillis());
    }

    private boolean sameDay(Calendar one,Calendar two){
        return (one.get(Calendar.YEAR)==two.get(Calendar.YEAR))&&(one.get(Calendar.MONTH)==two.get(Calendar.MONTH))&&(one.get(Calendar.DAY_OF_MONTH)==two.get(Calendar.DAY_OF_MONTH));
    }


    private boolean timeLater(Calendar one,Calendar two){
        return (one.get(Calendar.HOUR)>two.get(Calendar.HOUR))||((one.get(Calendar.HOUR)==two.get(Calendar.HOUR))&&(one.get(Calendar.MINUTE)>two.get(Calendar.MINUTE)));
    }

    @Override
    public void createInsulinToCarbModel(double breakInsulin, double breakCarbs, double lunInsulin,
                                         double lunCarbs, double dinInsulin, double dinCarbs,
                                         double nighInsulin,double nighCarbs ) {
        bolusInsulinModel.createInsulinToCarbModel(breakInsulin,breakCarbs,lunInsulin,lunCarbs,
                dinInsulin,dinCarbs,nighInsulin,nighCarbs);
    }

    @Override
    public void createInsulinToCarbModel(double icr) {
        bolusInsulinModel.createInsulinToCarbModel(icr);
    }

    @Override
    public void createInsulinSensitivityModel(double mornIsf, double afteIsf, double eveISF, double nighISF) {
        bolusInsulinModel.createInsulinSensitivityModel(mornIsf, afteIsf, eveISF, nighISF);
    }

    @Override
    public void createInsulinSensitivityModel(double ISF) {
        bolusInsulinModel.createInsulinSensitivityModel(ISF);
    }

    public void logModels(){
        basalInsulinModel.log();
        bolusInsulinModel.log();
    }

    public void addToCalCount(Calendar calendar,int cal){
        dailyFitnessInfoRecord.addToCalCount(calendar,cal);
    }

    private void addActivityToDB(Calendar calendar,int calories,String activity,int durHour,int durMin){
        activityRecord.insertActivityEntry(calendar,calories,activity,durHour,durMin);
    }


    @Override
    public void saveActivity(Calendar calendar,String activitytype,int durhour,int durmin) {
        int calories=calculateCalories(activitytype,durhour,durmin);
        addActivityToDB(calendar,calories,activitytype,durhour,durmin);
        addToCalCount(calendar,calories);
    }

    @Override
    public int getCalCount() {
        return dailyFitnessInfoRecord.getCalCount(Calendar.getInstance());
    }

    private int calculateCalories(String activity, int hours, int minutes){
        int length = (hours*60) + minutes;
        int calories = 0;
        float weight = sharPrefEdit.getFloat("weight",(float) 0.0);
        switch (activity){
            case "Walking":
                calories = (int) ((0.055*length*weight)+0.5d);
                break;
            case "Running":
                calories = (int) ((0.183*length*weight)+0.5d);
                break;
            case "Cycling":
                calories = (int) ((0.133*length*weight)+0.5d);
                break;
        }
        return calories;
    }


    @Override
    public void setError(String errorMessage) {
        //TODO
    }

    //-----------------------------------------------------------------------------------
    //AddCustomIngredientReadWriteModel

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
    }

    //------------------------------------------------------------------------------------
    //IIngredientsAmount


    @Override
    public void setUnits(String unit) {
        mealPlanner.getActiveIngredient().setUnit(unit);
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

    @Override
    public void addNewMealDateCarb(String amount) {
        timeCarbEatenRecord.addRawData(amount, Calendar.getInstance());
    }

    public List<BGReading> getHistoryBetween(Calendar from, Calendar to){
        return historyBGRecord.getReadingsBetween(from, to);
    }

    @Override
    public IMeal mealToEat() {
        return mealPlanner.getActiveMeal();
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


    public List<IIngredient> getSavedIngredients() {
        List<IIngredient> ingredients = new ArrayList<>();
        Map <Integer, List<String>> ing = savedIngredientsRecord.getAllSavedIngredients();

        List<Integer> idAttribute = new ArrayList<>(ing.keySet());
        Collections.sort(idAttribute);
        String iName;
        String iNutrition[];
        List<String> attributes;
        IIngredient savedIng;
        for(int i = 0; i < ing.size(); i++) {
            attributes = new ArrayList<>(ing.get(idAttribute.get(i)));
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

    @Override
    public void addIngredientToMeal(IIngredient ingredient) {
        activeMeal.addIngredient(ingredient);
    }

    @Override
    public List<List<String>> getAllScanableItems() {
        return scannedItemRecord.getAllSavedItems();
    }

    @Override
    public void setActiveMeal(IMeal activeMeal) {
        this.activeMeal=activeMeal;
    }

    @Override
    public IMeal getActiveMeal() {
        return activeMeal;
    }

    @Override
    public List<IMeal> getSavedMeals() {
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
            mealIng = new ArrayList<>();
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

    @Override
    public void saveMeal(String name, int amount) {
        savedMealsRecord.saveMeal(name, ",", ",",Integer.toString(amount),"1");
    }

    @Override
    public void registerCarbs(int amount) {
        timeCarbEatenRecord.addRawData(Integer.toString(amount),Calendar.getInstance());
    }

    @Override
    public void deleteMeal(IMeal meal) {
        savedMealsRecord.deleteMeal(meal);
    }
}
