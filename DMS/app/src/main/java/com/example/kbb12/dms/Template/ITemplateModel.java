package com.example.kbb12.dms.Template;

import android.os.Parcelable;

/**
 * Created by kbb12 on 17/01/2017.
 */
public interface ITemplateModel extends Parcelable {

    public String getExampleData();
    public void setExampleData(String newData);
}
