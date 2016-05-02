package msc.oulu.fi.familink.chat;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

import msc.oulu.fi.familink.FirebaseListAdapter;
import msc.oulu.fi.familink.R;

/**
 * Created by Simon on 27. Apr. 2016.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }

    @Override
    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView textAuthor = (TextView) view.findViewById(R.id.textAuthor);
        textAuthor.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            textAuthor.setTextColor(Color.RED);
        } else {
            textAuthor.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.textChat)).setText(chat.getMessage());
        ((TextView) view.findViewById(R.id.textTime)).setText(chat.getDate().toString());
    }
}
