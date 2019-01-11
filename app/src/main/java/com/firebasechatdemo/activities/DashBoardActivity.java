package com.firebasechatdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebasechatdemo.R;
import com.firebasechatdemo.fragments.ChatsFragment;
import com.firebasechatdemo.fragments.UsersFragment;
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

public class DashBoardActivity extends AppCompatActivity {


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private Toolbar toolbar;
    private android.support.design.widget.TabLayout tabs;
    private android.support.v4.view.ViewPager viewpager;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.tabs = (TabLayout) findViewById(R.id.tabs);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        initToolbar();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child(FBaseConstants.USERS).child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                toolbar.setTitle("Hi " + user.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DashBoardActivity.this, "Err " + databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        setupViewPager(viewpager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewpager);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
//        toolbar.setNavigationOnClickListener(v -> closeScreen());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashBoardActivity.this, MainActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatsFragment(), "Chats");
        adapter.addFragment(new UsersFragment(), "Users");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
