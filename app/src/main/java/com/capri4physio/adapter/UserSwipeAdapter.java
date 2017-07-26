package com.capri4physio.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.capri4physio.R;
import com.capri4physio.model.user.UserPOJO;

import java.util.List;

/**
 * Created by sunil on 24-07-2017.
 */

public class UserSwipeAdapter extends android.support.v4.view.PagerAdapter {
    Activity context;
    int noofpages;
    LayoutInflater layoutInflater;
    List<String> userList;
    List<List<UserPOJO>> userPojosList;


    public UserSwipeAdapter(Activity context, List<String> userList, List<List<UserPOJO>> userPojosList) {
        this.context = context;
        this.noofpages = userList.size();
        this.userList=userList;
        this.userPojosList=userPojosList;
    }

    @Override
    public int getCount() {
        return noofpages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.inflate_users_types, container, false);

        RecyclerView rv_users= (RecyclerView) item_view.findViewById(R.id.rv_users);
        UserListAdapter adapter = new UserListAdapter(context, userPojosList.get(position));
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_users.setLayoutManager(horizontalLayoutManagaer);
        rv_users.setHasFixedSize(true);
        rv_users.setItemAnimator(new DefaultItemAnimator());
        rv_users.setAdapter(adapter);

        container.addView(item_view);
        return item_view;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}