package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller.BolusControllerFactory;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller.KnowsICRListener;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller.KnowsISFListener;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller.SaveBolusInsulinListener;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model.BolusInsulinModelBuilderModel;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.view.BolusInsulinBuilderView;
import com.example.kbb12.dms.model.ModelHolder;

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
                (LinearLayout) findViewById(R.id.insulinSensitivityFactorDetailsHolder),
                model,this,new BolusControllerFactory(model));
        model.registerObserver(view);
        Switch knowISF = (Switch) findViewById(R.id.knowsInsulinSensitivityFactor);
        knowISF.setOnCheckedChangeListener(new KnowsISFListener(model));
        Switch knowICR = (Switch) findViewById(R.id.knowsInsulinToCarbRatio);
        knowICR.setOnCheckedChangeListener(new KnowsICRListener(model));
        Button done = (Button) findViewById(R.id.finishBolusInsulinSetUp);
        done.setOnClickListener(new SaveBolusInsulinListener(model,this));
        view.update();
    }

}
