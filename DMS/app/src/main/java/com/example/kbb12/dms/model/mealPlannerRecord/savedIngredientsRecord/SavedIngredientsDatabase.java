package com.example.kbb12.dms.model.mealPlannerRecord.savedIngredientsRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedIngredientsDatabase implements SavedIngredientsRecord {
    private SQLiteDatabase write;

    public SavedIngredientsDatabase(SQLiteDatabase write) {
        this.write = write;
    }

    @Override
    public void saveIngredient(IIngredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME, ingredient.getName());
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL, ingredient.getCarbsPerHundredG());
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT, ingredient.getPacketWeight());
        // insert row
        write.insert(SavedIngredientsContract.ContentsDefinition.TABLE_NAME, null, values);
    }

    @Override
    public List<IIngredient> getAllSavedIngredients() {
        List<IIngredient> ingredients = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SavedIngredientsContract.ContentsDefinition.TABLE_NAME;
        Cursor c = write.rawQuery(selectQuery, null);
        while (c.moveToNext()) {
            ingredients.add(new Ingredient(
                    c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME)),
                    Double.parseDouble(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL))),
                    Integer.parseInt(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT)))));
        }

        return ingredients;
    }

    @Override
    public IIngredient getIngredientByName(String name) {
        String selectQuery = "SELECT  * FROM " +
                SavedIngredientsContract.ContentsDefinition.TABLE_NAME+
                " where "+SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME +"=?";
        Cursor c = write.rawQuery(selectQuery, new String[]{name});
        if(c.moveToNext()){
            return new Ingredient(
                    c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME)),
                    Double.parseDouble(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL))),
                    Integer.parseInt(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT))));
        }
        return null;
    }

    @Override
    public IIngredient getIngredientByBarcode(String barcode) {
        String selectQuery = "SELECT  * FROM " +
                SavedIngredientsContract.ContentsDefinition.TABLE_NAME+
                " where "+SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_BARCODE +"=?";
        Cursor c = write.rawQuery(selectQuery, new String[]{barcode});
        if(c.moveToNext()){
            return new Ingredient(
                    c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME)),
                    Double.parseDouble(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL))),
                    Integer.parseInt(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT))));
        }
        return null;
    }
}
