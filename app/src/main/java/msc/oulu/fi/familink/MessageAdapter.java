package msc.oulu.fi.familink;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Simon on 08. Apr. 2016.
 */
public class MessageAdapter extends SimpleCursorAdapter {

    //TODO FROM is the chat history
    static final String[] FROM = {};
    static final int[] TO = {R.id.textTime};

    private String selfUserName;
    private String incomingUserName;

    public MessageAdapter(Context context, Cursor c, int flags) {
        super(context, R.layout.chat_row, c, FROM, TO, flags);
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        super.bindView(row, context, cursor);


    }

    public void setIncomingMessageUserName(String inputUserName) {
        this.incomingUserName = inputUserName;
    }

    public void setSelfUserName(String inputUserName) {
        this.selfUserName = inputUserName;
    }
}
