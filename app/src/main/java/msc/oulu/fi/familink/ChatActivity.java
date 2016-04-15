package msc.oulu.fi.familink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ListView chatHistory = (ListView) findViewById(R.id.chatHistory);
        EditText chatMessage = (EditText) findViewById(R.id.chatMessage);
        Button sendChatMessageButton = (Button) findViewById(R.id.sendChatMessageButton);

    }
}
