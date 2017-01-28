package com.example.kbb12.dms.InsulinModelBuilder.View;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.kbb12.dms.InsulinModelBuilder.Controller.IEntryControllerFactory;
import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadModel;
import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class InsulinModelBuilderView implements ModelObserver {

    LinearLayout insulinList;
    SpinnerAdapter adapter;
    IEntryControllerFactory controllerFactory;
    InsulinReadModel model;
    Context context;

    public InsulinModelBuilderView(LinearLayout insulinList,SpinnerAdapter adapter,IEntryControllerFactory controllerFactory,InsulinReadModel model,Context context){
        this.insulinList=insulinList;
        this.adapter=adapter;
        this.controllerFactory=controllerFactory;
        this.model=model;
        this.context=context;
        update();
    }

    @Override
    public void update() {
        List<InsulinEntry> entries = model.getInsulinEntries();
        insulinList.removeAllViews();
        for(int i=0;i<entries.size();i++){
            insulinList.addView(newEntry(entries.get(i).getType(),entries.get(i).getBrandName(),i));
        }
        insulinList.addView(newEntry(InsulinEntry.InsulinType.NOT_SET, null, entries.size()));
    }

    private LinearLayout newEntry(InsulinEntry.InsulinType type,String brandName,int entryNumber){
        LinearLayout newRow=new LinearLayout(context);
        switch(type){
            case NOT_SET:
                newRow=createNotSetRow(type,entryNumber);
                break;
            default:
                newRow=createSetRow(type,brandName,entryNumber);
                break;
        }
        return newRow;
    }

    private LinearLayout createNotSetRow(InsulinEntry.InsulinType type,int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(2);
        newRow.addView(createSpinner(type, entryNumber));
        return newRow;
    }

    private LinearLayout createSetRow(InsulinEntry.InsulinType type,String brandName,int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(3);
        newRow.addView(createSpinner(type, entryNumber));
        newRow.addView(createBrandTextBox(brandName,entryNumber));
        return newRow;
    }


    private Spinner createSpinner(InsulinEntry.InsulinType type,int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2);
        Spinner newDropDown=new Spinner(context);
        newDropDown.setAdapter(adapter);
        newDropDown.setLayoutParams(sectionLayout);
        switch (type){
            case NOT_SET:
                newDropDown.setSelection(0);
                break;
            case LONG_ACTING:
                newDropDown.setSelection(1);
                break;
            case SHORT_ACTING:
                newDropDown.setSelection(2);
                break;
        }
        newDropDown.setOnItemClickListener(controllerFactory.createTypeListener(entryNumber));
        return newDropDown;
    }

    private EditText createBrandTextBox(String brandName,int entryNumber){
        if (brandName.equals(null)){
            brandName="Brand Name";
        }
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        EditText brandTextBox = new EditText(context);
        brandTextBox.setText(brandName);
        brandTextBox.setLayoutParams(sectionLayout);
        brandTextBox.addTextChangedListener(controllerFactory.createBrandListener(entryNumber));
        return brandTextBox;
    }
}
