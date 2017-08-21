package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.UserListAdapter;
import com.capri4physio.model.chat.ChatUsersListPOJO;
import com.capri4physio.model.user.UserPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatUserActivity extends AppCompatActivity implements WebServicesCallBack{
    String user_type="";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_users)
    RecyclerView rv_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            user_type=bundle.getString("user_type");
        }else{
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllUsers();
    }
    private final String GET_ALL_USERS="get_all_users";
    public void getAllUsers(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", AppPreferences.getInstance(this).getUserID()));
        new WebServiceBase(nameValuePairs, this, GET_ALL_USERS).execute(ApiConfig.get_all_users);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_list, menu);//Menu Resource, Menu
        MenuItem menuItem=menu.findItem(R.id.menu_all_users);
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            menuItem.setVisible(true);
        }else{
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_search:
                Intent intent=new Intent(this,SearchUserActivity.class);
                intent.putExtra("user_type",user_type);
                startActivity(intent);
                return true;
            case R.id.menu_all_users:
                startActivity(new Intent(ChatUserActivity.this,ShowAllUsersChatActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_USERS:
                parseAllUsers(response);
                break;
        }
    }

    public void parseAllUsers(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            Gson gson=new Gson();
            ChatUsersListPOJO chatUsersListPOJO=gson.fromJson(response,ChatUsersListPOJO.class);
            if(chatUsersListPOJO.getSuccess().equals("true")){
//                UserListAdapter adapter = new UserListAdapter(this, chatUsersListPOJO.getUserPOJOList());
//                Log.d(TagUtils.getTag(),"users:-"+chatUsersListPOJO.getUserPOJOList().toString());
                inflateUsers(chatUsersListPOJO.getUserPOJOList());

            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Chats Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void inflateUsers(List<UserPOJO> horizontalList){
        UserListAdapter adapter = new UserListAdapter(this, horizontalList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_users.setLayoutManager(horizontalLayoutManagaer);
        rv_users.setHasFixedSize(true);
        rv_users.setItemAnimator(new DefaultItemAnimator());
        rv_users.setAdapter(adapter);
    }


}
