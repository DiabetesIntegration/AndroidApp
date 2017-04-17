package com.example.kbb12.dms.model;

import android.content.SharedPreferences;

import com.example.kbb12.dms.database.DatabaseBuilder;
import com.example.kbb12.dms.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.database.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.database.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoRecord;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kbb12 on 17/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserModelTest {

    UserModel model;

    IBasalInsulinModel basalInsulinModel;

    IBolusInsulinModel bolusInsulinModel;

    InsulinTakenRecord insulinTakenRecord;

    DailyFitnessInfoRecord dailyFitnessInfoRecord;

    ActivityRecord activityRecord;

    RawBGRecord rawBGRecord;

    BGRecord historyBGRecord;

    BGRecord currentBGRecord;

    SharedPreferences sharedPreferences;

    SavedIngredientsRecord savedIngredientsRecord;
    SavedMealsRecord savedMealsRecord;
    TimeCarbEatenRecord timeCarbEatenRecord;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        DatabaseBuilder db = mock(DatabaseBuilder.class);
        basalInsulinModel = mock(IBasalInsulinModel.class);
        when(db.getBasalInsulinModel()).thenReturn(basalInsulinModel);
        bolusInsulinModel = mock(IBolusInsulinModel.class);
        when(db.getBolusInsulinModel()).thenReturn(bolusInsulinModel);
        insulinTakenRecord = mock(InsulinTakenRecord.class);
        when(db.getInsulinTakenRecord()).thenReturn(insulinTakenRecord);
        dailyFitnessInfoRecord = mock(DailyFitnessInfoRecord.class);
        when(db.getDailyFitnessInfoRecord()).thenReturn(dailyFitnessInfoRecord);
        activityRecord = mock(ActivityRecord.class);
        when(db.getActivityRecord()).thenReturn(activityRecord);
        rawBGRecord = mock(RawBGRecord.class);
        when(db.getRawBGRecord()).thenReturn(rawBGRecord);
        historyBGRecord = mock(BGRecord.class);
        when(db.getHistoryBGRecord()).thenReturn(historyBGRecord);
        currentBGRecord = mock(BGRecord.class);
        when(db.getCurrentBGRecord()).thenReturn(currentBGRecord);
        savedIngredientsRecord = mock(SavedIngredientsRecord.class);
        when(savedIngredientsRecord.getIngredientByBarcode(isA(String.class))).thenReturn(null);
        when(db.getSavedIngredientsRecord()).thenReturn(savedIngredientsRecord);
        savedMealsRecord = mock(SavedMealsRecord.class);
        when(db.getSavedMealsRecord()).thenReturn(savedMealsRecord);
        timeCarbEatenRecord = mock(TimeCarbEatenRecord.class);
        sharedPreferences = mock(SharedPreferences.class);
        model = new UserModel(db,sharedPreferences);
    }

    @Test
    public void testAddingExamples() throws Exception{
        ArgumentCaptor<IIngredient> ingCapture = ArgumentCaptor.forClass(IIngredient.class);
        verify(savedIngredientsRecord,times(6)).saveIngredient(ingCapture.capture());
        List<IIngredient> ingredientsAdded =ingCapture.getAllValues();
        Assert.assertEquals(ingredientsAdded.get(0).getBarcode(),"5000232823458");
        Assert.assertEquals(ingredientsAdded.get(0).getCarbsPerHundredG(),(Double)72.0);
        Assert.assertEquals(ingredientsAdded.get(0).getPacketWeight(),(Integer) 1000);
        Assert.assertEquals(ingredientsAdded.get(0).getName(),"Napolina Fusilli Pasta");
        Assert.assertEquals(ingredientsAdded.get(1).getBarcode(),"5010061001613");
        Assert.assertEquals(ingredientsAdded.get(1).getCarbsPerHundredG(),(Double)3.6);
        Assert.assertEquals(ingredientsAdded.get(1).getPacketWeight(),(Integer) 100);
        Assert.assertEquals(ingredientsAdded.get(1).getName(),"Napolina Chopped Tomatoes");
        Assert.assertEquals(ingredientsAdded.get(2).getBarcode(),"4002359640469");
        Assert.assertEquals(ingredientsAdded.get(2).getCarbsPerHundredG(),(Double)8.4);
        Assert.assertEquals(ingredientsAdded.get(2).getPacketWeight(),(Integer) 100);
        Assert.assertEquals(ingredientsAdded.get(2).getName(),"Dolmio Tomato and Basil Sauce");
        Assert.assertEquals(ingredientsAdded.get(3).getBarcode(),"5012035936648");
        Assert.assertEquals(ingredientsAdded.get(3).getCarbsPerHundredG(),(Double)50.0);
        Assert.assertEquals(ingredientsAdded.get(3).getPacketWeight(),(Integer) 215);
        Assert.assertEquals(ingredientsAdded.get(3).getName(),"Haribo Tangfastics");
        Assert.assertEquals(ingredientsAdded.get(4).getBarcode(),"21043123");
        Assert.assertEquals(ingredientsAdded.get(4).getCarbsPerHundredG(),(Double)31.0);
        Assert.assertEquals(ingredientsAdded.get(4).getPacketWeight(),(Integer) 500);
        Assert.assertEquals(ingredientsAdded.get(4).getName(),"ASDA Conchigle Pasta");
        Assert.assertEquals(ingredientsAdded.get(5).getBarcode(),"21043123");
        Assert.assertEquals(ingredientsAdded.get(5).getCarbsPerHundredG(),(Double)30.0);
        Assert.assertEquals(ingredientsAdded.get(5).getPacketWeight(),(Integer) 300);
        Assert.assertEquals(ingredientsAdded.get(5).getName(),"Conchigle Pasta");
    }

    @Test
    public void addRawData() throws Exception {
        Calendar c = mock(Calendar.class);
        String data = "skdjbf";
        model.addRawData(c,data);
        verify(rawBGRecord,times(1)).addRawData(data,c);
    }

    @Test
    public void addHistoryReading() throws Exception {
        Calendar c = mock(Calendar.class);
        double data = 5.6;
        model.addHistoryReading(c,data);
        verify(historyBGRecord).insertReading(c,data);
    }

    @Test
    public void addCurrentReading() throws Exception {
        Calendar c = mock(Calendar.class);
        double data = 5.6;
        model.addCurrentReading(c,data);
        verify(currentBGRecord).insertReading(c,data);
    }

    @Test
    public void getMostRecentHistoryReading() throws Exception {
        BGReading reading = mock(BGReading.class);
        when(historyBGRecord.getMostRecentReading()).thenReturn(reading);
        BGReading result = model.getMostRecentHistoryReading();
        verify(historyBGRecord).getMostRecentReading();
        Assert.assertEquals(reading,result);
    }

    @Test
    public void saveDoses() throws Exception {
        ArgumentCaptor<BasalInsulinDose> doseArgumentCaptor = ArgumentCaptor.forClass(BasalInsulinDose.class);
        ArgumentCaptor<String> nameCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> dayCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> month = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> year = ArgumentCaptor.forClass(Integer.class);
        List<BasalInsulinDose> doses = new ArrayList<>();
        Calendar cal;
        BasalInsulinDose dose;
        for (int i=-3;i<3;i++){
            cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY,i);
            dose = mock(BasalInsulinDose.class);
            when(dose.getHour()).thenReturn(cal.get(Calendar.HOUR_OF_DAY));
            when(dose.getMinute()).thenReturn(cal.get(Calendar.MINUTE));
            doses.add(dose);
        }
        model.saveDoses(doses,"name");
        verify(basalInsulinModel,times(6)).addEntry(doseArgumentCaptor.capture(),nameCapture.capture(),dayCapture.capture(),month.capture(),year.capture());
        List<BasalInsulinDose> doseResults = doseArgumentCaptor.getAllValues();
        List<String> nameResults = nameCapture.getAllValues();
        List<Integer> dayResults = dayCapture.getAllValues();
        List<Integer> monthResults = month.getAllValues();
        List<Integer> yearResults = year.getAllValues();
        Calendar day;
        for(int i=0;i<6;i++){
            System.out.println(" "+i);
            Assert.assertEquals(doseResults.get(i),doses.get(i));
            Assert.assertEquals(nameResults.get(i),"name");
            day=Calendar.getInstance();
            if(i>3){
                day.add(Calendar.DAY_OF_MONTH,-1);
            }
            Assert.assertEquals(dayResults.get(i),(Integer) day.get(Calendar.DAY_OF_MONTH));
            Assert.assertEquals(monthResults.get(i),(Integer) day.get(Calendar.MONTH));
            Assert.assertEquals(yearResults.get(i),(Integer) day.get(Calendar.YEAR));
        }
    }

    @Test
    public void getDoses() throws Exception {
        List<BasalInsulinEntry> doses = mock(List.class);
        when(basalInsulinModel.getEntries(true)).thenReturn(doses);
        List<BasalInsulinEntry> result = model.getDoses();
        verify(basalInsulinModel).getEntries(true);
        Assert.assertEquals(doses,result);
    }

    @Test
    public void getLatestBasalRecommendation() throws Exception {
        Calendar cal = Calendar.getInstance();
        BasalInsulinEntry basalInsulinEntry = mock(BasalInsulinEntry.class);
        when(basalInsulinEntry.getHour()).thenReturn(cal.get(Calendar.HOUR_OF_DAY));
        when(basalInsulinEntry.getMinute()).thenReturn(cal.get(Calendar.MINUTE));
        when(basalInsulinModel.getLatestBefore(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)+1,true)).thenReturn(basalInsulinEntry);
        when(basalInsulinModel.getLastTakenAprox(basalInsulinEntry)).thenReturn(cal);
        Assert.assertEquals(null,model.getLatestBasalRecommendation());
        cal.add(Calendar.DAY_OF_MONTH,-1);
        when(basalInsulinModel.getLastTakenAprox(basalInsulinEntry)).thenReturn(cal);
        BasalInsulinEntry result = model.getLatestBasalRecommendation();
        verify(basalInsulinModel).allTakenBefore(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.DAY_OF_MONTH)+1,cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
        Assert.assertEquals(result,basalInsulinEntry);
    }

    @Test
    public void getCurrentBG() throws Exception {
        when(currentBGRecord.getMostRecentReading()).thenReturn(null);
        assertEquals(null,model.getCurrentBG());
    }

    @Test
    public void hasTakenBolusInsulinRecently() throws Exception {
        when(insulinTakenRecord.getMostRecentBolus()).thenReturn(null);
        assertFalse(model.hasTakenBolusInsulinRecently());
    }

    @Test
    public void createInsulinToCarbModel() throws Exception {
        Double val1 = 2.0;
        Double val2 = 3.0;
        Double val3 = 4.0;
        Double val4 = 5.0;
        Double val5 = 6.0;
        Double val6 = 7.0;
        Double val7 = 8.0;
        Double val8 = 9.0;
        model.createInsulinToCarbModel(val1,val2,val3,val4,val5,val6,val7,val8);
        verify(bolusInsulinModel).createInsulinToCarbModel(val1,val2,val3,val4,val5,val6,val7,val8);
    }

    @Test
    public void createInsulinToCarbModel1() throws Exception {
        Double val = 5.0;
        model.createInsulinToCarbModel(val);
        verify(bolusInsulinModel).createInsulinToCarbModel(val);
    }

    @Test
    public void createInsulinSensitivityModel() throws Exception {
        Double val1 = 2.0;
        Double val2 = 3.0;
        Double val3 = 4.0;
        Double val4 = 5.0;
        model.createInsulinSensitivityModel(val1,val2,val3,val4);
        verify(bolusInsulinModel).createInsulinSensitivityModel(val1,val2,val3,val4);
    }

    @Test
    public void createInsulinSensitivityModel1() throws Exception {
        Double val = 2.0;
        model.createInsulinSensitivityModel(val);
        verify(bolusInsulinModel).createInsulinSensitivityModel(val);
    }


    @Test
    public void addToCalCount() throws Exception {
        Calendar cal = Calendar.getInstance();
        int calorie = 50;
        model.addToCalCount(cal,calorie);
        verify(dailyFitnessInfoRecord).addToCalCount(cal,calorie);

    }


    @Test
    public void getHistoryBetween() throws Exception {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        model.getHistoryBetween(from,to);
        verify(historyBGRecord).getReadingsBetween(from,to);
    }

    @Test
    public void getSavedIngredients() throws Exception {
        List<IIngredient> ing = new ArrayList<IIngredient>();
        when(savedIngredientsRecord.getAllSavedIngredients()).thenReturn(ing);
        assertEquals(ing,model.getSavedIngredients());

    }

    @Test
    public void getIngredientByName() throws Exception {
        String name = "qwerty";
        model.getIngredientByName(name);
        verify(savedIngredientsRecord).getIngredientByName(name);
    }

    @Test
    public void getIngredientByBarcode() throws Exception {
        String b = "1234";
        model.getIngredientByBarcode(b);
        verify(savedIngredientsRecord).getIngredientByBarcode(b);

    }


    @Test
    public void mealNameUsed() throws Exception {
        List<IIngredient> ings = new ArrayList<IIngredient>();
        List<IMeal> meals = new ArrayList<IMeal>();
        IMeal m = mock(IMeal.class);
        when(m.getName()).thenReturn("a");
        meals.add(m);
        when(savedIngredientsRecord.getAllSavedIngredients()).thenReturn(ings);
        when(savedMealsRecord.getAllMeals(savedIngredientsRecord.getAllSavedIngredients())).thenReturn(meals);
        model.setActiveMeal(m);
        assertFalse(model.mealNameUsed("b"));
    }

    @Test
    public void updateAndSaveActiveMeal() throws Exception {
        IMeal m = mock(IMeal.class);
        when(m.getName()).thenReturn("a");
        model.setActiveMeal(m);
        model.updateAndSaveActiveMeal(m);
        verify(savedMealsRecord).editMeal("a",m);
        assertEquals(m,model.getActiveMeal());
    }

    @Test
    public void updateActiveMeal() throws Exception {
        IMeal m = mock(IMeal.class);
        model.updateActiveMeal(m);
        assertEquals(m,model.getActiveMeal());
    }

    @Test
    public void getSavedMeals() throws Exception {
        List<IIngredient> mealings = new ArrayList<IIngredient>();
        when(savedIngredientsRecord.getAllSavedIngredients()).thenReturn(mealings);
        assertEquals(mealings,model.getSavedMeals());
        verify(savedMealsRecord).getAllMeals(mealings);
    }

    @Test
    public void saveMeal() throws Exception {
        IMeal m = mock(IMeal.class);
        when(m.getName()).thenReturn("a");
        model.setActiveMeal(m);
        model.saveMeal(m);
        verify(savedMealsRecord).editMeal("a",m);
        assertEquals(m,model.getActiveMeal());
    }

    @Test
    public void deleteMeal() throws Exception {
        IMeal m = mock(IMeal.class);
        model.deleteMeal(m);
        verify(savedMealsRecord).deleteMeal(m);
    }

    @Test
    public void setIngredientAmount() throws Exception {
        IIngredient i = mock(IIngredient.class);
        IMeal m = mock(IMeal.class);
        model.setActiveIngredient(i);
        model.setActiveMeal(m);
        model.setIngredientAmount(10);
        verify(model.getActiveMeal()).setAmountOf(i,10.0);
    }

    @Test
    public void getActiveIngredientPacketWeight() throws Exception {
        IIngredient i = mock(IIngredient.class);
        Integer pw = 100;
        when(i.getPacketWeight()).thenReturn(pw);
        model.setActiveIngredient(i);
        assertEquals(pw,model.getActiveIngredientPacketWeight());
    }

    @Test
    public void removeActiveIngredient() throws Exception {
        IIngredient i = mock(IIngredient.class);
        IMeal m = mock(IMeal.class);

        model.setActiveIngredient(i);
        model.setActiveMeal(m);
        model.removeActiveIngredient();
        verify(model.getActiveMeal()).removeIngredient(i);
        assertEquals(null,model.getActiveIngredient());
    }

    @Test
    public void saveIngredient() throws Exception {
        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("ing");
        IMeal m = mock(IMeal.class);
        when(m.getName()).thenReturn("a");
        when(m.getAmountOf(i)).thenReturn(20.0);
        model.setActiveIngredient(null);
        model.saveIngredient(i);
        assertEquals(i,model.getActiveIngredient());
        verify(savedIngredientsRecord).saveIngredient(i);

        model.setActiveIngredient(i);
        model.setActiveMeal(m);
        model.saveIngredient(i);
        verify(model.getActiveMeal()).removeIngredient(i);
        verify(savedIngredientsRecord).updateIngredient(i.getName(),i);
        assertEquals(i,model.getActiveIngredient());


    }

    @Test
    public void getActiveIngredient() throws Exception {
        IIngredient i = mock(IIngredient.class);
        model.setActiveIngredient(i);
        assertEquals(i,model.getActiveIngredient());
    }


}