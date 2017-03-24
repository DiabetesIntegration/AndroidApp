package com.example.kbb12.dms.model.basalInsulinModel;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class BasalInsulinModelContractHolder {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BasalInsulinModelContractHolder() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME="BasalModel";
        public static final String COLUMN_ONE_TITLE = "Name";
        public static final String COLUMN_TWO_TITLE = "Time";
        public static final String COLUMN_THREE_TITLE = "ImprovedAmount";
        public static final String COLUMN_FOUR_TITLE="OrigAmount";
        public static final String COLUMN_FIVE_TITLE = "LastTaken";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE + " VARCHAR(1000)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE + " VARCHAR(5)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE + " FLOAT," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FIVE_TITLE + " DATE," +
                    "PRIMARY KEY( "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME;
}