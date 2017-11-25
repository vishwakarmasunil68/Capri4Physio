package com.capri4physio.addbranch;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBranchHeadGFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-03-31
 */
public class AddBranchHeadGFragment extends BaseFragment implements WebServicesCallBack{
    private static final String CALL_BRANCH_ADD_API = "call_add_branch_api";
    String GetURL = ApiConfig.BASE_URL + "users/getuserlist";

    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_website)
    EditText et_website;


    Spinner spinnerbranchloca;
    private ProgressDialog pDialog;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static AddBranchHeadGFragment newInstance() {
        AddBranchHeadGFragment fragment = new AddBranchHeadGFragment();
        return fragment;
    }

    public AddBranchHeadGFragment() {
        // Required empty public constructor
    }

    String[] location = {"Gurgaon", "Sant Parmanand Hospital", "Greater Kailash 1", "Karkarduma"};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_cbranhc, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateEdits(et_address,et_city,et_code,et_country,et_email,et_state,et_mobile,et_name,et_phone,et_pincode,et_website)){
                    if(et_code.getText().length()==6) {
                        callAddBranchAPI();
                    }else{
                        ToastClass.showShortToast(getActivity().getApplicationContext(),"Branch code length must be of 6 characters");
                    }
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter All Fields Properly");
                }
            }
        });
    }

    public void callAddBranchAPI(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("branch_name",et_name.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code",et_code.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("mobile",et_mobile.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("phone",et_phone.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("email",et_email.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("address",et_address.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("city",et_city.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("state",et_state.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("country",et_country.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pincode",et_pincode.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("website",et_website.getText().toString()));
        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,CALL_BRANCH_ADD_API).execute(ApiConfig.ADD_BRANCH_HEAD);
    }

    public boolean ValidateEdits(EditText... editTexts){
        for(EditText editText:editTexts){
            if(editText.getText().length()==0){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setListener() {
        super.setListener();


    }


    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Branch");
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case CALL_BRANCH_ADD_API:
                parseBranchAResponse(response);
                break;
        }
    }
    public void parseBranchAResponse(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            if(new JSONObject(response).optString("success").equals("true")){
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Branch Added");
                getFragmentManager().popBackStack();
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Failed to add branch");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Failed to add branch");
        }
    }
}