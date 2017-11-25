package com.capri4physio.fragment;

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

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.exams.SensoryExam;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateSensoryActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final String CALL_UPDATE_SENSORY = "call_update_sensory";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.savebtn)
    Button savebtn;

    SensoryExam sensoryExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sensory);
        ButterKnife.bind(this);
        sensoryExam = (SensoryExam) getIntent().getSerializableExtra("sensoryexam");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sensory Examination");

        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSensory();
            }
        });
    }

    public void updateSensory() {
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_exam_date",sensoryExam.getSensoryExamDate()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_occipital_port",sensoryDermatomeFragment.getRg_c2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_supra_fossa",sensoryDermatomeFragment.getRg_c3()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_acrom_joint",sensoryDermatomeFragment.getRg_c4()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_ante_fossa",sensoryDermatomeFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_thumb",sensoryDermatomeFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_middle_finger",sensoryDermatomeFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_little_finger",sensoryDermatomeFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_medial_side",sensoryDermatomeFragment.getRg_t1()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_apexaxilla",sensoryDermatomeFragment.getRg_t2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_nipples",sensoryDermatomeFragment.getRg_t4()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_xiphisternum",sensoryDermatomeFragment.getRg_t6()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_umbilicus",sensoryDermatomeFragment.getRg_t10()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_midpoint_inguinal",sensoryDermatomeFragment.getRg_t12()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_mid_anterior",sensoryDermatomeFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_medial_epicondyle",sensoryDermatomeFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_medial_malleolus",sensoryDermatomeFragment.getRg_l4()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_dorsum_foot",sensoryDermatomeFragment.getRg_l5()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_Lateral_heel",sensoryDermatomeFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_popliteal_fossa",sensoryDermatomeFragment.getRg_s2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_perianal",sensoryDermatomeFragment.getRg_s5()));

        nameValuePairArrayList.add(new BasicNameValuePair("sensory_neck_musc",sensoryMyotomeFragment.getRg_c2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_neck_lateral",sensoryMyotomeFragment.getRg_c3()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_shoulder_elevation",sensoryMyotomeFragment.getRg_c4()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_shoulder_abduction",sensoryMyotomeFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_biceps_supin_wrist",sensoryMyotomeFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_wrist_flex",sensoryMyotomeFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_thumb_extensors",sensoryMyotomeFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_hip_flexion",sensoryMyotomeFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_knee_extension",sensoryMyotomeFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_ankle_dorsi",sensoryMyotomeFragment.getRg_l4()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_extensor",sensoryMyotomeFragment.getRg_l5()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_ankle_plantar",sensoryMyotomeFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_knee_flexion",sensoryMyotomeFragment.getRg_s2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_bladder_rectum",sensoryMyotomeFragment.getRg_s5()));


        nameValuePairArrayList.add(new BasicNameValuePair("sensory_biceps_brachi",sensoryReflexesFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_biceps_brachi1",sensoryReflexesFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_triceps",sensoryReflexesFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_triceps1",sensoryReflexesFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_patellar",sensoryReflexesFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_painslr",sensoryReflexesFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_limitedslr",sensoryReflexesFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_limitedslr_achilles",sensoryReflexesFragment.getRg_s2()));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id",sensoryExam.getPatientId()));
        nameValuePairArrayList.add(new BasicNameValuePair("sensory_exam_id",sensoryExam.getSensoryExamId()));

        new WebServiceBase(nameValuePairArrayList,this,CALL_UPDATE_SENSORY).execute(ApiConfig.EDIT_SENSORY);
    }

    SensoryDermatomeFragment sensoryDermatomeFragment;
    SensoryMyotomeFragment sensoryMyotomeFragment;
    SensoryReflexesFragment sensoryReflexesFragment;

    private void setupViewPager(ViewPager viewPager) {
        sensoryDermatomeFragment = new SensoryDermatomeFragment(sensoryExam,true);
        sensoryMyotomeFragment = new SensoryMyotomeFragment(sensoryExam,true);
        sensoryReflexesFragment = new SensoryReflexesFragment(sensoryExam,true);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(sensoryDermatomeFragment, "Dermatome");
        adapter.addFrag(sensoryMyotomeFragment, "Myotome");
        adapter.addFrag(sensoryReflexesFragment, "Reflexes");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                ToastClass.showShortToast(getApplicationContext(), "Exam Updated");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","ok");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to update Exam");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Server Error");
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
