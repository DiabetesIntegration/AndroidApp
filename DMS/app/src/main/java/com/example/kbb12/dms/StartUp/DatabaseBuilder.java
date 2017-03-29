package com.example.kbb12.dms.StartUp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.MealPlannerRecord.SavedIngredientsRecord.SavedIngredientsContract;
import com.example.kbb12.dms.MealPlannerRecord.SavedIngredientsRecord.SavedIngredientsDatabase;
import com.example.kbb12.dms.MealPlannerRecord.SavedIngredientsRecord.SavedIngredientsRecord;
import com.example.kbb12.dms.MealPlannerRecord.SavedMealsRecord.SavedMealsContract;
import com.example.kbb12.dms.MealPlannerRecord.SavedMealsRecord.SavedMealsDatabase;
import com.example.kbb12.dms.MealPlannerRecord.SavedMealsRecord.SavedMealsRecord;
import com.example.kbb12.dms.MealPlannerRecord.TimeCarbEatenRecord.TimeCarbEatenContract;
import com.example.kbb12.dms.MealPlannerRecord.TimeCarbEatenRecord.TimeCarbEatenRecord;
import com.example.kbb12.dms.MealPlannerRecord.TimeCarbEatenRecord.TimeCarbsEatenDatabase;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class DatabaseBuilder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="BackingStorage";
    private static final int versionNumber=5;
    private SavedIngredientsRecord savedIngredientsRecord;
    private SavedMealsRecord savedMealsRecord;
    private TimeCarbEatenRecord timeCarbEatenRecord;



    public DatabaseBuilder(Context context){
        super(context,DATABASE_NAME,null,versionNumber);
        savedIngredientsRecord = new SavedIngredientsDatabase(getWritableDatabase());
        savedMealsRecord = new SavedMealsDatabase(getWritableDatabase());
        timeCarbEatenRecord = new TimeCarbsEatenDatabase(getWritableDatabase());
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


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SavedIngredientsContract.SQL_CREATE_TABLE);
        db.execSQL(SavedMealsContract.SQL_CREATE_TABLE);
        db.execSQL(TimeCarbEatenContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SavedIngredientsContract.SQL_DELETE_ENTRIES);
        db.execSQL(SavedMealsContract.SQL_DELETE_ENTRIES);
        db.execSQL(TimeCarbEatenContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
