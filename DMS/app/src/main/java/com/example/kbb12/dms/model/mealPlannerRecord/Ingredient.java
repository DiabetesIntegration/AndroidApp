package com.example.kbb12.dms.model.mealPlannerRecord;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient implements IIngredient {
    private String ingName;
    private Double carbAmountPerHundredGrams;
    private Integer packetWeight;

    public Ingredient(String name, Double carbAmountPerHundredGrams, Integer packetWeight) {
        this.ingName = name;
        this.carbAmountPerHundredGrams=carbAmountPerHundredGrams;
        this.packetWeight=packetWeight;
    }

    @Override
    public String getName() {
        return ingName;
    }

    @Override
    public Double getCarbsPerHundredG() {
        return carbAmountPerHundredGrams;
    }

    @Override
    public Integer getPacketWeight() {
        return packetWeight;
    }
}
