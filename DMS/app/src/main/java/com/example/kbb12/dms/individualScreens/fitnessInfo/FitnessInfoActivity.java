package com.example.kbb12.dms.individualScreens.fitnessInfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.individualScreens.fitnessInfo.controller.FitnessInfoController;
import com.example.kbb12.dms.individualScreens.fitnessInfo.model.FitnessInfoModel;
import com.example.kbb12.dms.individualScreens.fitnessInfo.view.FitnessInfoView;
import com.example.kbb12.dms.model.ModelHolder;


public class FitnessInfoActivity extends AppCompatActivity {

    FitnessInfoView view;
    FitnessInfoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_info);

        model = new FitnessInfoModel(ModelHolder.model,getSharedPreferences("fitnessprefs", getApplicationContext().MODE_PRIVATE));

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

}
