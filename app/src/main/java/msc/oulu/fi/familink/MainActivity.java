package msc.oulu.fi.familink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkLoginCredentials()){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        String name = "John"; // this info comes from login
        ((TextView)findViewById(R.id.heiMsg)).setText("Hei, "+name);

        ((RelativeLayout)findViewById(R.id.chatRL)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.notesRL)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.todoRL)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.locationRL)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.reminderRL)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.settingsRL)).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chatRL: Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
                                startActivity(chatIntent);break;
            case R.id.notesRL: Intent notesIntent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(notesIntent);break;
            case R.id.todoRL: Intent todoIntent = new Intent(MainActivity.this, TodoActivity.class);
                startActivity(todoIntent);break;
            case R.id.locationRL: Intent locIntent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(locIntent);break;
            case R.id.reminderRL: Intent reminderIntent = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(reminderIntent);break;
            case R.id.settingsRL: Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingIntent);break;
        }

    }

    private boolean checkLoginCredentials() {
        sharedPreferences = getSharedPreferences("familinkPrefernce", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user","");
        String password = sharedPreferences.getString("pass","");

        if(username.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
            return false;
        }
        return true;
    }
}
