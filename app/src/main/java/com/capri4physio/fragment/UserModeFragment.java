package com.capri4physio.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.util.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserModeFragment extends BaseFragment {

    private View mViewPatient, mViewBranchAdmin, mViewTherapist;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserModeFragment.
     */
    public static UserModeFragment newInstance() {
        UserModeFragment fragment = new UserModeFragment();
        return fragment;
    }

    public UserModeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        ((SplashActivity) getActivity()).getSupportActionBar().show();
        mViewPatient = (View) view.findViewById(R.id.layout_patient);
        mViewBranchAdmin = (View) view.findViewById(R.id.layout_branch_admin);
        mViewTherapist = (View) view.findViewById(R.id.layout_therapist);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpDetailFragment fragment = SignUpDetailFragment.newInstance(Constants.GlobalConst.USER_PATIENT);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewBranchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpStudentDetailFragment fragment = SignUpStudentDetailFragment.newInstance(Constants.GlobalConst.USER_STUDENT);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewTherapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpDetailFragment fragment = SignUpDetailFragment.newInstance(Constants.GlobalConst.USER_THERAPIST);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }
}