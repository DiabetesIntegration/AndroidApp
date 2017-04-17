package com.example.kbb12.dms.individualScreens.customIngredient.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.Ingredient;
import com.example.kbb12.dms.model.CustomIngredientMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.argumentmatching.ArgumentMatchingTool;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Garry on 16-Apr-17.
 */
public class AddCustomIngredientModelTest {

    @Mock
    ModelObserver view;
    AddCustomIngredientModel model;
    CustomIngredientMainModel mainModel;

    @Before
    public void setup() {
            MockitoAnnotations.initMocks(this);
            view = mock(ModelObserver.class);
            mainModel = mock(CustomIngredientMainModel.class);
            model = new AddCustomIngredientModel(mainModel);
            model.registerObserver(view);
    }

        @Test
    public void save() throws Exception {
            String name = "test";
            Double carbs = 2.3;
            Double perg = 3.2;
            Integer pweight = 123;
            model.save(name, carbs, perg, pweight);
            ArgumentCaptor<IIngredient> ing = ArgumentCaptor.forClass(IIngredient.class);
            verify(mainModel).saveIngredient(ing.capture());
            assertEquals(name, ing.getValue().getName());
            assertEquals(carbs/perg*100, ing.getValue().getCarbsPerHundredG(), 0);
            assertEquals(pweight, ing.getValue().getPacketWeight());
    }

    @Test
    public void hasExisting() throws Exception {
        IIngredient ing = mock(IIngredient.class);
        when(mainModel.getActiveIngredient()).thenReturn(null).thenReturn(ing);
        assertFalse(model.hasExisting());
        assertTrue(model.hasExisting());
    }

    @Test
    public void getIngredientName() throws Exception {
        String name = "test";
        IIngredient ing = mock(IIngredient.class);
        when(ing.getName()).thenReturn(name);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        assertEquals(model.getIngredientName(), name);
    }

    @Test
    public void getCarbPerHundred() throws Exception {
        Double cpg = 12.34;
        IIngredient ing = mock(IIngredient.class);
        when(ing.getCarbsPerHundredG()).thenReturn(cpg);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        assertEquals(model.getCarbPerHundred(), cpg, 0);
    }

    @Test
    public void getPacketWeight() throws Exception {
        Integer weight = 13;
        IIngredient ing = mock(IIngredient.class);
        when(ing.getPacketWeight()).thenReturn(weight);
        when(mainModel.getActiveIngredient()).thenReturn(ing);
        assertEquals(model.getPacketWeight(), weight);
    }

}