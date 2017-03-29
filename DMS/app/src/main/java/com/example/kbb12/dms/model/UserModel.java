package com.example.kbb12.dms.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;

import com.example.kbb12.dms.addIngredient.IAddIngredient;
import com.example.kbb12.dms.customIngredient.IAddCustomIngredient;
import com.example.kbb12.dms.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.errorHandling.ErrorReadModel;
import com.example.kbb12.dms.errorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.ingredientAmount.IIngredientsAmount;
import com.example.kbb12.dms.ingredientList.IIngredientList;
import com.example.kbb12.dms.mealAmount.IMealAmount;
import com.example.kbb12.dms.mealCarbohydrateValue.IMealCarbohydrateValue;
import com.example.kbb12.dms.mealList.IMealList;
import com.example.kbb12.dms.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.mealPlannerRecord.savedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;
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
import com.example.kbb12.dms.startUp.ModelObserver;
import com.example.kbb12.dms.template.ITemplateModel;

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
public class UserModel implements ErrorReadModel,ErrorReadWriteModel,ITemplateModel,BasalInsulinModelBuilderMainModel,
        TakeInsulinMainModel,BolusInsulinModelBuilderMainModel, IBloodGlucoseModel,
        AddFitnessMainModel,FitnessInfoMainModel, EnterWeightMainModel,IMealList, IAddIngredient, IAddCustomIngredient, IIngredientsAmount, IIngredientList, IMealAmount, IDeleteCustomItem, IMealCarbohydrateValue {

    private String exampleData;

    private List<ModelObserver> observers;

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

    private IMealPlanner mealPlanner;
    private boolean ingList;
    private String errorMessage;

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
        mealPlanner = new com.example.kbb12.dms.startUp.MealPlanner();
        mealPlanner.setSavedIngredients(getDatabaseIngredients());
        mealPlanner.setSavedMeals(getDatabaseMeals());


        ingList = false;
        this.sharPrefEdit=sharPrefEdit;
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
    }

    @Override
    public void addCurrentReading(Calendar c, double reading){
        currentBGRecord.insertReading(c, reading);
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

    @Override
    public void addNewDateCarbMealList(String amount) {
        timeCarbEatenRecord.addRawData(amount,Calendar.getInstance());
    }

    @Override
    public IMeal mealCarbToEatMealList() {
        return mealPlanner.getActiveMeal();
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

    @Override
    public void addNewDateCarb(String amount) {
        timeCarbEatenRecord.addRawData(amount,Calendar.getInstance());
    }

    @Override
    public IMeal mealCarbToEat() {
        return mealPlanner.getActiveMeal();
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

}
