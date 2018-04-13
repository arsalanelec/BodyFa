package com.example.arsalan.mygym;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.arsalan.mygym.fragments.GymListFragment;
import com.example.arsalan.mygym.fragments.HomeFragment;
import com.example.arsalan.mygym.fragments.MyGymFragment;
import com.example.arsalan.mygym.fragments.MyTrainerFragment;
import com.example.arsalan.mygym.fragments.NewsListFragment;
import com.example.arsalan.mygym.fragments.TrainerListFragment;
import com.example.arsalan.mygym.fragments.TutorialFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final public static String KEY_OMOMI = "KEY_OMOMI";
    final public static String KEY_VARZESHKAR = "KEY_VARZESHKAR";
    private FragmentStatePagerAdapter viewPagerOmomiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ViewPager viewPager = findViewById(R.id.viewpager);
        if (getIntent().getStringExtra("KEY") != null && getIntent().getStringExtra("KEY").equals(KEY_OMOMI))
            viewPagerOmomiAdapter = new ViewPagerOmoomiAdapter(getSupportFragmentManager());
        else if (getIntent().getStringExtra("KEY") != null && getIntent().getStringExtra("KEY").equals(KEY_VARZESHKAR))
            viewPagerOmomiAdapter = new ViewPagerVarzeshkarAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerOmomiAdapter);
        TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);

            if (getIntent().getStringExtra("KEY").equals(KEY_OMOMI))
                tab.setCustomView(((ViewPagerOmoomiAdapter) viewPagerOmomiAdapter).getTabView(i));
            else if (getIntent().getStringExtra("KEY").equals(KEY_VARZESHKAR))
                tab.setCustomView(((ViewPagerVarzeshkarAdapter) viewPagerOmomiAdapter).getTabView(i));

            TextView tv = (TextView) tab.getCustomView().findViewById(R.id.textView);
            tv.setTextColor(ContextCompat.getColor(this, tab.isSelected() ? R.color.colorPrimary : R.color.colorAccent));
            if (tab.isSelected())
                tab.getCustomView().setBackgroundResource(R.drawable.white_rect_back);
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setBackgroundResource(R.drawable.white_rect_back);
                    TextView tv = (TextView) tab.getCustomView().findViewById(R.id.textView);
                    tv.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    TextView tv = (TextView) tab.getCustomView().findViewById(R.id.textView);
                    tv.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_user_account) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_contact_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ViewPagerOmoomiAdapter extends FragmentStatePagerAdapter {
        String[] titles = {"خانه", "اخبار ورزشی و تغذیه", "آموزش حرکات", "قهرمان ها", "باشگاه ها"};

        public ViewPagerOmoomiAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(titles[position]);
            Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.iran_sans_mobile);
            tv.setTypeface(typeface);
            return v;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) return new NewsListFragment();
            if (position == 4) return new GymListFragment();
            if (position == 3) return new TrainerListFragment();
            if (position == 2) return new TutorialFragment();

            return new HomeFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    private class ViewPagerVarzeshkarAdapter extends FragmentStatePagerAdapter {
        String[] titles = {"باشگاه من", "مربی من", "برنامه غذایی", "برنامه تمرینی"};

        public ViewPagerVarzeshkarAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(titles[position]);
            Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.iran_sans_mobile);
            tv.setTypeface(typeface);
            return v;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new MyGymFragment();
            if (position == 1) return new MyTrainerFragment();
            if (position == 3) return new TrainerListFragment();
            if (position == 2) return new TutorialFragment();

            return new HomeFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

}
