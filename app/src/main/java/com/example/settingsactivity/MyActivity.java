package com.example.settingsactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Create the fragment only when the activity is created for the first time.
            //仅在首次创建活动时创建片段。
            // ie. not after orientation changes
            //即。 方向改变后没有
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(MyPreferenceFragment.FRAGMENT_TAG);
            if (fragment == null) {
                fragment = new MyPreferenceFragment();
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment, MyPreferenceFragment.FRAGMENT_TAG);
            ft.commit();
        }

//        SharedPreferences sp = getSharedPreferences("com.example.settingsactivity_preferences",Context.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String str = sp.getString("key_list_preference",null);
        Log.d("111", "onCreate: "+str);
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat,
                                           PreferenceScreen preferenceScreen) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MyPreferenceFragment fragment = new MyPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preferenceScreen.getKey());
        fragment.setArguments(args);
        ft.add(R.id.fragment_container, fragment, preferenceScreen.getKey());
        ft.addToBackStack(preferenceScreen.getKey());
        ft.commit();
        return true;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_menu:
                Toast.makeText(this, item.getTitle() + " pressed!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
