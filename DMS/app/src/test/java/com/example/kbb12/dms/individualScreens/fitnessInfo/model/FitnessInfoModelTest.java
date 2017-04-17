package com.example.kbb12.dms.individualScreens.fitnessInfo.model;

import com.example.kbb12.dms.model.FitnessInfoMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Garry on 17-Apr-17.
 */
public class FitnessInfoModelTest {

    @Mock
    ModelObserver view;
    FitnessInfoModel model;
    FitnessInfoMainModel mainModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel = mock(FitnessInfoMainModel.class);
        model = new FitnessInfoModel(mainModel);
        model.registerObserver(view);
    }

    @Test
    public void getCalCount() throws Exception {
        int cal = 345;
        when(mainModel.getCalCount()).thenReturn(cal);
        assertEquals(cal, model.getCalCount());
    }

    @Test
    public void getWeight() throws Exception {
        Double cal = 34.5;
        when(mainModel.getWeight()).thenReturn(cal);
        assertEquals(cal, model.getWeight(), 0);
    }

}