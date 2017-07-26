package com.capri4physio.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.adapter.SlotAdapter;
import com.capri4physio.dialog.PaymentTypeDialog;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.AvailibilityModel;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;
import com.capri4physio.view.Ranger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AvailabilityFragment extends BaseFragment implements HttpUrlListener,DialogListener<String> {


    private Ranger mRanger;
    private TextView mTxtTitle;
    private EditText mEdtxtReason;
    private Button mBtnBookAppointment;
    private GridView mGridView;
    private RadioGroup mRadioGroup;

    private static final String CLINIC_ID = "clinic_id";
    private static final String CLINIC_USER_ID = "clinic_user_id";
    private SlotAdapter mAdapter;
    private List<String> mList;
    private int timeDay = 0;
    private String timeSlots = "";
    private String mClinicUserId = "";
    private String mClinicId = "";
    private String mVisitType = "";
    private String mPaymentType = "";


    public AvailabilityFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AvailabilityFragment.
     */

    public static AvailabilityFragment newInstance(String clinicId, String clincUserId) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        Bundle args = new Bundle();
        args.putString(CLINIC_ID, clinicId);
        args.putString(CLINIC_USER_ID, clincUserId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();
        mAdapter = new SlotAdapter(getActivity(), mList);

        if (getArguments() != null) {
            mClinicId = getArguments().getString(CLINIC_ID);
            mClinicUserId = getArguments().getString(CLINIC_USER_ID);
            AppLog.i("App", "Availability clinic Id  :" + mClinicId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_availability, container, false);
        initView(rootView);
        setListener();
        availibilityApiCall();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        mRanger = (Ranger) view.findViewById(R.id.listener_ranger);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mEdtxtReason = (EditText) view.findViewById(R.id.edtxt_reason);
        mBtnBookAppointment = (Button) view.findViewById(R.id.btn_book_appointment);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mTxtTitle.setText(Utils.getDateMonth());
        mGridView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRanger.setDayViewOnClickListener(new Ranger.DayViewOnClickListener() {
            @Override
            public void onDaySelected(int day) {
                //Toast.makeText(getActivity(),"selected day : "+day, Toast.LENGTH_LONG).show();
                timeDay = day;
                timeSlots = "";
                availibilityApiCall();

            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"this is the point",Toast.LENGTH_SHORT).show();
                AppLog.i("Capri4Physio", "setOnItemClickListener Result : " + mList.get(i));
                timeSlots = mList.get(i);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    mVisitType = rb.getText().toString();
                }
            }
        });

        mBtnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bookAppointmentApiCall();
                checkPaymentType();
            }
        });
    }

    /**
     * @return none
     * @description Login web service API calling
     */
    private void availibilityApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {

                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, mClinicUserId);
                params.put(ApiConfig.DATE, "2016-06-03");
                new UrlConnectionTask(getActivity(), ApiConfig.AVAILIBILITY_URL, ApiConfig.ID1, true, params, AvailibilityModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    private void bookAppointmentApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, AppPreferences.getInstance(getActivity()).getUserID());
                params.put(ApiConfig.USER_ID, mClinicUserId);
                params.put(ApiConfig.REASON, mEdtxtReason.getText().toString().trim());
                params.put(ApiConfig.BOOKING_DAY, "2016-06-03");
                params.put(ApiConfig.BOOKING_DATE, "2016-06-03");
                params.put(ApiConfig.BOOKING_START_TIME, "09:00 AM");
                params.put(ApiConfig.BOOKING_END_TIME, "10:00 AM");
                params.put(ApiConfig.BOOKING_TYPE, mVisitType);
                params.put(ApiConfig.BOOKING_PAYMENT_TYPE, mPaymentType);
                new UrlConnectionTask(getActivity(), ApiConfig.BOOK_APPOINTMNET_URL, ApiConfig.ID2, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    private void checkPaymentType() {
        if (!mVisitType.isEmpty() && mVisitType.equals("OPD patient")) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            PaymentTypeDialog paymentTypeDialog = PaymentTypeDialog.newInstance("Pay at clinic");
            paymentTypeDialog.setDialogListener(this);
            paymentTypeDialog.show(ft, "dialog");

        } else if (!mVisitType.isEmpty() && mVisitType.equals("Home visit")) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            PaymentTypeDialog paymentTypeDialog = PaymentTypeDialog.newInstance("Pay at home");
            paymentTypeDialog.setDialogListener(this);
            paymentTypeDialog.show(ft, "dialog");
        }else{
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_visit_type));
        }
    }

    @Override
    public void onPostSuccess(Object response, int id) {


        switch (id) {
            case ApiConfig.ID1:
                AvailibilityModel availibilityModel = (AvailibilityModel) response;
                if (availibilityModel.getStatus() == 1) {
                    AppLog.i("Capri4Physio", "AvailibilityModel Response : " + availibilityModel.getMessage());
                    AppLog.i("Capri4Physio", "AvailibilityModel Result : " + availibilityModel.getResult());
                    mList.clear();
                    String slots = availibilityModel.getResult();
                    if (slots != null) {
                        mList.addAll(Arrays.asList(slots.split(",")));
                    }
                    mAdapter.notifyDataSetChanged();

                } else {
                    Utils.showMessage(getActivity(), availibilityModel.getMessage());
                }

                break;

            case ApiConfig.ID2:
                BaseModel baseResponse = (BaseModel) response;
                Toast.makeText(getActivity(), baseResponse.getMessage(), Toast.LENGTH_LONG).show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                PatientDashboardFragment fragment = PatientDashboardFragment.newInstance();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
//                Utils.showMessage(getActivity(), baseResponse.getMessage());
                break;

            default:
                AppLog.e("Capri4Physio", "UNKNOW RESPONSE - " + id);

        }


    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onDialogResult(String paymentType, int Id) {
        mPaymentType = paymentType;
        bookAppointmentApiCall();
    }

    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String txtReason = mEdtxtReason.getText().toString().trim();
        if (txtReason.isEmpty()) {
            mEdtxtReason.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_reason_booking));
            return false;
        }

        if (timeSlots.isEmpty()) {
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_select_slot));
            return false;
        }

        return true;
    }

    private String getSelectedDate() {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, timeDay);
        return strDate;
    }


}
