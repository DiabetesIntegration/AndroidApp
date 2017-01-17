package com.example.kbb12.dms.StartUp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.kbb12.dms.Template.ITemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements ITemplateModel {

    //An example item of data to show how it's bundled up when moving between
    //activities.
    private String exampleData;

    //Since this isn't parceled up it will be refreshed in each activity but this
    //is probably what we want really.
    private List<ModelObserver> observers;

    //The standard constructor used to first make the model.
    public UserModel(String exampleData){
        this.exampleData=exampleData;
        observers= new ArrayList<>();
    }

    //Needed to implement parcelable but I've still to work out what it does.
    public int describeContents() {
        return 0;
    }

    //Takes the information necessary to create the model and bundles it up.
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(exampleData);
    }

    //Inner class used to build the model again in the new activity.
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>(){
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    //Constructor used to create the model in the new activity.
    private UserModel(Parcel in){
        //Create Model from passed in info
        exampleData=in.readString();
        observers= new ArrayList<>();
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
}
