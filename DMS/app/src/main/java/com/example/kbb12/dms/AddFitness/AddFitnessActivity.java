package com.example.kbb12.dms.AddFitness;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.AddFitness.Controller.ActivityTypeListener;
import com.example.kbb12.dms.AddFitness.Controller.DateSetListener;
import com.example.kbb12.dms.AddFitness.Controller.DateTimeListener;
import com.example.kbb12.dms.AddFitness.Controller.DismissDateListener;
import com.example.kbb12.dms.AddFitness.Controller.DismissTimeListener;
import com.example.kbb12.dms.AddFitness.Controller.HourTextListener;
import com.example.kbb12.dms.AddFitness.Controller.MinuteTextListener;
import com.example.kbb12.dms.AddFitness.Controller.TimeSetListener;
import com.example.kbb12.dms.AddFitness.Controller.ValidateAndSave;
import com.example.kbb12.dms.AddFitness.View.AddFitnessView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

import java.util.Arrays;
import java.util.List;

public class AddFitnessActivity extends AppCompatActivity {

    UserModel model;
    AddFitnessView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness);

        model = ModelHolder.model;

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

        view = new AddFitnessView(this, model, fragman, mSpinner, mDateTime, mHour, mMins, new DateSetListener(model), new TimeSetListener(model), new DismissDateListener(model), new DismissTimeListener(model));
        view.update();

        model.registerObserver(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}