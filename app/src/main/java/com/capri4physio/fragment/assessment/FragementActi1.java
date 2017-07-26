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
import com.capri4physio.util.TagUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi1 extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    android.support.v7.app.ActionBar actionBar;
    private ViewPager viewPager;
    Button savebtn;
    ProgressDialog pDialog;
    Android frag_left;
    Windows frag_right;
    Ios frag_parameters;
    public static String Hiplt1 = "", patient_id = "", Hiplt2 = "", Hiplt3 = "", Hiplt4 = "", Hiplt5 = "", Hiplt6 = "", Hiplt7 = "", Hiplt8 = "", Hiplt9 = "", Hiplp1 = "", Hiplp2 = "", Hiplp3 = "", Hiplp4 = "", Hiplp5 = "", Hiplp6 = "", Hiplp7 = "", Hiplp8 = "", Hiplp9 = "", Hiplr1 = "", Hiplr2 = "", Hiplr3 = "", Hiplr4 = "", Hiplr5 = "", Hiplr6 = "", Hiplr7 = "", Hiplr8 = "", Hiplr9 = "", Hiprt1 = "", Hiprt2 = "", Hiprt3 = "", Hiprt4 = "", Hiprt5 = "", Hiprt6 = "", Hiprt7 = "", Hiprt8 = "", Hiprt9 = "", Hiprp1 = "", Hiprp2 = "", Hiprp3 = "",
            Hiprp4 = "", Hiprp5 = "", Hiprp6 = "", Hiprp7 = "", Hiprp8 = "", Hiprp9 = "", Hiprr1 = "", Hiprr2 = "", Hiprr3 = "", Hiprr4 = "", Hiprr5 = "", Hiprr6 = "", Hiprr7 = "", Hiprr8 = "", Hiprr9 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//		toolbar.setTitle(s);
        actionBar = getSupportActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*TabAdapter = new TabPagerAdapter1(getSupportFragmentManager());
        savebtn=(Button)findViewById(R.id.savebtn);
        Tab = (ViewPager)findViewById(R.id.pager);*/
        savebtn = (Button) findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button", Toast.LENGTH_LONG);
