package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller.BrandListener;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller.EntryControllerFactory;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller.IEntryControllerFactory;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller.ValidateBasalInsulinController;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinModel;
import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.view.BasalInsulinModelBuilderView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

public class BasalInsulinModelBuilderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_model_builder);
        LinearLayout doseList = (LinearLayout) findViewById(R.id.doses);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.insulin_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BasalInsulinModel currentModel = new BasalInsulinModel(ModelHolder.model);
        EditText brandName = (EditText) findViewById(R.id.editText4);
        brandName.addTextChangedListener(new BrandListener(currentModel));
        Button nextButton = (Button) findViewById(R.id.nextButton2);
        nextButton.setOnClickListener(new ValidateBasalInsulinController(this,currentModel));
        IEntryControllerFactory controllerFactory = new EntryControllerFactory(currentModel);
        ModelObserver view = new BasalInsulinModelBuilderView(doseList,controllerFactory,currentModel,this,getFragmentManager(),new DefaultErrorController(currentModel));
        currentModel.registerObserver(view);
    }
}
