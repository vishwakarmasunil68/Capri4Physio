package com.capri4physio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.activity.ChatActivity;
import com.capri4physio.model.user.UserPOJO;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.ToastClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 26-05-2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private List<UserPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user,tv_email,tv_user_type;
        public LinearLayout ll_user;
        public CircleImageView cv_profile_pic;


        public MyViewHolder(View view) {
            super(view);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_email = (TextView) view.findViewById(R.id.tv_email);
            tv_user_type = (TextView) view.findViewById(R.id.tv_user_type);
            ll_user = (LinearLayout) view.findViewById(R.id.ll_user);
            cv_profile_pic = (CircleImageView) view.findViewById(R.id.cv_profile_pic);

        }
    }


    public UserListAdapter(Activity activity, List<UserPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_users, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_user.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_email.setText(horizontalList.get(position).getEmail());
        holder.tv_user_type.setText(getUserType(horizontalList.get(position).getUserType()));
        try {
            Glide.with(activity)
                    .load(horizontalList.get(position).getProfilePic())
                    .dontAnimate()
                    .into(holder.cv_profile_pic);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("friend_user_id",horizontalList.get(position).getId());
                intent.putExtra("user_id", AppPreferences.getInstance(activity.getApplicationContext()).getUserID());
                activity.startActivity(intent);
                ToastClass.showShortToast(activity.getApplicationContext(),"user id:-"+horizontalList.get(position).getId());
            }
        });
    }

    public String getUserType(String type){
        switch (type){
            case "0":
                return "Patient";
            case "1":
                return "Staff";
            case "2":
                return "Therapist";
            case "3":
                return "Branch Manager";
            case "4":
                return "Admin";
            case "5":
                return "Student";
            default:
                return "";
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
