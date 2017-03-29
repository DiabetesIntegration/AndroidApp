package com.example.kbb12.dms.addFitness.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Garry on 15/03/2017.
 */

public class TimeSelectionFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener controller;
    private int hour;
    private int minute;
    private DialogInterface.OnDismissListener dismissListener;

    public void setController(TimePickerDialog.OnTimeSetListener controller){
        this.controller=controller;
    }

    public void setTime(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }

    public void setDismissController (DialogInterface.OnDismissListener dismissListener){
        this.dismissListener=dismissListener;
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