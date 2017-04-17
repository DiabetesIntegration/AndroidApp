package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinDose;
import com.example.kbb12.dms.model.BasalInsulinModelBuilderMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Created by Garry on 16-Apr-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class BasalInsulinModelTest {

    @Mock
    ModelObserver view;
    BasalInsulinModel model;
    BasalInsulinModelBuilderMainModel mainModel;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        view = mock(ModelObserver.class);
        mainModel = mock(BasalInsulinModelBuilderMainModel.class);
        model = new BasalInsulinModel(mainModel);
        model.registerObserver(view);
    }


    @Test
    public void setHour() throws Exception {
        Integer hour = 4;
        Integer min = 22;
        String brandname = "test";
        model.setSelectedTime(0);
        model.setHour(hour);
        model.setMinute(min);
        model.setBasalBrandName(brandname);

        ArgumentCaptor<List> bid = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);
        model.saveDoses();
        verify(mainModel).saveDoses(bid.capture(), name.capture());

        assertEquals(1, bid.getValue().size());
        BasalInsulinDose b = (BasalInsulinDose) bid.getValue().get(0);
        assertEquals(hour, b.getHour());
        assertEquals(min, b.getMinute());
        assertEquals(brandname, name.getValue());

        verify(view, times(1)).update();
    }

    @Test
    public void deselectTime() throws Exception {
        model.deselectTime();
        verify(view, times(1)).update();
    }

    @Test
    public void setDoseToBeDeleted() throws Exception {
        model.setSelectedTime(0);
        model.setDoseToBeDeleted(0);
        verify(view, times(2)).update();
    }

    @Test
    public void cancelDelete() throws Exception {
        model.cancelDelete();
        verify(view, times(1)).update();
    }

    @Test
    public void deleteDose() throws Exception {
        model.deleteDose();
        verify(view, times(1)).update();;
    }

    @Test
    public void setDose() throws Exception {
        String brand = "test";
        for(int i=0;i<10;i++){
            model.setSelectedTime(i);
        }
        model.setBasalBrandName(brand);
        model.setDose(15,4);
        ArgumentCaptor<List> listcatcher = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<String> namecatcher = ArgumentCaptor.forClass(String.class);
        model.saveDoses();
        verify(mainModel).saveDoses(listcatcher.capture(), namecatcher.capture());
        List<BasalInsulinDose> bid = (List<BasalInsulinDose>) listcatcher.getValue();
        for(int i=0; i<10; i++){
            if (i!=4){
                assertEquals(0.0, bid.get(i).getDose(), 0);
            } else {
                assertEquals(15.0, bid.get(i).getDose(), 0);
            }
        }
    }

    @Test
    public void cancelSelection() throws Exception {

    }

    @Test
    public void getTempDoses() throws Exception {
        for (int i=0; i<5; i++){
            model.setSelectedTime(i);
        }
        model.setDoseToBeDeleted(3);
        model.deleteDose();
        assertEquals(4, model.getTempDoses().size());
    }

    @Test
    public void isTimeSelected() throws Exception {
        assertFalse(model.isTimeSelected());
        model.setSelectedTime(0);
        model.setHour(1);
        model.setMinute(1);
        assertTrue(model.isTimeSelected());
    }

    @Test
    public void isReadyToDelete() throws Exception {
        model.setSelectedTime(0);
        model.setDoseToBeDeleted(0);
        assertTrue(model.isReadyToDelete());
        model.cancelDelete();
        assertFalse(model.isReadyToDelete());
        model.setDoseToBeDeleted(0);
        model.deleteDose();
        assertFalse(model.isReadyToDelete());
    }

    @Test
    public void getBasalBrandName() throws Exception {
        String name = "test";
        model.setBasalBrandName(name);
        assertEquals(name, model.getBasalBrandName());
    }

}