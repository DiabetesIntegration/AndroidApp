package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model;

import com.example.kbb12.dms.model.BolusInsulinModelBuilderMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Garry on 16-Apr-17.
 */
public class BolusInsulinModelBuilderModelTest {

    @Mock
    ModelObserver view;
    BolusInsulinModelBuilderModel model;
    BolusInsulinModelBuilderMainModel mainModel;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        view=mock(ModelObserver.class);
        mainModel = mock(BolusInsulinModelBuilderMainModel.class);
        model = new BolusInsulinModelBuilderModel(mainModel);
        model.registerObserver(view);
    }

    @Test
    public void knowsISF() throws Exception {
        model.setKnowsISF(false);
        assertFalse(model.knowsISF());
        model.setKnowsISF(true);
        assertTrue(model.knowsISF());
        verify(view, times(2)).update();
    }

    @Test
    public void knowsICR() throws Exception {
        model.setKnowsICR(false);
        assertFalse(model.knowsICR());
        model.setKnowsICR(true);
        assertTrue(model.knowsICR());
        verify(view, times(2)).update();
    }

    @Test
    public void getICR() throws Exception {
        assertEquals(null, model.getICR());
        Integer nbupd = 12;
        model.setNumBolUnitsPerDay(nbupd);
        model.setHumalogNovolog(false);
        assertEquals((double) nbupd/450, model.getICR(), 0);
        model.setHumalogNovolog(true);
        assertEquals((double) nbupd/500, model.getICR(), 0);
    }

    @Test
    public void getISF() throws Exception {
        assertEquals(null, model.getISF());
        Integer nbbupd = 23;
        model.setNumBasBolUnitsPerDay(nbbupd);
        model.setRapidActing(false);
        assertEquals((1500/((double) nbbupd))/18, model.getISF(), 0);
        model.setRapidActing(true);
        assertEquals((1800/((double)nbbupd))/18, model.getISF(), 0);
    }

    @Test
    public void getNumBolUnitsPerDay() throws Exception {
        Integer num = 12;
        model.setNumBolUnitsPerDay(num);
        assertEquals(num, model.getNumBolUnitsPerDay());
        verify(view, times(1)).update();
    }

    @Test
    public void isHumalogNovolog() throws Exception {
        model.setHumalogNovolog(false);
        assertFalse(model.isHumalogNovolog());
        model.setHumalogNovolog(true);
        assertTrue(model.isHumalogNovolog());
        verify(view, times(2)).update();
    }

    @Test
    public void getBreakInsulin() throws Exception {
        Double ins = 1.5;
        model.setBreakInsulin(ins);
        assertEquals(ins, model.getBreakInsulin(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getBreakCarbs() throws Exception {
        Double car = 23.4;
        model.setBreakCarbs(car);
        assertEquals(car, model.getBreakCarbs(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getLunInsulin() throws Exception {
        Double ins = 11.1;
        model.setLunInsulin(ins);
        assertEquals(ins, model.getLunInsulin(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getLunCarbs() throws Exception {
        Double car = 76.5;
        model.setLunCarbs(car);
        assertEquals(car, model.getLunCarbs(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getDinInsulin() throws Exception {
        Double ins = 11.1;
        model.setDinInsulin(ins);
        assertEquals(ins, model.getDinInsulin(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getDinCarbs() throws Exception {
        Double car = 23.4;
        model.setDinCarbs(car);
        assertEquals(car, model.getDinCarbs(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getNighInsulin() throws Exception {
        Double ins = 11.1;
        model.setNighInsulin(ins);
        assertEquals(ins, model.getNighInsulin(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getNighCarbs() throws Exception {
        Double car = 23.4;
        model.setNighCarbs(car);
        assertEquals(car, model.getNighCarbs(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getNumBasBolUnitsPerDay() throws Exception {
        Integer basbol = 34;
        model.setNumBasBolUnitsPerDay(basbol);
        assertEquals(basbol, model.getNumBasBolUnitsPerDay());
        verify(view, times(1)).update();
    }

    @Test
    public void isRapidActing() throws Exception {
        model.setRapidActing(false);
        assertFalse(model.isRapidActing());
        model.setRapidActing(true);
        assertTrue(model.isRapidActing());
        verify(view, times(2)).update();
    }

    @Test
    public void getMornISF() throws Exception {
        Double isf = 12.3;
        model.setMornISF(isf);
        assertEquals(isf, model.getMornISF(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getAfteISF() throws Exception {
        Double isf = 12.3;
        model.setAfteISF(isf);
        assertEquals(isf, model.getAfteISF(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void getEveISF() throws Exception {
        Double isf = 12.3;
        model.setEveISF(isf);
        assertEquals(isf, model.getEveISF(), 0);
    }

    @Test
    public void getNighISF() throws Exception {
        Double isf = 12.3;
        model.setNighISF(isf);
        assertEquals(isf, model.getNighISF(), 0);
        verify(view, times(1)).update();
    }

    @Test
    public void saveValues1() throws Exception {
        model.setKnowsICR(true);
        model.setBreakCarbs(null);
        model.setBreakInsulin(null);
        model.setLunCarbs(null);
        model.setLunInsulin(null);
        model.setDinCarbs(null);
        model.setDinInsulin(null);
        model.setNighCarbs(null);
        model.setNighInsulin(null);
        assertFalse(model.saveValues());
        assertEquals("You must enter your full insulin to carb ratio for each time scale.\n If " +
                "you do not know it at different times please enter the same value for each", model.getError());
    }

    public void saveValues2() throws Exception {
        model.setKnowsICR(false);
        model.setKnowsISF(false);
        model.setNumBasBolUnitsPerDay(1);
        model.setNumBolUnitsPerDay(2);
        assertFalse(model.saveValues());
        assertEquals("The total number of basal and bolus units must be greater than the number" +
                " of bolus units", model.getError());
    }

    public void saveValues3() throws Exception {
        model.setKnowsICR(false);
        model.setNumBolUnitsPerDay(null);
        assertFalse(model.saveValues());
        assertEquals("You must enter your average number of bolus units per day to work out" +
                " an approximate Insulin to carbohydrate ratio.", model.getError());
    }

    public void saveValues4() throws Exception{
        model.setKnowsISF(true);
        model.setMornISF(null);
        model.setAfteISF(null);
        model.setEveISF(null);
        model.setNighISF(null);
        assertFalse(model.saveValues());
        assertEquals("You must enter your insulin sensitivity factor for each time scale.\n If " +
                "you do not know it at different times please enter the same value for each", model.getError());
    }

    public void saveValues5() throws Exception {
        model.setKnowsISF(false);
        model.setNumBasBolUnitsPerDay(null);
        assertFalse(model.saveValues());
        assertEquals("You must enter your total number of basal and bolus units per day to work out" +
                " an approximate Insulin Sensitivity Factor.", model.getError());
    }

    public void saveValues6() throws Exception {
        model.setKnowsICR(true);
        Double bc = 1.0;
        Double bi = 2.0;
        Double lc = 3.0;
        Double li = 4.0;
        Double dc = 5.0;
        Double di = 6.0;
        Double nc = 7.0;
        Double ni = 8.0;
        model.setBreakCarbs(bc);
        model.setBreakInsulin(bi);
        model.setLunCarbs(lc);
        model.setLunInsulin(li);
        model.setDinCarbs(dc);
        model.setDinInsulin(di);
        model.setNighCarbs(nc);
        model.setNighInsulin(ni);
        ArgumentCaptor<Double> bcc = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> bic = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> lcc = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> lic = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> dcc = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> dic = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> ncc = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> nic = ArgumentCaptor.forClass(Double.class);
        model.saveValues();
        verify(mainModel).createInsulinToCarbModel(bcc.capture(), bic.capture(), lcc.capture(), lic.capture(), dcc.capture(), dic.capture(), ncc.capture(), nic.capture());
        assertEquals(bc, bcc.getValue(), 0);
        assertEquals(bi, bic.getValue(), 0);
        assertEquals(lc, lcc.getValue(), 0);
        assertEquals(li, lic.getValue(), 0);
        assertEquals(dc, dcc.getValue(), 0);
        assertEquals(di, dic.getValue(), 0);
        assertEquals(nc, ncc.getValue(), 0);
        assertEquals(ni, nic.getValue(), 0);
        assertTrue(model.saveValues());
    }

    public void saveValues7() throws Exception {
        model.setKnowsICR(false);
        Double bc = 1.0;
        Double bi = 2.0;
        Double lc = 3.0;
        Double li = 4.0;
        Double dc = 5.0;
        Double di = 6.0;
        Double nc = 7.0;
        Double ni = 8.0;
        model.setBreakCarbs(bc);
        model.setBreakInsulin(bi);
        model.setLunCarbs(lc);
        model.setLunInsulin(li);
        model.setDinCarbs(dc);
        model.setDinInsulin(di);
        model.setNighCarbs(nc);
        model.setNighInsulin(ni);
        Double dub = 23.45;
        when(model.getICR()).thenReturn(dub);
        assertTrue(model.saveValues());
        ArgumentCaptor<Double> dubcap = ArgumentCaptor.forClass(Double.class);
        model.saveValues();
        verify(mainModel).createInsulinToCarbModel(dubcap.capture());
        assertEquals(dub, dubcap.getValue());
    }

    public void saveValues8() throws Exception {
        model.setKnowsISF(true);
        Double m = 1.0;
        Double a = 2.0;
        Double e = 3.0;
        Double n = 4.0;
        ArgumentCaptor<Double> mcap = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> acap = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> ecap = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> ncap = ArgumentCaptor.forClass(Double.class);
        model.setMornISF(m);
        model.setAfteISF(a);
        model.setEveISF(e);
        model.setNighISF(n);
        assertTrue(model.saveValues());
        model.saveValues();
        verify(mainModel).createInsulinSensitivityModel(mcap.capture(), acap.capture(), ecap.capture(), ncap.capture());
        assertEquals(m, mcap.getValue(), 0);
        assertEquals(a, acap.getValue(), 0);
        assertEquals(e, ecap.getValue(), 0);
        assertEquals(n, ncap.getValue(), 0);
    }

    public void saveValues9() throws Exception {
        model.setKnowsISF(false);
        Double m = 1.0;
        Double a = 2.0;
        Double e = 3.0;
        Double n = 4.0;
        model.setMornISF(m);
        model.setAfteISF(a);
        model.setEveISF(e);
        model.setNighISF(n);
        assertTrue(model.saveValues());
        Double dub = 45.67;
        when(model.getISF()).thenReturn(dub);
        ArgumentCaptor<Double> dubcap= ArgumentCaptor.forClass(Double.class);
        model.saveValues();
        verify(mainModel).createInsulinSensitivityModel(dubcap.capture());
        assertEquals(dub, dubcap.getValue(), 0);

    }

}