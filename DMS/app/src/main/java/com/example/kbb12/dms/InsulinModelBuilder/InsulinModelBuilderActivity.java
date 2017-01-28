package com.example.kbb12.dms.InsulinModelBuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kbb12.dms.InsulinModelBuilder.Controller.EntryControllerFactory;
import com.example.kbb12.dms.InsulinModelBuilder.Controller.IEntryControllerFactory;
import com.example.kbb12.dms.InsulinModelBuilder.Controller.ValidateInsulinController;
import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinModelBuilderView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.ModelObserver;

public class InsulinModelBuilderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_model_builder);
        LinearLayout insulinList = (LinearLayout) findViewById(R.id.insulinList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.insulin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Button nextButton = (Button) findViewById(R.id.nextButton2);
        nextButton.setOnClickListener(new ValidateInsulinController());
        IEntryControllerFactory controllerFactory = new EntryControllerFactory(ModelHolder.model);
        ModelObserver view = new InsulinModelBuilderView(insulinList,adapter,controllerFactory,ModelHolder.model,this);
        ModelHolder.model.registerObserver(view);
        //ModelHolder.model.registerObserver(view);
        //Ok so the plan is here you add new horizontal linear layouts every time was is filled in
        //this lets the user put in as many as they want. The insulin list is a linear layout inside
        //a scroll view so you can add as many as you need. When the model updates you want the
        //amount of insulin entries displayed plus one. But this means you'll need to dynamically
        //add listeners to the view as you go hence the factory to reduce coupling.
        //The button listener should make the model check everything necessary has been filled in
        //then move to the next activity.
    }
}
