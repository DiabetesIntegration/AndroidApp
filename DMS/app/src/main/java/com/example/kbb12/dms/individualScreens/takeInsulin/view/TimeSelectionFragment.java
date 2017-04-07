package com.example.kbb12.dms.individualScreens.takeInsulin.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class TimeSelectionFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener controller;
    private int hour;
    private int minute;
    private DialogInterface.OnDismissListener dismissListener;

    public void setController(TimePickerDialog.OnTimeSetListener controller){
        this.controller=controller;
    }

    public void setDismissControler (DialogInterface.OnDismissListener dismissListener){
        this.dismissListener=dismissListener;
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissListener.onDismiss(dialog);
    }
}
