package com.example.kbb12.dms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.database.activityRecord.ActivityRecord;
import com.example.kbb12.dms.database.activityRecord.ActivityRecordContract;
import com.example.kbb12.dms.database.activityRecord.ActivityRecordDatabase;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinModel;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinModelContract;
import com.example.kbb12.dms.database.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.CurrentBGContract;
import com.example.kbb12.dms.database.bloodGlucoseRecord.CurrentBGModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.HistoryBGContract;
import com.example.kbb12.dms.database.bloodGlucoseRecord.HistoryBGModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGDatabase;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawBGRecord;
import com.example.kbb12.dms.database.bloodGlucoseRecord.RawDataContract;
import com.example.kbb12.dms.database.bolusInsulinModel.BolusInsulinModel;
import com.example.kbb12.dms.database.bolusInsulinModel.BolusInsulinModelContractHolder;
import com.example.kbb12.dms.database.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoContract;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoDatabase;
import com.example.kbb12.dms.database.dailyFitnessInfo.DailyFitnessInfoRecord;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenContract;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenDatabase;
import com.example.kbb12.dms.database.insulinTakenRecord.InsulinTakenRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsContract;
import com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsDatabase;
import com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord.SavedMealsContract;
import com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord.SavedMealsDatabase;
import com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenContract;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.database.mealPlannerRecord.timeCarbEatenRecord.TimeCarbsEatenDatabase;

/**
 * Created by kbb12 on 24/03/2017.
 */

public class DatabaseBuilder extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="BackingStorage";
    private static final int versionNumber=13;
    private IBolusInsulinModel bolusInsulinModel;
    private IBasalInsulinModel basalInsulinModel;
    private InsulinTakenRecord insulinTakenRecord;
    private RawBGRecord rawBGRecord;
    private BGRecord historyBGRecord;
    private BGRecord currentBGRecord;
    private DailyFitnessInfoRecord dailyFitnessInfoRecord;
    private ActivityRecord activityRecord;
    private SavedIngredientsRecord savedIngredientsRecord;
    private SavedMealsRecord savedMealsRecord;
    private TimeCarbEatenRecord timeCarbEatenRecord;

    public IBolusInsulinModel getBolusInsulinModel() {
        return bolusInsulinModel;
    }

    public IBasalInsulinModel getBasalInsulinModel() {
        return basalInsulinModel;
    }

    public InsulinTakenRecord getInsulinTakenRecord() {
        return insulinTakenRecord;
    }

    public RawBGRecord getRawBGRecord(){
        return rawBGRecord;
    }

    public BGRecord getHistoryBGRecord(){
        return historyBGRecord;
    }

    public BGRecord getCurrentBGRecord(){
        return currentBGRecord;
    }

    public ActivityRecord getActivityRecord() {
        return activityRecord;
    }

    public SavedIngredientsRecord getSavedIngredientsRecord() {
        return savedIngredientsRecord;
    }

    public SavedMealsRecord getSavedMealsRecord() {
        return savedMealsRecord;
    }

    public TimeCarbEatenRecord getTimeCarbEatenRecord() {
        return timeCarbEatenRecord;
    }

    public DatabaseBuilder(Context context){
        super(context,DATABASE_NAME,null,versionNumber);
        bolusInsulinModel=new BolusInsulinModel(getWritableDatabase());
        basalInsulinModel=new BasalInsulinModel(getWritableDatabase());
        insulinTakenRecord=new InsulinTakenDatabase(getWritableDatabase());
        rawBGRecord = new RawBGDatabase(getWritableDatabase());
        historyBGRecord = new HistoryBGModel(getWritableDatabase());
        currentBGRecord = new CurrentBGModel(getWritableDatabase());
        dailyFitnessInfoRecord = new DailyFitnessInfoDatabase(getWritableDatabase());
        activityRecord= new ActivityRecordDatabase(getWritableDatabase());
        savedIngredientsRecord = new SavedIngredientsDatabase(getWritableDatabase());
        savedMealsRecord = new SavedMealsDatabase(getWritableDatabase());
        timeCarbEatenRecord = new TimeCarbsEatenDatabase(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BolusInsulinModelContractHolder.SQL_CREATE_ENTRIES);
        for(int i=0;i<24;i++){
            db.execSQL("INSERT INTO "
                    + BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME +
                    " (" + BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +
                    ")VALUES(\"" + String.format("%2d:00",i) + "\")");
        }
        db.execSQL(BasalInsulinModelContract.SQL_CREATE_ENTRIES);
        db.execSQL(InsulinTakenContract.SQL_CREATE_ENTRIES);
        db.execSQL(RawDataContract.SQL_CREATE_ENTRIES);
        db.execSQL(HistoryBGContract.SQL_CREATE_TABLE);
        db.execSQL(CurrentBGContract.SQL_CREATE_TABLE);
        db.execSQL(DailyFitnessInfoContract.SQL_CREATE_ENTRIES);
        db.execSQL(ActivityRecordContract.SQL_CREATE_ENTRIES);
        db.execSQL(SavedIngredientsContract.SQL_CREATE_ENTRIES);
        db.execSQL(SavedMealsContract.SQL_CREATE_ENTRIES);
        db.execSQL(TimeCarbEatenContract.SQL_CREATE_TABLE);
    }

    public DailyFitnessInfoRecord getDailyFitnessInfoRecord() {
        return dailyFitnessInfoRecord;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BolusInsulinModelContractHolder.SQL_DELETE_ENTRIES);
        db.execSQL(BasalInsulinModelContract.SQL_DELETE_ENTRIES);
        db.execSQL(InsulinTakenContract.SQL_DELETE_ENTRIES);
        db.execSQL(RawDataContract.SQL_DELETE_ENTRIES);
        db.execSQL(HistoryBGContract.SQL_DELETE_ENTRIES);
        db.execSQL(CurrentBGContract.SQL_DELETE_ENTRIES);
        db.execSQL(DailyFitnessInfoContract.SQL_DELETE_ENTRIES);
        db.execSQL(ActivityRecordContract.SQL_DELETE_ENTRIES);
        db.execSQL(SavedIngredientsContract.SQL_DELETE_ENTRIES);
        db.execSQL(SavedMealsContract.SQL_DELETE_ENTRIES);
        db.execSQL(TimeCarbEatenContract.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
