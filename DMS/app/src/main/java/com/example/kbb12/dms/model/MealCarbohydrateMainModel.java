package com.example.kbb12.dms.model;

import com.example.kbb12.dms.startUp.IMeal;

import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface MealCarbohydrateMainModel {
    List<IMeal> getSavedMeals();
    void saveMeal(String name,int amount);
    void registerCarbs(int amount);
}
