package com.example.mathsolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mathsolver.Models.Message;
import com.example.mathsolver.Models.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChatActivity extends AppCompatActivity {
private FirebaseListAdapter<Message> adapter;
    DatabaseReference reff;
    private FirebaseAuth mAuth;
    private RelativeLayout activity_chat;
    private EmojiconEditText emojiconEditText;
    ImageView emoji_btn, submit_btn;
    private EmojIconActions emojIconActions;
    String uid, usrname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activity_chat = findViewById(R.id.relativechat);
        submit_btn = findViewById(R.id.btn_supmit);
        emoji_btn = findViewById(R.id.btn_emoji);
        emojiconEditText = findViewById(R.id.txt_fild);
        emojIconActions = new EmojIconActions(getApplicationContext(), activity_chat, emojiconEditText, emoji_btn);
        emojIconActions.ShowEmojIcon();

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usrname = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emojiconEditText.getText().toString() == "")
                    return;

                FirebaseDatabase.getInstance().getReference("Chat").push().setValue(new Message(usrname, emojiconEditText.getText().toString()));
                emojiconEditText.setText("");
            }
        });

        displayAllMsg();
    }



    private void displayAllMsg() {
        ListView lidtofmsg = findViewById(R.id.msgList);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_items, FirebaseDatabase.getInstance().getReference("Chat")) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView msgusername,msgtime;
                BubbleTextView msgtxt;
                msgusername = v.findViewById(R.id.msg_user);
                msgtime = v.findViewById(R.id.msg_time);
                msgtxt = v.findViewById(R.id.msg_text);
                msgusername.setText(model.getUserName());
                msgtxt.setText(model.getTxtMessage());
                msgtime.setText(DateFormat.format("HH:mm:ss", model.getMsgTime()));
            }
        };
        lidtofmsg.setAdapter(adapter);
    }
}