package msc.oulu.fi.familink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    protected static final String FAMILINK_PREFERENCES = "familink_preferences";

    SharedPreferences sharedPreferences;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(FAMILINK_PREFERENCES, Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

        Firebase.setAndroidContext(this);
        if (savedInstanceState != null) {
            username = savedInstanceState.getString(LoginActivity.USERNAME);
        }
        String name = username.equals("") ? "Unknown" : username; // this info comes from login
        setUserName(name);

        ((RelativeLayout) findViewById(R.id.chatRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.notesRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.todoRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.locationRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.reminderRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.settingsRL)).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        setUserName(sharedPreferences.getString(LoginActivity.USERNAME, ""));
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LoginActivity.USERNAME, sharedPreferences.getString(LoginActivity.USERNAME, ""));
        outState.putString(LoginActivity.EMAIL, sharedPreferences.getString(LoginActivity.EMAIL, ""));
        outState.putStringArrayList(LoginActivity.FRIENDS, new ArrayList<>((sharedPreferences.getStringSet(LoginActivity.FRIENDS, new HashSet<String>()))));

        super.onSaveInstanceState(outState);
    }

    protected void setUserName(String name) {
        ((TextView) findViewById(R.id.heiMsg)).setText("Hei, " + name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatRL:
                Intent chatIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                chatIntent.putExtra("section", "Chat");
                startActivity(chatIntent);
                break;
            case R.id.notesRL:
                Intent notesIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                notesIntent.putExtra("section", "Notes");
                startActivity(notesIntent);
                break;
            case R.id.todoRL:
                Intent todoIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                todoIntent.putExtra("section", "Todo");
                startActivity(todoIntent);
                break;
            case R.id.locationRL:
                Intent locIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                locIntent.putExtra("section", "Location");
                startActivity(locIntent);
                break;
            case R.id.reminderRL:
                Intent reminderIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                reminderIntent.putExtra("section", "Reminder");
                startActivity(reminderIntent);
                break;
            case R.id.settingsRL:
                Intent settingIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                settingIntent.putExtra("section", "Settings");
                startActivity(settingIntent);
                break;
        }
    }
}
