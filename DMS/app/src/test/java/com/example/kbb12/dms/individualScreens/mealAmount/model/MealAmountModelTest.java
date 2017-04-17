package com.example.kbb12.dms.individualScreens.mealAmount.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.model.MealAmountMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Ciaran on 4/16/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MealAmountModelTest {

    @Mock
    ModelObserver view;
    MealAmountMainModel mainModel;
    MealAmountModel model;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(MealAmountMainModel.class);
        model = new MealAmountModel(mainModel);
        model.registerObserver(view);
    }



    @Test
    public void eatCurrentMeal() throws Exception {
        ArgumentCaptor<Double> percent = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<IMeal> aMeal = ArgumentCaptor.forClass(IMeal.class);
        Integer p = 80;
        Double pCheck = 80.0;
        model.eatCurrentMeal(p);
        verify(mainModel).eatCurrentMeal(percent.capture());
        assertEquals(pCheck,percent.getValue());
        verify(mainModel).setActiveMeal(aMeal.capture());
        assertEquals(null,aMeal.getValue());

    }

}