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
import com.capri4physio.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi10 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	ProgressDialog pDialog;
	Android frag_left;
	Windows frag_right;
	Ios frag_parameters;
	public static String Wristlt1,patient_id,Wristlt2,Wristlt3,Wristlt4,Wristlt5,Wristlt6,Wristlt7,Wristlt8,Wristlt9,Wristlp1,Wristlp2,Wristlp3,Wristlp4,Wristlp5,Wristlp6,Wristlp7,Wristlp8,Wristlp9,Wristlr1,Wristlr2,Wristlr3,Wristlr4,Wristlr5,Wristlr6,Wristlr7,Wristlr8,Wristlr9,Wristrt1,Wristrt2,Wristrt3,Wristrt4,Wristrt5,Wristrt6,Wristrt7,Wristrt8,Wristrt9,Wristrp1,Wristrp2,Wristrp3,
	Wristrp4,Wristrp5,Wristrp6,Wristrp7,Wristrp8,Wristrp9,Wristrr1,Wristrr2,Wristrr3,Wristrr4,Wristrr5,Wristrr6,Wristrr7,Wristrr8,Wristrr9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main10);

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
//				addMotorAPi();
				captureBitmap();
			}
		});
		patient_id =getIntent().getStringExtra("patient_id");
		viewPager.setOffscreenPageLimit(2);

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
		Wristlt1 = Android.spinner1.getSelectedItem().toString().trim();
		Wristlt2 = Android.spinner2.getSelectedItem().toString().trim();
		Wristlt3  = Android.spinner3.getSelectedItem().toString().trim();
		Wristlt4  = Android.spinner4.getSelectedItem().toString().trim();
		Wristlt5=Android.spinner5.getSelectedItem().toString().trim();
		Wristlt6=Android.spinner6.getSelectedItem().toString().trim();
		Wristlt7=Android.spinner7.getSelectedItem().toString().trim();
//		Wristlt8=Android.spinner1_1.getSelectedItem().toString().trim();
//		Wristlt9=Android.spinner1_2.getSelectedItem().toString().trim();
		Wristlp1 = Android.spinner8.getSelectedItem().toString().trim();
		Wristlp2 = Android.spinner9.getSelectedItem().toString().trim();
		Wristlp3 = Android.spinner10.getSelectedItem().toString().trim();
		Wristlp4 = Android.spinner11.getSelectedItem().toString().trim();
		Wristlp5=Android.spinner12.getSelectedItem().toString().trim();
		Wristlp6=Android.spinner13.getSelectedItem().toString().trim();
//		Wristlp7=Android.spinner14.getSelectedItem().toString().trim();
//		Wristlp8=Android.spinner15.getSelectedItem().toString().trim();
//		Wristlp9=Android.spinner16.getSelectedItem().toString().trim();
		Wristlr1=ValidateEdit( Android.editText1);
		Wristlr2 =ValidateEdit( Android.editText2);
		Wristlr3 =ValidateEdit( Android.editText3);
		Wristlr4 =ValidateEdit( Android.editText4);
		Wristlr5 =ValidateEdit( Android.editText5);
		Wristlr6 =ValidateEdit( Android.editText6);
//		Wristlr7 =ValidateEdit( Android.editText7);
//		Wristlr8 =ValidateEdit( Android.editText8);
//		Wristlr9 =ValidateEdit( Android.editText9);
		Wristrt1=ValidateSpinner(Windows.spinner1);
		Wristrt2 = ValidateSpinner(Windows.spinner2);
		Wristrt3= ValidateSpinner(Windows.spinner3);
		Wristrt4 = ValidateSpinner(Windows.spinner4);
		Wristrt5 =ValidateSpinner(Windows.spinner5);
		Wristrt6 =ValidateSpinner(Windows.spinner6);
		Wristrt7 =ValidateSpinner(Windows.spinner7);
//		Wristrt8 =ValidateSpinner(Windows.spinner1_1);
//		Wristrt9 =ValidateSpinner(Windows.spinner1_2);
		Wristrp1 = ValidateSpinner(Windows.spinner8);
		Wristrp2 =ValidateSpinner(Windows.spinner9);
		Wristrp3 = ValidateSpinner(Windows.spinner10);
		Wristrp4 =ValidateSpinner(Windows.spinner11);
		Wristrp5=ValidateSpinner(Windows.spinner12);
		Wristrp6=ValidateSpinner(Windows.spinner13);
		Wristrp7=ValidateSpinner(Windows.spinner14);
		Wristrp8=ValidateSpinner(Windows.spinner15);
		Wristrp9=ValidateSpinner(Windows.spinner16);
		Wristrr1=ValidateEdit( Windows.editText1);
		Wristrr2 =ValidateEdit( Windows.editText2);
		Wristrr3 =ValidateEdit( Windows.editText3);
		Wristrr4 =ValidateEdit( Windows.editText4);
		Wristrr5 =ValidateEdit( Windows.editText5);
		Wristrr6 =ValidateEdit( Windows.editText6);
