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

public class FragementActi12 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	public static String Thumblt1,patient_id,Thumblt2,Thumblt3,Thumblt4,Thumblt5,Thumblt6,Thumblt7,Thumblp1,Thumblp2,Thumblp3,Thumblp4,Thumblp5,Thumblp6,Thumblp7,Thumblr1,Thumblr2,Thumblr3,Thumblr4,Thumblr5,Thumblr6,Thumblr7,Thumbrt1,Thumbrt2,Thumbrt3,Thumbrt4,Thumbrt5,Thumbrt6,Thumbrt7,Thumbrp1,Thumbrp2,Thumbrp3,
			Thumbrp4,Thumbrp5,Thumbrp6,Thumbrp7,Thumbrr1,Thumbrr2,Thumbrr3,Thumbrr4,Thumbrr5,Thumbrr6,Thumbrr7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main12);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//		toolbar.setTitle(s);
		actionBar=getSupportActionBar();
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
		savebtn=(Button)findViewById(R.id.savebtn);
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
		Thumblt1 = Android12.spinner1.getSelectedItem().toString().trim();
		Thumblt2 = Android12.spinner2.getSelectedItem().toString().trim();
		Thumblt3  = Android12.spinner3.getSelectedItem().toString().trim();
		Thumblt4  = Android12.spinner4.getSelectedItem().toString().trim();
		Thumblt5=Android12.spinner5.getSelectedItem().toString().trim();
		Thumblt6=Android12.spinner6.getSelectedItem().toString().trim();
		Thumblt7=Android12.spinner7.getSelectedItem().toString().trim();
		Thumblp1 = Android12.spinner8.getSelectedItem().toString().trim();
		Thumblp2 = Android12.spinner9.getSelectedItem().toString().trim();
		Thumblp3 = Android12.spinner10.getSelectedItem().toString().trim();
		Thumblp4 = Android12.spinner11.getSelectedItem().toString().trim();
		Thumblp5=Android12.spinner12.getSelectedItem().toString().trim();
		Thumblp6=Android12.spinner13.getSelectedItem().toString().trim();
		Thumblp7=Android12.spinner14.getSelectedItem().toString().trim();
		Thumblr1 =ValidateEdit(Android12.editText1);
		Thumblr2 =ValidateEdit(Android12.editText2);
		Thumblr3 =ValidateEdit(Android12.editText3);
		Thumblr4 =ValidateEdit(Android12.editText4);
		Thumblr5 =ValidateEdit(Android12.editText5);
		Thumblr6 =ValidateEdit(Android12.editText6);
		Thumblr7 =ValidateEdit(Android12.editText7);
		Thumbrt1=	Windows12.spinner1.getSelectedItem().toString().trim();
		Thumbrt2 = Windows12.spinner2.getSelectedItem().toString().trim();
		Thumbrt3= Windows12.spinner3.getSelectedItem().toString().trim();
		Thumbrt4 = Windows12.spinner4.getSelectedItem().toString().trim();
		Thumbrt5 =Windows12.spinner5.getSelectedItem().toString().trim();
		Thumbrt6 =Windows12.spinner7.getSelectedItem().toString().trim();
		Thumbrt7 =Windows12.spinner6.getSelectedItem().toString().trim();
		Thumbrp1 = Windows12.spinner8.getSelectedItem().toString().trim();
		Thumbrp2 = Windows12.spinner9.getSelectedItem().toString().trim();
		Thumbrp3 = Windows12.spinner10.getSelectedItem().toString().trim();
		Thumbrp4 =Windows12.spinner11.getSelectedItem().toString().trim();
		Thumbrp5=Windows12.spinner12.getSelectedItem().toString().trim();
		Thumbrp6=Windows12.spinner13.getSelectedItem().toString().trim();
		Thumbrp7=Windows12.spinner14.getSelectedItem().toString().trim();
		Thumbrr1=ValidateEdit(Windows12.editText1);
		Thumbrr2=ValidateEdit(Windows12.editText2);
		Thumbrr3=ValidateEdit(Windows12.editText3);
		Thumbrr4=ValidateEdit(Windows12.editText4);
		Thumbrr5=ValidateEdit(Windows12.editText5);
		Thumbrr6=ValidateEdit(Windows12.editText6);
		Thumbrr7=ValidateEdit(Windows12.editText7);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_THUMB_URL,
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
				Map<String,String> params = new HashMap<String, String>();
				params.put("moter_exam_date", Utils.getCurrentDate());
				params.put("patient_id", patient_id);
				params.put("moter_examthumb_left_tone1", Thumblt1);
				params.put("moter_examthumb_left_tone2", Thumblt2);
				params.put("moter_examthumb_left_tone3", Thumblt3);
				params.put("moter_examthumb_left_tone4",Thumblt4);
				params.put("moter_examthumb_left_tone5",Thumblt5);
				params.put("moter_examthumb_left_tone6",Thumblt6);
				params.put("moter_examthumb_left_tone7",Thumblt7);
				params.put("moter_examthumb_left_power1",Thumblp1);
				params.put("moter_examthumb_left_power2", Thumblp2);
				params.put("moter_examthumb_left_power3", Thumblp3);
				params.put("moter_examthumb_left_power4", Thumblp4);
				params.put("moter_examthumb_left_power5", Thumblp5);
				params.put("moter_examthumb_left_power6", Thumblp6);
				params.put("moter_examthumb_left_power7", Thumblp7);
				params.put("moter_examthumb_left_rom1", Thumblr1);
				params.put("moter_examthumb_left_rom2",Thumblr2);
				params.put("moter_examthumb_left_rom3",Thumblr3);
				params.put("moter_examthumb_left_rom4",Thumblr4);
				params.put("moter_examthumb_left_rom5", Thumblr5);
				params.put("moter_examthumb_left_rom6",Thumblr6);
				params.put("moter_examthumb_left_rom7", Thumblr7);
				params.put("moter_examthumb_right_tone1",Thumbrt1);
				params.put("moter_examthumb_right_tone2", Thumbrt2);
				params.put("moter_examthumb_right_tone3", Thumbrt3);
				params.put("moter_examthumb_right_tone4", Thumbrt4);
				params.put("moter_examthumb_right_tone5",Thumbrt5);
				params.put("moter_examthumb_right_tone6", Thumbrt6);
				params.put("moter_examthumb_right_tone7",Thumbrt7);
				params.put("moter_examthumb_right_power1", Thumbrp1);
				params.put("moter_examthumb_right_power2", Thumbrp2);
				params.put("moter_examthumb_right_power3", Thumbrp3);
				params.put("moter_examthumb_right_power4", Thumbrp4);
				params.put("moter_examthumb_right_power5", Thumbrp5);
				params.put("moter_examthumb_right_power6", Thumbrp6);
				params.put("moter_examthumb_right_power7", Thumbrp7);
				params.put("moter_examthumb_right_rom1", Thumbrr1);
				params.put("moter_examthumb_right_rom2", Thumbrr2);
				params.put("moter_examthumb_right_rom3", Thumbrr3);
				params.put("moter_examthumb_right_rom4", Thumbrr4);
				params.put("moter_examthumb_right_rom5", Thumbrr5);
				params.put("moter_examthumb_right_rom6", Thumbrr6);
				params.put("moter_examthumb_right_rom7", Thumbrr7);
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
				return params;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFrag(new Android12(), "Left");
		adapter.addFrag(new Ios12(), "Parameters");
		adapter.addFrag(new Windows12(), "Right");
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
