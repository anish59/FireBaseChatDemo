package com.firebasechatdemo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasechatdemo.R;
import com.firebasechatdemo.helper.AppConstants;
import com.firebasechatdemo.helper.FBaseConstants;
import com.firebasechatdemo.helper.Functions;
import com.firebasechatdemo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    private Context context;
    private android.support.v7.widget.RecyclerView rvConversations;
    private android.widget.ImageView imgAttach;
    private android.widget.ImageView imgCam;
    private android.widget.EditText edtWriteMessage;
    private android.widget.ImageView imgSend;
    private android.widget.RelativeLayout layoutMessageCompose;
    private FirebaseUser firebaseUser;
    private User user;
    Toolbar toolbar;
    TextView txtTitle;


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