//		Wristrr7 =ValidateEdit( Windows.editText7);
		Wristrr8 =ValidateEdit( Windows.editText8);
		Wristrr9 =ValidateEdit( Windows.editText9);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_WRIST_URL,
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
				params.put("moter_examwrist_left_tone1", Wristlt1);
				params.put("moter_examwrist_left_tone2", Wristlt2);
				params.put("moter_examwrist_left_tone3", Wristlt3);
				params.put("moter_examwrist_left_tone4",Wristlt4);
				params.put("moter_examwrist_left_tone5",Wristlt5);
				params.put("moter_examwrist_left_tone6",Wristlt6);
				params.put("moter_examwrist_left_tone7",Wristlt7);
				params.put("moter_examwrist_left_tone8 ",Wristlt8);
				params.put("moter_examwrist_left_tone8 ",Wristlt9);
				params.put("moter_examwrist_left_power1",Wristlp1);
				params.put("moter_examwrist_left_power2", Wristlp2);
				params.put("moter_examwrist_left_power3", Wristlp3);
				params.put("moter_examwrist_left_power4", Wristlp4);
				params.put("moter_examwrist_left_power5", Wristlp5);
				params.put("moter_examwrist_left_power6", Wristlp6);
				params.put("moter_examwrist_left_power7", Wristlp7);
				params.put("moter_examwrist_left_power8", Wristlp8);
				params.put("moter_examwrist_left_power8", Wristlp9);
				params.put("moter_examwrist_left_rom1", Wristlr1);
				params.put("moter_examwrist_left_rom2",Wristlr2);
				params.put("moter_examwrist_left_rom3",Wristlr3);
				params.put("moter_examwrist_left_rom4",Wristlr4);
				params.put("moter_examwrist_left_rom5", Wristlr5);
				params.put("moter_examwrist_left_rom6",Wristlr6);
				params.put("moter_examwrist_left_rom7", Wristlr7);
				params.put("moter_examwrist_left_rom8", Wristlr8);
				params.put("moter_examwrist_left_rom8", Wristlr9);
				params.put("moter_examwrist_right_tone1",Wristrt1);
				params.put("moter_examwrist_right_tone2", Wristrt2);
				params.put("moter_examwrist_right_tone3", Wristrt3);
				params.put("moter_examwrist_right_tone4", Wristrt4);
				params.put("moter_examwrist_right_tone5",Wristrt5);
				params.put("moter_examwrist_right_tone6", Wristrt6);
				params.put("moter_examwrist_right_tone7",Wristrt7);
				params.put("moter_examwrist_right_tone8",Wristrt8);
				params.put("moter_examwrist_right_tone8",Wristrt9);
				params.put("moter_examwrist_right_power1", Wristrp1);
				params.put("moter_examwrist_right_power2", Wristrp2);
				params.put("moter_examwrist_right_power3", Wristrp3);
				params.put("moter_examWrist_right_power4", Wristrp4);
				params.put("moter_examwrist_right_power5", Wristrp5);
				params.put("moter_examwrist_right_power6", Wristrp6);
				params.put("moter_examwrist_right_power7", Wristrp7);
				params.put("moter_examwrist_right_power8", Wristrp8);
				params.put("moter_examwrist_right_rom8", Wristrp9);
				params.put("moter_examwrist_right_rom1", Wristrr1);
				params.put("moter_examwrist_right_rom2", Wristrr2);
				params.put("moter_examwrist_right_rom3", Wristrr3);
				params.put("moter_examwrist_right_rom4", Wristrr4);
				params.put("moter_examwrist_right_rom5", Wristrr5);
				params.put("moter_examwrist_right_rom6", Wristrr6);
				params.put("moter_examwrist_right_rom7", Wristrr7);
				params.put("moter_examwrist_right_rom8", Wristrr8);
				params.put("moter_examwrist_right_rom8", Wristrr9);
				params.put("moterexamswrist_image", base_string);
				return params;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
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
		pDialog = new ProgressDialog(FragementActi10.this);
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
