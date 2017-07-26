package com.capri4physio.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.Utils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfileFragment extends BaseFragment implements HttpUrlListener {

//    private Button mBtnChangePassword;
    private ImageView mgProfile;
    private TextView mTxtName,mTxtEmail,mTxtPhone,aadharid,food_habbit,address,city,Pincode,height,ContactPerson,age,ContactPersonMobile,Referralsource
            ,weight,gender,marital_status;

    private UserDetailModel mUserModel;
    private ImageView mImgProfile;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static PatientProfileFragment newInstance() {
        PatientProfileFragment fragment = new PatientProfileFragment();
        return fragment;
    }

    public PatientProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String userDetails = AppPreferences.getInstance(getActivity()).getUserDetails();
            Gson gson = new Gson();
            mUserModel = gson.fromJson(userDetails, UserDetailModel.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_patient, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mImgProfile = (CircleImageView)view.findViewById(R.id.img_profile);
        mTxtName =(TextView)view.findViewById(R.id.txt_name);
        mTxtEmail =(TextView)view.findViewById(R.id.txt_email);
        mTxtPhone =(TextView)view.findViewById(R.id.txt_phone);
        aadharid = (TextView) view.findViewById(R.id.edtxt_aadhar_ID);
        city = (TextView) view.findViewById(R.id.edtxt_City);
        ContactPerson = (TextView) view.findViewById(R.id.edtxt_contact_person);
        ContactPersonMobile = (TextView) view.findViewById(R.id.edtxt_contact_person_mobile);
        Referralsource = (TextView) view.findViewById(R.id.edtxt_reffs);
        address = (TextView) view.findViewById(R.id.edtxt_address);
        Pincode = (TextView) view.findViewById(R.id.edtxt_pin_code);
        height = (TextView) view.findViewById(R.id.edtxt_height);
        weight = (TextView) view.findViewById(R.id.edtxt_weight);
        marital_status = (TextView) view.findViewById(R.id.edtxt_marital);
        food_habbit = (TextView) view.findViewById(R.id.edtxt_food);
        age = (TextView) view.findViewById(R.id.edtxt_age);
        gender = (TextView) view.findViewById(R.id.edtxt_gender);
//        mBtnChangePassword = (Button)view.findViewById(R.id.btn_change_password);

        String fName=mUserModel.getResult().getUser().getFirstName();
        if(!(fName == null || fName.equalsIgnoreCase("null") || fName.equals(null)))
        mTxtName.setText(mUserModel.getResult().getUser().getFirstName() +" "+mUserModel.getResult().getUser().getLastName());

        mTxtEmail.setText(mUserModel.getResult().getUser().getEmail());
        mTxtPhone.setText(mUserModel.getResult().getUser().getMobile());
        aadharid.setText(mUserModel.getResult().getUser().getAadharId());
        city.setText(mUserModel.getResult().getUser().getCity());
        ContactPerson.setText(mUserModel.getResult().getUser().getContact_person());
        ContactPersonMobile.setText(mUserModel.getResult().getUser().getContact_person_mob());
        Referralsource.setText(mUserModel.getResult().getUser().getRef_source());
        address.setText(mUserModel.getResult().getUser().getAddress2());
        Pincode.setText(mUserModel.getResult().getUser().getPincode());
        height.setText(mUserModel.getResult().getUser().getHeight());
        weight.setText(mUserModel.getResult().getUser().getWeight());
        marital_status.setText(mUserModel.getResult().getUser().getMaritalStatus());
        food_habbit.setText(mUserModel.getResult().getUser().getFoodHabit());
        age.setText(mUserModel.getResult().getUser().getAge());
        gender.setText(mUserModel.getResult().getUser().getGender());

        try {
            String bitmap = mUserModel.getResult().getUser().getProfilePic();

            Log.e("stringToBitmap", bitmap.toString());
            ImageLoader.getInstance().displayImage(bitmap,mImgProfile);
//            Picasso.with(getActivity()).load(bitmap).into(mImgProfile);
        }
        catch (Exception e){

        }


    }

    @Override
    protected void setListener() {
        super.setListener();
//        mBtnChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changePassword();
//            }
//        });
    }


    @Override
    public void onPostSuccess(Object response, int id) {
        BaseModel baseResponse = (BaseModel) response;
        int status = 0;
        String msg = "";
        try {
            status = baseResponse.getStatus();
            msg = baseResponse.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            status = 0;
            msg = "Oops! problem to change password!";
        }


        if (status == 1) {

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            AppPreferences.getInstance(getActivity()).setUserLogin(false);
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.putExtra(BundleConst.IS_LOGOUT, true);
            startActivity(intent);
            getActivity().finish();

        } else {
            Utils.showMessage(getActivity(), msg);
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void changePassword(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ChangePasswordFragment fragment = ChangePasswordFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}