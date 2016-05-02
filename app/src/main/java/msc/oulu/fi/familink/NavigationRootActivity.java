package msc.oulu.fi.familink;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import msc.oulu.fi.familink.TodoAndReminder.ReminderFragment;
import msc.oulu.fi.familink.TodoAndReminder.TodoFragment;
import msc.oulu.fi.familink.chat.ChatFragment;
import msc.oulu.fi.familink.location.LocationFragment;
import msc.oulu.fi.familink.notes.NotesFragment;

public class NavigationRootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout baseLayout;
    LayoutInflater inflater;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_navigation_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inflater = (LayoutInflater) NavigationRootActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        baseLayout = (FrameLayout)findViewById(R.id.navigationRootRL);
        fragmentManager = getFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SetCurrentView(getIntent().getStringExtra("section"));

    }

    private void SetCurrentView(String section) {
//        MainActivity.progressDialog.show();
        baseLayout.removeAllViews();
        switch (section) {
            case "Chat":
                Fragment chatFragment = ChatFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, chatFragment) .commit();
                break;
            case "Location":
                Fragment locationFragment = LocationFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, locationFragment) .commit();
                break;
            case "Notes":
                Fragment notesFragment = NotesFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, notesFragment) .commit();
                break;
            case "Reminder":
                Fragment reminderFragment = ReminderFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, reminderFragment) .commit();
                break;
            case "Todo":
                Fragment todoFragment = TodoFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, todoFragment) .commit();
                break;
            case "Settings":
                Fragment settingsFragment = SettingsFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, settingsFragment) .commit();
                break;
//            case R.id.nav_send:
//                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
//                break;
//            case R.id.nav_share:
//                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
//                break;
        }
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        MainActivity.progressDialog.show(this,"","Loading...");
        baseLayout.removeAllViews();
        switch (id) {
            case R.id.nav_chat:
                Fragment chatFragment = ChatFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, chatFragment) .commit();
                break;
            case R.id.nav_location:
                Fragment locationFragment = LocationFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, locationFragment) .commit();
                break;
            case R.id.nav_notes:
                Fragment notesFragment = NotesFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, notesFragment) .commit();
                break;
            case R.id.nav_reminder:
                Fragment reminderFragment = ReminderFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, reminderFragment) .commit();
                break;
            case R.id.nav_todo:
                Fragment todoFragment = TodoFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, todoFragment) .commit();
                break;
            case R.id.nav_settings:
                Fragment settingsFragment = SettingsFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, settingsFragment) .commit();
                break;
//            case R.id.nav_send:
//                Fragment locationFragment = LocationFragment.getInstance();
//                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, locationFragment) .commit();
//                break;
//            case R.id.nav_share:
//                Fragment locationFragment = LocationFragment.getInstance();
//                fragmentManager.beginTransaction().replace(R.id.navigationRootRL, locationFragment) .commit();
//                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
//        MainActivity.progressDialog.cancel();
        return true;
    }
}
