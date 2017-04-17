package com.example.kbb12.dms.individualScreens.addIngredient.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.individualScreens.addIngredient.model.AddIngredientModel;
import com.example.kbb12.dms.model.AddIngredientMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import junit.framework.Assert;

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
 * Created by Garry on 15/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddIngredientModelTest {

    @Mock
    ModelObserver view;
    AddIngredientMainModel mainModel;
    AddIngredientModel model;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel = mock(AddIngredientMainModel.class);
        model = new AddIngredientModel(mainModel);
        model.registerObserver(view);
    }

    @Test
    public void itemSearch() throws Exception {

    }

    @Test
    public void getSavedIngredients() throws Exception {
        String search = "teststring";
        List<IIngredient> allIngredients = new ArrayList<>();
        IIngredient ing;
        for(int i=0; i<5; i++){
            ing = mock(IIngredient.class);
            when(ing.getName()).thenReturn(search+i);
            allIngredients.add(ing);
        }
        for (int i=0; i<5; i++){
            ing = mock(IIngredient.class);
            when(ing.getName()).thenReturn("othername"+i);
            allIngredients.add(ing);
        }
        when(mainModel.getSavedIngredients()).thenReturn(allIngredients);
        model.itemSearch(search);
        List<String> result = model.getSavedIngredients();
        assertEquals(result.size(), 5);
        for(int i=0; i<5; i++){
            assertEquals(result.get(i), search+i);
        }
    }

    @Test
    public void getIngredient() throws Exception {
        String name = "testname";
        IIngredient ing = mock(IIngredient.class);
        when(ing.getName()).thenReturn(name);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        Assert.assertEquals(model.getIngredient().getName(), name);
    }

    @Test
    public void setScannedIngredient1() throws Exception {
        ArgumentCaptor<String> codeCatcher1 = ArgumentCaptor.forClass(String.class);
        String code = "123";
        when(mainModel.getIngredientByBarcode(code)).thenReturn(null);
        assertFalse(model.setScannedIngredient(code));
        verify(mainModel).getIngredientByBarcode(codeCatcher1.capture());
        assertEquals(codeCatcher1.getValue(), code);
    }

    @Test
    public void setScannedIngredient2() throws Exception {
        ArgumentCaptor<String> codeCatcher2 = ArgumentCaptor.forClass(String.class);
        String code = "123";
        IIngredient ing = mock(IIngredient.class);
        when(mainModel.getIngredientByBarcode(code)).thenReturn(ing);
        assertTrue(model.setScannedIngredient(code));
        verify(mainModel).getIngredientByBarcode(codeCatcher2.capture());
        assertEquals(codeCatcher2.getValue(), code);
    }

    @Test
    public void setIngredient() throws Exception {
        String ingname = "testname";
        IIngredient ing = mock(IIngredient.class);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        model.setIngredient(ingname);
        assertEquals(model.getIngredient(), ing);
    }

    @Test
    public void setUpNewIngredient() throws Exception {
        IIngredient ing = null;
        ArgumentCaptor<IIngredient> ingcatcher = ArgumentCaptor.forClass(IIngredient.class);
        model.setUpNewIngredient();
        verify(mainModel).setActiveIngredient(ingcatcher.capture());
        assertEquals(ing, ingcatcher.getValue());
    }

    @Test
    public void getActiveMeal() throws Exception {
        IMeal meal = mock(IMeal.class);
        when(mainModel.getActiveMeal()).thenReturn(meal);
        assertEquals(meal, model.getActiveMeal());
    }

}