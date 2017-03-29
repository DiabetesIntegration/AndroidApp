package com.example.kbb12.dms.enterWeight.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.enterWeight.IEnterWeight;
import com.example.kbb12.dms.errorHandling.IErrorController;
import com.example.kbb12.dms.errorHandling.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Garry on 29-Mar-17.
 */

public class EnterWeightView extends MasterView implements ModelObserver {

    private IEnterWeight model;

    public EnterWeightView(IEnterWeight model, FragmentManager fragMan, IErrorController controller) {
        super(fragMan, controller);
        this.model=model;
    }

    @Override
    public void update() {
        handleError(model.getError());
    }
}
