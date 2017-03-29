package com.example.kbb12.dms.basalInsulinModelBuilder.controller;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface IEntryControllerFactory {

    public AdapterView.OnClickListener createTimeEntryListener(int entryNumber);

    public TextWatcher createDoseListener(int entryNumber);

    public View.OnClickListener createDeleteListener(int entryNumber);

    public DialogInterface.OnClickListener createConfirmDeleteListener();

    public DialogInterface.OnClickListener createCancelDeleteListener();

    public TimePickerDialog.OnTimeSetListener createTimeChangeListener();

    public DialogInterface.OnDismissListener createTimeDismissListener();
}
