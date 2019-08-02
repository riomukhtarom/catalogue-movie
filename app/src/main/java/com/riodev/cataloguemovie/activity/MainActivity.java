package com.riodev.cataloguemovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.riodev.cataloguemovie.fragment.FavoriteFragment;
import com.riodev.cataloguemovie.R;
import com.riodev.cataloguemovie.fragment.NowPlayingFragment;
import com.riodev.cataloguemovie.fragment.SearchFragment;
import com.riodev.cataloguemovie.fragment.UpComingFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG_FRAGMENT = "fragment";
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_now_playing:
                        fragment = new NowPlayingFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_up_coming:
                        fragment = new UpComingFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_favorite:
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_search_movie:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigationMain);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState==null){
            navigation.setSelectedItemId(R.id.navigation_now_playing);
            fragment = new NowPlayingFragment();
        } else {
            fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            loadFragment(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_language :
                Intent intentLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intentLanguage);

            case R.id.menu_reminder :
                Intent intentRemainder = new Intent(MainActivity.this, RemainderSettingsActivity.class);
                startActivity(intentRemainder);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_mainFrame, fragment, TAG_FRAGMENT)
                    .commit();
        }
    }

}
