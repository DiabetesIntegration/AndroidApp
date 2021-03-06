package com.example.kbb12.dms.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.kbb12.dms.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.database.basalInsulinModel.DuplicateDoseException;
import com.example.kbb12.dms.database.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.database.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoRecord;
import com.example.kbb12.dms.database.DatabaseBuilder;
import com.example.kbb12.dms.database.insulinTakenRecord.IInsulinTakenEntry;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Ingredient;
import com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements BasalInsulinModelBuilderMainModel,
        TakeInsulinMainModel,BolusInsulinModelBuilderMainModel, IBloodGlucoseModel,
        AddFitnessMainModel,FitnessInfoMainModel, EnterWeightMainModel,IngredientsAmountMainModel,
        MealCarbohydrateMainModel, MealListMainModel,AddIngredientMainModel,IngredientsListMainModel,
        MealAmountMainModel,CustomIngredientMainModel{

    private IBasalInsulinModel basalInsulinModel;

    private IBolusInsulinModel bolusInsulinModel;

    private InsulinTakenRecord insulinTakenRecord;

    private DailyFitnessInfoRecord dailyFitnessInfoRecord;

    private ActivityRecord activityRecord;

    private RawBGRecord rawBGRecord;

    private BGRecord historyBGRecord;

    private BGRecord currentBGRecord;

    private boolean usingImprovements=true;

    private SharedPreferences sharPref;

    private SavedIngredientsRecord savedIngredientsRecord;
    private SavedMealsRecord savedMealsRecord;
    private TimeCarbEatenRecord timeCarbEatenRecord;

    private IMeal activeMeal;
    private String activeMealOrigName;
    private IIngredient activeIngredient;

    public UserModel(DatabaseBuilder db,SharedPreferences sharPref){
        basalInsulinModel =db.getBasalInsulinModel();
        bolusInsulinModel= db.getBolusInsulinModel();
        insulinTakenRecord= db.getInsulinTakenRecord();
        rawBGRecord = db.getRawBGRecord();
        historyBGRecord = db.getHistoryBGRecord();
        currentBGRecord = db.getCurrentBGRecord();
        dailyFitnessInfoRecord=db.getDailyFitnessInfoRecord();
        activityRecord=db.getActivityRecord();
        savedIngredientsRecord = db.getSavedIngredientsRecord();
        savedMealsRecord = db.getSavedMealsRecord();
        timeCarbEatenRecord = db.getTimeCarbEatenRecord();
        for(Calendar c : rawBGRecord.getAllBasicData().keySet()){
            Log.d("Record", rawBGRecord.getAllBasicData().get(c));
            Log.d("EM: ", rawBGRecord.getAllBasicData().get(c).substring(586,588) + rawBGRecord.getAllBasicData().get(c).substring(584,586));
        }
        this.sharPref = sharPref;
        activeMeal =null;
        activeIngredient=null;
        setUpScanningExamples();
    }

    private void setUpScanningExamples(){
        IIngredient example;
        if(getIngredientByBarcode("5000232823458")==null){
            example=new Ingredient("Napolina Fusilli Pasta", 72.0, 1000,"5000232823458");
            savedIngredientsRecord.saveIngredient(example);
        }
        if(getIngredientByBarcode("5010061001613")==null){
            example=new Ingredient("Napolina Chopped Tomatoes", 3.6, 100,"5010061001613");
            savedIngredientsRecord.saveIngredient(example);
        }
        if(getIngredientByBarcode("4002359640469")==null){
            example=new Ingredient("Dolmio Tomato and Basil Sauce", 8.4, 100,"4002359640469");
            savedIngredientsRecord.saveIngredient(example);
        }
        if(getIngredientByBarcode("5012035936648")==null){
            example=new Ingredient("Haribo Tangfastics", 50.0, 215, "5012035936648");
            savedIngredientsRecord.saveIngredient(example);
        }
        if(getIngredientByBarcode("21043123")==null){
            example=new Ingredient("ASDA Conchigle Pasta", 31.0, 500, "21043123");
            savedIngredientsRecord.saveIngredient(example);
        }
        if(getIngredientByBarcode("25215342")==null){
            example=new Ingredient("Conchigle Pasta", 30.0, 300, "21043123");
            savedIngredientsRecord.saveIngredient(example);
        }
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

    @Override
    public void saveDoses(List<BasalInsulinDose> basicDoses,String basalInsulinBrandName) throws DuplicateDoseException {
        try {
            Calendar currentTime = Calendar.getInstance();
            Calendar lastTaken;
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
        double weight = getWeight();
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

    public List<BGReading> getHistoryBetween(Calendar from, Calendar to){
        return historyBGRecord.getReadingsBetween(from, to);
    }


    public List<IIngredient> getSavedIngredients() {
        return savedIngredientsRecord.getAllSavedIngredients();
    }


    @Override
    public IIngredient getIngredientByName(String name) {
        return savedIngredientsRecord.getIngredientByName(name);
    }

    @Override
    public IIngredient getIngredientByBarcode(String barcode) {
        return savedIngredientsRecord.getIngredientByBarcode(barcode);
    }

    @Override
    public void eatCurrentMeal(double percentEaten) {
        timeCarbEatenRecord.addCarbsEaten((int)(activeMeal.getNumCarbs()*(percentEaten/100)),
                Calendar.getInstance());
    }

    @Override
    public void setActiveMeal(IMeal activeMeal) {
        this.activeMeal=activeMeal;
        if(activeMeal==null){
            this.activeMealOrigName="";
        }else {
            this.activeMealOrigName = activeMeal.getName();
        }
    }

    @Override
    public IMeal getActiveMeal() {
        return activeMeal;
    }

    @Override
    public boolean mealNameUsed(String newName) {
        if(newName.equals(activeMeal.getName())){
            return false;
        }
        for(IMeal current:savedMealsRecord.getAllMeals(savedIngredientsRecord.getAllSavedIngredients())){
            if(current.getName().equals(newName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setActiveIngredient(IIngredient ingredient) {
        this.activeIngredient=ingredient;
    }

    @Override
    public void updateAndSaveActiveMeal(IMeal meal) {
        if(activeMealOrigName.equals("")){
            savedMealsRecord.saveMeal(meal);
        }else {
            savedMealsRecord.editMeal(activeMealOrigName, meal);
        }
        setActiveMeal(meal);
    }

    @Override
    public void updateActiveMeal(IMeal meal){
        activeMeal=meal;
    }

    @Override
    public List<IMeal> getSavedMeals() {
        return savedMealsRecord.getAllMeals(savedIngredientsRecord.getAllSavedIngredients());
    }

    @Override
    public void saveMeal(IMeal meal) {
        if(activeMealOrigName.equals("")) {
            savedMealsRecord.saveMeal(meal);
        }else{
            savedMealsRecord.editMeal(activeMealOrigName,meal);
        }
        setActiveMeal(meal);
    }

    @Override
    public void registerCarbs(int amount) {
        timeCarbEatenRecord.addCarbsEaten(amount,Calendar.getInstance());
    }

    @Override
    public void deleteMeal(IMeal meal) {
        savedMealsRecord.deleteMeal(meal);
    }

    @Override
    public void setIngredientAmount(int amount) {
        activeMeal.setAmountOf(activeIngredient,amount);
    }

    @Override
    public Integer getActiveIngredientPacketWeight() {
        return activeIngredient.getPacketWeight();
    }

    @Override
    public void removeActiveIngredient() {
        activeMeal.removeIngredient(activeIngredient);
        activeIngredient=null;
    }

    @Override
    public void saveIngredient(IIngredient ingredient) {
        if(activeIngredient==null){
            savedIngredientsRecord.saveIngredient(ingredient);
        }else{
            if(activeMeal.getAmountOf(activeIngredient)!=null&&activeMeal.getAmountOf(activeIngredient)>0){
                activeMeal.removeIngredient(activeIngredient);
            }
            savedIngredientsRecord.updateIngredient(activeIngredient.getName(),ingredient);
        }
        activeIngredient=ingredient;
    }

    @Override
    public IIngredient getActiveIngredient() {
        return activeIngredient;
    }

    @Override
    public double getWeight() {
        return sharPref.getFloat("weight",(float)0.0);
    }

    @Override
    public void setWeight(double weight) {
        SharedPreferences.Editor edit= sharPref.edit();
        edit.putFloat("weight",(float)weight);
        edit.commit();
    }
}
