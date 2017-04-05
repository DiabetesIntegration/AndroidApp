package com.example.kbb12.dms.model.mealPlannerRecord.savedMealsRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.model.mealPlannerRecord.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedMealsDatabase implements SavedMealsRecord {
    private SQLiteDatabase write;


    public SavedMealsDatabase(SQLiteDatabase write) {
        this.write = write;
    }


    @Override
    public void saveMeal(IMeal meal) {
        ContentValues values = new ContentValues();
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME, meal.getName());
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS, getIngredientsString(meal));
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTAMOUNTS, getAmountsString(meal));
        // insert row
        write.insert(SavedMealsContract.ContentsDefinition.TABLE_NAME, null, values);
    }


    @Override
    public void editMeal(IMeal meal) {
        ContentValues values = new ContentValues();
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS, getIngredientsString(meal));
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTAMOUNTS, getAmountsString(meal));
        write.update(SavedMealsContract.ContentsDefinition.TABLE_NAME, values, SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME +" = ?", new String[] {meal.getName()});
    }

    @Override
    public void deleteMeal(IMeal m) {
        write.delete(SavedMealsContract.ContentsDefinition.TABLE_NAME, "name = ? ", new String[] {m.getName()} );
    }

    @Override
    public List<IMeal> getAllMeals(List<IIngredient> allIngredients) {
        List<IMeal> meals = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SavedMealsContract.ContentsDefinition.TABLE_NAME;
        Cursor c = write.rawQuery(selectQuery, null);
        while (c.moveToNext()) {
            meals.add(
                    new Meal(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME)),
                            reverseIngredientsString(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS)),allIngredients),
                            reverseAmountsString(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTAMOUNTS)))));
        }

        return meals;
    }

    private String getIngredientsString(IMeal meal){
        String ingredientNames="";
        ingredientNames+=meal.getIngredients().get(0).getName();
        for(int i=1;i<meal.getIngredients().size();i++){
            ingredientNames+=","+meal.getIngredients().get(i).getName();
        }
        return ingredientNames;
    }

    private List<IIngredient> reverseIngredientsString(String ingredientsString,List<IIngredient> allIngredients){
        Scanner scan = new Scanner(ingredientsString).useDelimiter(",");
        String currentName;
        List<IIngredient> ingredients=new ArrayList<>();
        while(scan.hasNext()){
            currentName=scan.next();
            for(IIngredient current:allIngredients){
                if(current.getName().equals(currentName)){
                    ingredients.add(current);
                    break;
                }
            }
        }
        return new ArrayList<>();
    }

    private String getAmountsString(IMeal meal){
        String amounts="";
        amounts+=Double.toString(meal.getAmounts().get(0));
        for(int i=1;i<meal.getIngredients().size();i++){
            amounts+=","+Double.toString(meal.getAmounts().get(0));
        }
        return amounts;
    }

    private List<Double> reverseAmountsString(String amounts){
        List<Double> amountsList = new ArrayList<>();
        Scanner scan = new Scanner(amounts).useDelimiter(",");
        while (scan.hasNextDouble()){
            amountsList.add(scan.nextDouble());
        }
        return amountsList;
    }
}
