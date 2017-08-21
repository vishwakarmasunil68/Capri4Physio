package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.ShowAllUsersChatActivity;
import com.capri4physio.model.allchatusers.AllUsersResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.MyViewHolder> {

    private List<AllUsersResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_first_user,tv_second_user;
        public CardView cv_users;

        public MyViewHolder(View view) {
            super(view);
            tv_first_user = (TextView) view.findViewById(R.id.tv_first_user);
            tv_second_user = (TextView) view.findViewById(R.id.tv_second_user);
            cv_users = (CardView) view.findViewById(R.id.cv_users);

        }
    }


    public AllUsersAdapter(Activity activity, List<AllUsersResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_all_users, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_first_user.setText(horizontalList.get(position).getUser1first()+" "+horizontalList.get(position).getUser1last());
        holder.tv_second_user.setText(horizontalList.get(position).getUser2first()+" "+horizontalList.get(position).getUser2last());

        holder.cv_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllUsersChatActivity showAllUsersChatActivity= (ShowAllUsersChatActivity) activity;
                showAllUsersChatActivity.OpenChats(horizontalList.get(position));
            }
        });

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
