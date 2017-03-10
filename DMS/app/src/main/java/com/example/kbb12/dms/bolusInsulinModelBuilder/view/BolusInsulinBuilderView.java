package com.example.kbb12.dms.bolusInsulinModelBuilder.view;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.bolusInsulinModelBuilder.controller.IBolusControllerFactory;
import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadModel;
import com.example.kbb12.dms.errorHandling.IErrorController;
import com.example.kbb12.dms.errorHandling.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class BolusInsulinBuilderView extends MasterView implements ModelObserver {

    private LinearLayout insulinToCarbDetailHolder;
    private LinearLayout insulinSensitivityFactorDetails;
    private BolusInsulinReadModel model;
    private Context context;
    private IBolusControllerFactory factory;


    public BolusInsulinBuilderView(FragmentManager fragMan, IErrorController controller,
                                   LinearLayout insulinToCarbDetailHolder,
                                   LinearLayout insulinSensitivityFactorDetails,
                                   BolusInsulinReadModel model,Context context,
                                   IBolusControllerFactory factory) {
        super(fragMan, controller);
        this.insulinToCarbDetailHolder=insulinToCarbDetailHolder;
        this.insulinSensitivityFactorDetails=insulinSensitivityFactorDetails;
        this.model=model;
        this.context=context;
        this.factory=factory;
    }

    @Override
    public void update() {
        insulinToCarbDetailHolder.removeAllViews();
        insulinSensitivityFactorDetails.removeAllViews();
        if(model.knowsICR()){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            View section = inflater.inflate(R.layout.know_insulin_to_carb,holder,false);
            EditText breakInsulin =(EditText) section.findViewById(R.id.breakfastInsulin);
            if(model.getBreakInsulin()!=null){
                breakInsulin.setText(model.getBreakInsulin().toString());
                breakInsulin.setSelection(model.getBreakInsulin().toString().length());
            }
            breakInsulin.addTextChangedListener(factory.getBreakInsulinListener());
            insulinToCarbDetailHolder.addView(section);
        }else{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            View section = inflater.inflate(R.layout.dont_know_insulin_to_carb,holder,false);
            insulinToCarbDetailHolder.addView(section);
        }
        if(model.knowsISF()){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            View section = inflater.inflate(R.layout.know_isf,holder,false);
            insulinSensitivityFactorDetails.addView(section);
        }else{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            View section = inflater.inflate(R.layout.dont_know_isf,holder,false);
            insulinSensitivityFactorDetails.addView(section);
        }
    }
}
