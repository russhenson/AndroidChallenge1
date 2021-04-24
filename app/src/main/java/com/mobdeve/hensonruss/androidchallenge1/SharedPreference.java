package com.mobdeve.hensonruss.androidchallenge1;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SharedPreference {

    private static final String LIST_KEY = "LIST_KEY";

    public static void writeListInPref(Context context, ArrayList<Email> emailList){
        Gson gson = new Gson();
        String jsonString = gson.toJson(emailList);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList<Email> readListFromPref(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sp.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Email>>(){}.getType();
        ArrayList<Email> emailList = gson.fromJson(jsonString, type);

        return emailList;

    }

}
