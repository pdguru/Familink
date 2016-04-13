package msc.oulu.fi.familink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class NavigationRootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout baseLayout;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inflater = (LayoutInflater) NavigationRootActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        baseLayout = (RelativeLayout)findViewById(R.id.navigationRootRL);

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
        baseLayout.removeAllViews();
        switch (section) {
            case "Chat":
                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
                break;
            case "Location":
                baseLayout.addView(inflater.inflate(R.layout.fragment_location,null));
                break;
            case "Notes":
                baseLayout.addView(inflater.inflate(R.layout.fragment_notes,null));
                break;
            case "Reminder":
                baseLayout.addView(inflater.inflate(R.layout.fragment_reminder,null));
                break;
            case "Todo":
                baseLayout.addView(inflater.inflate(R.layout.fragment_todo,null));
                break;
            case "Settings":
                baseLayout.addView(inflater.inflate(R.layout.fragment_settings,null));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_root, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        baseLayout.removeAllViews();
        switch (id) {
            case R.id.nav_chat:
                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
                break;
            case R.id.nav_location:
                baseLayout.addView(inflater.inflate(R.layout.fragment_location,null));
                break;
            case R.id.nav_notes:
                baseLayout.addView(inflater.inflate(R.layout.fragment_notes,null));
                break;
            case R.id.nav_reminder:
                baseLayout.addView(inflater.inflate(R.layout.fragment_reminder,null));
                break;
            case R.id.nav_todo:
                baseLayout.addView(inflater.inflate(R.layout.fragment_todo,null));
                break;
            case R.id.nav_settings:
                baseLayout.addView(inflater.inflate(R.layout.fragment_settings,null));
                break;
            case R.id.nav_send:
                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
                break;
            case R.id.nav_share:
                baseLayout.addView(inflater.inflate(R.layout.fragment_chat,null));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
