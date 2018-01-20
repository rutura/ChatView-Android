package com.blikoon.androidchatview.model;

import android.content.ContentValues;
import android.text.format.DateFormat;

import java.util.concurrent.TimeUnit;

/**
 * Created by gakwaya on 2018/1/20.
 */

public class ChatMessage {

    private String message;
    private long timestamp;
    private Type type;
    private String contactJid;


    public static final String TABLE_NAME = "chatMessages";

    public static final class Cols
    {
        public static final String message = "message";
        public static final String timestamp = "timestamp";
        public static final String messageType = "messageType";
        public static final String contactJid = "contactjid";
    }




    public ChatMessage(String message, long timestamp, Type type , String contactJid){
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.contactJid = contactJid;

    }
    public String getContactJid() {
        return contactJid;
    }

    public void setContactJid(String contactJid) {
        this.contactJid = contactJid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getFormattedTime(){

        long oneDayInMillis = TimeUnit.DAYS.toMillis(1); // 24 * 60 * 60 * 1000;

        long timeDifference = System.currentTimeMillis() - timestamp;

        return timeDifference < oneDayInMillis
                ? DateFormat.format("hh:mm a", timestamp).toString()
                : DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("timestamp", timestamp);
        values.put("messageType",getTypeStringValue(type));
        values.put("contactjid",contactJid);

        return values;

    }

    public String getTypeStringValue(Type type)
    {
        if(type==Type.SENT)
            return "SENT";
        else
            return "RECEIVED";
    }

    public enum Type {
        SENT, RECEIVED
    }
}