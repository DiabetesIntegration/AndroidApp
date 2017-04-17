package com.example.kbb12.dms.individualScreens.takeInsulin.view;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;
import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;

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
        super(fragMan,errorController,model);
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
        super.update();
        if(model.isDateToChange()){
            clearPopUp("Set Date");
            Calendar taken=model.getTimeTaken();
            dateFrag.setDate(taken.get(Calendar.DAY_OF_MONTH),taken.get(Calendar.MONTH),taken.get(Calendar.YEAR));
            dateFrag.show(fragMan,"Set Date");
            return;
        }
        if(model.isTimeToChange()){
            clearPopUp("Set Time");
            Calendar taken = model.getTimeTaken();
            timeFrag.setTime(taken.get(Calendar.HOUR),taken.get(Calendar.MINUTE));
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
        Calendar taken = model.getTimeTaken();
        int day = taken.get(Calendar.DAY_OF_MONTH);
        int month = taken.get(Calendar.MONTH);
        int year = taken.get(Calendar.YEAR);
        int hour = taken.get(Calendar.HOUR);
        int minute = taken.get(Calendar.MINUTE);
        String dateTime = String.format("%02d",day)+"/" + String.format("%02d",month+1)+"/" + String.format("%04d",year)+"   "+String.format("%02d", hour)+":"+String.format("%02d", minute);
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
