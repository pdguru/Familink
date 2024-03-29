package msc.oulu.fi.familink.chat;

import java.util.Date;

/**
 * Created by Simon on 02. Mai. 2016.
 */
public class Chat {
    private String message;
    private String author;
    private Date date;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    public Chat(String message, String author, Date date) {
        this.message = message;
        this.author = author;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }
}
