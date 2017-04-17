package com.example.kbb12.dms.individualScreens.takeInsulin.model;

import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.TakeInsulinMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ciaran on 4/16/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TakeInsulinModelTest {
    @Mock
    ModelObserver view;
    TakeInsulinMainModel mainModel;
    TakeInsulinModel model;
    BasalInsulinEntry bEntry;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel= mock(TakeInsulinMainModel.class);

        when(mainModel.getCurrentBG()).thenReturn(6.0);
        when(mainModel.getRecentCarbs()).thenReturn(3);
        when(mainModel.getCurrentICR()).thenReturn(1.0);
        when(mainModel.getCurrentISF()).thenReturn(2.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(false);

        model = new TakeInsulinModel(mainModel);
        model.registerObserver(view);


    }

    @Test
    public void entryNotNull() throws Exception {
        //when bolus is false and entry != null
        when(mainModel.getCurrentBG()).thenReturn(null);
        when(mainModel.getRecentCarbs()).thenReturn(2);
        when(mainModel.getCurrentICR()).thenReturn(3.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(true);
        bEntry = mock(BasalInsulinEntry.class);
        when(mainModel.getLatestBasalRecommendation()).thenReturn(bEntry);
        when(bEntry.getHour()).thenReturn(12);
        when(bEntry.getMinute()).thenReturn(30);
        when(bEntry.getDose()).thenReturn(10.0);
        model = new TakeInsulinModel(mainModel);

        assertEquals("You have injected bolus insulin recently. We do not recommend" +
                " doing so again until this has fully taken effect." + "\nYou are over-due for your "+bEntry.getHour()+
                ":"+bEntry.getMinute()+" dose of basal insulin.", model.getCalculationDescription());
        assertEquals(model.getTypeTaken(), TakeInsulinReadModel.InsulinType.BASAL);
        assertEquals(model.getRecommendedType(), TakeInsulinReadModel.InsulinType.BASAL);
    }

    @Test
    public void entryNull() throws Exception {
        //when bolus is false and entry == null
        when(mainModel.getCurrentBG()).thenReturn(null);
        when(mainModel.getRecentCarbs()).thenReturn(2);
        when(mainModel.getCurrentICR()).thenReturn(3.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(true);
        bEntry = mock(BasalInsulinEntry.class);
        when(mainModel.getLatestBasalRecommendation()).thenReturn(null);
        when(bEntry.getHour()).thenReturn(12);
        when(bEntry.getMinute()).thenReturn(30);
        when(bEntry.getDose()).thenReturn(10.0);
        model = new TakeInsulinModel(mainModel);

        Double val = 0.0;
        assertEquals("You have injected bolus insulin recently. We do not recommend" +
                " doing so again until this has fully taken effect." +"\nYou are not over-due for a basal insulin dose.", model.getCalculationDescription());
        assertEquals(val, model.getRecommendedUnits());
        assertEquals(val, model.getAmountTaken());
        assertEquals(model.getTypeTaken(), TakeInsulinReadModel.InsulinType.NOT_SET);
        assertEquals(model.getRecommendedType(), TakeInsulinReadModel.InsulinType.NOT_SET);
    }

    @Test
    public void bgNull() throws Exception {
        //when bolus is false and blood glucose == null
        when(mainModel.getCurrentBG()).thenReturn(null);
        when(mainModel.getRecentCarbs()).thenReturn(2);
        when(mainModel.getCurrentICR()).thenReturn(3.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(false);
        bEntry = mock(BasalInsulinEntry.class);

        model = new TakeInsulinModel(mainModel);
        assertTrue(model.getCalculationDescription().contains("There is not a recent enough blood glucose value to" +
                " give accurate bolus recommendations. Please scan your Libre sensor and return."));
    }

    @Test
    public void bgOutOfRange() throws Exception {
        //when blood glucose is not in range
        when(mainModel.getCurrentBG()).thenReturn(25.0);
        when(mainModel.getRecentCarbs()).thenReturn(2);
        when(mainModel.getCurrentICR()).thenReturn(3.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(false);
        bEntry = mock(BasalInsulinEntry.class);

        model = new TakeInsulinModel(mainModel);
        String bg = String.format("Your current blood glucose %.2fmmol/l is outwith the " +
                "recommended range of 4-7mmol/l",mainModel.getCurrentBG());;
        assertTrue(model.getCalculationDescription().contains(bg));
    }

    @Test
    public void recBelowZero() throws Exception {
        //when recommended < 0
        when(mainModel.getCurrentBG()).thenReturn(1.0);
        when(mainModel.getRecentCarbs()).thenReturn(1);
        when(mainModel.getCurrentICR()).thenReturn(1.0);
        when(mainModel.getCurrentISF()).thenReturn(2.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(false);
        bEntry = mock(BasalInsulinEntry.class);

        model = new TakeInsulinModel(mainModel);
        assertTrue(model.getCalculationDescription().contains("We do not calculate that "+ mainModel.getRecentCarbs()+
                " carbohydrates is enough to balance your low blood glucose reading of "+
                mainModel.getCurrentBG()+"mmol/l"));
    }

    @Test
    public void healthyBG() throws Exception {
        //when recommended < 0
        when(mainModel.getCurrentBG()).thenReturn(6.0);
        when(mainModel.getRecentCarbs()).thenReturn(3);
        when(mainModel.getCurrentICR()).thenReturn(1.0);
        when(mainModel.getCurrentISF()).thenReturn(2.0);
        when(mainModel.hasTakenBolusInsulinRecently()).thenReturn(false);
        bEntry = mock(BasalInsulinEntry.class);

        model = new TakeInsulinModel(mainModel);
        assertTrue(model.getCalculationDescription().contains("Carbs eaten recently: "+ mainModel.getRecentCarbs()));
        assertEquals(model.getTypeTaken(), TakeInsulinReadModel.InsulinType.BOLUS);
        assertEquals(model.getRecommendedType(), TakeInsulinReadModel.InsulinType.BOLUS);
        assertEquals(model.getRecommendedUnits(), model.getAmountTaken());
    }

    @Test
    public void setAmountTaken() throws Exception {

        Double amount = 2.0;
        model.setAmountTaken(amount);
        assertEquals(amount, model.getAmountTaken());
        verify(view,times(1)).update();
    }

    @Test
    public void setTimeToChange() throws Exception {

        model.setTimeToChange(true);
        assertTrue(model.isTimeToChange());
        verify(view,times(1)).update();
    }

    @Test
    public void setDateToChange() throws Exception {
        model.setDateToChange(true);
        assertTrue(model.isDateToChange());
        verify(view,times(1)).update();
    }

    @Test
    public void setTypeTaken() throws Exception {
        TakeInsulinReadModel.InsulinType itype = TakeInsulinReadModel.InsulinType.BASAL;
        model.setTypeTaken(itype);
        assertEquals(model.getTypeTaken(),itype);
        verify(view,times(1)).update();
    }

    @Test
    public void setDateTaken() throws Exception {
        model.setDateToChange(true);
        Integer day = 12;
        Integer month = 6;
        Integer year = 2017;
        model.setDateTaken(day,month,year);
        Integer m = model.getTimeTaken().get(Calendar.DAY_OF_MONTH);
        int mm = 12;
        assertEquals(m,day);
        verify(view,times(2)).update();
    }

    @Test
    public void setTimeTaken() throws Exception {
        model.setTimeToChange(true);
        Integer hour = 10;
        Integer minute = 30;
        model.setTimeTaken(hour,minute);
        Integer h = model.getTimeTaken().get(Calendar.HOUR);
        assertEquals(h,hour);
        verify(view,times(2)).update();
    }

    @Test
    public void takeInsulinBasal() throws Exception {
        ArgumentCaptor<Calendar> cal = ArgumentCaptor.forClass(Calendar.class);
        ArgumentCaptor<Double> amountTaken = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Boolean> b = ArgumentCaptor.forClass(Boolean.class);
        model.setTypeTaken(TakeInsulinReadModel.InsulinType.BASAL);
        model.takeInsulin();
        verify(mainModel).takeInsulin(cal.capture(),amountTaken.capture(),b.capture());
        assertEquals(cal.getValue(),model.getTimeTaken());
        assertEquals(amountTaken.getValue(),model.getAmountTaken());
        assertTrue(b.getValue());
    }

    @Test
    public void takeInsulinBolus() throws Exception {
        ArgumentCaptor<Calendar> cal = ArgumentCaptor.forClass(Calendar.class);
        ArgumentCaptor<Double> amountTaken = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Boolean> b = ArgumentCaptor.forClass(Boolean.class);
        model.setTypeTaken(TakeInsulinReadModel.InsulinType.BOLUS);
        model.takeInsulin();
        verify(mainModel).takeInsulin(cal.capture(),amountTaken.capture(),b.capture());
        assertEquals(cal.getValue(),model.getTimeTaken());
        assertEquals(amountTaken.getValue(),model.getAmountTaken());
        assertFalse(b.getValue());
    }

    @Test
    public void takeInsulinNotSet() throws Exception {
        model.setTypeTaken(TakeInsulinReadModel.InsulinType.NOT_SET);
        model.takeInsulin();
        assertEquals("You must set which type of insulin you are taking",model.getError());
    }
}