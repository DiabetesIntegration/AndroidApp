package com.example.kbb12.dms.TakeInsulin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.ErrorHandling.DefaultErrorController;
import com.example.kbb12.dms.Model.UserModel;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.TakeInsulin.Controller.AmountTakenListener;
import com.example.kbb12.dms.TakeInsulin.Controller.ChangeTimeTakenListener;
import com.example.kbb12.dms.TakeInsulin.Controller.InsulinTypeChoiceListener;
import com.example.kbb12.dms.TakeInsulin.Model.TakeInsulinModel;
import com.example.kbb12.dms.TakeInsulin.View.TakeInsulinView;

public class TakeInsulin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_insulin);
        if(ModelHolder.model==null){
            ModelHolder.model = new UserModel(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.insulin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner insulinChoice = (Spinner) findViewById(R.id.takeInsulinTypeChoice);
        insulinChoice.setAdapter(adapter);
        insulinChoice.setOnItemSelectedListener(new InsulinTypeChoiceListener());
        EditText amountTaken = (EditText) findViewById(R.id.amountTakenEntry);
        amountTaken.addTextChangedListener(new AmountTakenListener());
        ImageView changeTime = (ImageView) findViewById(R.id.changeTimeTakenListener);
        changeTime.setOnClickListener(new ChangeTimeTakenListener());
        TakeInsulinModel model = new TakeInsulinModel(ModelHolder.model);
        TakeInsulinView view = new TakeInsulinView((TextView) findViewById(R.id.recommendedUnitsDisplay),insulinChoice,amountTaken,(TextView) findViewById(R.id.timeTakenDisplay),model,getFragmentManager(),new DefaultErrorController(model));
        view.update();
    }
}
