package com.example.kbb12.dms.individualScreens.enterWeight.model;

import com.example.kbb12.dms.model.EnterWeightMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Garry on 17-Apr-17.
 */
public class EnterWeightModelTest {

    @Mock
    ModelObserver view;
    EnterWeightModel model;
    EnterWeightMainModel mainModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel = mock(EnterWeightMainModel.class);
        model = new EnterWeightModel(mainModel);
        model.registerObserver(view);
    }

    @Test
    public void setWeight() throws Exception {
        double weight = 12.34;
        ArgumentCaptor<Double> wcap = ArgumentCaptor.forClass(Double.class);
        model.setWeight(weight);
        verify(mainModel).setWeight(wcap.capture());
        assertEquals(weight, wcap.getValue(), 0);
    }

}