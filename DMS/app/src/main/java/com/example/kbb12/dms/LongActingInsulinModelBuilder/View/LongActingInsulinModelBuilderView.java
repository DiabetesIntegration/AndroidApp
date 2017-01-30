package com.example.kbb12.dms.LongActingInsulinModelBuilder.View;

import android.app.FragmentManager;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.kbb12.dms.ErrorHandling.IErrorController;
import com.example.kbb12.dms.ErrorHandling.MasterView;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.IEntryControllerFactory;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadModel;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public class LongActingInsulinModelBuilderView extends MasterView implements ModelObserver {

    LinearLayout insulinList;
    SpinnerAdapter adapter;
    IEntryControllerFactory controllerFactory;
    LongActingInsulinReadModel model;
    Context context;
    List<LongActingInsulinEntry> entries;
    int id;

    public LongActingInsulinModelBuilderView(LinearLayout insulinList, SpinnerAdapter adapter, IEntryControllerFactory controllerFactory, LongActingInsulinReadModel model, Context context, FragmentManager fragMan, IErrorController errorController){
        super(fragMan,errorController);
        this.insulinList=insulinList;
        this.adapter=adapter;
        this.controllerFactory=controllerFactory;
        this.model=model;
        this.id=0;
        this.context=context;
        this.entries=new ArrayList<>();
        refreshView();
    }

    @Override
    public void update() {
        //Only refreshing the view if it doesn't already match the model
        //This stops continuous loops.
        handleError(model.getError());
        List<LongActingInsulinEntry> newEntries = model.getInsulinEntries();
        if(entries.size()==(newEntries.size())){
            for(int i=0;i<entries.size();i++){
                if(!entries.get(i).getType().equals(newEntries.get(i).getType())){
                    entries=newEntries;
                    refreshView();
                }
            }
        }else {
            entries = newEntries;
            refreshView();
        }
    }


    private void refreshView(){
        insulinList.removeAllViews();
        for (int i = 0; i < entries.size(); i++) {
            //insulinList.addView(newEntry(entries.get(i).getType(), entries.get(i).getBrandName(), i));
        }
        insulinList.addView(createNotSetRow(entries.size()));
    }


    private LinearLayout createNotSetRow(int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(1);
        newRow.addView(createSpinner(type, entryNumber));
        return newRow;
    }

    private LinearLayout createSetRow(LongActingInsulinEntry.InsulinType type,String brandName,int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(2);
        newRow.addView(createSpinner(type, entryNumber));
        newRow.addView(createBrandTextBox(brandName,entryNumber));
        return newRow;
    }


    private Spinner createSpinner(LongActingInsulinEntry.InsulinType type,int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        Spinner newDropDown=new Spinner(context);
        id++;
        newDropDown.setId(this.id);
        newDropDown.setAdapter(adapter);
        newDropDown.setLayoutParams(sectionLayout);
        switch (type){
            case NOT_SET:
                newDropDown.setSelection(0);
                break;
            case LONG_ACTING:
                newDropDown.setSelection(2);
                break;
            case SHORT_ACTING:
                newDropDown.setSelection(1);
                break;
        }
        newDropDown.setOnItemSelectedListener(controllerFactory.createTypeListener(entryNumber));
        return newDropDown;
    }

    private EditText createBrandTextBox(String brandName,int entryNumber){
        if (brandName==null){
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
