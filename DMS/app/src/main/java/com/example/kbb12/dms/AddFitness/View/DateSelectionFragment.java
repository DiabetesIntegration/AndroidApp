package com.example.kbb12.dms.AddFitness.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Garry on 15/03/2017.
 */

public class DateSelectionFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener controller;

    private int day;
    private int month;
    private int year;

    public void setController(DatePickerDialog.OnDateSetListener controller){
        this.controller=controller;
    }

    public void setDate(int day,int month,int year){
        this.day=day;
        this.month=month;
        this.year=year;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new DatePickerDialog(getActivity(),controller,year,month,day);
    }
}
