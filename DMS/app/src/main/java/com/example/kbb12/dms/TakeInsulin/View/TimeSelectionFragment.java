package com.example.kbb12.dms.TakeInsulin.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

public class TimeSelectionFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener controller;
    private int hour;
    private int minute;

    public void setController(TimePickerDialog.OnTimeSetListener controller){
        this.controller=controller;
    }

    public void setTime(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), controller, hour, minute,
                true);
    }
}
