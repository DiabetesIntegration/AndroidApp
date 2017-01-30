package com.example.kbb12.dms.InsulinModelBuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kbb12.dms.ErrorHandling.DefaultErrorController;
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
        nextButton.setOnClickListener(new ValidateInsulinController(ModelHolder.model));
        IEntryControllerFactory controllerFactory = new EntryControllerFactory(ModelHolder.model);
        ModelObserver view = new InsulinModelBuilderView(insulinList,adapter,controllerFactory,ModelHolder.model,this,getFragmentManager(),new DefaultErrorController(ModelHolder.model));
        ModelHolder.model.registerObserver(view);
    }
}
