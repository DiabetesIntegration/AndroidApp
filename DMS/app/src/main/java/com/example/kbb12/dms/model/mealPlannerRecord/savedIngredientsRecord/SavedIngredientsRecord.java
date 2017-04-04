package com.example.kbb12.dms.model.mealPlannerRecord.savedIngredientsRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface SavedIngredientsRecord {

    public long saveIngredient(String n, String cVal, String pVal, String pWeight);
    public Map<Integer, List<String>> getAllSavedIngredients();

}
