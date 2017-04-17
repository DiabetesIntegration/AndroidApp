package com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Meal;
import com.example.kbb12.dms.model.MealCarbohydrateMainModel;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ciaran on 4/16/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MealCarbohydrateModelTest {

    @Mock
    ModelObserver view;
    MealCarbohydrateMainModel mainModel;
    MealCarbohydrateModel model;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(MealCarbohydrateMainModel.class);
        model = new MealCarbohydrateModel(mainModel);
        model.registerObserver(view);
    }


    @Test
    public void isNameTaken() throws Exception {
        IMeal m = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(m);
        when(m.getName()).thenReturn("egg");

        assertFalse(model.isNameTaken("egg"));

        when(m.getName()).thenReturn("ham");
        assertFalse(model.isNameTaken("egg"));


        List<IMeal> allMeals = new ArrayList<IMeal>();
        allMeals.add(new Meal("ham and chips", 80));
        allMeals.add(new Meal("spaghetti",120));
        allMeals.add(new Meal("fish and veg",100));
        when(mainModel.getSavedMeals()).thenReturn(allMeals);
        assertTrue(model.isNameTaken("spaghetti"));
        assertFalse(model.isNameTaken("bangers and mash"));


    }

    @Test
    public void addNewCarbMeal() throws Exception {
        ArgumentCaptor<IMeal> meal = ArgumentCaptor.forClass(IMeal.class);
        ArgumentCaptor<IMeal> m = ArgumentCaptor.forClass(IMeal.class);
        ArgumentCaptor<IIngredient> ing = ArgumentCaptor.forClass(IIngredient.class);
        String mn = "cake";
        Integer carb = 70;
        IMeal newm = new Meal(mn,carb);

        model.addNewCarbMeal(mn,carb);
        verify(mainModel).saveMeal(meal.capture());
        assertEquals(newm.getName(),meal.getValue().getName());
        assertEquals(newm.getAmounts().size(),meal.getValue().getAmounts().size());
        assertEquals(newm.getAmounts().get(0), meal.getValue().getAmounts().get(0));

        verify(mainModel).setActiveMeal(m.capture());
        assertEquals(null,m.getValue());

        verify(mainModel).setActiveIngredient(ing.capture());
        assertEquals(null,ing.getValue());
    }

    @Test
    public void eatCarbs() throws Exception {
        ArgumentCaptor<Integer> d = ArgumentCaptor.forClass(Integer.class);
        Integer a = 50;
        model.eatCarbs(a);
        verify(mainModel).registerCarbs(d.capture());
        assertEquals(a,d.getValue());
    }

    @Test
    public void hasMeal() throws Exception {
        IMeal m = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(m);
        when(m.getName()).thenReturn("");
        assertFalse(model.hasMeal());
        when(m.getName()).thenReturn("fried egg");
        assertTrue(model.hasMeal());

    }

    @Test
    public void getMealName() throws Exception {
        IMeal m = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(m);
        when(m.getName()).thenReturn("toast");

        assertEquals(model.getMealName(),"toast");
    }

    @Test
    public void getMealCarbs() throws Exception {
        IMeal m = mock(IMeal.class);
        Integer mCarb = 15;
        when(mainModel.getActiveMeal()).thenReturn(m);
        when(m.getNumCarbs()).thenReturn(15.0);

        assertEquals(mCarb,model.getMealCarbs());

    }

}