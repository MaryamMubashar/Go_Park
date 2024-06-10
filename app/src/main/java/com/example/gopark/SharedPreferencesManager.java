package com.example.gopark;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String APP_SETTINGS = "APP_SETTINGS";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static boolean getSlotStatus(Context context, String slotId) {
        return getSharedPreferences(context).getBoolean(slotId, false);
    }

    public static void setSlotStatus(Context context, String slotId, boolean status) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(slotId, status);
        editor.apply();
    }

    public static void setSlotTime(Context context, String slotId, long time) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(slotId + "_time", time);
        editor.apply();
    }

    public static long getSlotTime(Context context, String slotId) {
        return getSharedPreferences(context).getLong(slotId + "_time", 0);
    }
}
