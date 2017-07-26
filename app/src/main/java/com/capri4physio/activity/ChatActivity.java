package com.capri4physio.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.ChatUserAdapter;
import com.capri4physio.database.DatabaseHelper;
import com.capri4physio.model.chat.ChatPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.StringUtils;
import com.capri4physio.util.TagUtils;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements WebServicesCallBack{
    String friend_user_id;
    String user_id;

    @BindView(R.id.et_chat)
    EditText et_chat;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    DatabaseHelper databaseHelper;

    private final static String CHAT_API="chat_api";
    List<ChatPOJO> chatPOJOList=new ArrayList<>();
    ChatUserAdapter chatUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        databaseHelper=new DatabaseHelper(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            friend_user_id=bundle.getString("friend_user_id");
            user_id= AppPreferences.getInstance(getApplicationContext()).getUserID();
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_chat.getText().toString().length()>0){
                    sendMessage(et_chat.getText().toString());
                    et_chat.setText("");
                }else{
                }
            }
        });
        inflateChats();
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
    public void inflateChats(){

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        horizontalLayoutManagaer.setStackFromEnd(true);
        chatPOJOList=databaseHelper.getUserChatList(user_id,friend_user_id);
        Log.d(TagUtils.getTag(),"user chats:-"+chatPOJOList.size());
        chatUserAdapter=new ChatUserAdapter(this,chatPOJOList,user_id);
        rv_chat.setLayoutManager(horizontalLayoutManagaer);
        rv_chat.setHasFixedSize(true);
        rv_chat.setItemAnimator(new DefaultItemAnimator());
        rv_chat.setAdapter(chatUserAdapter);
    }


    public void sendMessage(String message){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("chat_user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("chat_fri_id", friend_user_id));
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyy hh:mm a");
        Date d=new Date();
        String[] dates=sdf.format(d).split(" ");
        String date=dates[0];
        String time=dates[1]+" "+dates[2];
        nameValuePairs.add(new BasicNameValuePair("chat_date", date));
        nameValuePairs.add(new BasicNameValuePair("chat_time", time));
        nameValuePairs.add(new BasicNameValuePair("chat_msg", message));
        ChatPOJO chatPOJO=new ChatPOJO("",user_id,friend_user_id,date,time,message);
        databaseHelper.insertchatdata(chatPOJO);
        chatPOJOList.add(chatPOJO);
        chatUserAdapter.notifyDataSetChanged();
        rv_chat.scrollToPosition(chatPOJOList.size() - 1);
        new WebServiceBase(nameValuePairs, this, CHAT_API,false).execute(ApiConfig.chat_api);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.CHAT_CLASS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TagUtils.getTag(), "chatresult:-" + result);
            try {
                Gson gson = new Gson();
                ChatPOJO chatResultPOJO = gson.fromJson(result, ChatPOJO.class);
                if(chatResultPOJO.getChat_fri_id().equals(friend_user_id)||chatResultPOJO.getChat_user_id().equals(friend_user_id)) {
                    chatPOJOList.add(chatResultPOJO);
                    chatUserAdapter.notifyDataSetChanged();
                    rv_chat.scrollToPosition(chatPOJOList.size() - 1);
                    Log.d(TagUtils.getTag(), "user chats:-" + databaseHelper.getUserChatList(user_id, friend_user_id).size());
                }
            } catch (Exception e) {
                Log.d(TagUtils.getTag(), e.toString());
            }
        }
    };

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case CHAT_API:

                break;
        }
    }
}
