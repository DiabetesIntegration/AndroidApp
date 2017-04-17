package com.example.kbb12.dms.test;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.individualScreens.ingredientAmount.model.IngredientsAmountModel;
import com.example.kbb12.dms.model.IngredientsAmountMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Ciaran on 4/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class IngredientsAmountModelTest {

    @Mock
    ModelObserver view;
    IngredientsAmountMainModel mainModel;
    IngredientsAmountModel model;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(IngredientsAmountMainModel.class);
        model = new IngredientsAmountModel(mainModel);
        model.registerObserver(view);
    }


    @Test
    public void getUnits() throws Exception {
        Assert.assertEquals("g", model.getUnits());
        model.setUnits("%");
        verify(view,times(1)).update();
        Assert.assertEquals("%", model.getUnits());
    }

    @Test
    public void getActiveIngredient() throws Exception {
        IIngredient ing = mock(IIngredient.class);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        assertEquals(ing,model.getActiveIngredient());
        verify(mainModel,times(1)).getActiveIngredient();
    }

    @Test
    public void saveAmount() throws Exception {
        ArgumentCaptor<Integer> ingAmount = ArgumentCaptor.forClass(Integer.class);
        Integer amount = 100;
        model.saveAmount(amount);
        verify(mainModel).setIngredientAmount(ingAmount.capture());
        Assert.assertEquals(amount,ingAmount.getValue());
    }

    @Test
    public void removeActiveIngredient() throws Exception {
        model.removeActiveIngredient();
        verify(mainModel,times(1)).removeActiveIngredient();
    }

    @Test
    public void getActiveMeal() throws Exception {
        IMeal meal = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(meal);
        assertEquals(meal,model.getActiveMeal());
        verify(mainModel,times(1)).getActiveMeal();
    }

}