package com.capri4physio.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.capri4physio.R;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.ClinicsDetailsModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;


public class MyClinicFragment extends BaseFragment implements HttpUrlListener {

    private String mClinicId = "";
    private static final String CLINIC_ID = "clinic_id";
    private ImageView mImgLogo;
    private TextView mTxtTitle;
    private TextView mTxtLocation;
    private TextView mTxtDesc;
    private RatingBar mRatingBar;
    private DisplayImageOptions options;

    public MyClinicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyClinicFragment.
     */

    public static MyClinicFragment newInstance(String clinicId) {
        MyClinicFragment fragment = new MyClinicFragment();
        Bundle args = new Bundle();
        args.putString(CLINIC_ID, clinicId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mClinicId = getArguments().getString(CLINIC_ID);
            AppLog.i("App", "mUserType  :" + mClinicId);
        }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_clinic, container, false);
        initView(rootView);
        setListener();
        clinicApiCall();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mImgLogo = (ImageView) view.findViewById(R.id.img_logo);
        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mTxtLocation = (TextView) view.findViewById(R.id.txt_location);
        mTxtDesc = (TextView) view.findViewById(R.id.txt_desc_value);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main,menu);
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
    private void clinicApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {

                JSONObject params = new JSONObject();
                params.put(ApiConfig.CLINIC_ID, mClinicId);
                new UrlConnectionTask(getActivity(), ApiConfig.CLINIC_URL, ApiConfig.ID1, true, params, ClinicsDetailsModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onPostSuccess(Object response, int id) {

        switch (id) {
            case ApiConfig.ID1:

                ClinicsDetailsModel clinicsDetailsModel = (ClinicsDetailsModel) response;
                if (clinicsDetailsModel.getStatus() == 1) {

                    AppLog.i("Capri4Physio", "ClinicsDetailsModel Response : " + clinicsDetailsModel.getMessage());
                    mTxtTitle.setText(clinicsDetailsModel.getResult().clinicName);
                    mTxtLocation.setText(clinicsDetailsModel.getResult().getLocation());
                    mTxtDesc.setText(clinicsDetailsModel.getResult().getDescription());
                    ImageLoader.getInstance().displayImage(clinicsDetailsModel.getResult().getProfilePic(), mImgLogo, options);

                } else {
                    Utils.showMessage(getActivity(), clinicsDetailsModel.getMessage());
                }

                break;

            case ApiConfig.ID3:
                BaseModel baseResponse = (BaseModel) response;
                Utils.showMessage(getActivity(), baseResponse.getMessage());
                break;

            default:
                AppLog.e("Capri4Physio", "UNKNOW RESPONSE - " + id);
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }
}
