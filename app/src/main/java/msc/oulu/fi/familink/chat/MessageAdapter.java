package msc.oulu.fi.familink.chat;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import msc.oulu.fi.familink.R;

/**
 * Created by Simon on 08. Apr. 2016.
 */
public class MessageAdapter extends SimpleCursorAdapter {

    //TODO FROM is the chat history
    static final String[] FROM = ChatHistoryManager.getInstance().getChatHistory();
    static final int[] TO = {R.id.textTime};

    static final int MESSAGE_INCOMING_DIR = 1;

    private String selfUserName;
    private String incomingUserName;

    public MessageAdapter(Context context, Cursor c, int flags) {
        super(context, R.layout.chat_row, c, FROM, TO, flags);
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        super.bindView(row, context, cursor);

        LinearLayout.LayoutParams userNameAndChatMessageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        userNameAndChatMessageParams.setLayoutDirection(RelativeLayout.ALIGN_PARENT_RIGHT);

        LinearLayout.LayoutParams userNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        userNameParams.setLayoutDirection(RelativeLayout.ALIGN_PARENT_LEFT);

        LinearLayout.LayoutParams chatMessageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        chatMessageParams.setLayoutDirection(RelativeLayout.ALIGN_PARENT_RIGHT);

        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeParams.setLayoutDirection(RelativeLayout.ALIGN_RIGHT);
        timeParams.setLayoutDirection(RelativeLayout.BELOW);

        row.setBackgroundResource(R.color.colorTextMessageBackground);

        // Set the chat message
        String chatMessage = cursor.getString(cursor.getColumnIndex(ChatHistoryManager.C_TEXT));
        TextView textMessage = (TextView) row.findViewById(R.id.textUser);
        textMessage.setText(chatMessage.trim());
        textMessage.setLayoutParams(chatMessageParams);

        // Format the time stamp of the message
        long timestamp = cursor.getLong(cursor.getColumnIndex(ChatHistoryManager.C_TIME));
        TextView textTime = (TextView) row.findViewById(R.id.textTime);
        String readableTimeStamp = (String) DateUtils.getRelativeTimeSpanString(timestamp);
        textTime.setText(readableTimeStamp.trim());
        textTime.setLayoutParams(timeParams);

        // Format the message owner and the message
        TextView textUser = (TextView) row.findViewById(R.id.textUser);
        textUser.setText(incomingUserName + ": ");
        textUser.setLayoutParams(userNameParams);
        //TODO customized drawable for chat bubbles
        row.setBackgroundResource(android.R.drawable.editbox_background_normal);
    }

    public void setIncomingMessageUserName(String inputUserName) {
        this.incomingUserName = inputUserName;
    }

    public void setSelfUserName(String inputUserName) {
        this.selfUserName = inputUserName;
    }
}
