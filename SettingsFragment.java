package com.vipromos.pursuit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String PREFERENCE_THEME = "pref_theme";
    public static String PREFERENCE_SUBJECT_ORDER = "pref_subject_order";
    public static String PREFERENCE_DEFAULT_QUESTION = "pref_default_question";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREFERENCE_THEME)) {
            // Recreate the activity so the theme takes effect
            getActivity().recreate();
        }
    }
}