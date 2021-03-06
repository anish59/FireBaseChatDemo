package com.firebasechatdemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebasechatdemo.R;
import com.firebasechatdemo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context context;
    private List<User> users;
    private UserClickedListener listener;

    public UsersAdapter(Context context, UserClickedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setUsers(List<User> users) {
        this.users = new ArrayList<>();
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.setData(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layoutItemMain;
        TextView txtLastMsg;
        TextView txtName;
        SimpleDraweeView imgUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLastMsg = (TextView) itemView.findViewById(R.id.txtLastMsg);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgUser = (SimpleDraweeView) itemView.findViewById(R.id.imgUser);
            layoutItemMain = (LinearLayout) itemView.findViewById(R.id.layoutItemMain);
        }

        void setData(final User user) {
            txtName.setText(user.getUserName());
            imgUser.setImageURI(user.getImageUrl());

            layoutItemMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onUserClicked(user);
                }
            });

        }
    }

    public interface UserClickedListener {
        void onUserClicked(User user);
    }
}
