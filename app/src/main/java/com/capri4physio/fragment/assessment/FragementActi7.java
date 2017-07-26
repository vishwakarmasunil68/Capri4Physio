package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class FragementActi7 extends AppCompatActivity {
	private Toolbar toolbar;
	private TabLayout tabLayout;
	android.support.v7.app.ActionBar actionBar;
	private ViewPager viewPager;
	Button savebtn;
	ProgressDialog pDialog;
	Android frag_left;
	Windows frag_right;
	Ios frag_parameters;
	public static String Shoulderlt1="",patient_id="",Shoulderlt2="",Shoulderlt3="",Shoulderlt4="",Shoulderlt5="",Shoulderlt6="",Shoulderlt7="",Shoulderlt8="",Shoulderlp1="",Shoulderlp2="",Shoulderlp3="",Shoulderlp4="",Shoulderlp5="",Shoulderlp6="",Shoulderlp7="",Shoulderlp8="",Shoulderlr1="",Shoulderlr2="",Shoulderlr3="",Shoulderlr4="",Shoulderlr5="",Shoulderlr6="",Shoulderlr7="",Shoulderlr8="",Shoulderrt1="",Shoulderrt2="",Shoulderrt3="",Shoulderrt4="",Shoulderrt5="",Shoulderrt6="",Shoulderrt7="",Shoulderrt8="",Shoulderrp1="",Shoulderrp2="",Shoulderrp3="",
			Shoulderrp4="",Shoulderrp5="",Shoulderrp6="",Shoulderrp7="",Shoulderrp8="",Shoulderrr1="",Shoulderrr2="",Shoulderrr3="",Shoulderrr4="",Shoulderrr5="",Shoulderrr6="",Shoulderrr7="",Shoulderrr8="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main7);

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
		Shoulderlt1 = Android.spinner1.getSelectedItem().toString().trim();
		Shoulderlt2 = Android.spinner2.getSelectedItem().toString().trim();
		Shoulderlt3  = Android.spinner3.getSelectedItem().toString().trim();
		Shoulderlt4  = Android.spinner4.getSelectedItem().toString().trim();
		Shoulderlt5=Android.spinner5.getSelectedItem().toString().trim();
		Shoulderlt6=Android.spinner6.getSelectedItem().toString().trim();
		Shoulderlt7=Android.spinner7.getSelectedItem().toString().trim();
//		Shoulderlt8=Android.spinner1_1.getSelectedItem().toString().trim();
		Shoulderlp1 = Android.spinner8.getSelectedItem().toString().trim();
		Shoulderlp2 = Android.spinner9.getSelectedItem().toString().trim();
		Shoulderlp3 = Android.spinner10.getSelectedItem().toString().trim();
		Shoulderlp4 = Android.spinner11.getSelectedItem().toString().trim();
		Shoulderlp5=Android.spinner12.getSelectedItem().toString().trim();
		Shoulderlp6=Android.spinner13.getSelectedItem().toString().trim();
//		Shoulderlp7=Android.spinner14.getSelectedItem().toString().trim();
//		Shoulderlp8=Android.spinner15.getSelectedItem().toString().trim();
		Shoulderlr1 = ValidateEdit(Android.editText1);
		Shoulderlr2 =ValidateEdit(Android.editText2);
		Shoulderlr3 = ValidateEdit(Android.editText3);
		Shoulderlr4 = ValidateEdit(Android.editText4);
		Shoulderlr5 =ValidateEdit(Android.editText5);
		Shoulderlr6 =ValidateEdit(Android.editText6);
//		Shoulderlr7 =ValidateEdit(Android.editText7);
//		Shoulderlr8 =ValidateEdit(Android.editText8);
		Shoulderrt1= ValidateSpinner( Windows.spinner1);
		Shoulderrt2 =ValidateSpinner( Windows.spinner2);
		Shoulderrt3=ValidateSpinner( Windows.spinner3);
		Shoulderrt4 = ValidateSpinner( Windows.spinner4);
		Shoulderrt5 =ValidateSpinner( Windows.spinner5);
		Shoulderrt6 =ValidateSpinner( Windows.spinner6);
		Shoulderrt7 =ValidateSpinner( Windows.spinner7);
//		Shoulderrt8 =ValidateSpinner( Windows.spinner1_1);
		Shoulderrp1 =ValidateSpinner( Windows.spinner8);
		Shoulderrp2 = ValidateSpinner( Windows.spinner9);
		Shoulderrp3 = ValidateSpinner( Windows.spinner10);
		Shoulderrp4 =ValidateSpinner( Windows.spinner11);
		Shoulderrp5=ValidateSpinner( Windows.spinner12);
		Shoulderrp6=ValidateSpinner( Windows.spinner13);
		Shoulderrp7=ValidateSpinner( Windows.spinner14);
		Shoulderrp8=ValidateSpinner( Windows.spinner15);
		Shoulderrr1= ValidateEdit(Windows.editText1);
		Shoulderrr2= ValidateEdit(Windows.editText2);
		Shoulderrr3=ValidateEdit(Windows.editText3);
		Shoulderrr4=ValidateEdit(Windows.editText4);
		Shoulderrr5=ValidateEdit(Windows.editText5);
		Shoulderrr6=ValidateEdit(Windows.editText6);
//		Shoulderrr7=ValidateEdit(Windows.editText7);
		Shoulderrr8=ValidateEdit(Windows.editText8);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
		StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_SHOULDER_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.e("result",response);
                            pDialog.dismiss();


						} catch (Exception e) {
							e.printStackTrace();
						}
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
						Log.e("Postdat", "" + response.toString());
						Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
						Intent returnIntent = new Intent();
						returnIntent.putExtra("result","ok");
						setResult(Activity.RESULT_OK,returnIntent);
						finish();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
						Log.w("Postdat", "" + error);
						Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
						Intent returnIntent = new Intent();
						returnIntent.putExtra("result","ok");
						setResult(Activity.RESULT_OK,returnIntent);
						finish();
					}
				}){


			protected Map<String,String> getParams(){
				Map<String,String> params = new HashMap<String, String>();
				params.put("moter_exam_date", Utils.getCurrentDate());
				params.put("patient_id", patient_id);
				params.put("moter_examshoulder_left_tone1", Shoulderlt1);
				params.put("moter_examshoulder_left_tone2", Shoulderlt2);
				params.put("moter_examshoulder_left_tone3", Shoulderlt3);
				params.put("moter_examshoulder_left_tone4",Shoulderlt4);
				params.put("moter_examshoulder_left_tone5",Shoulderlt5);
				params.put("moter_examshoulder_left_tone6",Shoulderlt6);
				params.put("moter_examshoulder_left_tone7",Shoulderlt7);
				params.put("moter_examshoulder_left_tone8 ",Shoulderlt8);
				params.put("moter_examshoulder_left_power1",Shoulderlp1);
				params.put("moter_examshoulder_left_power2", Shoulderlp2);
				params.put("moter_examshoulder_left_power3", Shoulderlp3);
				params.put("moter_examshoulder_left_power4", Shoulderlp4);
				params.put("moter_examshoulder_left_power5", Shoulderlp5);
				params.put("moter_examshoulder_left_power6", Shoulderlp6);
				params.put("moter_examshoulder_left_power7", Shoulderlp7);
				params.put("moter_examshoulder_left_power8", Shoulderlp8);
				params.put("moter_examshoulder_left_rom1", Shoulderlr1);
				params.put("moter_examshoulder_left_rom2",Shoulderlr2);
				params.put("moter_examshoulder_left_rom3",Shoulderlr3);
				params.put("moter_examshoulder_left_rom4",Shoulderlr4);
				params.put("moter_examshoulder_left_rom5", Shoulderlr5);
				params.put("moter_examshoulder_left_rom6",Shoulderlr6);
				params.put("moter_examshoulder_left_rom7", Shoulderlr7);
				params.put("moter_examshoulder_left_rom8", Shoulderlr8);
				params.put("moter_examshoulder_right_tone1",Shoulderrt1);
				params.put("moter_examshoulder_right_tone2", Shoulderrt2);
				params.put("moter_examshoulder_right_tone3", Shoulderrt3);
				params.put("moter_examshoulder_right_tone4", Shoulderrt4);
				params.put("moter_examshoulder_right_tone5",Shoulderrt5);
				params.put("moter_examshoulder_right_tone6", Shoulderrt6);
				params.put("moter_examshoulder_right_tone7",Shoulderrt7);
				params.put("moter_examshoulder_right_tone8",Shoulderrt8);
				params.put("moter_examshoulder_right_power1", Shoulderrp1);
				params.put("moter_examshoulder_right_power2", Shoulderrp2);
				params.put("moter_examshoulder_right_power3", Shoulderrp3);
				params.put("moter_examshoulder_right_power4", Shoulderrp4);
				params.put("moter_examshoulder_right_power5", Shoulderrp5);
				params.put("moter_examshoulder_right_power6", Shoulderrp6);
				params.put("moter_examshoulder_right_power7", Shoulderrp7);
				params.put("moter_examshoulder_right_power8", Shoulderrp8);
				params.put("moter_examshoulder_right_rom1", Shoulderrr1);
				params.put("moter_examshoulder_right_rom2", Shoulderrr2);
				params.put("moter_examshoulder_right_rom3", Shoulderrr3);
				params.put("moter_examshoulder_right_rom4", Shoulderrr4);
				params.put("moter_examshoulder_right_rom5", Shoulderrr5);
				params.put("moter_examshoulder_right_rom6", Shoulderrr6);
				params.put("moter_examshoulder_right_rom7", Shoulderrr7);
				params.put("moter_examshoulder_right_rom8", Shoulderrr8);
				params.put("moterexamsshoulder_image", base_string);
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
		pDialog = new ProgressDialog(FragementActi7.this);
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
