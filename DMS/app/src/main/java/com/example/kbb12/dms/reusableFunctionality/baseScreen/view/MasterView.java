package com.example.kbb12.dms.reusableFunctionality.baseScreen.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;

/**
 * Created by kbb12 on 30/01/2017.
 */
public class MasterView {

    protected FragmentManager fragMan;
    private ErrorMessageDialogFragment frag;

    public MasterView(FragmentManager fragMan,IErrorController controller){
        this.fragMan=fragMan;
        this.frag=new ErrorMessageDialogFragment();
        frag.setController(controller);
    }

    protected void handleError(String errorMessage){
        if(errorMessage==null){
            return;
        }
        frag.setMessage(errorMessage);
        frag.show(fragMan, "Error");
    }

}
