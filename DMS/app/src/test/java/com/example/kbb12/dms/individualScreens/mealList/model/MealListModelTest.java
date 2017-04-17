package com.example.kbb12.dms.individualScreens.mealList.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Ingredient;
import com.example.kbb12.dms.database.mealPlannerRecord.Meal;
import com.example.kbb12.dms.model.MealListMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ciaran on 4/16/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MealListModelTest {

    @Mock
    ModelObserver view;
    MealListMainModel mainModel;
    MealListModel model;
    List<IMeal> meals;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(MealListMainModel.class);
        meals = new ArrayList<IMeal>();
        List<IIngredient> ings = new ArrayList<IIngredient>();
        List<Double> amnts = new ArrayList<Double>();
        ings.add(new Ingredient("z",20.0,100));
        amnts.add(200.0);
        IMeal m1 = new Meal("a",ings,amnts);
        IMeal m2 = new Meal("b",70);
        IMeal m3 = new Meal("c",ings,amnts);
        meals.add(m1);
        meals.add(m2);
        meals.add(m3);
        model = new MealListModel(mainModel);
        when(mainModel.getSavedMeals()).thenReturn(meals);
        model.registerObserver(view);
    }


    @Test
    public void setNewMeal() throws Exception {
        ArgumentCaptor<IMeal> meal = ArgumentCaptor.forClass(IMeal.class);
        model.setNewMeal();
        verify(mainModel).setActiveMeal(meal.capture());
        assertEquals("",meal.getValue().getName());
    }

    @Test
    public void getSavedMeals() throws Exception {
        List<String> mDetails = model.getSavedMeals();
        assertEquals("a - 40.0g of carbohydrate in meal", mDetails.get(0));
        assertEquals("b - 70.0g of carbohydrate in meal (No ingredients)", mDetails.get(1));
        assertEquals("c - 40.0g of carbohydrate in meal", mDetails.get(2));

    }

    @Test
    public void selectMeal() throws Exception {
        ArgumentCaptor<IMeal> selected = ArgumentCaptor.forClass(IMeal.class);

        model.selectMeal(0);
        verify(mainModel).setActiveMeal(selected.capture());
        assertEquals(meals.get(0).getName(),selected.getValue().getName());
    }

    @Test
    public void isSelectedMealCustom() throws Exception {
        IMeal m = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(m);
        when(m.isCustomCarbMeal()).thenReturn(true);
        assertTrue(model.isSelectedMealCustom());
        when(m.isCustomCarbMeal()).thenReturn(false);
        assertFalse(model.isSelectedMealCustom());
    }

    @Test
    public void removeItem() throws Exception {
        ArgumentCaptor<IMeal> mealRemove = ArgumentCaptor.forClass(IMeal.class);

        assertTrue(model.removeItem(0));
        verify(mainModel).deleteMeal(mealRemove.capture());
        assertEquals(meals.get(0).getName(), mealRemove.getValue().getName());

        verify(view,times(1)).update();
    }

}