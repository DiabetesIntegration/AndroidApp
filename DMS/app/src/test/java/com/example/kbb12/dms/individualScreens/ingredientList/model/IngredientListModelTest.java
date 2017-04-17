package com.example.kbb12.dms.individualScreens.ingredientList.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Ingredient;
import com.example.kbb12.dms.database.mealPlannerRecord.Meal;
import com.example.kbb12.dms.model.IngredientsListMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Assert;
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
 * Created by Ciaran on 4/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IngredientListModelTest {

    @Mock
    ModelObserver view;
    IngredientsListMainModel mainModel;
    IngredientListModel model;
    IMeal meal;
    List<IIngredient> ings;
    List<Double> amnts;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(IngredientsListMainModel.class);
        meal = mock(IMeal.class);
        IIngredient bread = new Ingredient("bread", 20.0, 100);
        IIngredient jam = new Ingredient("jam", 5.0, 100);
        ings = new ArrayList<IIngredient>();
        ings.add(bread);
        ings.add(jam);
        amnts = new ArrayList<Double>();
        amnts.add(50.0);
        amnts.add(75.0);
        when(meal.getIngredients()).thenReturn(ings);
        when(meal.getAmounts()).thenReturn(amnts);
        when(meal.getName()).thenReturn("toast");
        when(mainModel.getActiveMeal()).thenReturn(meal);
        assertEquals(2,meal.getIngredients().size());
        model = new IngredientListModel(mainModel);
        assertEquals(meal,model.getActiveMeal());
        verify(mainModel,times(2)).getActiveMeal();
        model.registerObserver(view);
    }


    @Test
    public void getMealName() throws Exception {
        ArgumentCaptor<IMeal> m = ArgumentCaptor.forClass(IMeal.class);

        //set a meal name
        model.setMealName("toast");
        verify(mainModel).updateActiveMeal(m.capture());
        verify(view,times(1)).update();
        Assert.assertEquals("toast", m.getValue().getName());
        Assert.assertEquals(ings, m.getValue().getIngredients());


        Assert.assertEquals(model.getMealName(), "toast");

    }

    @Test
    public void setEditableIngredientPosition() throws Exception {
        ArgumentCaptor<IIngredient> i = ArgumentCaptor.forClass(IIngredient.class);
        model.setEditableIngredientPosition(0);
        verify(mainModel).setActiveIngredient(i.capture());
        Assert.assertEquals(ings.get(0), i.getValue());
    }

    @Test
    public void checkMealName() throws Exception {
        ArgumentCaptor<String> mn = ArgumentCaptor.forClass(String.class);
        when(mainModel.mealNameUsed(meal.getName())).thenReturn(false);
        boolean nameExist = model.checkMealName();
        verify(mainModel).mealNameUsed(mn.capture());
        assertEquals(meal.getName(), mn.getValue());
        assertFalse(nameExist);
    }

    @Test
    public void saveMeal() throws Exception {
        ArgumentCaptor<IMeal> m = ArgumentCaptor.forClass(IMeal.class);
        model.saveMeal();
        verify(mainModel).updateAndSaveActiveMeal(m.capture());
        assertEquals(meal.getName(), m.getValue().getName());
    }

    @Test
    public void newActiveIngredient() throws Exception {
        ArgumentCaptor<IIngredient> i = ArgumentCaptor.forClass(IIngredient.class);
        model.newActiveIngredient();
        verify(mainModel).setActiveIngredient(i.capture());
        assertEquals(null, i.getValue());
    }

    @Test
    public void getActiveMeal() throws Exception {
        when(mainModel.getActiveMeal()).thenReturn(meal);
        IMeal m = model.getActiveMeal();
        assertEquals(m.getName(), meal.getName());
        assertEquals(m.getIngredients(), meal.getIngredients());
        assertEquals(m.getAmounts(), meal.getAmounts());
    }

    @Test
    public void getIngredients() throws Exception {
        List<IIngredient> ingredients = model.getIngredients();
        assertEquals(ingredients.size(), ings.size());
        assertEquals(ingredients.get(0).getName(), ings.get(0).getName());
        assertEquals(ingredients.get(1).getPacketWeight(), ings.get(1).getPacketWeight());
    }

    @Test
    public void getAmountOf() throws Exception {
        when(meal.getAmountOf(ings.get(0))).thenReturn(amnts.get(0));
        when(meal.getAmountOf(ings.get(1))).thenReturn(amnts.get(1));
        assertEquals(amnts.get(0), model.getAmountOf(ings.get(0)));
        assertEquals(amnts.get(1), model.getAmountOf(ings.get(1)));
    }

    @Test
    public void removeItem() throws Exception {
        ArgumentCaptor<Integer> index = ArgumentCaptor.forClass(Integer.class);

        Integer i = 0;
        model.removeItem(i);
        verify(meal).removeIngredient(index.capture());
        verify(view,times(1)).update();
        assertEquals(i,index.getValue());

    }

}