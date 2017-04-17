package com.example.kbb12.dms.individualScreens.fitnessInfo.view;

import android.app.FragmentManager;
import android.widget.TextView;

import com.example.kbb12.dms.individualScreens.fitnessInfo.model.IFitnessInfo;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Garry on 07/03/2017.
 */

public class FitnessInfoView extends MasterView implements ModelObserver {

    private TextView mTVCal;
    private IFitnessInfo lmodel;

    public FitnessInfoView(TextView tv, IFitnessInfo model, FragmentManager fm,
                           IErrorController errorController){
        super(fm,errorController,model);
        mTVCal = tv;
        lmodel = model;
    }

    @Override
    public void update() {
        super.update();
        mTVCal.setText(Integer.toString(lmodel.getCalCount()));
    }
}
