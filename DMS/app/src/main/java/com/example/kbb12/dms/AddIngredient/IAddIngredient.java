package com.example.kbb12.dms.AddIngredient;

import com.example.kbb12.dms.StartUp.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IAddIngredient {

    public void newCustomIngredient();
    public void itemSearch(String search);
    public List<String> getSavedIngredients();
    public void getSavedIngredient(String item);
}
