package com.example.kbb12.dms.addFitness.view;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.kbb12.dms.addFitness.controller.DateSetListener;
import com.example.kbb12.dms.addFitness.controller.TimeSetListener;
import com.example.kbb12.dms.addFitness.model.AddFitnessReadModel;
import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Garry on 13/03/2017.
 */

public class AddFitnessView extends MasterView implements ModelObserver {

    private AddFitnessReadModel model;
    private FragmentManager fragman;
    private TextView date;
    private DateSelectionFragment dFrag;
    private TimeSelectionFragment tFrag;

    //private IErrorController errcon;

    public AddFitnessView(AddFitnessReadModel model, FragmentManager fragman, TextView mDateTime, DateSetListener datecontroller, TimeSetListener timecontroller, DialogInterface.OnDismissListener datedismisscontroller, DialogInterface.OnDismissListener timedismisscontroller, IErrorController errcon){
        super(fragman, errcon);
        this.model = model;
        this.fragman = fragman;
        date = mDateTime;

        dFrag=new DateSelectionFragment();
        dFrag.setController(datecontroller);
        dFrag.setDismissController(datedismisscontroller);

        tFrag = new TimeSelectionFragment();
        tFrag.setController(timecontroller);
        tFrag.setDismissController(timedismisscontroller);

    }

    @Override
    public void update() {
        handleError(model.getError());
        if(model.getActDateToChange()){
            clearPopUp("Set Date");
            dFrag.setDate(model.getDayTaken(),model.getMonthTaken(),model.getYearTaken());
            dFrag.show(fragman,"Set Date");
            return;
        }
        if(model.getActTimeToChange()){
            clearPopUp("Set Time");
            tFrag.setTime(model.getHourTaken(),model.getMinuteTaken());
            tFrag.show(fragman,"Set Time");
            return;
        }
        String dateTime = String.format("%02d",model.getDayTaken())+"/" + String.format("%02d",model.getMonthTaken()+1)+"/" + String.format("%04d",model.getYearTaken())+"   "+String.format("%02d", model.getHourTaken())+":"+String.format("%02d", model.getMinuteTaken());
        date.setText(dateTime);

    }

    private void clearPopUp(String popUpString){
        DialogFragment popUp = (DialogFragment) fragman.findFragmentByTag(popUpString);
        if (popUp != null) {
            popUp.dismiss();
        }
    }

}
