package com.example.kbb12.dms.StartUp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements Parcelable {

    //An example item of data to show how it's bundled up when moving between
    //activities.
    private int exampleData;

    //The standard constructor used to first make the model.
    public UserModel(int exampleData){
        this.exampleData=exampleData;
    }

    //Needed to implement parcelable but I've still to work out what it does.
    public int describeContents() {
        return 0;
    }

    //Takes the information necessary to create the model and bundles it up.
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(exampleData);
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
        exampleData=in.readInt();
    }

    public int getExampleData(){
        return exampleData;
    }

}
