package com.example.aspirev3.farmmodule;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Aspire V3 on 6/16/2016.
 */
public class Utility {
    public static void changeCurrUser(SharedPreferences settings, String username, Boolean isLoggedIn){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putString("currUsername", username);
        editor.apply();
    }

    public static void changeCurrUser(Activity activity, String username, Boolean isLoggedIn){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putString("currUsername", username);
        editor.apply();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
