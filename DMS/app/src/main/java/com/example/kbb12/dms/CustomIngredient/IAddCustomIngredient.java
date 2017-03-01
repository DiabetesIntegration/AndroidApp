package com.example.kbb12.dms.CustomIngredient;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IAddCustomIngredient {
    public boolean checkEntry(String entry);
    public void setItemName(String name);
    public String getItemName();
    public void setCarbVal(String name);
    public String getCarbVal();
    public void setPacketVal(String name);
    public String getPacketVal();
    public void setSugarVal(String name);
    public String getSugarVal();
    //public void addCustomIngredient(double sugarVal);
}
