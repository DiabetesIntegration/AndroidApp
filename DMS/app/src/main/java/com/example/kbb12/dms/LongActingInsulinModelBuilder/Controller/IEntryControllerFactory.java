package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.app.TimePickerDialog;
import android.text.TextWatcher;
import android.widget.AdapterView;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface IEntryControllerFactory {

    public AdapterView.OnClickListener createTimeEntryListener(int entryNumber);

    public TextWatcher createDoseListener(int entryNumber);

    public TimePickerDialog.OnTimeSetListener createTimeChangeListener();
}
