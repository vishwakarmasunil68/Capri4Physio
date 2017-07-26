package com.capri4physio.fragment.assessment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.ImageUtil;
import com.capri4physio.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	ProgressDialog pDialog;
	String patient_id;
	Android frag_left;
	Windows frag_right;
	Ios frag_parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main);

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
        /*TabAdapter = new TabPagerAdapter1(getSupportFragmentManager());

        Tab = (ViewPager)findViewById(R.id.pager);*/

		savebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(),"button",Toast.LENGTH_LONG);
				captureBitmap();
//				addMotorAPi();
			}
		});
		patient_id =getIntent().getStringExtra("patient_id");
		viewPager.setOffscreenPageLimit(2);
    }
	public String ValidateEdit(EditText edit){
		if(edit.getText().toString().equals(null)||edit.getText().toString().equals("")){
			return "";
		}
		else{
			return edit.getText().toString().trim();
		}
	}

	public void captureBitmap(){
		Bitmap left_bitmap=frag_left.takeScreenShots();
//		Bitmap parameter_bitmap=frag_parameters.takeScreenShots();
		Bitmap right_bitmap=frag_right.takeScreenShots();


//		SaveBitmap(left_bitmap);
//		SaveBitmap(parameter_bitmap);
//		SaveBitmap(right_bitmap);
		ShowDialog(left_bitmap,right_bitmap);


		Toast.makeText(getApplicationContext(),"bitmap saved",Toast.LENGTH_SHORT).show();
	}

	public void ShowDialog(Bitmap lef,Bitmap right){
		final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

		//setting custom layout to dialog
		dialog.setContentView(R.layout.custom_fragment_acti_dialog);
		dialog.setTitle("Save");

		//adding text dynamically
		final ImageView iv_right = (ImageView) dialog.findViewById(R.id.iv_right);
		final ImageView iv_parameter = (ImageView) dialog.findViewById(R.id.iv_parameter);
		final ImageView iv_left = (ImageView) dialog.findViewById(R.id.iv_left);
		final ScrollView scroll_dialog = (ScrollView) dialog.findViewById(R.id.scroll_dialog);

		iv_left.setImageBitmap(lef);
//		iv_parameter.setImageBitmap(parameter);
		iv_right.setImageBitmap(right);


		//adding button click event
		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
		Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				SaveBitmap(takeScreenShots(scroll_dialog));
				String main_base64= ImageUtil.encodeTobase64(takeScreenShots(scroll_dialog));
				Toast.makeText(getApplicationContext(),"saved image",Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				initProgressDialog("Please wait..");
				addMotorAPi(main_base64);
			}
		});
		dialog.show();
	}
	public Bitmap takeScreenShots(ScrollView scrollView) {
		int h = 0;
		Bitmap bitmap = null;
		//get the actual height of scrollview
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			scrollView.getChildAt(i).setBackgroundResource(R.color.white);
		}
		// create bitmap with target size
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		return bitmap;
	}


	private void addMotorAPi(final String base_string){
		final String hlt1 = Android.spinner1.getSelectedItem().toString().trim();
		final String hlt2 = Android.spinner2.getSelectedItem().toString().trim();
		final String hlt3 = Android.spinner3.getSelectedItem().toString().trim();
		final String hlt4 = Android.spinner4.getSelectedItem().toString().trim();
		final String hlt5 = Android.spinner5.getSelectedItem().toString().trim();
		final String hlt6 = Android.spinner6.getSelectedItem().toString().trim();
		final String hlt7 = Android.spinner7.getSelectedItem().toString().trim();
		final String hlp1 = Android.spinner8.getSelectedItem().toString().trim();
		final String hlp2 = Android.spinner9.getSelectedItem().toString().trim();
		final String hlp3 = Android.spinner10.getSelectedItem().toString().trim();
		final String hlp4 = Android.spinner11.getSelectedItem().toString().trim();
		final String hlp5 = Android.spinner12.getSelectedItem().toString().trim();
		final String hlp6 = Android.spinner13.getSelectedItem().toString().trim();
		final String hlr1 = ValidateEdit(Android.editText1);
		final String hlr2 = ValidateEdit (Android.editText2);
		final String hlr3 = ValidateEdit(Android.editText3);
		final String hlr4 = ValidateEdit (Android.editText4);
		final String hlr5 = ValidateEdit (Android.editText5);
		final String hlr6 = ValidateEdit (Android.editText6);
		final String hrt1 = Windows.spinner1.getSelectedItem().toString().trim();
		final String hrt2 = Windows.spinner2.getSelectedItem().toString().trim();
		final String hrt3 = Windows.spinner3.getSelectedItem().toString().trim();
		final String hrt4 = Windows.spinner4.getSelectedItem().toString().trim();
		final String hrt5 = Windows.spinner5.getSelectedItem().toString().trim();
		final String hrt6 = Windows.spinner6.getSelectedItem().toString().trim();
		final String hrt7 = Windows.spinner7.getSelectedItem().toString().trim();
		final String hrp1 = Windows.spinner8.getSelectedItem().toString().trim();
		final String hrp2 = Windows.spinner9.getSelectedItem().toString().trim();
		final String hrp3 = Windows.spinner10.getSelectedItem().toString().trim();
		final String hrp4 = Android.spinner11.getSelectedItem().toString().trim();
		final String hrp5 = Windows.spinner12.getSelectedItem().toString().trim();
		final String hrp6 = Windows.spinner13.getSelectedItem().toString().trim();
		final String hrr1 = ValidateEdit(Windows.editText1);
		final String hrr2 = ValidateEdit(Windows.editText2);
		final String hrr3 = ValidateEdit(Windows.editText3);
		final String hrr4 = ValidateEdit(Windows.editText4);
		final String hrr5 = ValidateEdit(Windows.editText5);
		final String hrr6 = ValidateEdit(Windows.editText6);
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_HEAD_NECK_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							try{
								pDialog.dismiss();
							}
							catch (Exception e){
								e.toString();
							}
							Log.d("result",response);
							Toast.makeText(FragementActi.this,"successfully added", Toast.LENGTH_LONG).show();
							finish();

						} catch (Exception e) {
							e.printStackTrace();
						}
						Log.e("Postdat", "" + response.toString());
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.w("Postdat", "" + error);
					}
				}){


			protected Map<String,String> getParams(){
				Map<String,String> params = new HashMap<String, String>();
				params.put("moter_exam_date", Utils.getCurrentDate());
				params.put("patient_id", patient_id);
				params.put("hlt1", hlt1);
				params.put("hlt2", hlt2);
				params.put("hlt3", hlt3);
                params.put("hlt4",hlt4);
                params.put("hlt5",hlt5);
                params.put("hlt6",hlt6);
                params.put("hlt7", hlt7);
                params.put("hlp1",hlp1);
                params.put("hlp2", hlp2);
                params.put("hlp3", hlp3);
                params.put("hlp4", hlp4);
                params.put("hlp5",hlp5);
                params.put("hlp6", hlp6);
                params.put("hlr1", hlr1);
                params.put("hlr2", hlr2);
                params.put("hlr3", hlr3);
                params.put("hlr4", hlr4);
                params.put("hlr5", hlr5);
                params.put("hlr6", hlr6);
				params.put("hrtone1", hrt1);
				params.put("hrtone2", hrt2);
				params.put("hrtone3", hrt3);
				params.put("hrtone4",hrt4);
				params.put("hrtone5",hrt5);
				params.put("hrtone6",hrt6);
				params.put("hrtone7", hrt7);
				params.put("hrpower1",hrp1);
				params.put("hrpower2", hrp2);
				params.put("hrpower3", hrp3);
				params.put("hrpower1", hrp4);
				params.put("hrpower5",hrp5);
				params.put("hrpower6", hrp6);
				params.put("hrroom1", hrr1);
				params.put("hrroom2", hrr2);
				params.put("hrroom3", hrr3);
				params.put("hrroom4", hrr4);
				params.put("hrroom5", hrr5);
				params.put("hrroom6", hrr6);
				params.put("moterhltrp_image", base_string);
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
				return params;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private void setupViewPager(ViewPager viewPager) {
		frag_left=new Android();
		frag_right=new Windows();
//		frag_parameters=new Ios();
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFrag(frag_left, "Left");
//		adapter.addFrag(frag_parameters, "Movement");
		adapter.addFrag(frag_right, "Right");
		viewPager.setAdapter(adapter);
	}


	private void SaveBitmap(Bitmap bitmap) {
		FileOutputStream out = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio");
			if (!file.exists()) {
				file.mkdirs();
			}
			out = new FileOutputStream(file.toString() + File.separator + System.currentTimeMillis() + ".png");
//        Toast.makeText(c,"")
		} catch (Exception e) {
			Log.e("fileerror1", e.toString());
			e.printStackTrace();
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				Toast.makeText(getApplicationContext(), "File saved to gallery", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("fileerror4", e.toString());
			Toast.makeText(getApplicationContext(), "File not saved to gallery", Toast.LENGTH_SHORT).show();
			// TODO: handle exception
		}
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


	private void initProgressDialog(String loading) {
		pDialog = new ProgressDialog(FragementActi.this);
		pDialog.setMessage(loading);
		pDialog.setCancelable(false);
		pDialog.show();
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
