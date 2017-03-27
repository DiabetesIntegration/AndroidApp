package com.example.kbb12.dms.FitnessInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kbb12.dms.EnterWeight.EnterWeightActivity;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

import java.util.Objects;


public class FitnessInfoActivity extends AppCompatActivity {

    //private TextView mTVCal;
    //private FloatingActionButton mFABAddActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_info);

        UserModel model = ModelHolder.model;

        TextView mTVCal = (TextView) findViewById(R.id.CaloriesTextView);
        FloatingActionButton mFABAddActivity = (FloatingActionButton) findViewById(R.id.AddActivityFAB);

        FitnessInfoController controller = new FitnessInfoController(model, this);
        mFABAddActivity.setOnClickListener(controller);

        FitnessInfoView view = new FitnessInfoView(mTVCal, this, model);
        view.update();

        model.registerObserver(view);
    }

}
