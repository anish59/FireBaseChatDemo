package com.firebasechatdemo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasechatdemo.R;
import com.firebasechatdemo.adapters.ChatAdapter;
import com.firebasechatdemo.helper.AppConstants;
import com.firebasechatdemo.helper.FBaseConstants;
import com.firebasechatdemo.helper.Functions;
import com.firebasechatdemo.model.Chat;
import com.firebasechatdemo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Context context;
    private android.support.v7.widget.RecyclerView rvConversations;
    private android.widget.ImageView imgAttach;
    private android.widget.ImageView imgCam;
    private android.widget.EditText edtWriteMessage;
    private android.widget.ImageView imgSend;
    private android.widget.RelativeLayout layoutMessageCompose;
    private ChatAdapter chatAdapter;
    private FirebaseUser firebaseUser;
    private User user;
    Toolbar toolbar;
    TextView txtTitle;
    DatabaseReference reference;
    List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.layoutMessageCompose = (RelativeLayout) findViewById(R.id.layoutMessageCompose);
        this.imgSend = (ImageView) findViewById(R.id.imgSend);
        this.edtWriteMessage = (EditText) findViewById(R.id.edtWriteMessage);
        this.imgCam = (ImageView) findViewById(R.id.imgCam);
        this.imgAttach = (ImageView) findViewById(R.id.imgAttach);
        this.rvConversations = (RecyclerView) findViewById(R.id.rvConversations);

        context = this;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        initToolbar();
        actionListeners();

        getIntentData();

        getUserChat();
    }

    private void getUserChat() {

        chatAdapter = new ChatAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvConversations.setLayoutManager(layoutManager);
        rvConversations.setAdapter(chatAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child(FBaseConstants.CHATS);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if ((chat.getSenderId().equals(firebaseUser.getUid()) && chat.getReceiverId().equals(user.getId()))
                            || (chat.getSenderId().equals(user.getId()) && chat.getReceiverId().equals(firebaseUser.getUid()))) {
                        chatList.add(chat);
                    }
                }

                chatAdapter.setChatList(chatList);
                rvConversations.scrollToPosition(chatList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "DataBaseErr: " + databaseError.toString(), Toast.LENGTH_SHORT).show();
                Log.e("DataBaseErr: ", databaseError.toString());
            }
        });
    }

    private void getIntentData() {
        user = (User) getIntent().getSerializableExtra(AppConstants.INTENT_USER);
        txtTitle.setText(user.getUserName());
        Log.e("getIntentData", user.toString());
    }

    private void actionListeners() {

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtWriteMessage.getText().toString().trim())) {
                    Toast.makeText(context, "please enter msg!", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendMessage(edtWriteMessage.getText().toString().trim(), firebaseUser.getUid(), user.getId()
                        , Functions.getCurrentUtctime(Functions.yyyyMMddHHmmss));
                edtWriteMessage.setText("");


            }
        });

    }

    void sendMessage(String msg, String senderId, String recieverId, String utcTime) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(FBaseConstants.MESSAGE, msg);
        hashMap.put(FBaseConstants.SENDER_ID, senderId);
        hashMap.put(FBaseConstants.RECEIVER_ID, recieverId);
        hashMap.put(FBaseConstants.UTC_TIME, utcTime);
        hashMap.put(FBaseConstants.MediaType, String.valueOf(0));
        hashMap.put(FBaseConstants.Media, "");

        databaseReference.child(FBaseConstants.CHATS).push().setValue(hashMap);

    }

    private void initToolbar() {

        toolbar = findViewById(R.id.toolbar);
        txtTitle = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
