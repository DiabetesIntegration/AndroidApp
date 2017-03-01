package com.example.kbb12.dms.LongActingInsulinModelBuilder.View;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
            return;
        }
        if(model.isReadyToDelete()){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you wish to delete this entry?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", controllerFactory.createConfirmDeleteListener());
            builder.setNegativeButton("No", controllerFactory.createCancelDeleteListener());
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        List<LongActingInsulinEntry> newEntries = model.getTempDoses();
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
        newRow.setWeightSum(5);
        newRow.addView(createTimeEntryBox(true, hour, minute, entryNumber));
        newRow.addView(createDoseTextBox(dose, entryNumber));
        newRow.addView(createUnitTextBox());
        newRow.addView(createDeleteButton(entryNumber));
        return newRow;
    }


    private TextView createTimeEntryBox(boolean set,int hour,int minute,int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2);
        TextView newTime=new TextView(context);
        newTime.setLayoutParams(sectionLayout);
        if(set){
            String timeString="";
            if(hour<10){
                timeString+="0";
            }
            timeString+=(hour+":");
            if(minute<10){
                timeString+="0";
            }
            timeString+=minute;
           newTime.setText(timeString);
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
        if(dose.equals(0.0)){
            doseTextBox.setHint("0.0");
        }else {
            doseTextBox.setText(dose.toString());
        }
        doseTextBox.setLayoutParams(sectionLayout);
        doseTextBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
    private ImageView createDeleteButton(int entryNumber){
        LinearLayout.LayoutParams sectionLayout = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        ImageView deleteButton = new ImageView(context);
        deleteButton.setImageResource(android.R.drawable.ic_delete);;
        deleteButton.setLayoutParams(sectionLayout);
        deleteButton.setOnClickListener(controllerFactory.createDeleteListener(entryNumber));
        return deleteButton;
    }
}
