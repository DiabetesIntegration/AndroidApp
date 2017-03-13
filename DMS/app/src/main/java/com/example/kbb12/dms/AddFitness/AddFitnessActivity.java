package com.example.kbb12.dms.AddFitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.kbb12.dms.FitnessInfo.FitnessInfoView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class AddFitnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness);

        UserModel model = ModelHolder.model;

        Spinner mSpinner = (Spinner) findViewById(R.id.activitySpinner);
        EditText mHour = (EditText) findViewById(R.id.hourEText);
        EditText mMins = (EditText) findViewById(R.id.minuteEText);
        ImageButton saveActivity = (ImageButton) findViewById(R.id.saveActivityIB);

        AddFitnessController controller = new AddFitnessController(model, this);

        AddFitnessView view = new AddFitnessView();
        model.registerObserver(view);
    }
}
