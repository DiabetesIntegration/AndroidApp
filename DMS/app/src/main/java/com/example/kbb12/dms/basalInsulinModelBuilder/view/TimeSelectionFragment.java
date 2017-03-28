package com.example.kbb12.dms.basalInsulinModelBuilder.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

public class TimeSelectionFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener controller;

    public void setController(TimePickerDialog.OnTimeSetListener controller){
        this.controller=controller;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), controller, 0, 0,
                true);
    }
}
