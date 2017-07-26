package com.capri4physio.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment implements OnBackPressed{
    private BaseFragment _currentFragment;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static BaseFragment newInstance(ActionBar actionBar) {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }

    public BaseFragment() {
        // Required empty public constructor
    }


    /**
     * Initialize all the view controls here
     * @return
     */
    protected  void initView(View view){}

    /**
     * Set listener on required view controls
     * @return
     */
    protected  void setListener(){}


    @Override
    public void onBackPressed() {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();
        Log.d("backstack","popbackiscall");
        getFragmentManager().popBackStack();

        fragmentTransaction.commit();
    }
}