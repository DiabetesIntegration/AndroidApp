package com.example.kbb12.dms.basalInsulinModelBuilder.controller;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;


/**
 * Created by kbb12 on 20/01/2017.
 */
public class EntryControllerFactory implements IEntryControllerFactory {

    BasalInsulinReadWriteModel model;

    public EntryControllerFactory(BasalInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public AdapterView.OnClickListener createTimeEntryListener(int entryNumber) {
        return new TimeEntryListener(entryNumber,model);
    }

    @Override
    public TextWatcher createDoseListener(int entryNumber){
        return new DoseListener(entryNumber,model);
    }

    @Override
    public View.OnClickListener createDeleteListener(int entryNumber) {
        return new DeleteEntryListener(model,entryNumber);
    }

    @Override
    public DialogInterface.OnClickListener createConfirmDeleteListener() {
        return new ConfirmDeleteListener(model);
    }

    @Override
    public DialogInterface.OnClickListener createCancelDeleteListener() {
        return new CancelDeleteListener(model);
    }

    @Override
    public TimePickerDialog.OnTimeSetListener createTimeChangeListener() {
        return new TimeChangeListener(model);
    }

    @Override
    public DialogInterface.OnDismissListener createTimeDismissListener() {
        return new DismissTimeChangeListener(model);
    }
}
