package com.example.kbb12.dms.individualScreens.takeInsulin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.AmountTakenListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.ChangeTimeTakenListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.DateSetListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.DismissDateListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.DismissTimeListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.InsulinTypeChoiceListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.TimeSetListener;
import com.example.kbb12.dms.individualScreens.takeInsulin.controller.ValidateTakeInsulinController;
import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinModel;
import com.example.kbb12.dms.individualScreens.takeInsulin.view.TakeInsulinView;

public class TakeInsulin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_insulin);
        TakeInsulinModel model = new TakeInsulinModel(ModelHolder.model);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.insulin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner insulinChoice = (Spinner) findViewById(R.id.takeInsulinTypeChoice);
        insulinChoice.setAdapter(adapter);
        insulinChoice.setOnItemSelectedListener(new InsulinTypeChoiceListener(model));
        EditText amountTaken = (EditText) findViewById(R.id.amountTakenEntry);
        amountTaken.addTextChangedListener(new AmountTakenListener(model));
        ImageView changeTime = (ImageView) findViewById(R.id.changeTimeTakenListener);
        changeTime.setOnClickListener(new ChangeTimeTakenListener(model));
        Button done = (Button) findViewById(R.id.finaliseTakeInsulinButton);
        done.setOnClickListener(new ValidateTakeInsulinController(model,this));
        TakeInsulinView view = new TakeInsulinView(
                (TextView) findViewById(R.id.recommendedUnitsDisplay),insulinChoice,amountTaken,
                (TextView) findViewById(R.id.timeTakenDisplay),model,getFragmentManager(),
                new DefaultErrorController(model),new DateSetListener(model),
                new TimeSetListener(model),(TextView) findViewById(R.id.calcDescription),
                new DismissTimeListener(model),new DismissDateListener(model));
        view.update();
        model.registerObserver(view);
    }
}
