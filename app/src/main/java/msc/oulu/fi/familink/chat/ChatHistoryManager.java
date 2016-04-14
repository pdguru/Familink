package msc.oulu.fi.familink.chat;

import java.util.ArrayList;

/**
 * Created by Simon on 13. Apr. 2016.
 */
public class ChatHistoryManager {

    public final static String C_TIME = "time";
    public final static String C_DIR = "dir";
    public final static String C_TEXT = "text";


    private static ArrayList<String> CHAT_HISTORY = new ArrayList<>();

    public String[] getChatHistory() {
        return CHAT_HISTORY.toArray(new String[0]);
    }

    private static ChatHistoryManager chatHistoryManager = null;

    private void ChatHistoryManager() {
    }

    public static ChatHistoryManager getInstance() {
        return chatHistoryManager == null ? new ChatHistoryManager() : chatHistoryManager;
    }

    public void addMessageToHistory(String message) {
        CHAT_HISTORY.add(message);
    }

    public boolean deleteMessageFromHistory(String message, int position) {

        if (message.equals(CHAT_HISTORY.get(position))) {
            return message.equals(CHAT_HISTORY.remove(position));
        } else {
            return false;
        }
    }
}
