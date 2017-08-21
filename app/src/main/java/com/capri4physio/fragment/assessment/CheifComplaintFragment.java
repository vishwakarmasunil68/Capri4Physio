package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;

import com.capri4physio.R;
import com.capri4physio.adapter.assessment.CheifComplaintAdapter;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.assessment.CheifComplaint;
import com.capri4physio.model.assessment.CheifComplaintModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheifComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class CheifComplaintFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<CheifComplaint> {

    private RecyclerView mRecyclerView;
    private Button mBtnAdd;
    private CoordinatorLayout mSnackBarLayout;
    private CheifComplaintAdapter mAdapter;
    private List<CheifComplaint> mList;
    private int itemPosition;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private Button btn_skip;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static CheifComplaintFragment newInstance(String patientId, String assessmentType) {
        CheifComplaintFragment fragment = new CheifComplaintFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public CheifComplaintFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            assessmentType = getArguments().getString(KEY_TYPE);
        }

        mList = new ArrayList<>();
        mAdapter = new CheifComplaintAdapter(getActivity(), mList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cheif_complaint, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewAssessmentApiCall();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        btn_skip = (Button) view.findViewById(R.id.btn_skip);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mBtnAdd = (Button) view.findViewById(R.id.btn_add);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment();
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                HandlerConstant.POP_BACK_HANDLER.sendMessage(HandlerConstant.POP_BACK_HANDLER.obtainMessage(0,"1"));
            }
        });

    }


    private void addFragment() {
        Intent intent = new Intent(getActivity(), AddComplaintFragment.class);
        intent.putExtra("patient_id", patientId);
        intent.putExtra("type", assessmentType);
        startActivityForResult(intent, 2);
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddComplaintFragment addComplaintFragment = AddComplaintFragment.newInstance(patientId, assessmentType);
        ft.replace(R.id.fragment_container, addComplaintFragment);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    /**
     * @return none
     * @description view assessment web service API calling
     */
    private void viewAssessmentApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, "ChiefComplaint");

                new UrlConnectionTask(getActivity(), ApiConfig.VIEW_ASSESSMENT_URL, ApiConfig.ID1, true, params, CheifComplaintModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    private void deleteApiCall(String recordId, int position1) {
        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.ASSESSMENT_TYPE, "ChiefComplaint");
                params.put(ApiConfig.DELET_ID, recordId);
                position = position1;
                new UrlConnectionTask(getActivity(), ApiConfig.DELETE_ASSESSMENT_URL, ApiConfig.ID2, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    int position;

    private void deleteAlert(final String id, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to delete");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteApiCall(id, position);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {
            case ApiConfig.ID1:
                CheifComplaintModel cheifComplaint = (CheifComplaintModel) response;
                AppLog.i("Capri4Physio", "Assessment type - CheifComplaintModel: " + cheifComplaint.getStatus());
                if (cheifComplaint.result.size() > 0) {
                    mList.clear();
                    mList.addAll(cheifComplaint.result);
                    Collections.reverse(mList);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case ApiConfig.ID2:
                BaseModel deleteResponse = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient delete Response : " + deleteResponse.getStatus());
                if (deleteResponse.getStatus() > 0) {
                    mList.remove(position);
                    mAdapter.notifyDataSetChanged();
                    showSnackMessage(deleteResponse.getMessage());
                    position = -1;
                }

        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("start", "onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Chief Complaint");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Chief Complaint");
    }

    @Override
    public void onViewItemClick(CheifComplaint complaint, int position, int actionId) {
        if (position == -2) {
            getFragmentManager().popBackStack();
        } else {
            deleteAlert(complaint.getId(), position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            Log.d("requestcode", "result");
//            viewAssessmentApiCall();

            btn_skip.callOnClick();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
    }

    /**
     * @return none
     * @description Login web service API calling
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alert) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}