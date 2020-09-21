package com.axelia.nyttopstories.ui.list.browse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.ui.list.favorites.FavoritesFragment;
import com.axelia.nyttopstories.utils.ActivityUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewFragment();
        setupToolbar();
        setupBottomNavigation();
    }
    private void setupViewFragment() {
        BrowseItemsFragment browseItemsFragment = BrowseItemsFragment.newInstance();
        ActivityUtils.replaceFragment(
                getSupportFragmentManager(), browseItemsFragment, R.id.fragment_container);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_browse:
                        ActivityUtils.replaceFragment(
                                getSupportFragmentManager(), BrowseItemsFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                    case R.id.action_favorites:
                        ActivityUtils.replaceFragment(
                                getSupportFragmentManager(), FavoritesFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                }
                return false;
            }
        });
    }
}
