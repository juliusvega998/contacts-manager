package com.example.aspirev3.farmmodule.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Aspire V3 on 6/15/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    public DBHelper (Context context) {
        super(context, DBSchema.DB_NAME, null, DBSchema.VERSION);
        this.mContext = context;
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
        String[] salt_args = {username};
        String query = "SELECT " + DBSchema.USER_NAME_COL + " FROM " + DBSchema.USER_TABLE_NAME +
                " WHERE LOWER(" + DBSchema.USER_NAME_COL + ")=LOWER(?) AND " +
                DBSchema.USER_PASS_COL + "=?";
        String salt_query = "SELECT " + DBSchema.USER_SALT_COL + " FROM " + DBSchema.USER_TABLE_NAME +
                " WHERE LOWER(" + DBSchema.USER_NAME_COL + ")=LOWER(?)";
        String result_username = null;
        String salt = null;
        Cursor salt_c = db.rawQuery(salt_query, salt_args);
        Cursor c;

        if(salt_c.getCount() > 0) {
            salt_c.moveToFirst();
            salt = salt_c.getString(0);
        }
        salt_c.close();

        String[] args = {username, SHA256(password + salt)};
        c = db.rawQuery(query, args);
        if(c.getCount() > 0) {
            c.moveToFirst();
            result_username = c.getString(0);
        }

        c.close();
        db.close();

        return result_username;
    }

    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String salt = generateRandomString();
        String[] args = {username, SHA256(password + salt), salt};
        String[] check_dup_args = {username};
        String check_dup_username_query = "SELECT * FROM " + DBSchema.USER_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=?";
        String query = "INSERT INTO " + DBSchema.USER_TABLE_NAME +
                "(" + DBSchema.USER_NAME_COL + "," + DBSchema.USER_PASS_COL + "," + DBSchema.USER_SALT_COL + ") " +
                "VALUES (?,?,?)";
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
        String[] args = {username, name.replace("<", "&lt;").replace(">", "&gt;"), Integer.toString(age)};
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

    public String[] getHtmlContacts(String username) {
        String query = "SELECT * FROM " + DBSchema.CONTACT_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=?" +
                " ORDER BY " + DBSchema.CONTACT_NAME_COL ;
        String[] args = {username};
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> finalList = new ArrayList< >();

        Cursor c = db.rawQuery(query, args);
        if(c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                finalList.add("<h4>" + c.getString(0) + "</h4>"
                        + c.getString(1));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return finalList.toArray(new String[0]);
    }

    public String[] getContacts(String username) {
        String query = "SELECT * FROM " + DBSchema.CONTACT_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=?" +
                " ORDER BY " + DBSchema.CONTACT_NAME_COL ;
        String[] args = {username};
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> finalList = new ArrayList< >();

        Cursor c = db.rawQuery(query, args);
        if(c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                finalList.add(c.getString(0) + " " + c.getString(1));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return finalList.toArray(new String[0]);
    }

    public void editContact(String username, String oldName, String oldAge, String newName, String newAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + DBSchema.CONTACT_TABLE_NAME +
                " SET " + DBSchema.CONTACT_NAME_COL + "=?, " + DBSchema.CONTACT_AGE_COL + "=?" +
                " WHERE " + DBSchema.USER_NAME_COL + "=? AND " +
                DBSchema.CONTACT_NAME_COL + "=? AND " +
                DBSchema.CONTACT_AGE_COL + "=?";
        String[] args = {newName, newAge, username, oldName, oldAge};

        db.execSQL(query, args);
        db.close();
    }

    public void deleteContact(String username, String name, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DBSchema.CONTACT_TABLE_NAME +
                " WHERE " + DBSchema.USER_NAME_COL + "=? AND " +
                DBSchema.CONTACT_NAME_COL + "=? AND " +
                DBSchema.CONTACT_AGE_COL + "=?";
        String[] args = {username, name, age};

        db.execSQL(query, args);
        db.close();
    }

    /*code from http://stackoverflow.com/questions/415953/*/
    private String SHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = md.digest(password.getBytes());
            String hashPassword = new BigInteger(1, passBytes).toString();
            while(hashPassword.length() < 32) {
                hashPassword += "0";
            }
            return hashPassword;
        } catch (Exception e) {
            Log.e("DBHelper", "No such algorithm error.", e);
            return null;
        }
    }

    private String generateRandomString() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
