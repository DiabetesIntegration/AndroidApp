package com.example.kbb12.dms.individualScreens.enterWeight.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

/**
 * Created by Garry on 29-Mar-17.
 */

public class EnterWeightView extends MasterView implements ModelObserver {

    private BaseReadModel model;

    public EnterWeightView(BaseReadModel model, FragmentManager fragMan, IErrorController controller) {
        super(fragMan, controller);
        this.model=model;
    }

    @Override
    public void update() {
        handleError(model.getError());
    }
}
