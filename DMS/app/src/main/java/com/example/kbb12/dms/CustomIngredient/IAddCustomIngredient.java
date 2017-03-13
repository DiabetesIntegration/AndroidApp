package com.example.kbb12.dms.CustomIngredient;

import com.example.kbb12.dms.StartUp.IIngredient;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IAddCustomIngredient {
    public boolean checkEntry(String entry);
    public IIngredient getCustomIngredient();
    public void setCustomName(String name);
    public void setCustomCarbVal(String val);
    public void setCustomPacketVal(String val);
    public void setCustomSugarVal(String val);
    public void setCustomPacketWeightVal(String val);
}
