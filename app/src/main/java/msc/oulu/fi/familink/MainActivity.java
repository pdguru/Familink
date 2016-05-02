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

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    protected static final String FAMILINK_PREFERENCES = "familink_preferences";

    SharedPreferences mSharedPreferences;
    private String mUsername = "Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(this);

        mSharedPreferences = getSharedPreferences(FAMILINK_PREFERENCES, Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getLogin();

        ((RelativeLayout) findViewById(R.id.chatRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.notesRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.todoRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.locationRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.reminderRL)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.settingsRL)).setOnClickListener(this);
    }

    private void getLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onResume() {
        if (AccessToken.getCurrentAccessToken() == null) {
            getLogin();
        }

        mUsername = mSharedPreferences.getString(LoginActivity.USERNAME, mUsername);
        setUserName(mUsername);
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LoginActivity.USERNAME, mSharedPreferences.getString(LoginActivity.USERNAME, ""));
        outState.putString(LoginActivity.EMAIL, mSharedPreferences.getString(LoginActivity.EMAIL, ""));
        outState.putStringArrayList(LoginActivity.FRIENDS, new ArrayList<>((mSharedPreferences.getStringSet(LoginActivity.FRIENDS, new HashSet<String>()))));

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
