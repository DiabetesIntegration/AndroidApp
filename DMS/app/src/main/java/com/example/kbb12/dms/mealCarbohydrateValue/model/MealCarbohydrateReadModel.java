package com.example.kbb12.dms.mealCarbohydrateValue.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;

/**
 * Created by kbb12 on 06/04/2017.
 */

public interface MealCarbohydrateReadModel extends BaseReadModel {
    boolean hasMeal();
    String getMealName();
    Integer getMealCarbs();
}
