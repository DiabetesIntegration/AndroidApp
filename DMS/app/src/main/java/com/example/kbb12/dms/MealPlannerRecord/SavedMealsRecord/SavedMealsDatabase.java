package com.example.kbb12.dms.MealPlannerRecord.SavedMealsRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kbb12.dms.MealPlannerRecord.SavedIngredientsRecord.SavedIngredientsContract;
import com.example.kbb12.dms.StartUp.IMeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedMealsDatabase implements SavedMealsRecord {
    private SQLiteDatabase write;
    private String name;


    public SavedMealsDatabase(SQLiteDatabase write) {
        this.write = write;
    }


    @Override
    public long saveMeal(String n, String ing, String ingVal, String tCarb, String custom) {
        ContentValues values = new ContentValues();
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME, n);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS, ing);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTVALS, ingVal);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_TOTALCARBS, tCarb);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_CUSTOMCARBS, custom);

        // insert row
        long id = write.insert(SavedMealsContract.ContentsDefinition.TABLE_NAME, null, values);

        return id;
    }


    @Override
    public long editMeal(Integer id, String n, String ing, String ingVal, String tCarb, String custom) {
        ContentValues values = new ContentValues();
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME, n);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS, ing);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTVALS, ingVal);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_TOTALCARBS, tCarb);
        values.put(SavedMealsContract.ContentsDefinition.COLUMN_NAME_CUSTOMCARBS, custom);

        return write.update(SavedMealsContract.ContentsDefinition.TABLE_NAME, values, SavedMealsContract.ContentsDefinition._ID +" = ?", new String[] {Integer.toString(id)});
    }

    @Override
    public long deleteMeal(IMeal m) {
        String name = m.getMealName();
        return write.delete(SavedMealsContract.ContentsDefinition.TABLE_NAME, "name = ? ", new String[] {name} );
    }

    @Override
    public Map<Integer, List<String>> getAllMeals() {
        Map<Integer, List<String>> basicData = new HashMap<Integer, List<String>>();

        String selectQuery = "SELECT  * FROM " + SavedMealsContract.ContentsDefinition.TABLE_NAME;

        Cursor c = write.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                List<String> values = new ArrayList<String>();
                int id = c.getInt(c.getColumnIndex(SavedMealsContract.ContentsDefinition._ID));
                values.add(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_MEALNAME)));
                values.add(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTS)));
                values.add(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_INGREDIENTVALS)));
                values.add(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_TOTALCARBS)));
                values.add(c.getString(c.getColumnIndex(SavedMealsContract.ContentsDefinition.COLUMN_NAME_CUSTOMCARBS)));
                basicData.put(id,values);
            } while (c.moveToNext());
        }

        return basicData;
    }
}
