package com.example.kbb12.dms.enterWeight;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.enterWeight.controller.EnterWeightController;
import com.example.kbb12.dms.enterWeight.model.EnterWeightModel;
import com.example.kbb12.dms.enterWeight.view.EnterWeightView;
import com.example.kbb12.dms.errorHandling.DefaultErrorController;
import com.example.kbb12.dms.errorHandling.IErrorController;
import com.example.kbb12.dms.model.EnterWeightMainModel;
import com.example.kbb12.dms.startUp.ModelHolder;

public class EnterWeightActivity extends AppCompatActivity {

    private EditText weighttext;
    private ImageButton savebut;
    private EnterWeightModel model;
    private SharedPreferences.Editor spredit;
    private EnterWeightView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);

        model= new EnterWeightModel(ModelHolder.model);

        spredit = getSharedPreferences("fitnessprefs", MODE_PRIVATE).edit();

        weighttext = (EditText) findViewById(R.id.enterWeightET);
        savebut = (ImageButton) findViewById(R.id.saveWeightIB);

        android.app.FragmentManager fragman = getFragmentManager();

        savebut.setOnClickListener(new EnterWeightController(this, weighttext, model));

        /*savebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double weight = Double.parseDouble(weighttext.getText().toString());
                    spredit.putFloat("weight", (float) weight);
                    spredit.apply();
                } catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Please enter a numeric value", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });*/

        view = new EnterWeightView(model, fragman, new DefaultErrorController(model));
        model.registerObserver(view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
