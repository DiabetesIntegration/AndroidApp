package com.example.kbb12.dms.TakeInsulin.View;

import android.app.FragmentManager;
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

    public TakeInsulinView(TextView recommendedUnitsDisplay,Spinner insulinChoice,EditText amountTaken,TextView timeTakenDisplay,TakeInsulinReadModel model,FragmentManager fragMan,IErrorController errorController){
        super(fragMan,errorController);
        this.recommendedUnitsDisplay=recommendedUnitsDisplay;
        this.insulinChoice=insulinChoice;
        this.amountTaken=amountTaken;
        this.timeTakenDisplay=timeTakenDisplay;
        this.model=model;
    }


    @Override
    public void update() {
        handleError(model.getError());
        recommendedUnitsDisplay.setText(model.getRecommendedUnits().toString()+" Units");
        amountTaken.setText(model.getAmountTaken().toString());
        String dateTime = String.format("%02d",model.getDayTaken())+"/" + String.format("%02d",model.getMonthTaken()+1)+"/" + String.format("%04d",model.getYearTaken())+"   "+String.format("%02d",model.getHourTaken())+":"+String.format("%02d",model.getMinuteTaken());
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
