package com.example.kbb12.dms.StartUp;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient {
    private String name;
    private boolean byWeight;
    private double carbVal, totalCarb;

    public Ingredient(String name, boolean w, double totalCarb) {
        this.name = name;
        byWeight = w;
        carbVal = 0.0;
        this.totalCarb = totalCarb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void calculateCarb(double val) {
        if(byWeight) {
            carbVal = val*totalCarb;
        }
        else {
            carbVal = val;
        }
    }

    public double getCarbVal() {
        return carbVal;
    }

}
