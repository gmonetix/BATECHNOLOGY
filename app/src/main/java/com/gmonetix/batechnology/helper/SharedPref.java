package com.gmonetix.batechnology.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gmonetix.batechnology.R;

public class SharedPref {

    private Context ctx;
    private SharedPreferences default_prefence;


    public SharedPref(Context context) {
        this.ctx = context;
        default_prefence = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String str(int string_id) {
        return ctx.getString(string_id);
    }

    /**
     * Preference for User profile
     */

    public void setYourName(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_name), name).apply();
    }

    public String getYourName() {
        return default_prefence.getString(str(R.string.pref_title_name), str(R.string.default_your_name));
    }

    public void setYourEmail(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_email), name).apply();
    }

    public String getYourEmail() {
        return default_prefence.getString(str(R.string.pref_title_email), str(R.string.default_your_email));
    }

    public void setYourNumber(String number) {
        default_prefence.edit().putString(str(R.string.pref_title_number), number).apply();
    }

    public String getYourNumber() {
        return default_prefence.getString(str(R.string.pref_title_number), str(R.string.default_your_number));
    }

}
