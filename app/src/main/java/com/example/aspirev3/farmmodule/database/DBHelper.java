package com.example.aspirev3.farmmodule.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aspire V3 on 6/15/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper (Context context) {
        super(context, DBSchema.DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBSchema.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBSchema.USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBSchema.CONTACT_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public String login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {username, password};
        String result_username = null;
        String query = "SELECT * FROM " + DBSchema.USER_TABLE_NAME +
                " WHERE LOWER(" + DBSchema.USER_NAME_COL + ")=LOWER(?) AND " + DBSchema.USER_PASS_COL + "=?";

        Cursor c = db.rawQuery(query, args);
        if(c.getCount() > 0) {
            c.moveToFirst();
            result_username = c.getString(1);
        }

        c.close();
        db.close();
        return result_username;
    }

    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {username, password};
        String[] check_dup_args = {username};
        String check_dup_username_query = "SELECT * FROM " + DBSchema.USER_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=?";
        String query = "INSERT INTO " + DBSchema.USER_TABLE_NAME +
                "(" + DBSchema.USER_NAME_COL + "," + DBSchema.USER_PASS_COL + ") " +
                "VALUES (?,?)";

        Cursor c = db.rawQuery(check_dup_username_query, check_dup_args);
        if(c.getCount() > 0) {
            return false;
        }
        c.close();

        db.execSQL(query, args);
        db.close();

        return true;
    }

    public boolean addContact(String username, String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {username, name, Integer.toString(age)};
        String query = "INSERT INTO " + DBSchema.CONTACT_TABLE_NAME +
            "(" + DBSchema.USER_NAME_COL + "," + DBSchema.CONTACT_NAME_COL + "," + DBSchema.CONTACT_AGE_COL + ") " +
            "VALUES (?,?,?)";
        String check_dup_query = "SELECT * FROM " + DBSchema.CONTACT_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=? AND " +
                DBSchema.CONTACT_NAME_COL + "=? AND " +
                DBSchema.CONTACT_AGE_COL + "=?";

        Cursor c = db.rawQuery(check_dup_query, args);
        if(c.getCount() > 0) {
            return false;
        }

        db.execSQL(query, args);
        db.close();
        return true;
    }
}
