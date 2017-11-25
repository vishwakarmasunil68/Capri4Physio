package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragementActiSensory1 extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    android.support.v7.app.ActionBar actionBar;
    private ViewPager viewPager;
    Button savebtn;
    public static Boolean status = true;
    public String patient_id, Hiplt1, Hiplt2, Hiplt3, Hiplt4, Hiplt5, Hiplt6, Hiplt7, Hiplt8, Hiplt9, Hiplp1, Hiplp2, Hiplp3, Hiplp4, Hiplp5, Hiplp6, Hiplp7, Hiplp8, Hiplp9, Hiplr1, Hiplr2, Hiplr3, Hiplr4, Hiplr5, Hiplr6, Hiplr7, Hiplr8, Hiplr9, Hiprt1, Hiprt2, Hiprt3, Hiprt4, Hiprt5, Hiprt6, Hiprt7, Hiprt8, Hiprt9, Hiprp1, Hiprp2, Hiprp3,
            Hiprp4, Hiprp5, Hiprp6, Hiprp7, Hiprp8, Hiprp9, Hiprr1, Hiprr2, Hiprr3, Hiprr4, Hiprr5, Hiprr6, Hiprr7, Hiprr8, Hiprr9;

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
        viewPager.setOffscreenPageLimit(3);
        savebtn = (Button) findViewById(R.id.savebtn);

        patient_id = getIntent().getStringExtra("patient_id");

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addMotorAPi();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please select all values", Toast.LENGTH_LONG);
                }

            }
        });
        getSupportActionBar().setTitle("Add Sensory Exam");
    }

    private void addMotorAPi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_SENSORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TagUtils.getTag(),"response:-"+ response);
//                            pDialog.dismiss();
                            Toast.makeText(FragementActiSensory1.this, "success", Toast.LENGTH_LONG).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "1");
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
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("sensory_exam_date", Utils.getCurrentDate());
                objresponse.put("patient_id", patient_id);
                objresponse.put("sensory_occipital_port", Categories_Fragment.rate);
                objresponse.put("sensory_neck_musc", Categories_Fragment.rate1);
                objresponse.put("sensory_supra_fossa", Categories_Fragment.rate2);
                objresponse.put("sensory_neck_lateral", Categories_Fragment.rate3);
                objresponse.put("sensory_acrom_joint", Categories_Fragment.rate4);
                objresponse.put("sensory_shoulder_elevation", Categories_Fragment.rate5);
                objresponse.put("sensory_ante_fossa", Categories_Fragment.rate6);
                objresponse.put("sensory_shoulder_abduction", Categories_Fragment.rate7);
                objresponse.put("sensory_biceps_brachi", Categories_Fragment.rate8);
                objresponse.put("sensory_thumb", Categories_Fragment.rate5);
                objresponse.put("sensory_biceps_supin_wrist", Categories_Fragment.rate10);
                objresponse.put("sensory_biceps_brachi1", Categories_Fragment.rate11);
                objresponse.put("sensory_middle_finger", Categories_Fragment.rate12);
                objresponse.put("sensory_wrist_flex", Categories_Fragment.rate13);
                objresponse.put("sensory_triceps", Categories_Fragment.rate15);
                objresponse.put("sensory_little_finger", Categories_Fragment.rate16);
                objresponse.put("sensory_thumb_extensors", Categories_Fragment.rate17);
                objresponse.put("sensory_triceps1", Categories_Fragment.rate18);
                objresponse.put("sensory_medial_side", Categories_Fragment.rate19);
                objresponse.put("sensory_apexaxilla", Categories_Fragment.rate20);
                objresponse.put("sensory_nipples", Categories_Fragment.rate21);
                objresponse.put("sensory_xiphisternum", Search_Fragment.rate);
                objresponse.put("sensory_umbilicus", Search_Fragment.rate1);
                objresponse.put("sensory_midpoint_inguinal", Search_Fragment.rate2);
                objresponse.put("sensory_mid_anterior", Search_Fragment.rate3);
                objresponse.put("sensory_hip_flexion", Search_Fragment.rate4);
                objresponse.put("sensory_patellar", Search_Fragment.rate5);
                objresponse.put("sensory_medial_epicondyle", Search_Fragment.rate6);
                objresponse.put("sensory_knee_extension", Search_Fragment.rate13);
                objresponse.put("sensory_painslr", Search_Fragment.rate15);
                objresponse.put("sensory_medial_malleolus", Categories_Fragment.rate16);
                objresponse.put("sensory_ankle_dorsi", Search_Fragment.rate17);
                objresponse.put("sensory_dorsum_foot", Search_Fragment.rate18);
                objresponse.put("sensory_extensor", Search_Fragment.rate19);
                objresponse.put("sensory_Lateral_heel", Search_Fragment.rate20);
                objresponse.put("sensory_ankle_plantar", Search_Fragment.rate21);
                objresponse.put("sensory_limitedslr", Favourite_Fragment.rate);
                objresponse.put("sensory_popliteal_fossa", Favourite_Fragment.rate1);
                objresponse.put("sensory_knee_flexion", Favourite_Fragment.rate2);
                objresponse.put("sensory_limitedslr_achilles", Favourite_Fragment.rate3);
                objresponse.put("sensory_perianal", Favourite_Fragment.rate4);
                objresponse.put("sensory_bladder_rectum", Favourite_Fragment.rate5);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Categories_Fragment(), "Dermatome");
        adapter.addFrag(new Search_Fragment(), "Myotome");
        adapter.addFrag(new Favourite_Fragment(), "Reflexes");
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
