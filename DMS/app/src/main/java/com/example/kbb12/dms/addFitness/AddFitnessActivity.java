package com.example.kbb12.dms.addFitness;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.addFitness.controller.ActivityTypeListener;
import com.example.kbb12.dms.addFitness.controller.DateSetListener;
import com.example.kbb12.dms.addFitness.controller.DateTimeListener;
import com.example.kbb12.dms.addFitness.controller.DismissDateListener;
import com.example.kbb12.dms.addFitness.controller.DismissTimeListener;
import com.example.kbb12.dms.addFitness.controller.HourTextListener;
import com.example.kbb12.dms.addFitness.controller.MinuteTextListener;
import com.example.kbb12.dms.addFitness.controller.TimeSetListener;
import com.example.kbb12.dms.addFitness.controller.ValidateAndSave;
import com.example.kbb12.dms.addFitness.model.AddFitnessModel;
import com.example.kbb12.dms.addFitness.view.AddFitnessView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.startUp.ModelHolder;

import java.util.Arrays;
import java.util.List;

public class AddFitnessActivity extends AppCompatActivity {

    AddFitnessModel model;
    AddFitnessView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness);

        model=new AddFitnessModel(ModelHolder.model);

        Spinner mSpinner = (Spinner) findViewById(R.id.activitySpinner);
        List<String> list = Arrays.asList("Walking", "Running", "Cycling");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        mSpinner.setAdapter(spinnerArrayAdapter);
        mSpinner.setOnItemSelectedListener(new ActivityTypeListener(model));

        TextView mDateTime = (TextView) findViewById(R.id.dateAndTimeTV);

        ImageView dateAndTime = (ImageView) findViewById(R.id.setDateAndTimeIV);
        dateAndTime.setOnClickListener(new DateTimeListener(model));

        FragmentManager fragman = getFragmentManager();

        EditText mHour = (EditText) findViewById(R.id.hourEText);
        mHour.addTextChangedListener(new HourTextListener(model));

        EditText mMins = (EditText) findViewById(R.id.minuteEText);
        mMins.addTextChangedListener(new MinuteTextListener(model));

        ImageView saveActivity = (ImageView) findViewById(R.id.saveActivityIV);
        saveActivity.setOnClickListener(new ValidateAndSave(model, this));

        view = new AddFitnessView(model, fragman, mDateTime, new DateSetListener(model), new TimeSetListener(model), new DismissDateListener(model), new DismissTimeListener(model), new DefaultErrorController(model));
        view.update();

        model.registerObserver(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}