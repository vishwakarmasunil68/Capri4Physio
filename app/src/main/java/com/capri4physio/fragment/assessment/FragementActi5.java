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
import android.widget.Spinner;
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

public class FragementActi5 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	public static String Halluxlt1,patient_id,Halluxlt2,Halluxlt3,Halluxlt4,Halluxlt5,Halluxlp1,Halluxlp2,Halluxlp3,Halluxlp4,Halluxlp5,Halluxlr1,Halluxlr2,Halluxlr3,Halluxlr4,Halluxlr5,Halluxrt1,Halluxrt2,Halluxrt3,Halluxrt4,Halluxrt5,Halluxrp1,Halluxrp2,Halluxrp3,
	Halluxrp4,Halluxrp5,Halluxrr1,Halluxrr2,Halluxrr3,Halluxrr4,Halluxrr5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main5);

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
		try {
			if (edit.equals(null)) {
				return "";
			} else {
				if (edit.getText().toString().equals(null) || edit.getText().toString().equals("")) {
					return "";
				} else {
					return edit.getText().toString().trim();
				}

			}
		}
		catch (Exception e){
			return "";
		}
	}
	public String ValidateSpinner(Spinner edit){
		try {
			if (edit.equals(null)) {
				return "";
			} else {
				if (edit.getSelectedItem().toString().equals(null) || edit.getSelectedItem().toString().equals("")) {
					return "";
				} else {
					return edit.getSelectedItem().toString().trim();
				}

			}
		}
		catch (Exception e){
			return "";
		}
	}
	private void addMotorAPi(){
		Halluxlt1 = Android5.spinner1.getSelectedItem().toString().trim();
		Halluxlt2 = Android5.spinner2.getSelectedItem().toString().trim();
		Halluxlt3  = Android5.spinner3.getSelectedItem().toString().trim();
		Halluxlp1 = Android5.spinner8.getSelectedItem().toString().trim();
		Halluxlp2 = Android5.spinner9.getSelectedItem().toString().trim();
		Halluxlp3 = Android5.spinner10.getSelectedItem().toString().trim();
//		Halluxlr1 = Android5.editText1.getText().toString().trim();
		Halluxlr1=ValidateEdit( Android5.editText1);
		Halluxlr2 =ValidateEdit( Android5.editText2);
		Halluxlr3 =ValidateEdit( Android5.editText3);
		Halluxrt1=	ValidateSpinner( Windows5.spinner1);
		Halluxrt2 = ValidateSpinner( Windows5.spinner2);
		Halluxrt3= ValidateSpinner( Windows5.spinner3);
		Halluxrp1 = ValidateSpinner( Windows5.spinner8);
		Halluxrp2 = ValidateSpinner( Windows5.spinner9);
		Halluxrp3 = ValidateSpinner( Windows5.spinner10);
		Halluxrr1= ValidateEdit( Windows5.editText1);
		Halluxrr2= ValidateEdit( Windows5.editText2);
		Halluxrr3=ValidateEdit( Windows5.editText3);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_HALLUX_URL,
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
				params.put("moter_examfore_left_tone1", Halluxlt1);
				params.put("moter_examfore_left_tone2", Halluxlt2);
				params.put("moter_examfore_left_tone3", Halluxlt3);
				params.put("moter_examfore_left_power1",Halluxlp1);
				params.put("moter_examfore_left_power2", Halluxlp2);
				params.put("moter_examfore_left_power3", Halluxlp3);
				params.put("moter_examfore_left_rom1", Halluxlr1);
				params.put("moter_examfore_left_rom2",Halluxlr2);
				params.put("moter_examfore_left_rom3",Halluxlr3);
				params.put("moter_examfore_right_tone1",Halluxrt1);
				params.put("moter_examfore_right_tone2", Halluxrt2);
				params.put("moter_examfore_right_tone3", Halluxrt3);
				params.put("moter_examfore_right_power1", Halluxrp1);
				params.put("moter_examfore_right_power2", Halluxrp2);
				params.put("moter_examfore_right_power3", Halluxrp3);
				params.put("moter_examfore_right_rom1", Halluxrr1);
				params.put("moter_examfore_right_rom2", Halluxrr2);
				params.put("moter_examfore_right_rom3", Halluxrr3);
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
		adapter.addFrag(new Android5(), "Left");
		adapter.addFrag(new Ios5 (), "Parameters");
		adapter.addFrag(new Windows5(), "Right");
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
