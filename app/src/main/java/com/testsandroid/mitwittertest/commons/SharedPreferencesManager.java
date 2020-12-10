package com.testsandroid.mitwittertest.commons;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static  final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){

    }

    private static SharedPreferences getSharedPreferences(){
        return MyAPP
                .getContext().getSharedPreferences(APP_SETTINGS_FILE,Context.MODE_PRIVATE);


    }
    //CLAVE VALOR!!
    public static void setSomeStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }
    public static void setSomeBoolean(String dataLabel, Boolean dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel,dataValue);
        editor.commit();

    }
    public static String getSomeStringValue(String datalabel){
        return getSharedPreferences().getString(datalabel, null);
    }
    public static Boolean getSomeBooleanValue(String datalabel){
        return getSharedPreferences().getBoolean(datalabel, false);
    }


}
