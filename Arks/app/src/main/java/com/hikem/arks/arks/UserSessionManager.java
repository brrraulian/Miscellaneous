package com.hikem.arks.arks;

/**
 * Created by Makem2 on 12/01/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.HashMap;


public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    /*
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";*/
    public static final String KEY_ID = "id_seguranca";
    public static final String KEY_EMPRESA = "empresa";
    public static final String KEY_NOME = "nome";
    public static final String KEY_FOLDER = "id_folder";
    public static final String KEY_FOLDER_NAME = "folder_name";
    public static final String KEY_FOLDER_PARENT = "id_folder_parent";
    public static final String KEY_FLASH = "flash";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String id_seguranca, String empresa, String nome, String id_folder, String folder_name, String id_folder_parent){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        /*
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);*/
        editor.putString(KEY_ID, id_seguranca);
        editor.putString(KEY_EMPRESA, empresa);
        editor.putString(KEY_NOME, nome);
        editor.putString(KEY_FOLDER, id_folder);
        editor.putString(KEY_FOLDER_NAME, folder_name);
        editor.putString(KEY_FOLDER_PARENT, id_folder_parent);
        editor.putString(KEY_FLASH, "on");

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(Intent intent){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.setAction(intent.getAction());
            i.setType(intent.getType());

            if(intent.getAction() != null && intent.getType() != null) {
                if (intent.getAction().equals(Intent.ACTION_SEND) && intent.getType() != null) {
                    if (intent.getType().startsWith("image/")) {
                        i.putExtra(intent.EXTRA_STREAM, intent.getParcelableExtra(Intent.EXTRA_STREAM));
                    }
                } else if (intent.getAction().equals(Intent.ACTION_SEND_MULTIPLE) && intent.getType() != null) {
                    if (intent.getType().startsWith("image/")) {
                        i.putExtra(intent.EXTRA_STREAM, intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM));
                    }
                }
            }

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        /*
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));*/
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_EMPRESA, pref.getString(KEY_EMPRESA, null));
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));
        user.put(KEY_FOLDER, pref.getString(KEY_FOLDER, null));
        user.put(KEY_FOLDER_NAME, pref.getString(KEY_FOLDER_NAME, null));
        user.put(KEY_FOLDER_PARENT, pref.getString(KEY_FOLDER_PARENT, null));
        user.put(KEY_FLASH, pref.getString(KEY_FLASH, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(Intent intent){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.setAction(intent.getAction());
        i.setType(intent.getType());

        if(intent.getAction() != null && intent.getType() != null) {
            if (intent.getAction().equals(Intent.ACTION_SEND) && intent.getType() != null) {
                if (intent.getType().startsWith("image/")) {
                    i.putExtra(intent.EXTRA_STREAM, intent.getParcelableExtra(Intent.EXTRA_STREAM));
                }
            } else if (intent.getAction().equals(Intent.ACTION_SEND_MULTIPLE) && intent.getType() != null) {
                if (intent.getType().startsWith("image/")) {
                    i.putExtra(intent.EXTRA_STREAM, intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM));
                }
            }
        }

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public void SaveFolder(String id_folder, String folder_name, String id_folder_parent) {
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_FOLDER, id_folder);
        editor.putString(KEY_FOLDER_NAME, folder_name);
        editor.putString(KEY_FOLDER_PARENT, id_folder_parent);

        editor.commit();
    }

    public void SaveFlash(String flash) {

        editor.putString(KEY_FLASH, flash);

        editor.commit();
    }

}

