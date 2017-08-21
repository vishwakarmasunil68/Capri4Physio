package com.capri4physio.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.capri4physio.R;
import com.capri4physio.Services.LocationService;
import com.capri4physio.activity.ChatUserActivity;
import com.capri4physio.activity.CourseActivity;
import com.capri4physio.activity.ManageAppointmentActivity;
import com.capri4physio.activity.ScheduleActivity;
import com.capri4physio.activity.TherapistActivity;
import com.capri4physio.activity.TreatmentActivity;
import com.capri4physio.addbranch.AddBranchHeadGFragment;
import com.capri4physio.listener.FragmentListener;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;

import alarm.AlarmManageService;


public class StaffDashboardFragment extends BaseFragment {

    private View mViewMyPatient;
    private RelativeLayout message, addbranch, img_message2;
    private RelativeLayout img_chat1;
    Boolean indexing;
    private FragmentListener mListener;
    private View mViewStaff;
    private View schedule;
    private View Report;
    private View Billing;
    private View rl_course;
    ProgressDialog pDialog;
    private View Expense,rl_therapist;
    ImageView img_chat;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static StaffDashboardFragment newInstance() {
        StaffDashboardFragment fragment = new StaffDashboardFragment();
        return fragment;
    }

    public StaffDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentListener) {
            mListener = (FragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("3")
                || AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("1")) {
            Log.d("sunil", "view111");
            rootView = inflater.inflate(R.layout.fragment_staff_dashboard_clinic_head, container, false);
            if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("3")) {
                if (AppPreferences.getInstance(getActivity()).getClinicCount() == 1) {

                } else {
//                    addNewClinic();
                }
            }
        } else {
            if(AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")){
                rootView = inflater.inflate(R.layout.fragment_doctor_dashboard, container, false);
                if(!isMyServiceRunning(LocationService.class)){
                    getActivity().startService(new Intent(getActivity(), LocationService.class));
                }

                Log.d(TagUtils.getTag(),"device token:-"+AppPreferences.GetDeviceToken(getActivity().getApplicationContext()));

            }else{
                rootView = inflater.inflate(R.layout.fragment_staff_dashboard, container, false);
            }

            Log.d("sunil", "view222");


        }

        initView(rootView);
        setListener();
        setHasOptionsMenu(true);
        return rootView;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewMyPatient = (View) view.findViewById(R.id.layout_patient);
        mViewStaff = (View) view.findViewById(R.id.layout_staff);
        message = (RelativeLayout) view.findViewById(R.id.messag1);
//        img_message2 = (RelativeLayout) view.findViewById(R.id.img_message2);
        img_chat1 = (RelativeLayout) view.findViewById(R.id.img_chat1);
        img_chat = (ImageView) view.findViewById(R.id.img_chat);
        schedule = (View) view.findViewById(R.id.schedule);
        Report = (View) view.findViewById(R.id.layout_report);
        Billing = (View) view.findViewById(R.id.img_billing);
        Expense = (View) view.findViewById(R.id.layout_expense);
        rl_therapist = (View) view.findViewById(R.id.rl_therapist);
        addbranch = (RelativeLayout) view.findViewById(R.id.addbranch);
        rl_course = (RelativeLayout) view.findViewById(R.id.rl_course);

        try{
            View rl_appointment=view.findViewById(R.id.rl_appointment);
            rl_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ManageAppointmentActivity.class));
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            View rl_treatment=view.findViewById(R.id.rl_treatment);
            rl_treatment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), TreatmentActivity.class));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")) {
            message.setVisibility(View.VISIBLE);

            addbranch.setVisibility(View.VISIBLE);
        }
        if(img_chat1!=null) {
            img_chat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChatUserActivity.class);
                    intent.putExtra("user_type", AppPreferences.getInstance(getActivity()).getUserType());
                    startActivity(intent);
                }
            });
        }else{
            img_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChatUserActivity.class);
                    intent.putExtra("user_type", AppPreferences.getInstance(getActivity()).getUserType());
                    startActivity(intent);
                }
            });
        }
        try {
            rl_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), CourseActivity.class));
                }
            });

            rl_therapist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), TherapistActivity.class));
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addNewClinic() {
        AddClincFragment fragment = AddClincFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mViewMyPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialogopt);
                dialog.setTitle("Select -type");
                LinearLayout OPD = (LinearLayout) dialog.findViewById(R.id.OPD);
                dialog.setCancelable(true);
                LinearLayout Homevisit = (LinearLayout) dialog.findViewById(R.id.hvisit);


                Homevisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing = false;
                        dialog.dismiss();
                        viewPatient();
                    }
                });

                OPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing = true;
                        dialog.dismiss();
                        viewPatient();
                    }
                });
                dialog.show();
                /*viewPatient();*/
            }
        });
        mViewStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewStaff();
            }
        });

        addbranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addbranch();
            }
        });
        try {
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewclinic();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createDevice("");
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });

//        img_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgressDialog("Please wait...");
//                registerApiCall();
//            }
//        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewReport();
            }
        });

        Billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewBilling();
            }
        });

        Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewExpense();
            }
        });

    }

    private void createDevice(String capabilityToken) {
        Intent serviceIntent = new Intent(getActivity(), AlarmManageService.class);
        getActivity().startService(serviceIntent);
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main_staff, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem menuItem1 = menu.findItem(R.id.action_alert);
        menuItem1.setVisible(false);
        int mNotifCount = 0;
//        View count = menu.findItem(R.id.action_notification).getActionView();

    }

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
        if (id == R.id.action_new) {
            addNewStaff();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void addNewStaff() {
        NewStaffFragment fragment = NewStaffFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void viewReport() {

        ViewReportFragment reportfragment = ViewReportFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, reportfragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void viewExpense() {
        ViewExpenseFragment billfragment = ViewExpenseFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        getActivity().getActionBar().setTitle("hii");
        ft.replace(R.id.fragment_container, billfragment);
        ft.addToBackStack("Staff Dashboard");
        ft.commit();
    }

    private void viewBilling() {
        ViewBillingFragment billfragment = ViewBillingFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(0, billfragment, "login");
        ft.replace(R.id.fragment_container, billfragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void viewclinic() {
        ClinicHeadFragment fragment = ClinicHeadFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void viewStaff() {
        ViewStaffFragment fragment = ViewStaffFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void addbranch() {
        AddBranchHeadGFragment fragment = AddBranchHeadGFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void viewPatient() {
        Log.d("sunil", "in view patient");
        ViewPatientFragment fragment = ViewPatientFragment.newInstance(indexing);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void viewschedule() {
        ViewSceduleFragment fragment = ViewSceduleFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exit();
    }

    private void exit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                getActivity().finish();

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }
}
