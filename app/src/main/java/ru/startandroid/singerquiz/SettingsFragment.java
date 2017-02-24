package ru.startandroid.singerquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void deleteListener(){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onDestroy();
        deleteListener();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(MainActivity.KEY_QUESTIONS_PREFERENCES)){
            Log.d("PIAT", "ifonSharedPreferenceChanged");
            ResetDialog resetDialog = new ResetDialog();
            resetDialog.setTargetFragment(this, 0);
            resetDialog.show(getFragmentManager(), "RESET");
        }
    }
}
