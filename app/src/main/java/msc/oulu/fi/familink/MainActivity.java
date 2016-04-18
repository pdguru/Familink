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
            case R.id.chatRL: Intent chatIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                chatIntent.putExtra("section","Chat");
                startActivity(chatIntent);break;
            case R.id.notesRL: Intent notesIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                notesIntent.putExtra("section","Notes");
                startActivity(notesIntent);break;
            case R.id.todoRL: Intent todoIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                todoIntent.putExtra("section","Todo");
                startActivity(todoIntent);break;
            case R.id.locationRL: Intent locIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                locIntent.putExtra("section","Location");
                startActivity(locIntent);break;
            case R.id.reminderRL: Intent reminderIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                reminderIntent.putExtra("section","Reminder");
                startActivity(reminderIntent);break;
            case R.id.settingsRL: Intent settingIntent = new Intent(MainActivity.this, NavigationRootActivity.class);
                settingIntent.putExtra("section","Settings");
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
