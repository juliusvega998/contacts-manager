package com.example.aspirev3.farmmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    public static void logout(final Activity act) {
        new AlertDialog.Builder(act)
            .setTitle("Logging out")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(act, MainActivity.class);
                    Toast.makeText(act.getBaseContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                    Utility.changeCurrUser(act,null,false);
                    act.startActivity(intent);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            }).show();
    }

    public static String getUsername(Context c) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getString("currUsername", null);
    }
}
