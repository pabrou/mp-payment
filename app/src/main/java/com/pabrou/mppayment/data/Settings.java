package com.pabrou.mppayment.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pablo on 12/12/17.
 */

public class Settings {

    private final static String SHARED_PREFERENCES_SETTINGS = "settings_preferences";

    private final static String CURRENT_ENVIRONMENT = "current_environment";

    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        preferences = context.getSharedPreferences(SHARED_PREFERENCES_SETTINGS, Context.MODE_PRIVATE);
    }

    /**
     * Resets all the settings to the initial state
     */
    public static void clear(){
        preferences.edit().clear().apply();
    }

    public static void setCurrentEnvironment(String environment) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_ENVIRONMENT, environment);
        editor.apply();
    }

    public static String getCurrentEnvironment() {
        return preferences.getString(CURRENT_ENVIRONMENT, null);
    }
}
