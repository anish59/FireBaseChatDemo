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
import com.firebasechatdemo.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Chat> chatList;
    private Context context;
    private static final int VIEW_TYPE_LEFT = 0;
    private static final int VIEW_TYPE_RIGHT = 1;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    public ChatAdapter(Context context) {
        this.context = context;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = new ArrayList<>();
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, viewGroup, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, viewGroup, false);
        }
        return new ChatViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int position) {
        chatViewHolder.setData(chatList.get(position), getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return chatList != null ? chatList.size() : 0;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        //left
        SimpleDraweeView imgMsgLeft;
        TextView txtMsgLeft;
        TextView txtMsgTimeLeft;
        SimpleDraweeView imgUserLeft;

        //right
        LinearLayout layout2;
        TextView txtMsgTimeRight;
        SimpleDraweeView imgUserRight;
        SimpleDraweeView imgMsgRight;
        TextView txtMsgRight;

        public ChatViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_LEFT) {
                imgMsgLeft = (SimpleDraweeView) itemView.findViewById(R.id.imgMsgLeft);
                txtMsgLeft = (TextView) itemView.findViewById(R.id.txtMsgLeft);
                txtMsgTimeLeft = (TextView) itemView.findViewById(R.id.txtMsgTimeLeft);
                imgUserLeft = (SimpleDraweeView) itemView.findViewById(R.id.imgUserLeft);
            } else {
                layout2 = (LinearLayout) itemView.findViewById(R.id.layout2);
                txtMsgTimeRight = (TextView) itemView.findViewById(R.id.txtMsgTimeRight);
                imgUserRight = (SimpleDraweeView) itemView.findViewById(R.id.imgUserRight);
                imgMsgRight = (SimpleDraweeView) itemView.findViewById(R.id.imgMsgRight);
                txtMsgRight = (TextView) itemView.findViewById(R.id.txtMsgRight);

            }


        }

        void setData(Chat data, int viewType) {
            if (viewType == VIEW_TYPE_LEFT) {

                if (data.getMediaType().equals("0")) {
                    imgMsgLeft.setImageURI(data.getMedia());
                }

                txtMsgLeft.setText(data.getMessage());
//                imgUserLeft.setImageURI(data.get);
//                txtMsgTimeLeft.setText();

            } else {

                if (data.getMediaType().equals("0")) {
                    imgMsgRight.setImageURI(data.getMedia());
                }

                txtMsgRight.setText(data.getMessage());

            }
        }
    }


    @Override
    public int getItemViewType(int position) {

        return chatList.get(position).getSenderId().equals(firebaseUser.getUid()) ? VIEW_TYPE_RIGHT : VIEW_TYPE_LEFT;
    }
}
