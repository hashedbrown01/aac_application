package com.example.my_aac;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.List;

public class SPManager {
    private static final String PREF_NAME = "group_pref";
    private static final String GROUP_KEY = "group_list";
    private static final String AAC_KEY = "aac_list";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SPManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveGroupList(List<GroupModel> list){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(list);
        editor.putString(GROUP_KEY, json);
        editor.apply();
    }

    public List<GroupModel> getGroupList(){
        String json = sharedPreferences.getString(GROUP_KEY, null);
        Type type = new TypeToken<List<GroupModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveAACList(List<AACModel> list){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(list);
        editor.putString(AAC_KEY, json);
        editor.apply();
    }

    public List<AACModel> getAACList(){
        String json = sharedPreferences.getString(AAC_KEY, null);
        Type type = new TypeToken<List<AACModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
