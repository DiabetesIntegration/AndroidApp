package com.example.kbb12.dms.model.mealPlannerRecord;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IIngredient {
    String getName();
    Double getCarbsPerHundredG();
    Integer getPacketWeight();
    String getBarcode();
}
