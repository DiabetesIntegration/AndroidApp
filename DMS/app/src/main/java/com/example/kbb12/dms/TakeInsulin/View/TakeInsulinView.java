package com.example.kbb12.dms.TakeInsulin.View;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.ErrorHandling.IErrorController;
import com.example.kbb12.dms.ErrorHandling.MasterView;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.TakeInsulin.Model.TakeInsulinReadModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinView extends MasterView implements ModelObserver {

    private TextView recommendedUnitsDisplay;
    private Spinner insulinChoice;
    private EditText amountTaken;
    private TextView timeTakenDisplay;
    private TakeInsulinReadModel model;
    private DateSelectionFragment dateFrag;
    private TimeSelectionFragment timeFrag;

    public TakeInsulinView(TextView recommendedUnitsDisplay,Spinner insulinChoice,EditText amountTaken,TextView timeTakenDisplay,TakeInsulinReadModel model,FragmentManager fragMan,IErrorController errorController,DatePickerDialog.OnDateSetListener dateController,TimePickerDialog.OnTimeSetListener timeController){
        super(fragMan,errorController);
        this.recommendedUnitsDisplay=recommendedUnitsDisplay;
        this.insulinChoice=insulinChoice;
        this.amountTaken=amountTaken;
        this.timeTakenDisplay=timeTakenDisplay;
        this.model=model;
        this.dateFrag=new DateSelectionFragment();
        dateFrag.setController(dateController);
        this.timeFrag= new TimeSelectionFragment();
        timeFrag.setController(timeController);
        //Only want to set this the first time. After that it will
        //always be in sync with the model anyway and trying to
        //update the text field will just result in making it more
        //difficult to type.
        amountTaken.setText(model.getAmountTaken().toString());
    }


    @Override
    public void update() {
        handleError(model.getError());
        if(model.getDateToChange()){
            dateFrag.setDate(model.getDayTaken(),model.getMonthTaken(),model.getYearTaken());
            dateFrag.show(fragMan,"Set Date");
            return;
        }
        if(model.getTimeToChange()){
            timeFrag.setTime(model.getHourTaken(),model.getMinuteTaken());
            timeFrag.show(fragMan,"Set Time");
            return;
        }
        switch (model.getRecommendedType()){
            case LONG_ACTING:
                recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString()+" Units of long acting insulin");
                break;
            case SHORT_ACTING:
                recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString() + " Units of short acting insulin");
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
            case SHORT_ACTING:
                insulinChoice.setSelection(1);
                break;
            case LONG_ACTING:
                insulinChoice.setSelection(2);
                break;
        }
    }
}
