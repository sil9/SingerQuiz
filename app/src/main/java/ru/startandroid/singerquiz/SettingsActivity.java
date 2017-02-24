package ru.startandroid.singerquiz;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity{

    FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        fragManager = getFragmentManager();
        Fragment fragment = fragManager.findFragmentById(R.id.fragmentContainer);
        if(fragment == null){
            SettingsFragment settingsFragment = new SettingsFragment();
            fragManager.beginTransaction()
                    .add(R.id.fragmentContainer, settingsFragment)
                    .commit();
        }
    }
}
