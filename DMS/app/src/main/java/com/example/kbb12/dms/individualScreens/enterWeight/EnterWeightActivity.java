package com.example.kbb12.dms.individualScreens.enterWeight;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.enterWeight.controller.EnterWeightController;
import com.example.kbb12.dms.individualScreens.enterWeight.model.EnterWeightModel;
import com.example.kbb12.dms.individualScreens.enterWeight.model.EnterWeightReadWriteModel;
import com.example.kbb12.dms.individualScreens.enterWeight.view.EnterWeightView;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

public class EnterWeightActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);

        EnterWeightReadWriteModel model= new EnterWeightModel(getApplication().getSharedPreferences("fitnessprefs", Context.MODE_PRIVATE).edit());

        EditText weighttext = (EditText) findViewById(R.id.enterWeightET);
        TextView savebut = (TextView) findViewById(R.id.saveWeightIB);

        android.app.FragmentManager fragman = getFragmentManager();

        savebut.setOnClickListener(new EnterWeightController(this, weighttext, model));
        ModelObserver view = new EnterWeightView(model, fragman, new DefaultErrorController(model));
        model.registerObserver(view);

    }
}
