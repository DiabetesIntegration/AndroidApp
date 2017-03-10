package com.example.kbb12.dms.bolusInsulinModelBuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.bolusInsulinModelBuilder.controller.BolusControllerFactory;
import com.example.kbb12.dms.bolusInsulinModelBuilder.controller.KnowsICRListener;
import com.example.kbb12.dms.bolusInsulinModelBuilder.controller.KnowsISFListener;
import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinModelBuilderModel;
import com.example.kbb12.dms.bolusInsulinModelBuilder.view.BolusInsulinBuilderView;
import com.example.kbb12.dms.errorHandling.DefaultErrorController;
import com.example.kbb12.dms.startUp.ModelHolder;

public class BolusInsulinModelBuilder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolus_insulin_model_builder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Bolus Insulin Set-Up");
        BolusInsulinModelBuilderModel model = new BolusInsulinModelBuilderModel(ModelHolder.model);
        BolusInsulinBuilderView view = new BolusInsulinBuilderView(getFragmentManager(),
                new DefaultErrorController(model),
                (LinearLayout) findViewById(R.id.insulinToCarbDetailHolder),
                (LinearLayout) findViewById(R.id.insulinSensitivityFactorDetails),
                model,this,new BolusControllerFactory(model));
        model.registerObserver(view);
        Switch knowISF = (Switch) findViewById(R.id.knowsInsulinSensitivityFactor);
        knowISF.setOnCheckedChangeListener(new KnowsISFListener(model));
        Switch knowICR = (Switch) findViewById(R.id.knowsInsulinToCarbRatio);
        knowICR.setOnCheckedChangeListener(new KnowsICRListener(model));
        view.update();
    }

    @Override
    public void onStart(){
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bolus Insulin Set-Up");
    }

}
