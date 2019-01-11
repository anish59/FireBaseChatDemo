package com.firebasechatdemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebasechatdemo.R;
import com.firebasechatdemo.adapters.UsersAdapter;
import com.firebasechatdemo.helper.FBaseConstants;
import com.firebasechatdemo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private List<User> userList;
    private UsersAdapter usersAdapter;
    private android.support.v7.widget.RecyclerView rvUsers;

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        this.rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        userList = new ArrayList<>();

        usersAdapter = new UsersAdapter(getContext());
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(usersAdapter);

        init();
        return view;
    }

    private void init() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference(FBaseConstants.USERS);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null && firebaseUser != null) {
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            userList.add(user);
                        }
                    }
                }
                usersAdapter.setUsers(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Err: " + databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
