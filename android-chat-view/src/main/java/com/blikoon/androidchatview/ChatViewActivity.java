package com.blikoon.androidchatview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.blikoon.androidchatview.adapter.ChatMessagesAdapter;
import com.blikoon.androidchatview.model.ChatMessage;
import com.blikoon.androidchatview.model.ChatMessageModel;
import com.blikoon.androidchatview.ui.InsetDecoration;
import com.blikoon.androidchatview.ui.KeyboardUtil;

public class ChatViewActivity extends AppCompatActivity implements
        ChatMessagesAdapter.OnInformRecyclerViewToScrollDownListener,
        KeyboardUtil.KeyboardVisibilityListener {
    private RecyclerView mRecyclerView;
    private ChatMessagesAdapter mAdapter;
    private LinearLayoutManager mVerticalManager;
    private EditText textInputTextEdit;
    private ImageButton textSendButton;
    private String counterpartJid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        counterpartJid = "user@server.com";

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_message_recycler_view);

        mVerticalManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mAdapter = new ChatMessagesAdapter(this,counterpartJid);
        mAdapter.setmOnInformRecyclerViewToScrollDownListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //Apply margins decoration to all collections
        mRecyclerView.addItemDecoration(new InsetDecoration(this));

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
                ChatMessageModel.get(getApplicationContext(),counterpartJid).addMessage(new ChatMessage(textInputTextEdit.getText().toString(),System.currentTimeMillis(), ChatMessage.Type.SENT,counterpartJid));

                //Clear the text from the edit text
                textInputTextEdit.getText().clear();

            }
        });

        setTitle("user@server.com");

        KeyboardUtil.setKeyboardVisibilityListener(this,this);
    }

    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {

    }



    @Override
    public void onInformRecyclerViewToScrollDown(int size) {

    }
}
