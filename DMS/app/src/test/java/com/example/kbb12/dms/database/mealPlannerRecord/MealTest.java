package com.example.kbb12.dms.database.mealPlannerRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Ciaran on 4/17/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MealTest {
    @Mock
    Meal model;
    List<IIngredient> ings;
    List<Double> amnts;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ings = new ArrayList<IIngredient>();
        amnts = new ArrayList<Double>();

        model = new Meal();

    }







    @Test
    public void getName() throws Exception {
        assertEquals("",model.getName());

        String name = "pasta and sauce";
        model = new Meal(name,100);
        assertEquals(name,model.getName());

        name = "breakfast";
        model = new Meal(name,ings,amnts);
        assertEquals(name,model.getName());

    }

    @Test
    public void getIngredients() throws Exception {
        IIngredient i = mock(IIngredient.class);
        Double am = 2.5;

        model.addIngredient(i,am);
        assertEquals(1,model.getIngredients().size());

        assertEquals(i,model.getIngredients().get(0));
    }

    @Test
    public void getAmounts() throws Exception {
        IIngredient i = mock(IIngredient.class);
        Double am = 2.5;

        model.addIngredient(i,am);
        assertEquals(1,model.getAmounts().size());

        assertEquals(am,model.getAmounts().get(0));
    }

    @Test
    public void setAmountOf() throws Exception {
        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("bread");
        Double am = 2.5;
        Double aming = 50.0;

        model.addIngredient(i,am);
        model.setAmountOf(i,aming);

        assertEquals(1,model.getAmounts().size());
        assertEquals(1,model.getIngredients().size());

        assertEquals(aming,model.getAmounts().get(0));

        IIngredient i2 = mock(IIngredient.class);
        when(i2.getName()).thenReturn("jam");
        model = new Meal();
        model.addIngredient(i,am);
        model.setAmountOf(i2,aming);
        assertEquals(2,model.getAmounts().size());
        assertEquals(2,model.getIngredients().size());
    }

    @Test
    public void getAmountOf() throws Exception {
        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("bread");
        Double am = 2.5;

        model.addIngredient(i,am);
        model.getAmountOf(i);

        assertEquals(model.getAmounts().get(0),model.getAmountOf(i));

        IIngredient i2 = mock(IIngredient.class);
        when(i2.getName()).thenReturn("jam");
        model.getAmountOf(i2);
        Double zero = 0.0;
        assertEquals(zero,model.getAmountOf(i2));
    }

    @Test
    public void removeIngredientIndex() throws Exception {
        IIngredient i = mock(IIngredient.class);
        Double am = 2.5;

        model.addIngredient(i,am);
        assertEquals(1,model.getIngredients().size());
        assertEquals(1,model.getAmounts().size());

        model.removeIngredient(0);
        assertEquals(0,model.getIngredients().size());
        assertEquals(0,model.getAmounts().size());
    }

    @Test
    public void removeIngredientByIng() throws Exception {
        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("butter");
        Double am = 2.5;

        model.addIngredient(i,am);
        assertEquals(1,model.getIngredients().size());
        assertEquals(1,model.getAmounts().size());

        model.removeIngredient(i);
        assertEquals(0,model.getIngredients().size());
        assertEquals(0,model.getAmounts().size());
    }

    @Test
    public void getNumCarbs() throws Exception {
        assertTrue(0.0==model.getNumCarbs());

        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("butter");
        when(i.getCarbsPerHundredG()).thenReturn(20.0);
        Double am = 200.0;
        model.addIngredient(i,am);
        assertTrue(40.0==model.getNumCarbs());

    }

    @Test
    public void isCustomCarbMeal() throws Exception {
        IIngredient i = mock(IIngredient.class);
        when(i.getName()).thenReturn("butter");
        Double am = 2.5;

        model.addIngredient(i,am);
        assertFalse(model.isCustomCarbMeal());

        IIngredient i2 = mock(Ingredient.BasicIngredient.class);
        when(i2.getName()).thenReturn("Custom");
        model = new Meal();
        model.addIngredient(i2,am);
        assertTrue(model.isCustomCarbMeal());


    }

}