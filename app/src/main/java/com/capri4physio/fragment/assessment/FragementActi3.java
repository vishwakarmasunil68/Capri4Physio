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
import com.capri4physio.util.ImageUtil;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi3 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	ProgressDialog pDialog;
	AnkleFragment frag_left;
	AnkleFragment frag_right;
	Ios frag_parameters;
	public static String patient_id,Anklelt1,Anklelt2,Anklelt3,Anklelt4,Anklelt5,Anklelp1,Anklelp2,Anklelp3,Anklelp4,Anklelp5,Anklelr1,Anklelr2,Anklelr3,Anklelr4,Anklelr5,Anklert1,Anklert2,Anklert3,Anklert4,Anklert5,Anklerp1,Anklerp2,Anklerp3,
			Anklerp4,Anklerp5,Anklerr1,Anklerr2,Anklerr3,Anklerr4,Anklerr5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main3);

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
//				addMotorAPi();
				captureBitmap();
			}
		});

		patient_id =getIntent().getStringExtra("patient_id");
		viewPager.setOffscreenPageLimit(2);
		getSupportActionBar().setTitle("Ankle Exam");
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
	private void addMotorAPi(final String base_string){
//		Anklelt1 = Android.spinner1.getSelectedItem().toString().trim();
//		Anklelt2 = Android.spinner2.getSelectedItem().toString().trim();
//		Anklelt3  = Android.spinner3.getSelectedItem().toString().trim();
//		Anklelt4  = Android.spinner4.getSelectedItem().toString().trim();
//		Anklelt5=Android.spinner5.getSelectedItem().toString().trim();
//		Anklelp1 = Android.spinner8.getSelectedItem().toString().trim();
//		Anklelp2 = Android.spinner9.getSelectedItem().toString().trim();
//		Anklelp3 = Android.spinner10.getSelectedItem().toString().trim();
//		Anklelp4 = Android.spinner11.getSelectedItem().toString().trim();
//		Anklelp5=Android.spinner12.getSelectedItem().toString().trim();
//		Anklelr1 =ValidateEdit(Android.editText1);
//		Anklelr2 =ValidateEdit(Android.editText2);
//		Anklelr3 =ValidateEdit(Android.editText3);
//		Anklelr4 =ValidateEdit(Android.editText4);
//		Anklelr5 =ValidateEdit(Android.editText5);
//		Anklert1=	ValidateSpinner(Windows.spinner1);
//		Anklert2 =ValidateSpinner(Windows.spinner2);
//		Anklert3= ValidateSpinner(Windows.spinner3);
//		Anklert4 = ValidateSpinner(Windows.spinner4);
//		Anklert5 =ValidateSpinner(Windows.spinner5);
//		Anklerp1 = ValidateSpinner(Windows.spinner8);
//		Anklerp2 = ValidateSpinner(Windows.spinner9);
//		Anklerp3 = ValidateSpinner(Windows.spinner10);
//		Anklerp4 =ValidateSpinner(Windows.spinner11);
//		Anklerp5=ValidateSpinner(Windows.spinner12);
//		Anklerr1=ValidateEdit(Windows.editText1);
//		Anklerr2=ValidateEdit(Windows.editText2);
//		Anklerr3=ValidateEdit(Windows.editText3);
//		Anklerr4=ValidateEdit(Windows.editText4);
//		Anklerr5=ValidateEdit(Windows.editText5);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_ANKLE_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.e("result",response);
                            pDialog.dismiss();
							Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
							finish();

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
				params.put("moter_examankle_left_tone1", Utils.getToneSpinnerData(getApplicationContext(),frag_left.getSpinner1().getSelectedItemPosition()));
				params.put("moter_examankle_left_tone2", Utils.getToneSpinnerData(getApplicationContext(),frag_left.getSpinner2().getSelectedItemPosition()));
				params.put("moter_examankle_left_tone3", Utils.getToneSpinnerData(getApplicationContext(),frag_left.getSpinner3().getSelectedItemPosition()));
				params.put("moter_examankle_left_tone4",Utils.getToneSpinnerData(getApplicationContext(),frag_left.getSpinner4().getSelectedItemPosition()));
				params.put("moter_examankle_left_power1",Utils.getPowerSpinnerData(getApplicationContext(),frag_left.getSpinner8().getSelectedItemPosition()));
				params.put("moter_examankle_left_power2", Utils.getPowerSpinnerData(getApplicationContext(),frag_left.getSpinner9().getSelectedItemPosition()));
				params.put("moter_examankle_left_power3", Utils.getPowerSpinnerData(getApplicationContext(),frag_left.getSpinner10().getSelectedItemPosition()));
				params.put("moter_examankle_left_power4", Utils.getPowerSpinnerData(getApplicationContext(),frag_left.getSpinner11().getSelectedItemPosition()));
				params.put("moter_examankle_left_rom1", Utils.getEdittextData(frag_left.getEdtxt_1()));
				params.put("moter_examankle_left_rom2",Utils.getEdittextData(frag_left.getEdtxt_1()));
				params.put("moter_examankle_left_rom3",Utils.getEdittextData(frag_left.getEdtxt_1()));
				params.put("moter_examankle_left_rom4",Utils.getEdittextData(frag_left.getEdtxt_1()));
				params.put("moter_examankle_right_tone1",Utils.getToneSpinnerData(getApplicationContext(),frag_right.getSpinner1().getSelectedItemPosition()));
				params.put("moter_examankle_right_tone2", Utils.getToneSpinnerData(getApplicationContext(),frag_right.getSpinner2().getSelectedItemPosition()));
				params.put("moter_examankle_right_tone3", Utils.getToneSpinnerData(getApplicationContext(),frag_right.getSpinner3().getSelectedItemPosition()));
				params.put("moter_examankle_right_tone4", Utils.getToneSpinnerData(getApplicationContext(),frag_right.getSpinner4().getSelectedItemPosition()));
				params.put("moter_examankle_right_power1", Utils.getPowerSpinnerData(getApplicationContext(),frag_right.getSpinner8().getSelectedItemPosition()));
				params.put("moter_examankle_right_power2", Utils.getPowerSpinnerData(getApplicationContext(),frag_right.getSpinner9().getSelectedItemPosition()));
				params.put("moter_examankle_right_power3", Utils.getPowerSpinnerData(getApplicationContext(),frag_right.getSpinner10().getSelectedItemPosition()));
				params.put("moter_examankle_right_power4", Utils.getPowerSpinnerData(getApplicationContext(),frag_right.getSpinner11().getSelectedItemPosition()));
				params.put("moter_examankle_right_rom1", Utils.getEdittextData(frag_right.getEdtxt_1()));
				params.put("moter_examankle_right_rom2", Utils.getEdittextData(frag_right.getEdtxt_1()));
				params.put("moter_examankle_right_rom3", Utils.getEdittextData(frag_right.getEdtxt_1()));
				params.put("moter_examankle_right_rom4", Utils.getEdittextData(frag_right.getEdtxt_1()));
				params.put("moterexamsankle_image", base_string);
				Log.d(TagUtils.getTag(),"ankle params:-"+params.toString());
				return params;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private void setupViewPager(ViewPager viewPager) {
		frag_left=new AnkleFragment();
		frag_right=new AnkleFragment();
//		frag_parameters=new Ios();
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFrag(frag_left, "Left");
//		adapter.addFrag(frag_parameters, "Movement");
		adapter.addFrag(frag_right, "Right");
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

	private void initProgressDialog(String loading) {
		pDialog = new ProgressDialog(FragementActi3.this);
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
