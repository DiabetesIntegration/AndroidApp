package com.example.kbb12.dms.addIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IAddIngredient {

    public void setNewIngredient();
    public void removeIngListView();
    public void setAddIngredient(boolean ing);
    public void itemSearch(String search);

    public List<String> getSavedIngredients();
    public void getSavedIngredient(String item);
}
