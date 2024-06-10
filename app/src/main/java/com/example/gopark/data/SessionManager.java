package com.example.gopark.data;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "LoginSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String SESSION_ID = "sessionId";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void createLoginSession(String sessionId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(SESSION_ID, sessionId);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public String getSessionId() {
        return pref.getString(SESSION_ID, null);
    }
}
