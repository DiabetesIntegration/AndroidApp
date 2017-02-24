package com.example.kbb12.dms.Model;

import android.content.Context;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.Model.Insulin.DuplicateDoseException;
import com.example.kbb12.dms.Model.Insulin.ILongActingInsulinDatabase;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDatabase;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDose;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel,InsulinModel {

    private String exampleData;

    private List<ModelObserver> observers;

    private String errorMessage;

    private ILongActingInsulinDatabase database;

    public static final int versionNumber=37;

    public UserModel(Context context){
        database=new LongActingInsulinDatabase(context,versionNumber);
        observers= new ArrayList<>();
    }

    //TODO file handling methods
    public boolean loadData(){
        return false;
    }

    public void saveData(){

    }


    public void setError(String errorMessage){
        this.errorMessage=errorMessage;
        notifyObservers();
    }

    public String getError(){
        return errorMessage;
    }

    public String getExampleData(){
        return exampleData;
    }

    public void setExampleData(String newData){
        exampleData=newData;
        notifyObservers();
    }

    public void registerObserver(ModelObserver observer)
    {
        observers.add(observer);
        notifyObservers();
    }

    private void notifyObservers(){
        for(ModelObserver observer:observers){
            observer.update();
        }
    }

    @Override
    public void saveDoses(List<LongActingInsulinDose> basicDoses,String longActingInsulinBrandName) throws DuplicateDoseException {
        try {
            for (LongActingInsulinEntry dose : basicDoses) {
                database.addEntry(dose,longActingInsulinBrandName);
            }
        } catch (DuplicateDoseException e){
            database.clearValues();
            throw new DuplicateDoseException();
        }
    }

    public List<LongActingInsulinEntry> getDoses(){
        return database.getEntries();
    }


}
