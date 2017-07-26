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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActi11 extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    android.support.v7.app.ActionBar actionBar;
    private ViewPager viewPager;
    Button savebtn;
    ProgressDialog progressDialog;
    ProgressDialog pDialog;
    Android frag_left;
    Windows frag_right;
    Ios frag_parameters;
    public static String knlt1, patient_id, knlt2, knlt3, knlt4, knlp1, knlp2, knlp3, knlp4, knlr1, knlr2, knlr3, knlr4, respiration_Base3, knrt1, knrt2, knrt3, knrt4, knrp1, knrp2, knrp3,
            knrp4, knrr1, knrr2, knrr3, knrr4, knlt10, knlt20, knlt30, knlt40, knlp10, knlp20, knlp30, knlp40, knlr10, knlr20, knlr30, knlr40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main11);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//		toolbar.setTitle(s);
        actionBar = getSupportActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        savebtn = (Button) findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog("Please wait..");
                captureBitmap();
            }
        });
        patient_id = getIntent().getStringExtra("patient_id");
        viewPager.setOffscreenPageLimit(2);
    }

    public void captureBitmap() {
        Bitmap left_bitmap = frag_left.takeScreenShots();
//		Bitmap parameter_bitmap=frag_parameters.takeScreenShots();
        Bitmap right_bitmap = frag_right.takeScreenShots();


//		SaveBitmap(left_bitmap);
//		SaveBitmap(parameter_bitmap);
//		SaveBitmap(right_bitmap);

        try {
            pDialog.dismiss();
        } catch (Exception e) {
            Log.d("nullpointer", e.toString());
        }
        initProgressDialog("Please wait..");
        ShowDialog(left_bitmap, right_bitmap);


//		Toast.makeText(getApplicationContext(),"bitmap saved",Toast.LENGTH_SHORT).show();
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

        pDialog.dismiss();
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
//				SaveString(main_base64);
                Log.e("base", main_base64);
                Toast.makeText(getApplicationContext(), "saved image", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                initProgressDialog("Please wait..");
                addMotorAPi(main_base64);
            }
        });
        dialog.show();
    }

    public void SaveString(String str) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" +
                    File.separator + "base.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(str);
//			fileWriter.write("a test");
            fileWriter.flush();
            fileWriter.close();
            Log.d("sunil", "file written");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        frag_left = new Android();
        frag_right = new Windows();
//		frag_parameters=new Ios();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(frag_left, "Left");
//		adapter.addFrag(frag_parameters, "Movement");
        adapter.addFrag(frag_right, "Right");
        viewPager.setAdapter(adapter);
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

    private void addMotorAPi(final String base_string) {
        knlt1 = Android.spinner1.getSelectedItem().toString().trim();
        knlt2 = Android.spinner2.getSelectedItem().toString().trim();
        knlt3 = Android.spinner3.getSelectedItem().toString().trim();
        knlt4 = Android.spinner4.getSelectedItem().toString().trim();
        knlp1 = Android.spinner8.getSelectedItem().toString().trim();
        knlp2 = Android.spinner9.getSelectedItem().toString().trim();
        knlp3 = Android.spinner10.getSelectedItem().toString().trim();
        knlp4 = Android.spinner11.getSelectedItem().toString().trim();
        knlr1 = ValidateEdit(Android.editText1);
        knlr2 = ValidateEdit(Android.editText2);
        knlr3 = ValidateEdit(Android.editText3);
        knlr4 = ValidateEdit(Android.editText4);
        knrt1 = ValidateSpinner(Windows.spinner1);
        knrt2 = ValidateSpinner(Windows.spinner2);
        knrt3 = ValidateSpinner(Windows.spinner3);
        knrt4 = ValidateSpinner(Windows.spinner4);
        knrp1 = ValidateSpinner(Windows.spinner8);
        knrp2 = ValidateSpinner(Windows.spinner9);
        knrp3 = ValidateSpinner(Windows.spinner10);
        knrp4 = ValidateSpinner(Windows.spinner11);
        knrr1 = ValidateEdit(Windows.editText1);
        knrr2 = ValidateEdit(Windows.editText2);
        knrr3 = ValidateEdit(Windows.editText3);
        knrr4 = ValidateEdit(Windows.editText4);
        knlt10 = Android.spinner5.getSelectedItem().toString().trim();
        knlt20 = Android.spinner6.getSelectedItem().toString().trim();
        knlt30 = Android.spinner12.getSelectedItem().toString().trim();
        knlt40 = Android.spinner13.getSelectedItem().toString().trim();
        knlp10 = ValidateSpinner(Windows.spinner5);
        knlp20 = ValidateSpinner(Windows.spinner6);
        knlp30 = ValidateSpinner(Windows.spinner12);
        knlp40 = ValidateSpinner(Windows.spinner13);
        knlr10 = ValidateEdit(Windows.editText5);
        knlr20 = ValidateEdit(Windows.editText6);
        knlr30 = ValidateEdit(Android.editText5);
        knlr40 = ValidateEdit(Android.edit);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTER_FINGER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "successfully added", Toast.LENGTH_LONG).show();
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
                params.put("moter_exam_date", Utils.getCurrentDate());
                params.put("patient_id", patient_id);
                params.put("moter_examfinger_left_tone1", knlt1);
                params.put("moter_examfinger_left_tone2", knlt2);
                params.put("moter_examfinger_left_tone3", knlt3);
                params.put("moter_examfinger_left_tone4", knlt4);
                params.put("moter_examfinger_left_power1", knlp1);
                params.put("moter_examfinger_left_power2", knlp2);
                params.put("moter_examfinger_left_power3", knlp3);
                params.put("moter_examfinger_left_power4", knlp4);
                params.put("moter_examfinger_left_rom1", knlr1);
                params.put("moter_examfinger_left_rom2", knlr2);
                params.put("moter_examfinger_left_rom3", knlr3);
                params.put("moter_examfinger_left_rom4", knlr4);
                params.put("moter_examfinger_right_tone1", knrt1);
                params.put("moter_examfinger_right_tone2", knrt2);
                params.put("moter_examfinger_right_tone3", knrt3);
                params.put("moter_examfinger_right_tone4", knrt4);
                params.put("moter_examfinger_right_power1", knrp1);
                params.put("moter_examfinger_right_power2", knrp2);
                params.put("moter_examfinger_right_power3", knrp3);
                params.put("moter_examfinger_right_power4", knrp4);
                params.put("moter_examfinger_right_rom1", knrr1);
                params.put("moter_examfinger_right_rom2", knrr2);
                params.put("moter_examfinger_right_rom3", knrr3);
                params.put("moter_examfinger_right_rom4", knrr4);
                params.put("moter_examfinger_left_tone5", knlt10);
                params.put("moter_examfinger_left_tone6", knlt20);
                params.put("moter_examfinger_left_power5", knlt30);
                params.put("moter_examfinger_left_power6", knlt40);
                params.put("moter_examfinger_left_rom5", knlp10);
                params.put("moter_examfinger_left_rom6", knlp20);
                params.put("moter_examfinger_right_tone5", knlp30);
                params.put("moter_examfinger_right_tone6", knlp40);
                params.put("moter_examfinger_right_power5", knlr10);
                params.put("moter_examfinger_right_power6", knlr20);
                params.put("moter_examfinger_right_rom5", knlr30);
                params.put("moter_examfinger_right_rom6", knlr40);
                params.put("moterexamsfingers_images", base_string);
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
        pDialog = new ProgressDialog(FragementActi11.this);
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
