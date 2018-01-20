package com.blikoon.androidchatview.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blikoon.androidchatview.ChatMessageCursorWrapper;
import com.blikoon.androidchatview.persistance.DatabaseBackend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gakwaya on 2018/1/20.
 */

public class ChatMessageModel {


    public interface OnMessageAddListener {
        void onMessageAdd();
    }

    private static ChatMessageModel sFoodModel;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String counterpartJid;

    public OnMessageAddListener getMessageAddListener() {
        return messageAddListener;
    }

    public void setMessageAddListener(OnMessageAddListener messageAddListener) {
        this.messageAddListener = messageAddListener;
    }

    OnMessageAddListener messageAddListener;




    public static ChatMessageModel get(Context context, String counterpartJid)
    {
        if(sFoodModel == null)
        {
            sFoodModel = new ChatMessageModel(context,counterpartJid);
        }
        return  sFoodModel;
    }

    private ChatMessageModel(Context context ,String counterpartJid)
    {
        mContext = context.getApplicationContext();
        mDatabase = DatabaseBackend.getInstance(mContext).getWritableDatabase();
        this.counterpartJid = counterpartJid;
    }

    public void addMessage(ChatMessage c)
    {
        ContentValues values = c.getContentValues();
        mDatabase.insert(ChatMessage.TABLE_NAME, null, values);
        messageAddListener.onMessageAdd();
    }


    public List<ChatMessage> getMessages()
    {
        List<ChatMessage> messages = new ArrayList<>();

        ChatMessageCursorWrapper cursor = queryMessages("contactJid= ?",new String [] {counterpartJid});

        try
        {
            cursor.moveToFirst();
            while( !cursor.isAfterLast())
            {
                messages.add(cursor.getChatMessage());
                cursor.moveToNext();
            }

        }finally {
            cursor.close();
        }
        return messages;
    }



    private ChatMessageCursorWrapper queryMessages(String whereClause , String [] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                ChatMessage.TABLE_NAME,
                null ,//Columns - null selects all columns
                whereClause,
                whereArgs,
                null ,//groupBy
                null, //having
                null//orderBy
        );
        return new ChatMessageCursorWrapper(cursor);
    }

}