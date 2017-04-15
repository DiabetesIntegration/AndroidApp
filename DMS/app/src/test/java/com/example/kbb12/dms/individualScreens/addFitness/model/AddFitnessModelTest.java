package com.example.kbb12.dms.individualScreens.addFitness.model;

import android.content.Context;

import com.example.kbb12.dms.model.AddFitnessMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;



/**
 * Created by Garry on 15/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddFitnessModelTest {

    @Mock
    ModelObserver view;
    AddFitnessMainModel mainModel;
    AddFitnessModel model;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(AddFitnessMainModel.class);
        model = new AddFitnessModel(mainModel);
    }

    @Test
    public void getDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        Assert.assertEquals(format.format(date), model.getDate());
    }

    @Test
    public void addToCalCount() throws Exception {
        ArgumentCaptor<Integer> calorieCatcher = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Calendar> calendarCatcher = ArgumentCaptor.forClass(Calendar.class);
        Calendar calendar = Calendar.getInstance();
        Integer calories=50;
        model.addToCalCount(calories);
        verify(mainModel).addToCalCount(calendarCatcher.capture(),calorieCatcher.capture());
        Assert.assertEquals(calories,calorieCatcher.getValue());
        boolean check =(calendar.getTimeInMillis()-calendarCatcher.getValue().getTimeInMillis())<100;
        Assert.assertTrue(check);
    }


    @Test
    public void setActDateToChange() throws Exception {
        model.registerObserver(view);
        model.setActDateToChange(true);
        verify(view,times(1)).update();
    }

    @Test
    public void getActDateToChange() throws Exception {
        model.setActDateToChange(true);
        Assert.assertEquals(model.getActDateToChange(),true);
        model.setActDateToChange(false);
        Assert.assertEquals(model.getActDateToChange(),false);
    }

    @Test
    public void setActTimeToChange() throws Exception {
        model.registerObserver(view);
        model.setActTimeToChange(true);
        verify(view,times(1)).update();
    }

    @Test
    public void getActTimeToChange() throws Exception {
        model.setActTimeToChange(true);
        Assert.assertEquals(model.getActTimeToChange(),true);
        model.setActTimeToChange(false);
        Assert.assertEquals(model.getActTimeToChange(),false);
    }

    @Test
    public void setDateTaken() throws Exception {
        model.registerObserver(view);
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setDateTaken(1,1,1);
        verify(view,times(3)).update();
    }

    @Test
    public void getDayTaken() throws Exception {
        model.registerObserver(view);
        int day = 20;
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setDateTaken(1,1,day);
        Assert.assertEquals(day, model.getDayTaken());
    }

    @Test
    public void getMonthTaken() throws Exception {
        model.registerObserver(view);
        int month = 7;
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setDateTaken(1,month,1);
        Assert.assertEquals((month+1), model.getMonthTaken());
    }

    @Test
    public void getYearTaken() throws Exception {
        model.registerObserver(view);
        int year = 2040;
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setDateTaken(year,1,1);
        Assert.assertEquals(year, model.getYearTaken());
    }

    @Test
    public void setTimeTaken() throws Exception {
        model.registerObserver(view);
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setTimeTaken(1,1);
        verify(view,times(3)).update();
    }

    @Test
    public void getHourTaken() throws Exception {
        model.registerObserver(view);
        int hour = 11;
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setTimeTaken(hour,1);
        Assert.assertEquals(hour, model.getHourTaken());
    }

    @Test
    public void getMinuteTaken() throws Exception {
        model.registerObserver(view);
        int min = 46;
        model.setActDateToChange(true);
        model.setActTimeToChange(true);
        model.setTimeTaken(1, min);
        Assert.assertEquals(min, model.getMinuteTaken());
    }


    @Test
    public void getDurhours() throws Exception {
        int durhours = 3;
        model.setDurHours(durhours);
        Assert.assertEquals(durhours, model.getDurhours());
    }

    @Test
    public void getDurMins() throws Exception {
        int durmins = 33;
        model.setDurMins(durmins);
        Assert.assertEquals(durmins, model.getDurMins());
    }

    @Test
    public void saveActivity() throws Exception {
        model.registerObserver(view);

        ArgumentCaptor<Calendar> calendarCatcher = ArgumentCaptor.forClass(Calendar.class);
        ArgumentCaptor<String> activityCatcher = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> hourCatcher = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> minCatcher = ArgumentCaptor.forClass(Integer.class);
        String activity = "walking";
        Integer hour = 2;
        Integer min = 42;
        Calendar calendar = Calendar.getInstance();
        model.setActivityType(activity);
        model.setDurHours(hour);
        model.setDurMins(min);
        model.saveActivity();
        verify(mainModel).saveActivity(calendarCatcher.capture(), activityCatcher.capture(), hourCatcher.capture(), minCatcher.capture());
        verify(view,times(1)).update();

        Assert.assertEquals(activity, activityCatcher.getValue());
        Assert.assertEquals(hour, hourCatcher.getValue());
        Assert.assertEquals(min, minCatcher.getValue());
        boolean check =(calendar.getTimeInMillis()-calendarCatcher.getValue().getTimeInMillis())<100;
        Assert.assertTrue(check);
    }

}