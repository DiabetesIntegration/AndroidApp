package com.example.kbb12.dms.fitnessInfo;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Garry on 07/03/2017.
 */

public class FitnessInfoView implements ModelObserver {

    private TextView mTVCal;
    private FloatingActionButton mFABAddActivity;
    private IFitnessInfo lmodel;
    private Context context;

    public FitnessInfoView(TextView tv, Context context, IFitnessInfo model){
        mTVCal = tv;
        this.context = context;
        lmodel = model;
    }

    @Override
    public void update() {
        mTVCal.setText(Integer.toString(lmodel.getCalCount()));
    }
}
