package com.example.kbb12.dms.model.database.mealPlannerRecord;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class Ingredient implements IIngredient {
    private String ingName;
    private Double carbAmountPerHundredGrams;
    private Integer packetWeight;
    private String barcode;

    public Ingredient(String name, Double carbAmountPerHundredGrams, Integer packetWeight) {
        this.ingName = name;
        this.carbAmountPerHundredGrams=carbAmountPerHundredGrams;
        this.packetWeight=packetWeight;
        this.barcode=null;
    }

    public Ingredient(String name,Double carbAmountPerHundredGrams, Integer packetWeight,
                      String barcode){
        this.ingName = name;
        this.carbAmountPerHundredGrams=carbAmountPerHundredGrams;
        this.packetWeight=packetWeight;
        this.barcode=barcode;
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

    @Override
    public String getBarcode() {
        return barcode;
    }

    public static class BasicIngredient implements IIngredient {

        @Override
        public String getName() {
            return "Custom";
        }

        @Override
        public Double getCarbsPerHundredG() {
            return 100.0;
        }

        @Override
        public Integer getPacketWeight() {
            return 100;
        }

        @Override
        public String getBarcode() {
            return null;
        }
    }
}
