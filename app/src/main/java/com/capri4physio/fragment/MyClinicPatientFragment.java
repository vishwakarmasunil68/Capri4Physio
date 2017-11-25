package com.capri4physio.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.adapter.MenuAdapter;
import com.capri4physio.fragment.assessment.ADLExamFragment;
import com.capri4physio.fragment.assessment.AddMotorExamFragment;
import com.capri4physio.fragment.assessment.CaseNotesFragment;
import com.capri4physio.fragment.assessment.CheifComplaintFragment;
import com.capri4physio.fragment.assessment.HistoryFragment;
import com.capri4physio.fragment.assessment.InvestigationFragment;
import com.capri4physio.fragment.assessment.MedicalFragment;
import com.capri4physio.fragment.assessment.NdtntpExamFragment;
import com.capri4physio.fragment.assessment.NeuroFragment;
import com.capri4physio.fragment.assessment.PainFragment;
import com.capri4physio.fragment.assessment.PatientInfoFragment;
import com.capri4physio.fragment.assessment.PhysicalExamFragment;
import com.capri4physio.fragment.assessment.PhysiotheraputicFragment;
import com.capri4physio.fragment.assessment.ProgressNotesFragment;
import com.capri4physio.fragment.assessment.RemarksFragment;
import com.capri4physio.fragment.assessment.SensoryFragment;
import com.capri4physio.fragment.assessment.TreatmentFragment;
import com.capri4physio.fragment.assessment.TreatmentGivenFragment;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.model.UserDetails;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Constants;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Arrays;


public class MyClinicPatientFragment extends BaseFragment implements HttpUrlListener, DialogListener<Bundle>, ViewItemClickListener<String> {

