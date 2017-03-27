package com.example.kbb12.dms.AddFitness.View;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.AddFitness.Controller.DateSetListener;
import com.example.kbb12.dms.AddFitness.Controller.TimeSetListener;
import com.example.kbb12.dms.AddFitness.IAddFitness;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Garry on 13/03/2017.
 */

public class AddFitnessView implements ModelObserver {

    private Context context;
    private IAddFitness model;
    private FragmentManager fragman;
    private Spinner spinner;
    private TextView date;
    private EditText hours;
    private EditText mins;
    private DateSelectionFragment dFrag;
    private TimeSelectionFragment tFrag;

    public AddFitnessView(Context context, IAddFitness model, FragmentManager fragman, Spinner spinner, TextView mDateTime, EditText mHours, EditText mMins, DateSetListener datecontroller, TimeSetListener timecontroller){
        this.context = context;
        this.model = model;
        this.fragman = fragman;
        this.spinner = spinner;
        date = mDateTime;
        hours = mHours;
        mins = mMins;

        dFrag=new DateSelectionFragment();
        dFrag.setController(datecontroller);

        tFrag = new TimeSelectionFragment();
        tFrag.setController(timecontroller);

    }

    @Override
    public void update() {
        if(model.getActDateToChange()){
            dFrag.setDate(model.getDayTaken(),model.getMonthTaken(),model.getYearTaken());
            dFrag.show(fragman,"Set Date");
            return;
        }
        if(model.getActTimeToChange()){
            tFrag.setTime(model.getHourTaken(),model.getMinuteTaken());
            tFrag.show(fragman,"Set Time");
            return;
        }
        String dateTime = String.format("%02d",model.getDayTaken())+"/" + String.format("%02d",model.getMonthTaken()+1)+"/" + String.format("%04d",model.getYearTaken())+"   "+String.format("%02d", model.getHourTaken())+":"+String.format("%02d", model.getMinuteTaken());
        date.setText(dateTime);

    }
}
