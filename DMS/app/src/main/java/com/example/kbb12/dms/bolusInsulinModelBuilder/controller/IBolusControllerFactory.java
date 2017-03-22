package com.example.kbb12.dms.bolusInsulinModelBuilder.controller;

import android.text.TextWatcher;
import android.widget.CompoundButton;

/**
 * Created by kbb12 on 10/03/2017.
 */
public interface IBolusControllerFactory {
    TextWatcher getNumBolusUnitsListener();
    CompoundButton.OnCheckedChangeListener getHumalogNovologListener();
    TextWatcher getBreakInsulinListener();
    TextWatcher getBreakCarbListener();
    TextWatcher getLunchInsulinListener();
    TextWatcher getLunchCarbListener();
    TextWatcher getDinnerInsulinListener();
    TextWatcher getDinnerCarbListener();
    TextWatcher getNightInsulinListener();
    TextWatcher getNightCarbListener();

    TextWatcher getNumBolusBasalUnitsListener();
    CompoundButton.OnCheckedChangeListener getRapidActingListener();
    TextWatcher getMornISFListener();
    TextWatcher getAfteISFListener();
    TextWatcher getEveISFListener();
    TextWatcher getNighISFListener();
}
