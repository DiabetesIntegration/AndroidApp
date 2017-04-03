package com.example.kbb12.dms.takeInsulin.view;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;
import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinView extends MasterView implements ModelObserver {

    private TextView recommendedUnitsDisplay;
    private Spinner insulinChoice;
    private TextView timeTakenDisplay;
    private TakeInsulinReadModel model;
    private DateSelectionFragment dateFrag;
    private TimeSelectionFragment timeFrag;

    public TakeInsulinView(TextView recommendedUnitsDisplay, Spinner insulinChoice,
                           EditText amountTaken, TextView timeTakenDisplay,
                           TakeInsulinReadModel model, FragmentManager fragMan,
                           IErrorController errorController,
                           DatePickerDialog.OnDateSetListener dateController,
                           TimePickerDialog.OnTimeSetListener timeController,
                           TextView calculationDescription,
                           DialogInterface.OnDismissListener timeDismissController,
                           DialogInterface.OnDismissListener dateDismissController){
        super(fragMan,errorController);
        this.recommendedUnitsDisplay=recommendedUnitsDisplay;
        this.insulinChoice=insulinChoice;
        this.timeTakenDisplay=timeTakenDisplay;
        this.model=model;
        this.dateFrag=new DateSelectionFragment();
        dateFrag.setController(dateController);
        dateFrag.setDismissControler(dateDismissController);
        this.timeFrag= new TimeSelectionFragment();
        timeFrag.setController(timeController);
        timeFrag.setDismissControler(timeDismissController);
        //Only want to set this the first time. After that it will
        //always be in sync with the model anyway and trying to
        //update the text field will just result in making it more
        //difficult to type.
        amountTaken.setText(model.getAmountTaken().toString());
        calculationDescription.setText(model.getCalculationDescription());

    }


    @Override
    public void update() {
        handleError(model.getError());
        if(model.getDateToChange()){
            clearPopUp("Set Date");
            dateFrag.setDate(model.getDayTaken(),model.getMonthTaken(),model.getYearTaken());
            dateFrag.show(fragMan,"Set Date");
            return;
        }
        if(model.getTimeToChange()){
            clearPopUp("Set Time");
            timeFrag.setTime(model.getHourTaken(),model.getMinuteTaken());
            timeFrag.show(fragMan,"Set Time");
            return;
        }
        switch (model.getRecommendedType()){
            case BASAL:
                recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString()+" Units of basal insulin");
                break;
            case BOLUS:
                recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString() + " Units of bolus insulin");
                break;
            case NOT_SET:
                recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString() + " Units");
                break;
        }
        String dateTime = String.format("%02d",model.getDayTaken())+"/" + String.format("%02d",model.getMonthTaken()+1)+"/" + String.format("%04d",model.getYearTaken())+"   "+String.format("%02d", model.getHourTaken())+":"+String.format("%02d", model.getMinuteTaken());
        timeTakenDisplay.setText(dateTime);
        switch (model.getTypeTaken()){
            case NOT_SET:
                insulinChoice.setSelection(0);
                break;
            case BOLUS:
                insulinChoice.setSelection(1);
                break;
            case BASAL:
                insulinChoice.setSelection(2);
                break;
        }
    }

    private void clearPopUp(String popUpString){
        DialogFragment popUp = (DialogFragment) fragMan.findFragmentByTag(popUpString);
        if (popUp != null) {
            popUp.dismiss();
        }
    }
}