//				addMotorAPi();
                captureBitmap();
            }
        });
        patient_id = getIntent().getStringExtra("patient_id");
        Log.d(TagUtils.getTag(), "patient_id:-" + patient_id);
        viewPager.setOffscreenPageLimit(2);
    }

    //	private final String TAG=getClass().getName();
    public void captureBitmap() {
        Bitmap left_bitmap = frag_left.takeScreenShots();
//		Bitmap parameter_bitmap=frag_parameters.takeScreenShots();
        Bitmap right_bitmap = frag_right.takeScreenShots();


//		SaveBitmap(left_bitmap);
//		SaveBitmap(parameter_bitmap);
//		SaveBitmap(right_bitmap);

        ShowDialog(left_bitmap, right_bitmap);


        Toast.makeText(getApplicationContext(), "bitmap saved", Toast.LENGTH_SHORT).show();
    }

    public void ShowDialog(Bitmap lef, Bitmap right) {
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
                String main_base64 = ImageUtil.encodeTobase64(takeScreenShots(scroll_dialog));
                Toast.makeText(getApplicationContext(), "saved image", Toast.LENGTH_SHORT).show();
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


    public String ValidateEdit(EditText edit) {
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
        } catch (Exception e) {
            return "";
        }
    }

    public String ValidateSpinner(Spinner edit) {
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
        } catch (Exception e) {
            return "";
        }
    }

    public void addMototAPi(final String base_string) {

    }

    private void addMotorAPi(final String base_string) {
        Hiplt1 = Android.spinner1.getSelectedItem().toString().trim();
        Hiplt2 = Android.spinner2.getSelectedItem().toString().trim();
        Hiplt3 = Android.spinner3.getSelectedItem().toString().trim();
        Hiplt4 = Android.spinner4.getSelectedItem().toString().trim();
        Hiplt5 = Android.spinner5.getSelectedItem().toString().trim();
        Hiplt6 = Android.spinner6.getSelectedItem().toString().trim();
        Hiplt7 = Android.spinner7.getSelectedItem().toString().trim();
//		Hiplt8=Android.spinner1_1.getSelectedItem().toString().trim();
//		Hiplt9=Android.spinner1_2.getSelectedItem().toString().trim();
        Hiplp1 = Android.spinner8.getSelectedItem().toString().trim();
        Hiplp2 = Android.spinner9.getSelectedItem().toString().trim();
        Hiplp3 = Android.spinner10.getSelectedItem().toString().trim();
        Hiplp4 = Android.spinner11.getSelectedItem().toString().trim();
        Hiplp5 = Android.spinner12.getSelectedItem().toString().trim();
        Hiplp6 = Android.spinner13.getSelectedItem().toString().trim();
//		Hiplp7=Android.spinner14.getSelectedItem().toString().trim();
//		Hiplp8=Android.spinner15.getSelectedItem().toString().trim();
//		Hiplp9=Android.spinner16.getSelectedItem().toString().trim();
        Hiplr1 = ValidateEdit(Android.editText1);
        Hiplr2 = ValidateEdit(Android.editText2);
        Hiplr3 = ValidateEdit(Android.editText3);
        Hiplr4 = ValidateEdit(Android.editText4);
        Hiplr5 = ValidateEdit(Android.editText5);
        Hiplr6 = ValidateEdit(Android.editText6);
//		Hiplr7 =ValidateEdit(Android.editText7);
//		Hiplr8 =ValidateEdit(Android.editText8);
//		Hiplr9 =ValidateEdit(Android.editText9);
        Hiprt1 = ValidateSpinner(Windows.spinner1);
        Hiprt2 = ValidateSpinner(Windows.spinner2);
        Hiprt3 = ValidateSpinner(Windows.spinner3);
        Hiprt4 = ValidateSpinner(Windows.spinner4);
        Hiprt5 = ValidateSpinner(Windows.spinner5);
        Hiprt6 = ValidateSpinner(Windows.spinner6);
        Hiprt7 = ValidateSpinner(Windows.spinner7);
//		Hiprt8 =ValidateSpinner(Windows.spinner1_1);
//		Hiprt9 =ValidateSpinner(Windows.spinner1_2);
        Hiprp1 = ValidateSpinner(Windows.spinner8);
        Hiprp2 = ValidateSpinner(Windows.spinner9);
        Hiprp3 = ValidateSpinner(Windows.spinner10);
        Hiprp4 = ValidateSpinner(Windows.spinner11);
        Hiprp5 = ValidateSpinner(Windows.spinner12);
        Hiprp6 = ValidateSpinner(Windows.spinner13);
        Hiprp7 = ValidateSpinner(Windows.spinner14);
        Hiprp8 = ValidateSpinner(Windows.spinner15);
        Hiprp9 = ValidateSpinner(Windows.spinner16);
        Hiprr1 = ValidateEdit(Windows.editText1);
        Hiprr2 = ValidateEdit(Windows.editText2);
        Hiprr3 = ValidateEdit(Windows.editText3);
        Hiprr4 = ValidateEdit(Windows.editText4);
        Hiprr5 = ValidateEdit(Windows.editText5);
        Hiprr6 = ValidateEdit(Windows.editText6);
//		Hiprr7=ValidateEdit(Windows.editText7);
        Hiprr8 = ValidateEdit(Windows.editText8);
        Hiprr9 = ValidateEdit(Windows.editText9);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_HIP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "successfully added", Toast.LENGTH_LONG).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "ok");
                            setResult(Activity.RESULT_OK, returnIntent);
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
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("moter_exam_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                params.put("patient_id", patient_id);
                Log.d(TagUtils.getTag(), "LOG_patient_id:-" + patient_id);
                params.put("moter_examhip_left_tone1", Hiplt1);
                params.put("moter_examhip_left_tone2", Hiplt2);
                params.put("moter_examhip_left_tone3", Hiplt3);
                params.put("moter_examhip_left_tone4", Hiplt4);
                params.put("moter_examhip_left_tone5", Hiplt5);
                params.put("moter_examhip_left_tone6", Hiplt6);
                params.put("moter_examhip_left_tone7", Hiplt7);
                params.put("moter_examhip_left_tone8 ", Hiplt8);
                params.put("moter_examhip_left_tone9 ", Hiplt9);
                params.put("moter_examhip_left_power1", Hiplp1);
                params.put("moter_examhip_left_power2", Hiplp2);
                params.put("moter_examhip_left_power3", Hiplp3);
                params.put("moter_examhip_left_power4", Hiplp4);
                params.put("moter_examhip_left_power5", Hiplp5);
                params.put("moter_examhip_left_power6", Hiplp6);
                params.put("moter_examhip_left_power7", Hiplp7);
                params.put("moter_examhip_left_power8", Hiplp8);
                params.put("moter_examhip_left_power9", Hiplp9);
                params.put("moter_examhip_left_rom1", Hiplr1);
                params.put("moter_examhip_left_rom2", Hiplr2);
                params.put("moter_examhip_left_rom3", Hiplr3);
                params.put("moter_examhip_left_rom4", Hiplr4);
                params.put("moter_examhip_left_rom5", Hiplr5);
                params.put("moter_examhip_left_rom6", Hiplr6);
                params.put("moter_examhip_left_rom7", Hiplr7);
                params.put("moter_examhip_left_rom8", Hiplr8);
                params.put("moter_examhip_left_rom9", Hiplr9);
                params.put("moter_examhip_right_tone1", Hiprt1);
                params.put("moter_examhip_right_tone2", Hiprt2);
                params.put("moter_examhip_right_tone3", Hiprt3);
                params.put("moter_examhip_right_tone4", Hiprt4);
                params.put("moter_examhip_right_tone5", Hiprt5);
                params.put("moter_examhip_right_tone6", Hiprt6);
                params.put("moter_examhip_right_tone7", Hiprt7);
                params.put("moter_examhip_right_tone8", Hiprt8);
                params.put("moter_examhip_right_tone9", Hiprt9);
                params.put("moter_examhip_right_power1", Hiprp1);
                params.put("moter_examhip_right_power2", Hiprp2);
                params.put("moter_examhip_right_power3", Hiprp3);
                params.put("moter_examhip_right_power4", Hiprp4);
                params.put("moter_examhip_right_power5", Hiprp5);
                params.put("moter_examhip_right_power6", Hiprp6);
                params.put("moter_examhip_right_power7", Hiprp7);
                params.put("moter_examhip_right_power8", Hiprp8);
                params.put("moter_examhip_right_power9", Hiprp9);
                params.put("moter_examhip_right_rom1", Hiprr1);
                params.put("moter_examhip_right_rom2", Hiprr2);
                params.put("moter_examhip_right_rom3", Hiprr3);
                params.put("moter_examhip_right_rom4", Hiprr4);
                params.put("moter_examhip_right_rom5", Hiprr5);
                params.put("moter_examhip_right_rom6", Hiprr6);
                params.put("moter_examhip_right_rom7", Hiprr7);
                params.put("moter_examhip_right_rom8", Hiprr8);
                params.put("moter_examhip_right_rom9", Hiprr9);
                params.put("moterexamship_image", base_string);
                Log.d(TagUtils.getTag(), "hip params:-" + params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupViewPager(ViewPager viewPager) {
        frag_left = new Android();
        frag_right = new Windows();
//		frag_parameters=new Ios();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(frag_left, "Left");
//        adapter.addFrag(frag_parameters, "Movement");
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

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(FragementActi1.this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

}
