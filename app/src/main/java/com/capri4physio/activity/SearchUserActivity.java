package com.capri4physio.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.UserSwipeAdapter;
import com.capri4physio.model.search.SearchPOJO;
import com.capri4physio.model.user.UserPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserActivity extends AppCompatActivity implements WebServicesCallBack{

    private final String TAG=getClass().getSimpleName();
    private final static String SEARCH_API_CALL="search_api_call";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.ll_users_ada)
    LinearLayout ll_users_ada;

    @BindView(R.id.usersPager)
    ViewPager usersPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    String user_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
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
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_msg.setText("");
            }
        });
        loadEmptyAdapter();
        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_msg.getText().toString().length()>0){
                    callSearchAPI(et_msg.getText().toString());
                    ll_users_ada.setVisibility(View.VISIBLE);
                }else{
                    loadEmptyAdapter();
                }
            }
        });
    }

    public void loadEmptyAdapter(){
        ll_users_ada.setVisibility(View.GONE);
    }

    public void callSearchAPI(String user_name){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_name", user_name));
        nameValuePairs.add(new BasicNameValuePair("user_type", AppPreferences.getInstance(SearchUserActivity.this).getUserType()));
        new WebServiceBase(nameValuePairs, this, SEARCH_API_CALL,false).execute(ApiConfig.search_user_api);
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
            case SEARCH_API_CALL:
                parseSearchResponse(response);
                break;
        }
    }

    public void parseSearchResponse(String response){
        try{
            Gson gson=new Gson();
            SearchPOJO searchPOJO=gson.fromJson(response,SearchPOJO.class);
            if(searchPOJO.getSuccess().equals("true")){
                List<UserPOJO> userPOJOList=searchPOJO.getUserPOJOList();
                inflateUsers(userPOJOList);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No User Found");
                loadEmptyAdapter();
            }
        }catch (Exception e){
            e.printStackTrace();
            loadEmptyAdapter();
        }
    }
    List<String> user_types;
    public void inflateUsers(List<UserPOJO> userPOJOList){
        try {
            if (userPOJOList.size() > 0) {
                String user_id=AppPreferences.getInstance(this).getUserType();

                LinkedHashMap<String,List<UserPOJO>> user_map=new LinkedHashMap<>();

                user_types=new ArrayList<>();
                user_types.add("0");
                user_types.add("1");
                user_types.add("2");
                user_types.add("3");
                user_types.add("4");
                user_types.add("5");

//                List<String> userStringTypes=new ArrayList<>();
//                userStringTypes.add("")

//                int index=user_types.indexOf(user_id);
                user_types.remove(user_id);

                Log.d(TagUtils.getTag(),"user types:-"+user_types.toString());
                for(String s:user_types){
                    List<UserPOJO> userPOJOList1=new ArrayList<>();
                    for(UserPOJO userPOJO:userPOJOList){
                        if(userPOJO.getUserType().equals(s)){
                            userPOJOList1.add(userPOJO);
                        }
                    }

                    user_map.put(s,userPOJOList1);
                }

                if(user_map.size()>0){
                    setViewpager(user_map);
                }

                Log.d(TagUtils.getTag(),"final user pojos:-"+user_map.toString());
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void setViewpager(LinkedHashMap<String,List<UserPOJO>> user_map){
        Set<String> keyset=user_map.keySet();
        List<String> usersList=new ArrayList<>(keyset);
        List<List<UserPOJO>> userPOJOList=new ArrayList<>();
        for(String s:usersList){
            List<UserPOJO> userPOJOs=user_map.get(s);
            userPOJOList.add(userPOJOs);
        }
        Log.d(TagUtils.getTag(),"final users:-"+userPOJOList.toString());
        UserSwipeAdapter userSwipeAdapter=new UserSwipeAdapter(this,usersList,userPOJOList);
        usersPager.setAdapter(userSwipeAdapter);
        tabLayout.setupWithViewPager(usersPager);
        try {
            for (int i = 0; i < usersList.size(); i++) {
                tabLayout.getTabAt(i).setText(getUserType(usersList.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
