package com.example.kbb12.dms.StartUp;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IMeal {
    public void changeName(String name);

    public String getName();

    public void addIngredient(IIngredient ing);

    public List<IIngredient> getIngredients();

    public void calculateCarbVal(String percentage);

    public String getCarbValue();

    public void setAmountEaten(String percentage);

    public void calculateAmountAsCarb();



    public void setMealName(String name);
    public String getMealName();
    public String getTotalCarbs();
    public void setCarbsEaten();
    public String getCarbsEaten();
    public void setMealAmount(String amount);
    public String getMealAmount();
    public List<IIngredient> getAllIngredients();
    public boolean setIngredients(List<IIngredient> ing);
    public boolean addIngredient(String name, List<String> nutrients);
    public boolean removeIngredient(int i);
}