    public static TextView mTxtName, mTxtEmail, mTxtPhone, txt_id, mTxtAge, mTxtHeight, mTxtBMI;
    private de.hdodenhof.circleimageview.CircleImageView mImgProfile;
    private ImageView mImgEdit;
    private MenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] mList;
    private DisplayImageOptions options;

    private static final String KEY_CLINIC_ID = "clinic_id";
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_BRANCH_CODE = "branch_code";
    private String clinicId = "";
    private String patientId = "";
    private String patientBranchCode = "";
    private UserDetails mUser = null;
    public static String profilePic, ref_source, mob_number, aadhar_id, address, city, pin_code, con_person, con_per_mob, email, passwod;
    public static String treatment_type, getId, food_habbit, firstname, last_name, age, height, weight, gender, marital_status;
    public static String patient_id = "";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static MyClinicPatientFragment newInstance(String clinicId, String patientId,String branch_code) {
        MyClinicPatientFragment fragment = new MyClinicPatientFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CLINIC_ID, clinicId);
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_BRANCH_CODE, branch_code);
        fragment.setArguments(bundle);
        return fragment;
    }

    public MyClinicPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_action_person)
                .showImageForEmptyUri(R.drawable.ic_action_person)
                .showImageOnFail(R.drawable.ic_action_person)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(50))
                .build();

        if (getArguments() != null) {
            clinicId = getArguments().getString(KEY_CLINIC_ID);
            patientId = getArguments().getString(KEY_PATIENT_ID);
            patientBranchCode = getArguments().getString(KEY_BRANCH_CODE);
        }

        mList = getResources().getStringArray(R.array.menu_myclinic_patient);
        mAdapter = new MenuAdapter(getActivity(), Arrays.asList(mList), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myclinic_patient, container, false);
        initView(rootView);
        setListener();
        setHasOptionsMenu(false
        );
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TagUtils.getTag(),"patient details fragment");
        patientDetailApiCall(patientId);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mImgProfile = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_profile);
        mImgEdit = (ImageView) view.findViewById(R.id.img_edit);
        mTxtName = (TextView) view.findViewById(R.id.txt_name);
        mTxtEmail = (TextView) view.findViewById(R.id.txt_email);
        mTxtPhone = (TextView) view.findViewById(R.id.txt_phone);
        txt_id = (TextView) view.findViewById(R.id.txt_phone1);
        mTxtAge = (TextView) view.findViewById(R.id.txt_age);
        mTxtHeight = (TextView) view.findViewById(R.id.txt_height);
        mTxtBMI = (TextView) view.findViewById(R.id.txt_bmi);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                PatientInfoFragment fragment = PatientInfoFragment.newInstance(patientId, mUser);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        HandlerConstant.POP_BACK_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String) msg.obj;
                Log.d(TagUtils.getTag(),"pop back handler:-"+message);
                startFragment(message);
                return false;
            }
        });

    }

    public void startFragment(String message){
        try{
            int position=Integer.parseInt(message);
//            if(position==4){
//                startActivity(new Intent(getActivity(), AddMotorExamFragment.class));
//            }else{
                onViewItemClick(Arrays.asList(mList).get(position), position, Constants.ClickIDConst.ID_VIEW_CLICK);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    UserDetailModel userDetailModel;

    @Override
    public void onPostSuccess(Object response, int id) {
        int status = 0;
        String msg = "";

        switch (id) {
            case ApiConfig.ID1:
                userDetailModel = (UserDetailModel) response;
                try {

                    status = userDetailModel.getStatus();
                    msg = userDetailModel.getMessage();

                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    msg = "Oops! problem to change password!";
                }

                if (status == 1) {
                    Log.e("resp", response.toString());
                    mUser = userDetailModel.getResult().getUser();
                    String name = mUser.getFirstName() + " " + mUser.getLastName();
                    Log.e("name", name);
                    firstname = mUser.getFirstName();
                    last_name = mUser.getLastName();
                    age = mUser.getAge();
                    height = mUser.getHeight();
                    weight = mUser.getWeight();
                    gender = mUser.getGender();
                    getId = mUser.getId();
                    marital_status = mUser.getMaritalStatus();
                    mTxtName.setText(name);
                    treatment_type = mUser.getTreatment_type();
                    mTxtEmail.setText(mUser.getEmail());
                    txt_id.setText(mUser.getPatient_code());
                    mTxtPhone.setText(mUser.getMobile());
                    food_habbit = mUser.getFoodHabit();
                    ref_source = mUser.getRef_source();
                    profilePic = mUser.getProfilePic();
                    con_person = mUser.getContact_person();
                    con_per_mob = mUser.getContact_person_mob();
                    email = mUser.getEmail();
                    pin_code = mUser.getPincode();
                    mob_number = mUser.getMobile();
                    patient_id = mUser.getId();
                    passwod = mUser.getShowPassword();
                    city = mUser.getCity();
                    address = mUser.getAddress2();
                    aadhar_id = mUser.getAadharId();

//                    Log.e("stat",mUser.getProfilePic());
                    Log.d(TagUtils.getTag(),"profile pic:-"+mUser.getProfilePic());
                    Glide.with(getActivity().getApplicationContext())
                            .load(ApiConfig.PROFILE_PIC_BASE_URL+mUser.getProfilePic())
                            .error(R.drawable.ic_action_person)
                            .placeholder(R.drawable.ic_action_person)
                            .dontAnimate()
                            .into(mImgProfile);

                    if (null != mUser.getAge()) {
                        mTxtAge.setText(mUser.getAge() + " years");
                        mTxtHeight.setText(mUser.getWeight() + " KG");
                        mTxtBMI.setText("Bmi: " + mUser.getBmi());

                    } else {
                        mTxtAge.setText("30 years");
                        mTxtHeight.setText("170 KG");
                        mTxtBMI.setText("Bmi: 21.64");
                    }

                    try{
                        double weight=Double.parseDouble(mUser.getWeight());
                        double height=Double.parseDouble(mUser.getHeight());

                        height=height/100;
                        double bmi=weight/(height*height);
                        mTxtBMI.setText("Bmi: "+getConvertedValue(String.valueOf(bmi)));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    Utils.showMessage(getActivity(), msg);
                }

                break;

            case ApiConfig.ID2:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Base model : " + baseModel.getStatus());
                try {
                    status = baseModel.getStatus();
                    msg = baseModel.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    msg = "Oops! problem to change password!";
                }

                if (status == 1) {

                } else {
                    Utils.showMessage(getActivity(), msg);
                }

                break;
        }

    }
    public String getConvertedValue(String price) {
        try {
            double val = Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.##");
            return String.valueOf(f.format(val));
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }
    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onViewItemClick(String s, int position, int actionId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                CheifComplaintFragment personalnfoFragment = CheifComplaintFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, personalnfoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 1:
                HistoryFragment historyFragment = HistoryFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, historyFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 2:
                PainFragment painFragment = PainFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, painFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 3:
                PhysicalExamFragment examFragment = PhysicalExamFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, examFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 4:
                startActivityForResult(new Intent(getActivity(), AddMotorExamFragment.class),101);
               /* MotorExamFragment motorExamFragment = MotorExamFragment.newInstance(patientId,s);
                ft.replace(R.id.fragment_container, motorExamFragment);
                ft.addToBackStack(null);
                ft.commit();*/
                break;
            case 5:
                SensoryFragment sensoryFragment = SensoryFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, sensoryFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 6:
                NeuroFragment neuroFragment = NeuroFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, neuroFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 7:
                NdtntpExamFragment ndtntpExamFragment = NdtntpExamFragment.newInstance(patientId);
                ft.add(R.id.fragment_container, ndtntpExamFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 8:
                ADLExamFragment adlExamFragment = ADLExamFragment.newInstance(patientId);
                ft.add(R.id.fragment_container, adlExamFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 9:
                InvestigationFragment investigationFragment = InvestigationFragment.newInstance(patientId, s);
                ft.add(R.id.fragment_container, investigationFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 10:
                PhysiotheraputicFragment physiotheraputicFragment = PhysiotheraputicFragment.newInstance(patientId, "Physiotheraputic");
                ft.add(R.id.fragment_container, physiotheraputicFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 11:
                MedicalFragment medicalFragment = MedicalFragment.newInstance(patientId, "Medical");
                ft.add(R.id.fragment_container, medicalFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 12:
                TreatmentFragment tretmentFragment = TreatmentFragment.newInstance(patientId, "Treatment");
                ft.add(R.id.fragment_container, tretmentFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 13:
                TreatmentGivenFragment treatmentGivenFragment = TreatmentGivenFragment.newInstance(patientId, "TreatmentGiven",patientBranchCode);
                ft.add(R.id.fragment_container, treatmentGivenFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 14:
                CaseNotesFragment caseNotesFragment = CaseNotesFragment.newInstance(patientId, "casenote");
                ft.add(R.id.fragment_container, caseNotesFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 15:
                ProgressNotesFragment progressNotesFragment = ProgressNotesFragment.newInstance(patientId, "progressnotes");
                ft.add(R.id.fragment_container, progressNotesFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 16:
                RemarksFragment remark = RemarksFragment.newInstance(patientId, "remark");
                ft.add(R.id.fragment_container, remark);
                ft.addToBackStack(null);
                ft.commit();
                break;
            default:
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result.equals("2")) {

                }else{
                    Log.d(TagUtils.getTag(), "on activity result");
                    startFragment("5");
                }
            }
        }
    }
    @Override
    public void onDialogResult(Bundle bundle, int Id) {

    }


    private void patientDetailApiCall(String patientId) {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {

                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_DETAIL_ID, patientId);
                new UrlConnectionTask(getActivity(), ApiConfig.USER_DETAIL_URL, ApiConfig.ID1, true, params, UserDetailModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main1, menu);
        MenuItem menuItem1 = menu.findItem(R.id.action_new);
        MenuItem menuItem2 = menu.findItem(R.id.action_search);
        menuItem1.setVisible(false);
        menuItem2.setVisible(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("start", "onpause");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Patient List");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Assesment");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}