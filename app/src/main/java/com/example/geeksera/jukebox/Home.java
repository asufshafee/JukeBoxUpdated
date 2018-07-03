package com.example.geeksera.jukebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.geeksera.jukebox.Session.MyApplication;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    MyApplication myApplication;
    TabLayout tabLayout;
    MaterialSearchView searchView;
    FrameLayout frameLayout;

    String Query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myApplication = (MyApplication) getApplicationContext();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarfortab);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic

                musicLibrary.Default();
                musicQueue.Default();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if (newText.equals("")) {
                    setQuery("");
                    musicLibrary.Default();
                    musicQueue.Default();
                } else {
                    setQuery(newText);
                    musicQueue.Search(newText);
                    musicLibrary.Search(newText);
                }


                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                musicLibrary.Default();
                musicQueue.Default();
            }
        });

        frameLayout = (FrameLayout) findViewById(R.id.Fragments);

        getSupportActionBar().setTitle("Music Queue");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    getSupportActionBar().setTitle("Music Queue");

                } else {

                    getSupportActionBar().setTitle("Music Library");

                }
                mViewPager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mViewPager.getAdapter().notifyDataSetChanged();


            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        itemSearch.setVisible(false);
        if (id == R.id.TokenRequest) {
            getSupportActionBar().setTitle("Request Token");

            fragmentClass = Requests_Of_Tokens.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.Fragments, fragment).commit();
            tabLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);


        } else if (id == R.id.TodayTheme) {
            getSupportActionBar().setTitle("Today Theme");

            fragmentClass = TodayTheme.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.Fragments, fragment).commit();
            tabLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.Home) {
            itemSearch.setVisible(true);
            getSupportActionBar().setTitle("Music Queue");
            tabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);

        } else if (id == R.id.history) {
            startActivity(new Intent(getBaseContext(), PopularSongActivity.class));

        } else if (id == R.id.NowPlaying) {

            getSupportActionBar().setTitle("Now Playing");

            fragmentClass = NowPlayingAndDadications.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.Fragments, fragment).commit();
            tabLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    MusicLibrary musicLibrary;
    MusicQueue musicQueue;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    musicQueue = new MusicQueue();
                    return musicQueue;
                case 1:
                    musicLibrary = new MusicLibrary();
                    return musicLibrary;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Music Queue";

                case 1:
                    return "Music Library \n " + "Theme: " + myApplication.getThemee();


            }
            return null;
        }
    }


    MenuItem itemSearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        itemSearch = menu.findItem(R.id.action_search);
        searchView.setMenuItem(itemSearch);

        return true;
    }

    public void ShowHome() {
        getSupportActionBar().setTitle("Music Queue");
        tabLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }


    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }
}
