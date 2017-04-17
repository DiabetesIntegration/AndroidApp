package com.example.kbb12.dms.individualScreens.enterWeight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.enterWeight.controller.EnterWeightController;
import com.example.kbb12.dms.individualScreens.enterWeight.model.EnterWeightModel;
import com.example.kbb12.dms.individualScreens.enterWeight.model.EnterWeightReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

public class EnterWeightActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);

        EnterWeightReadWriteModel model= new EnterWeightModel(ModelHolder.model);

        EditText weighttext = (EditText) findViewById(R.id.enterWeightET);
        TextView savebut = (TextView) findViewById(R.id.saveWeightIB);

        android.app.FragmentManager fragman = getFragmentManager();

        savebut.setOnClickListener(new EnterWeightController(this, weighttext, model));
        ModelObserver view = new MasterView(fragman, new DefaultErrorController(model),model);
        model.registerObserver(view);

    }
}
