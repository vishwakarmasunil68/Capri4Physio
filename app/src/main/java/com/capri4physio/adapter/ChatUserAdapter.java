package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.model.chat.ChatPOJO;
import com.capri4physio.util.TagUtils;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.MyViewHolder> {

    private List<ChatPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    private String user_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_chat;
        public TextView tv_time;
        public LinearLayout ll_chat;
        public LinearLayout ll_main;


        public MyViewHolder(View view) {
            super(view);
            tv_chat = (TextView) view.findViewById(R.id.tv_chat);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            ll_chat = (LinearLayout) view.findViewById(R.id.ll_chat);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);

        }
    }


    public ChatUserAdapter(Activity activity, List<ChatPOJO> horizontalList,String user_id) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        this.user_id=user_id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_chats, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_chat.setText(horizontalList.get(position).getChat_msg());
        holder.tv_time.setText(horizontalList.get(position).getChat_time());
        Log.d(TagUtils.getTag(),"chat pojo:-"+horizontalList.get(position).toString());
        if(user_id.equals(horizontalList.get(position).getChat_user_id())){
            holder.ll_main.setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
            holder.ll_main.setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
            holder.ll_chat.setBackgroundResource(R.drawable.chat_send_linear_back);
        }else{
            holder.ll_main.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
            holder.ll_main.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
            holder.ll_chat.setBackgroundResource(R.drawable.chat_receive_linear_back);
        }

    }

    @Override
    public int getItemCount() {
        if (horizontalList != null) {
            return horizontalList.size();
        } else {
            return 0;
        }
    }
}
