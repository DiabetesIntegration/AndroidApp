package com.example.kbb12.dms.database.mealPlannerRecord;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Ciaran on 4/17/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IngredientTest {
    @Mock
    Ingredient model;
    IIngredient basicmodel;
    String name;
    Double amPerHundred;
    Integer pWeight;
    String barcode;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        name = "pasta sauce";
        amPerHundred = 20.0;
        pWeight = 100;
        barcode = "19403866382";
        model = new Ingredient(name,amPerHundred,pWeight);
        basicmodel = new Ingredient.BasicIngredient();

    }



    @Test
    public void getName() throws Exception {
        assertEquals(name, model.getName());
    }

    @Test
    public void getCarbsPerHundredG() throws Exception {
        assertEquals(amPerHundred,model.getCarbsPerHundredG());
    }

    @Test
    public void getPacketWeight() throws Exception {
        assertEquals(pWeight,model.getPacketWeight());
    }

    @Test
    public void getBarcode() throws Exception {
        assertEquals(null,model.getBarcode());
        amPerHundred = 50.0;

        model = new Ingredient(name,amPerHundred,pWeight,barcode);
        assertEquals(name,model.getName());
        assertEquals(amPerHundred,model.getCarbsPerHundredG());
        assertEquals(pWeight,model.getPacketWeight());
        assertEquals(barcode,model.getBarcode());


    }

    @Test
    public void getCustom() throws Exception {
        Double val = 100.0;
        Integer w = 100;
        assertEquals("Custom",basicmodel.getName());
        assertEquals(val,basicmodel.getCarbsPerHundredG());
        assertEquals(w,basicmodel.getPacketWeight());
        assertEquals(null,basicmodel.getBarcode());

    }

}