package com.example.kbb12.dms.test;

import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.individualScreens.mainMenu.model.MainMenuModel;
import com.example.kbb12.dms.model.IBloodGlucoseModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
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
public class MainMenuModelTest {
    @Mock
    ModelObserver view;
    IBloodGlucoseModel mainModel;
    MainMenuModel model;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(IBloodGlucoseModel.class);
        model = new MainMenuModel(mainModel);
        model.registerObserver(view);
    }



    @Test
    public void addRawData() throws Exception {
        ArgumentCaptor<String> d = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Calendar> c = ArgumentCaptor.forClass(Calendar.class);
        Calendar calendar = Calendar.getInstance();
        String data = "a";
        model.addRawData(calendar,data);
        verify(mainModel).addRawData(c.capture(),d.capture());
        verify(view,times(1)).update();

        assertEquals(data,d.getValue());
        boolean check =(calendar.getTimeInMillis()-c.getValue().getTimeInMillis())<100;
        assertTrue(check);
    }

    @Test
    public void addHistoryReading() throws Exception {
        ArgumentCaptor<Double> r = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Calendar> c = ArgumentCaptor.forClass(Calendar.class);
        Calendar calendar = Calendar.getInstance();
        Double reading = 15.0;
        model.addHistoryReading(calendar,reading);
        verify(mainModel).addHistoryReading(c.capture(),r.capture());
        verify(view,times(1)).update();

        assertEquals(reading,r.getValue());
        boolean check =(calendar.getTimeInMillis()-c.getValue().getTimeInMillis())<100;
        assertTrue(check);
    }

    @Test
    public void addCurrentReading() throws Exception {
        ArgumentCaptor<Double> r = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Calendar> c = ArgumentCaptor.forClass(Calendar.class);
        Calendar calendar = Calendar.getInstance();
        Double reading = 12.0;
        model.addCurrentReading(calendar,reading);
        verify(mainModel).addCurrentReading(c.capture(),r.capture());
        verify(view,times(1)).update();

        assertEquals(reading,r.getValue());
        boolean check =(calendar.getTimeInMillis()-c.getValue().getTimeInMillis())<100;
        assertTrue(check);
    }

    @Test
    public void getMostRecentHistoryReading() throws Exception {
        BGReading bgr = mock(BGReading.class);
        when(mainModel.getMostRecentHistoryReading()).thenReturn(bgr);

        BGReading bgr2 = model.getMostRecentHistoryReading();
        assertEquals(bgr,bgr2);
    }

    @Test
    public void getHistoryBetween() throws Exception {
        ArgumentCaptor<Calendar> cFrom = ArgumentCaptor.forClass(Calendar.class);
        ArgumentCaptor<Calendar> cTo = ArgumentCaptor.forClass(Calendar.class);
        List<BGReading> bgr = mock(List.class);
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        when(mainModel.getHistoryBetween(calendarFrom,calendarTo)).thenReturn(bgr);


        List<BGReading> bgr2 = model.getHistoryBetween(calendarFrom,calendarTo);
        verify(mainModel).getHistoryBetween(cFrom.capture(),cTo.capture());
        assertEquals(calendarFrom, cFrom.getValue());
        assertEquals(calendarTo, cTo.getValue());
        assertEquals(bgr,bgr2);
    }

}