package com.example.kbb12.dms.LongActingInsulinModelBuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kbb12.dms.ErrorHandling.DefaultErrorController;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.BrandListener;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.EntryControllerFactory;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.IEntryControllerFactory;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.ValidateInsulinController;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinModelBuilderView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.ModelObserver;

public class InsulinModelBuilderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_model_builder);
        LinearLayout doseList = (LinearLayout) findViewById(R.id.doses);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.insulin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LongActingInsulinModel currentModel = new LongActingInsulinModel(ModelHolder.model);
        EditText brandName = (EditText) findViewById(R.id.editText4);
        brandName.addTextChangedListener(new BrandListener(currentModel));
        Button nextButton = (Button) findViewById(R.id.nextButton2);
        nextButton.setOnClickListener(new ValidateInsulinController(this,currentModel));
        IEntryControllerFactory controllerFactory = new EntryControllerFactory(currentModel);
        ModelObserver view = new LongActingInsulinModelBuilderView(doseList,controllerFactory,currentModel,this,getFragmentManager(),new DefaultErrorController(ModelHolder.model));
        currentModel.registerObserver(view);
    }
}
