package com.example.kbb12.dms.ErrorHandling;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.kbb12.dms.R;

/**
 * Created by kbb12 on 17/02/2016.
 */
public class ErrorMessageDialogFragment extends DialogFragment {
    private String message;
    private IErrorController controller;

    public void setController(IErrorController controller){
        this.controller=controller;
    }

    public void setMessage(String message){
        this.message=message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInsanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controller.errorAcknowledged();
            }
        });
        return builder.create();
    }
}