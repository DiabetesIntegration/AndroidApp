package com.example.kbb12.dms.bolusInsulinModelBuilder.controller;

import android.text.TextWatcher;
import android.widget.CompoundButton;

import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class BolusControllerFactory implements IBolusControllerFactory {

    private BolusInsulinReadWriteModel model;

    public BolusControllerFactory(BolusInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public TextWatcher getNumBolusUnitsListener() {
        return new NumBolusUnitsListener(model);
    }

    @Override
    public CompoundButton.OnCheckedChangeListener getHumalogNovologListener() {
        return new HumalogNovologListener(model);
    }

    @Override
    public TextWatcher getBreakInsulinListener() {
        return new BreakInsulinListener(model);
    }

    @Override
    public TextWatcher getBreakCarbListener() {
        return new BreakCarbsListener(model);
    }

    @Override
    public TextWatcher getLunchInsulinListener() {
        return new LunInsulinListener(model);
    }

    @Override
    public TextWatcher getLunchCarbListener() {
        return new LunCarbsListener(model);
    }

    @Override
    public TextWatcher getDinnerInsulinListener() {
        return new DinInsulinListener(model);
    }

    @Override
    public TextWatcher getDinnerCarbListener() {
        return new DinCarbsListener(model);
    }

    @Override
    public TextWatcher getNumBolusBasalUnitsListener() {
        return new NumBolusBasalUnitsListener(model);
    }

    @Override
    public CompoundButton.OnCheckedChangeListener getRapidActing() {
        return new RapidActingListener(model);
    }

    @Override
    public TextWatcher getMornISFListener() {
        return new MornISFListener(model);
    }

    @Override
    public TextWatcher getAfteISFListener() {
        return new AfteISFListener(model);
    }

    @Override
    public TextWatcher getNighISFListener() {
        return new NighIsfListener(model);
    }
}
