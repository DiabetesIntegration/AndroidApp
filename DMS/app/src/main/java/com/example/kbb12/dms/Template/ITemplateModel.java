package com.example.kbb12.dms.Template;


import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by kbb12 on 17/01/2017.
 */
public interface ITemplateModel {

    public String getExampleData();
    public void setExampleData(String newData);
    public void registerObserver(ModelObserver obs);
}
