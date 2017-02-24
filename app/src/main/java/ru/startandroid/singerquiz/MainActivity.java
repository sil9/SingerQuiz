package ru.startandroid.singerquiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;




public class MainActivity extends AppCompatActivity implements SingerFragment.OnResetQuizCallback {

    public static final String KEY_QUESTIONS_PREFERENCES = "key_questions_preferences";
    public static final String KEY_VARIANTS_PREFERENCES = "key_variants_preferences";

    FragmentManager fragManager;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        fragManager = getSupportFragmentManager();
        Fragment fragment = fragManager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            SingerFragment singerFragment = SingerFragment.newInstance(sharedpreferences.getString(KEY_QUESTIONS_PREFERENCES, null),
                    sharedpreferences.getString(KEY_VARIANTS_PREFERENCES, null));
            fragManager.beginTransaction()
                    .add(R.id.fragmentContainer, singerFragment)
                    .commit();
        }
    }


    private void setNewFragment(String numberQuestions, String numberVariants) {
        SingerFragment singerFragment = SingerFragment.newInstance(numberQuestions, numberVariants);
        fragManager.beginTransaction()
                .replace(R.id.fragmentContainer, singerFragment)
                .commit();
    }

    @Override
    public void onResetQuiz() {
        setNewFragment(sharedpreferences.getString(KEY_QUESTIONS_PREFERENCES, null), sharedpreferences.getString(KEY_VARIANTS_PREFERENCES, null));
    }
}

