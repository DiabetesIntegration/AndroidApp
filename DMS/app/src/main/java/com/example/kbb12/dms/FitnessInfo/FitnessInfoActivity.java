package com.example.kbb12.dms.fitnessInfo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.ModelHolder;


public class FitnessInfoActivity extends AppCompatActivity {

    FitnessInfoView view;
    FitnessInfoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_info);

        model = new FitnessInfoModel(ModelHolder.model);

        TextView mTVCal = (TextView) findViewById(R.id.CaloriesTextView);
        FloatingActionButton mFABAddActivity = (FloatingActionButton) findViewById(R.id.AddActivityFAB);

        FitnessInfoController controller = new FitnessInfoController(model, this);
        mFABAddActivity.setOnClickListener(controller);

        view = new FitnessInfoView(mTVCal, this, model);
        view.update();

        model.registerObserver(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        view.update();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        model.removeObserver(view);
        finish();
    }
}
