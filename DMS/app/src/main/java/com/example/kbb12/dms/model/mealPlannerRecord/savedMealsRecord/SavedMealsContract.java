package com.example.kbb12.dms.model.mealPlannerRecord.savedMealsRecord;

import android.provider.BaseColumns;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedMealsContract {
    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "savedMeals";
        public static final String COLUMN_NAME_MEALNAME = "name";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_INGREDIENTAMOUNTS = "ingredientVals";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContentsDefinition.TABLE_NAME + " (" +
                    ContentsDefinition.COLUMN_NAME_MEALNAME + " TEXT," +
                    ContentsDefinition.COLUMN_NAME_INGREDIENTS + " TEXT," +
                    ContentsDefinition.COLUMN_NAME_INGREDIENTAMOUNTS + " TEXT," +
                    "PRIMARY KEY( "+ ContentsDefinition.COLUMN_NAME_MEALNAME +" ));";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
