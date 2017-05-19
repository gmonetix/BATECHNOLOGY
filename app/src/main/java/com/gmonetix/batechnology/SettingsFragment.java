package com.gmonetix.batechnology;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.gmonetix.batechnology.helper.SharedPref;
import com.gmonetix.batechnology.helper.Utils;

public class SettingsFragment extends PreferenceFragment {

    private SharedPref sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_settings);

        sharedPref = new SharedPref(getActivity());

        final EditTextPreference prefName = (EditTextPreference) findPreference(getString(R.string.pref_title_name));
        final EditTextPreference prefEmail = (EditTextPreference) findPreference(getString(R.string.pref_title_email));
        final EditTextPreference prefNumber = (EditTextPreference) findPreference(getString(R.string.pref_title_number));
        Preference prefTermAndPolicy = (Preference) findPreference(getResources().getString(R.string.pref_title_term));

        prefTermAndPolicy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dialogTerm(getActivity());
                return true;
            }
        });

        prefName.setSummary(sharedPref.getYourName());
        prefName.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if(!s.trim().isEmpty()){
                    prefName.setSummary(s);
                    return true;
                }else{
                    Toast.makeText(getActivity(), "Invalid Name Input", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        prefEmail.setSummary(sharedPref.getYourEmail());
        prefEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if (Utils.isValidEmail(s)) {
                    prefEmail.setSummary(s);
                    return true;
                } else {
                    Toast.makeText(getActivity(), "Invalid Email Input", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        prefNumber.setSummary(sharedPref.getYourNumber());
        prefNumber.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String) o;
                if(s.length() == 10){
                    prefNumber.setSummary(s);
                    return true;
                }else{
                    Toast.makeText(getActivity(), "Invalid Phone Number Input", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

    }

    public void dialogTerm(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.pref_title_term));
        builder.setMessage(getResources().getString(R.string.terms_conditions));
        builder.setPositiveButton("OK", null);
        builder.show();
    }

}
