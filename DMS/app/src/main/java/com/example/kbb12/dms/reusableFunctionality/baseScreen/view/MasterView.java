package com.example.kbb12.dms.reusableFunctionality.baseScreen.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;

/**
 * Created by kbb12 on 30/01/2017.
 */
public class MasterView implements ModelObserver{

    protected FragmentManager fragMan;
    private ErrorMessageDialogFragment frag;
    private BaseReadModel model;

    public MasterView(FragmentManager fragMan,IErrorController controller,BaseReadModel model){
        this.fragMan=fragMan;
        this.frag=new ErrorMessageDialogFragment();
        frag.setController(controller);
        this.model=model;
    }

    public void update(){
        if(model.getError()==null){
            return;
        }
        frag.setMessage(model.getError());
        frag.show(fragMan, "Error");
    }

}
