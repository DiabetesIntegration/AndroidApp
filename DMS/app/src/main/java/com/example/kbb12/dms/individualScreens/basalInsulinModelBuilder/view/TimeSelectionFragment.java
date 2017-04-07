package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class TimeSelectionFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener controller;
    private DialogInterface.OnDismissListener dismissListener;

    public void setController(TimePickerDialog.OnTimeSetListener controller){
        this.controller=controller;
    }

    public void setDismissController(DialogInterface.OnDismissListener dismissController){
        this.dismissListener=dismissController;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), controller, 0, 0,
                true);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissListener.onDismiss(dialog);
    }
}
