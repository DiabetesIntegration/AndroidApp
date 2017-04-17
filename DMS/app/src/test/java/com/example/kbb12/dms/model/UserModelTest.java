package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.DatabaseBuilder;
import com.example.kbb12.dms.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.database.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.database.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoRecord;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kbb12 on 17/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserModelTest {

    UserModel model;

    private DailyFitnessInfoRecord dailyFitnessInfoRecord;

    private ActivityRecord activityRecord;

    private RawBGRecord rawBGRecord;

    private BGRecord historyBGRecord;

    private BGRecord currentBGRecord;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        DatabaseBuilder db = mock(DatabaseBuilder.class);
        IBasalInsulinModel basalInsulinModel = mock(IBasalInsulinModel.class);
        when(db)
        IBolusInsulinModel bolusInsulinModel = mock(IBolusInsulinModel.class);
        InsulinTakenRecord insulinTakenRecord = mock(InsulinTakenRecord.class);
        DailyFitnessInfoRecord dailyFitnessInfoRecord = mock(DailyFitnessInfoRecord.class);
        ActivityRecord activityRecord = mock(ActivityRecord.class);
        RawBGRecord rawBGRecord = mock(RawBGRecord.class);
        BGRecord historyBGRecord = mock(BGRecord.class);
        BGRecord currentBGRecord = mock(BGRecord.class);
        model = new UserModel();
    }

    @Test
    public void addRawData() throws Exception {

    }

    @Test
    public void addHistoryReading() throws Exception {

    }

    @Test
    public void addCurrentReading() throws Exception {

    }

    @Test
    public void getMostRecentHistoryReading() throws Exception {

    }

    @Test
    public void saveDoses() throws Exception {

    }

    @Test
    public void getDoses() throws Exception {

    }

    @Test
    public void getLatestBasalRecommendation() throws Exception {

    }

    @Test
    public void takeInsulin() throws Exception {

    }

    @Test
    public void getRecentCarbs() throws Exception {

    }

    @Test
    public void getCurrentICR() throws Exception {

    }

    @Test
    public void getCurrentISF() throws Exception {

    }

    @Test
    public void getCurrentBG() throws Exception {

    }

    @Test
    public void hasTakenBolusInsulinRecently() throws Exception {

    }

    @Test
    public void createInsulinToCarbModel() throws Exception {

    }

    @Test
    public void createInsulinToCarbModel1() throws Exception {

    }

    @Test
    public void createInsulinSensitivityModel() throws Exception {

    }

    @Test
    public void createInsulinSensitivityModel1() throws Exception {

    }

    @Test
    public void logModels() throws Exception {

    }

    @Test
    public void addToCalCount() throws Exception {

    }

    @Test
    public void saveActivity() throws Exception {

    }

    @Test
    public void getCalCount() throws Exception {

    }

    @Test
    public void getHistoryBetween() throws Exception {

    }

    @Test
    public void getSavedIngredients() throws Exception {

    }

    @Test
    public void getIngredientByName() throws Exception {

    }

    @Test
    public void getIngredientByBarcode() throws Exception {

    }

    @Test
    public void eatCurrentMeal() throws Exception {

    }

    @Test
    public void setActiveMeal() throws Exception {

    }

    @Test
    public void getActiveMeal() throws Exception {

    }

    @Test
    public void mealNameUsed() throws Exception {

    }

    @Test
    public void setActiveIngredient() throws Exception {

    }

    @Test
    public void updateAndSaveActiveMeal() throws Exception {

    }

    @Test
    public void updateActiveMeal() throws Exception {

    }

    @Test
    public void getSavedMeals() throws Exception {

    }

    @Test
    public void saveMeal() throws Exception {

    }

    @Test
    public void registerCarbs() throws Exception {

    }

    @Test
    public void deleteMeal() throws Exception {

    }

    @Test
    public void setIngredientAmount() throws Exception {

    }

    @Test
    public void getActiveIngredientPacketWeight() throws Exception {

    }

    @Test
    public void removeActiveIngredient() throws Exception {

    }

    @Test
    public void saveIngredient() throws Exception {

    }

    @Test
    public void getActiveIngredient() throws Exception {

    }

    @Test
    public void getWeight() throws Exception {

    }

    @Test
    public void setWeight() throws Exception {

    }

}