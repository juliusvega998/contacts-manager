package com.example.aspirev3.farmmodule.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aspire V3 on 6/16/2016.
 */
public class DBSchema {
    public static final String DB_NAME = "Sample.db";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_NAME_COL = "username";
    public static final String USER_PASS_COL = "password";

    public static final String CONTACT_TABLE_NAME = "contacts";
    public static final String CONTACT_NAME_COL = "name";
    public static final String CONTACT_AGE_COL = "age";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_NAME_COL + " VARCHAR(50) PRIMARY KEY," +
                USER_PASS_COL + " VARCHAR(50) NOT NULL" +
            ")"
        );

        db.execSQL(
            "CREATE TABLE " + CONTACT_TABLE_NAME + "(" +
                CONTACT_NAME_COL + " VARCHAR(50) NOT NULL, " +
                CONTACT_AGE_COL + " INT NOT NULL, " +
                USER_NAME_COL + "VARCHAR(50) NOT NULL, " +
                "PRIMARY KEY (" + CONTACT_NAME_COL + "," + CONTACT_AGE_COL + "," + USER_NAME_COL + "), " +
                "FOREIGN KEY " + USER_NAME_COL + " REFERENCES "
                    + USER_TABLE_NAME + "(" + USER_NAME_COL + ") ON DELETE CASCADE" +
            ")"
        );
    }
}
