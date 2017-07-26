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

public class FragementActi6 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	public static String Scapulalt1,patient_id,Scapulalt2,Scapulalt3,Scapulalt4,Scapulalt5,Scapulalp1,Scapulalp2,Scapulalp3,Scapulalp4,Scapulalp5,Scapulalr1,Scapulalr2,Scapulalr3,Scapulalr4,Scapulalr5,Scapulart1,Scapulart2,Scapulart3,Scapulart4,Scapulart5,Scapularp1,Scapularp2,Scapularp3,
			Scapularp4,Scapularp5,Scapularr1,Scapularr2,Scapularr3,Scapularr4,Scapularr5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main6);


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
		Scapulalt1 = Android6.spinner1.getSelectedItem().toString().trim();
		Scapulalt2 = Android6.spinner2.getSelectedItem().toString().trim();
		Scapulalt3  = Android6.spinner3.getSelectedItem().toString().trim();
		Scapulalt4  = Android6.spinner4.getSelectedItem().toString().trim();
		Scapulalt5=Android6.spinner5.getSelectedItem().toString().trim();
		Scapulalp1 = Android6.spinner8.getSelectedItem().toString().trim();
		Scapulalp2 = Android6.spinner9.getSelectedItem().toString().trim();
		Scapulalp3 = Android6.spinner10.getSelectedItem().toString().trim();
		Scapulalp4 = Android6.spinner11.getSelectedItem().toString().trim();
		Scapulalp5=Android6.spinner12.getSelectedItem().toString().trim();
		Scapulalr1 =ValidateEdit(Android6.editText1);
		Scapulalr2 =ValidateEdit(Android6.editText2);
		Scapulalr3 = ValidateEdit(Android6.editText3);
		Scapulalr4 =ValidateEdit(Android6.editText4);
		Scapulalr5 =ValidateEdit(Android6.editText5);
		Scapulart1=	ValidateSpinner( Windows5.spinner1);
		Scapulart2 = ValidateSpinner( Windows5.spinner2);
		Scapulart3= ValidateSpinner( Windows5.spinner3);
		Scapulart4 = ValidateSpinner( Windows5.spinner4);
		Scapulart5 =ValidateSpinner( Windows5.spinner5);
		Scapularp1 = ValidateSpinner( Windows5.spinner8);
		Scapularp2 = ValidateSpinner( Windows5.spinner9);
		Scapularp3 = ValidateSpinner( Windows5.spinner10);
		Scapularp4 =ValidateSpinner( Windows5.spinner11);
		Scapularp5=ValidateSpinner( Windows5.spinner12);
		Scapularr1=ValidateEdit(Windows6.editText1);
		Scapularr2=ValidateEdit(Windows6.editText2);
		Scapularr3=ValidateEdit(Windows6.editText3);
		Scapularr4=ValidateEdit(Windows6.editText4);
		Scapularr5=ValidateEdit(Windows6.editText5);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_SCAPULA_URL,
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
				params.put("moter_examscapula_left_tone1", Scapulalt1);
				params.put("moter_examscapula_left_tone2", Scapulalt2);
				params.put("moter_examscapula_left_tone3", Scapulalt3);
				params.put("moter_examscapula_left_tone4",Scapulalt4);
				params.put("moter_examscapula_left_tone5",Scapulalt5);
				params.put("moter_examscapula_left_power1",Scapulalp1);
				params.put("moter_examscapula_left_power2", Scapulalp2);
				params.put("moter_examscapula_left_power3", Scapulalp3);
				params.put("moter_examscapula_left_power4", Scapulalp4);
				params.put("moter_examscapula_left_power5", Scapulalp5);
				params.put("moter_examscapula_left_rom1", Scapulalr1);
				params.put("moter_examscapula_left_rom2",Scapulalr2);
				params.put("moter_examscapula_left_rom3",Scapulalr3);
				params.put("moter_examscapula_left_rom4",Scapulalr4);
				params.put("moter_examscapula_left_rom5", Scapulalr5);
				params.put("moter_examscapula_right_tone1,",Scapulart1);
				params.put("moter_examscapula_right_tone2", Scapulart2);
				params.put("moter_examscapula_right_tone3", Scapulart3);
				params.put("moter_examscapula_right_tone4", Scapulart4);
				params.put("moter_examscapula_right_tone5",Scapulart5);
				params.put("moter_examscapula_right_power1", Scapularp1);
				params.put("moter_examscapula_right_power2", Scapularp2);
				params.put("moter_examscapula_right_power3", Scapularp3);
				params.put("moter_examscapula_right_power4", Scapularp4);
				params.put("moter_examscapula_right_power5", Scapularp5);
				params.put("moter_examscapula_right_rom1", Scapularr1);
				params.put("moter_examscapula_right_rom2", Scapularr2);
				params.put("moter_examscapula_right_rom3", Scapularr3);
				params.put("moter_examscapula_right_rom4", Scapularr4);
				params.put("moter_examscapula_right_rom5", Scapularr5);
                /*params.put("sfirst_name",name);
                params.put("slast_name",lastName);
                params.put("sdob",dob);
                params.put("sage", "26");
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
		adapter.addFrag(new Android6(), "Left");
		adapter.addFrag(new Ios6 (), "Parameters");
		adapter.addFrag(new Windows6(), "Right");
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
