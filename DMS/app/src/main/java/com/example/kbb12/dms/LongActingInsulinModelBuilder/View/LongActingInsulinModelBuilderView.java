package com.example.kbb12.dms.LongActingInsulinModelBuilder.View;

import android.app.FragmentManager;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kbb12.dms.ErrorHandling.IErrorController;
import com.example.kbb12.dms.ErrorHandling.MasterView;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller.IEntryControllerFactory;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadModel;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public class LongActingInsulinModelBuilderView extends MasterView implements ModelObserver {

    LinearLayout insulinList;
    IEntryControllerFactory controllerFactory;
    LongActingInsulinReadModel model;
    Context context;
    List<LongActingInsulinEntry> entries;
    TimeSelectionFragment timeFragment;

    public LongActingInsulinModelBuilderView(LinearLayout insulinList, IEntryControllerFactory controllerFactory, LongActingInsulinReadModel model, Context context, FragmentManager fragMan, IErrorController errorController){
        super(fragMan,errorController);
        this.insulinList=insulinList;
        this.controllerFactory=controllerFactory;
        this.model=model;
        this.context=context;
        this.entries=new ArrayList<>();
        timeFragment=new TimeSelectionFragment();
        timeFragment.setController(controllerFactory.createTimeChangeListener());
        refreshView();
    }

    @Override
    public void update() {
        //Only refreshing the view if it doesn't already match the model
        //This stops continuous loops.
        handleError(model.getError());
        if(model.isTimeSelected()){
            timeFragment.show(fragMan,"Enter time");
        }else {
            List<LongActingInsulinEntry> newEntries = model.getDoses();
            if (entries.size() == (newEntries.size())) {
                for (int i = 0; i < entries.size(); i++) {
                    if (!entries.get(i).equals(newEntries.get(i))) {
                        entries = newEntries;
                        refreshView();
                    }
                }
            } else {
                entries = newEntries;
                refreshView();
            }
        }
    }


    private void refreshView(){
        insulinList.removeAllViews();
        for (int i = 0; i < entries.size(); i++) {
            insulinList.addView(createSetRow(entries.get(i).getHour(), entries.get(i).getMinute(),entries.get(i).getDose(), i));
        }
        insulinList.addView(createNotSetRow(entries.size()));
    }


    private LinearLayout createNotSetRow(int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(2);
        newRow.addView(createTimeEntryBox(false, 0, 0, entryNumber));
        return newRow;
    }

    private LinearLayout createSetRow(int hour,int minute,double dose,int entryNumber){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        LinearLayout newRow = new LinearLayout(context);
        newRow.setLayoutParams(entryLayout);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setWeightSum(4);
        newRow.addView(createTimeEntryBox(true, hour, minute, entryNumber));
        newRow.addView(createDoseTextBox(dose, entryNumber));
        newRow.addView(createUnitTextBox());
        return newRow;
    }


    private TextView createTimeEntryBox(boolean set,int hour,int minute,int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2);
        TextView newTime=new TextView(context);
        newTime.setLayoutParams(sectionLayout);
        if(set){
           newTime.setText(hour+":"+minute);
        }else{
            newTime.setText("Tap to add a new time");
        }
        newTime.setClickable(true);
        newTime.setOnClickListener(controllerFactory.createTimeEntryListener(entryNumber));
        return newTime;
    }

    private EditText createDoseTextBox(Double dose,int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        EditText doseTextBox = new EditText(context);
        doseTextBox.setText(dose.toString());
        doseTextBox.setLayoutParams(sectionLayout);
        doseTextBox.addTextChangedListener(controllerFactory.createDoseListener(entryNumber));
        return doseTextBox;
    }
    private TextView createUnitTextBox(){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        TextView unitTextBox = new TextView(context);
        unitTextBox.setText("Units");
        unitTextBox.setLayoutParams(sectionLayout);
        return unitTextBox;
    }
}
