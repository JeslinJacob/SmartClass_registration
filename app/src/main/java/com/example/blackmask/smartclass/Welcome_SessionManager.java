package com.example.blackmask.smartclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Blackma$k on 23/03/2018.
 */

public class Welcome_SessionManager {
    // LogCat tag
    private static String TAG = Welcome_SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    //private static final String PREF_NAME = "AndroLawyer";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public Welcome_SessionManager(Context context,String PREF_NAME) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String id) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString("userid",id);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
