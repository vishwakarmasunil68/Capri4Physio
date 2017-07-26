package com.capri4physio.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capri4physio.R;
import com.capri4physio.adapter.PracticeAvailibilityAdapter;
import com.capri4physio.dialog.ChangePasswordDialog;
import com.capri4physio.dialog.UpdatePracticeHoursDialog;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.PracticHoursModel;
import com.capri4physio.model.PracticeItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffPracticeHoursFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-May-08
 */
public class StaffPracticeHoursFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<PracticeItem>, DialogListener<Bundle> {

    private RecyclerView mRecyclerView;
    private List<PracticeItem> mList;
    private PracticeAvailibilityAdapter mAdapter;
    private PracticeItem mPracticeItem;
    int index = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StaffPracticeHoursFragment.
     */
    public static StaffPracticeHoursFragment newInstance() {
        StaffPracticeHoursFragment fragment = new StaffPracticeHoursFragment();
        return fragment;
    }

    public StaffPracticeHoursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mAdapter = new PracticeAvailibilityAdapter(getActivity(), mList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_practice_hours_staff, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        getPracticeAvailibilityAPI();

    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    public void onViewItemClick(PracticeItem practiceItem, int position, int actionId) {
        index = position;
        mPracticeItem = practiceItem;
        showUpdateDialog(practiceItem);
    }

    private void showUpdateDialog(PracticeItem practiceItem) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdatePracticeHoursDialog practiceHoursDialog = UpdatePracticeHoursDialog.newInstance(practiceItem.getStartTime(), practiceItem.getEndTime());
        practiceHoursDialog.setDialogListener(this);
        practiceHoursDialog.show(ft, "dialog");
    }

    /**
     * @return none
     * @description get availibility web service API calling
     */
    private void getPracticeAvailibilityAPI() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(getActivity()).getUserID());

                new UrlConnectionTask(getActivity(), ApiConfig.PRACTICE_TIME_URL, ApiConfig.ID1, true, params, PracticHoursModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    /**
     * @return none
     * @description update availibility web service API calling
     */
    private void updateAvailibilityApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(getActivity()).getUserID());
                params.put(ApiConfig.DAY, mPracticeItem.getDay());
                params.put(ApiConfig.START_TIME, mPracticeItem.getStartTime());
                params.put(ApiConfig.END_TIME, mPracticeItem.getEndTime());
                params.put(ApiConfig.DATE, "");

                new UrlConnectionTask(getActivity(), ApiConfig.CHANGE_AVAILIBILITY_URL, ApiConfig.ID2, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        int status = 0;
        String msg = "";

        switch (id) {
            case ApiConfig.ID1:
                PracticHoursModel practicHoursModel = (PracticHoursModel) response;
                try {
                    status = practicHoursModel.getStatus();
                    msg = practicHoursModel.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    msg = "Oops! problem to get availibility";
                }

                if (status == 1) {
                    mList.clear();
                    mList.addAll(practicHoursModel.result);
                    mAdapter.notifyDataSetChanged();

                } else {
                    Utils.showMessage(getActivity(), msg);
                }

                break;

            case ApiConfig.ID2:
                BaseModel baseResponse2 = (BaseModel) response;
                try {
                    status = baseResponse2.getStatus();
                    msg = baseResponse2.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    msg = "Oops! problem to update availibility";
                }

                if (status == 1) {
                    mList.set(index, mPracticeItem);
                    mAdapter.notifyDataSetChanged();

                } else {
                    Utils.showMessage(getActivity(), msg);
                }
                break;
        }


    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onDialogResult(Bundle bundle, int Id) {
        mPracticeItem.startTime = bundle.getString(ApiConfig.START_TIME);
        mPracticeItem.endTime = bundle.getString(ApiConfig.END_TIME);
        updateAvailibilityApiCall();
    }
}