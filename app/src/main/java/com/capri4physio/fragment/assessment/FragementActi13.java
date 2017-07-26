package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi13 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	EditText ed1,ed2,ed3,ed4,ed5,ed6;
    TabPagerAdapter13 TabAdapter;
	public static String respiration_Apex1,patient_id,respiration_Apex2,respiration_Apex3,respiration_Base1,respiration_Base2,respiration_Base3;
    Button savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main13);
		/*ed1=(EditText)findViewById(R.id.edtxt_blood_presure);
		ed2=(EditText)findViewById(R.id.edtxt_temp);
		ed3=(EditText)findViewById(R.id.Side_Flexion_Left);
		ed4=(EditText)findViewById(R.id.Side_Flexion_Right);
		ed5=(EditText)findViewById(R.id.RotationLeft);
		ed6=(EditText)findViewById(R.id.RotationRight);*/
        TabAdapter = new TabPagerAdapter13(getSupportFragmentManager());
		savebtn=(Button)findViewById(R.id.savebtn);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//		toolbar.setTitle(s);
		actionBar=getSupportActionBar();
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
		savebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"button",Toast.LENGTH_LONG);
				addMotorAPi();
			}
		});
		patient_id =getIntent().getStringExtra("patient_id");


    }
	public String ValidateEdit(EditText edit){
		if(edit.getText().toString().equals(null)||edit.getText().toString().equals("")){
			return "";
		}
		else{
			return edit.getText().toString().trim();
		}
	}
	private void addMotorAPi(){
		respiration_Apex1 =ValidateEdit(Android13.editText1);
		respiration_Apex2 =ValidateEdit(Android13.editText1);
		respiration_Apex3 = ValidateEdit(Android13.editText1);
		respiration_Base1 =ValidateEdit(Android13.editText1);
		respiration_Base2=ValidateEdit(Android13.editText1);
		respiration_Base3=ValidateEdit(Android13.editText1);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_RESPIRATION_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.e("result",response);
//                            pDialog.dismiss();
							Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
							finish();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*/
						} catch (Exception e) {
							e.printStackTrace();
						}
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
						Log.e("Postdat", "" + response.toString());
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
						Log.w("Postdat", "" + error);
					}
				}){


			protected Map<String,String> getParams(){
				Map<String,String> objresponse = new HashMap<String, String>();
				objresponse.put("moter_exam_date", Utils.getCurrentDate());
				objresponse.put("patient_id", patient_id);
				objresponse.put("resromapex1", respiration_Apex1);
				objresponse.put("resromapex2", respiration_Apex2);
				objresponse.put("resromapex3", respiration_Apex3);
				objresponse.put("resrombase1",respiration_Base1);
				objresponse.put("resrombase2",respiration_Base2);
				objresponse.put("resrombase3",respiration_Base3);
                /*params.put("sfirst_name",name);
                params.put("slast_name",lastName);
                params.put("sdob",dob);
                params.put("sage", "23");
                params.put("sdatejoing",doj);
                params.put("senddate", endingdateofcontract);
                params.put("sgender", rate);
                params.put("smarital_status", rate1);
                params.put("sdesignation",designation);
                params.put("saddress", address);
                params.put("scity", city);
                params.put("spincode", pin_code);
                params.put("smobile", phone);
                params.put("semail", email_id);
                params.put("squalifation", degree);
                params.put("sexprience", experienceduration);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
				return objresponse;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}
	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFrag(new Android13(), "ROM");
		adapter.addFrag(new Ios13(), "Parameters");
		viewPager.setAdapter(adapter);
	}


	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFrag(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == android.R.id.home) {
			Log.e("clik", "action bar clicked");
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

    
}
