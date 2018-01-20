package com.blikoon.androidchatview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.blikoon.androidchatview.adapter.ChatMessagesAdapter;
import com.blikoon.androidchatview.model.ChatMessage;
import com.blikoon.androidchatview.model.ChatMessageModel;
import com.blikoon.androidchatview.ui.InsetDecoration;
import com.blikoon.androidchatview.ui.KeyboardUtil;

public class ChatView extends RelativeLayout {

    private Context context;
    private RecyclerView mRecyclerView;
    private ChatMessagesAdapter mAdapter;
    private LinearLayoutManager mVerticalManager;
    private EditText textInputTextEdit;
    private ImageButton textSendButton;
    private String counterpartJid;

    /* Constructors */
    public ChatView(Context context) {
        this(context, null);
    }

    /*The two constructors below allow the class to be instantiated from XML*/
    public ChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatView(final Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.activity_chat_view, this, true);
        this.context = context;
        counterpartJid = "user@server.com";

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_message_recycler_view);

        mVerticalManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);

        mAdapter = new ChatMessagesAdapter(context,counterpartJid);
        //mAdapter.setmOnInformRecyclerViewToScrollDownListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //Apply margins decoration to all collections
        mRecyclerView.addItemDecoration(new InsetDecoration(context));

        //Default to vertical layout
        mRecyclerView.setLayoutManager(mVerticalManager);


        //Send text EditText and Send ImageButton
        textInputTextEdit = (EditText) findViewById(R.id.textinput);

        textSendButton = (ImageButton)findViewById(R.id.textSendButton);
        textSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Here you should have the logic to send the message with whatever it is you are using [xmpp|angularJs|...]
                * after that you update your model with the new message. Note that we update the model here, not the view. The view picks up the data from the model when it needs to.*/
                ChatMessageModel.get(context,counterpartJid).addMessage(new ChatMessage(textInputTextEdit.getText().toString(),System.currentTimeMillis(), ChatMessage.Type.SENT,counterpartJid));

                //Clear the text from the edit text
                textInputTextEdit.getText().clear();

            }
        });

        //KeyboardUtil.setKeyboardVisibilityListener(this,this);
    }

}
