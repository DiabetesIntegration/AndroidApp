package com.example.kbb12.dms.mealPlannerRecord.savedMealsRecord;

import com.example.kbb12.dms.startUp.IMeal;

import java.util.List;
import java.util.Map;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface SavedMealsRecord {

    public long saveMeal(String n, String ing, String ingVal, String tCarb, String custom);
    public long editMeal(Integer id, String n, String ing, String ingVal, String tCarb, String custom);
    public long deleteMeal(IMeal m);
    public Map<Integer, List<String>> getAllMeals();

}
