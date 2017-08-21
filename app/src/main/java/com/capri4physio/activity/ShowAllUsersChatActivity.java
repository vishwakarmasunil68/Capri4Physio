package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.AllUsersAdapter;
import com.capri4physio.model.allchatusers.AllChatPOJO;
import com.capri4physio.model.allchatusers.AllUsersPOJO;
import com.capri4physio.model.allchatusers.AllUsersResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAllUsersChatActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_ALL_USERS = "get_all_users";
    private static final String GET_ALL_CHATS = "get_all_users_chats";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_users)
    RecyclerView rv_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users_chat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Chats");

        new GetWebServices(this,GET_ALL_USERS).execute(ApiConfig.all_chat_users);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_USERS:
                parseGetAllUsers(response);
                break;
            case GET_ALL_CHATS:
                parseGetAllChats(response);
                break;
        }
    }

    public void parseGetAllChats(String response){
        Log.d(TagUtils.getTag(),"all chats:-"+response);
        try{
            Gson gson=new Gson();
            AllChatPOJO allChatPOJO=gson.fromJson(response,AllChatPOJO.class);
            if(allChatPOJO.getSuccess().equals("true")){
                Intent intent=new Intent(this, ChatActivity.class);
                intent.putExtra("user_id",allUsersResultPOJO.getValue1());
                intent.putExtra("friend_user_id",allUsersResultPOJO.getValue2());
                intent.putExtra("allchatpojo",allChatPOJO);
                startActivity(intent);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Chats Found");
            }

        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
    AllUsersResultPOJO allUsersResultPOJO;
    public void OpenChats(AllUsersResultPOJO allUsersResultPOJO){
        this.allUsersResultPOJO=allUsersResultPOJO;
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("user_id",allUsersResultPOJO.getValue1()));
        nameValuePairArrayList.add(new BasicNameValuePair("fri_id",allUsersResultPOJO.getValue2()));
        new WebServiceBase(nameValuePairArrayList,this,GET_ALL_CHATS).execute(ApiConfig.get_all_users_chats);


    }

    public void parseGetAllUsers(String response){
        Log.d(TagUtils.getTag(),"users response:-"+response);
        try{
            Gson gson=new Gson();
            AllUsersPOJO allUsersPOJO=gson.fromJson(response,AllUsersPOJO.class);
            if(allUsersPOJO.getSuccess().equals("true")){
                AllUsersAdapter adapter = new AllUsersAdapter(this, allUsersPOJO.getAllUsersResultPOJOs());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rv_users.setLayoutManager(horizontalLayoutManagaer);
                rv_users.setHasFixedSize(true);
                rv_users.setItemAnimator(new DefaultItemAnimator());
                rv_users.setAdapter(adapter);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Chats Found");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
}
