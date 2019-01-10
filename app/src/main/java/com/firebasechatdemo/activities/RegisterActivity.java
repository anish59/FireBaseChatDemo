package com.firebasechatdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebasechatdemo.helper.AppConstants;
import com.firebasechatdemo.helper.FBaseConstants;
import com.firebasechatdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private android.widget.EditText edtUserName;
    private android.widget.EditText edtEmailId;
    private android.widget.EditText edtPassword;
    private android.widget.Button btnRegister;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        this.btnRegister = (Button) findViewById(R.id.btnRegister);
        this.edtPassword = (EditText) findViewById(R.id.edtPassword);
        this.edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        this.edtUserName = (EditText) findViewById(R.id.edtUserName);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtUserName.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Please enter UserId", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtEmailId.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Please enter Email Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                doRegister(edtUserName.getText().toString().trim(), edtEmailId.getText().toString().trim(), edtPassword.getText().toString().trim());
            }
        });
    }

    private void doRegister(final String userName, String email, String pwd) {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference(FBaseConstants.USER).child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(FBaseConstants.ID, userId);
                    hashMap.put(FBaseConstants.USER_NAME, userName);
                    hashMap.put(FBaseConstants.IMAGE_URL, AppConstants.IMG_DEFAULT_NAME);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Unable to Sign UP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Wrong credential may be! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
