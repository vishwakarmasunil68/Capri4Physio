package com.capri4physio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.NotesPrintActivity;
import com.capri4physio.activity.ViewCaseReportActivity;
import com.capri4physio.activity.ViewPatientWalletReportActivity;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import viewreport.ViewIncomeTreatmentwiseReport;


public class ViewReportFragment extends BaseFragment {
    String[] IteamList;

    ListView list;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    TextView textDoc;
    public static String status;
    String GetURL = ApiConfig.BASE_URL + "users/servervalueget";

    public static ViewReportFragment newInstance() {
        ViewReportFragment fragment = new ViewReportFragment();
        return fragment;
    }

    public ViewReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("4")) {
            IteamList = new String[]{"Case Report", "Income Statement", "Income (Treatment Wise)","Income (Therapist Wise)","Income (Patient Wise)", "Expense Report", "Patient Wallet Report","Case Notes Report","Progress Report","Remark Report"};
//            IteamList = new String[]{"Case Report", "Income Statement", "Income(Treatment Wise)","Income(Therapist Wise)","Income(Patient Wise)", "Expense Report", "Balance Sheet", "Productivity(From Invoice)"};
        } else {
            if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
                IteamList = new String[]{"Case Report","Income","Income (Patient Wise)"};
            } else {
                IteamList = new String[]{"Case Report","Income Statement", "Income (Treatment Wise)","Income (Therapist Wise)","Income (Patient Wise)", "Expense Report","Patient Wallet Report","Case Notes Report","Progress Report","Remark Report"};
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_doctorsecond, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        textDoc = (TextView) rootView.findViewById(R.id.textDoc);
        list.setAdapter(new customAdapter());
        return rootView;
    }

    public class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IteamList.length;
        }

        @Override
        public Object getItem(int position) {
            return IteamList;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            convertView = inflater.inflate(R.layout.listitem, null);
            TextView text_listitem = (TextView) convertView.findViewById(R.id.text_listitem);
            ImageView image_iteam = (ImageView) convertView.findViewById(R.id.image_pic);
            text_listitem.setText(IteamList[position]);
//                image_iteam.setImageResource(imageId [position]);
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openScreens(position);
                }
            });
            return convertView;
        }
    }

    public void openScreens(int position) {
//        if(AppPreferences.getInstance(getActivity().getApplicationContext()).equals("4")||
//                AppPreferences.getInstance(getActivity().getApplicationContext()).equals("1")) {
        Intent intent;
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("4")) {
            if (position == 0) {
//                startActivity(new Intent(getActivity(), WebCaseReport.class));
                startActivity(new Intent(getActivity(), ViewCaseReportActivity.class));
            } else if (position == 1) {
                startActivity(new Intent(getActivity(), ViewIncomeReport.class));
            } else if (position == 2) {
                startActivity(new Intent(getActivity(), ViewIncomeTreatmentwiseReport.class));
            } else if(position==3){
                startActivity(new Intent(getActivity(), ViewIncomeTherapistWise.class));
            }else if(position==4){
                startActivity(new Intent(getActivity(), IncomePatientWise.class));
            }else if (position == 5) {
                startActivity(new Intent(getActivity(), ViewExpenseReport.class));
            }
//            else if (position == 6) {
//                startActivity(new Intent(getActivity(), ViewStaff_Pro_InvoReport.class));
//            }
            else if(position==6){
                startActivity(new Intent(getActivity(),ViewPatientWalletReportActivity.class));
            }else if(position==7){
                intent=new Intent(getActivity(), NotesPrintActivity.class);
                intent.putExtra("notetype","CASE");
                startActivity(intent);
            }else if(position==8){
                intent=new Intent(getActivity(), NotesPrintActivity.class);
                intent.putExtra("notetype","PROGRESS");
                startActivity(intent);
            }else if(position==9){
                intent=new Intent(getActivity(), NotesPrintActivity.class);
                intent.putExtra("notetype","REMARK");
                startActivity(intent);
            }

        } else {
            if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
                if (position == 0) {
                    startActivity(new Intent(getActivity(), ViewCaseReportActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(getActivity(), ViewIncomeTherapistWise.class));
                } else if (position == 2) {
                    startActivity(new Intent(getActivity(), IncomePatientWise.class));
                }
//                else if(position==3){
//                    startActivity(new Intent(getActivity(), ViewStaff_Pro_InvoReport.class));
//                }
            } else {
                if (position == 0) {
                    startActivity(new Intent(getActivity(), ViewCaseReportActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(getActivity(), ViewIncomeReport.class));
                } else if (position == 2) {
                    startActivity(new Intent(getActivity(), ViewIncomeTreatmentwiseReport.class));
                } else if (position == 3) {
                    startActivity(new Intent(getActivity(), ViewIncomeTherapistWise.class));
                }else if (position == 4) {
                    startActivity(new Intent(getActivity(), IncomePatientWise.class));
                }else if (position == 5) {
                    startActivity(new Intent(getActivity(), ViewExpenseReport.class));
                }
//                else if (position == 6) {
//                    startActivity(new Intent(getActivity(), ViewStaff_Pro_InvoReport.class));
//                }
                else if(position==6){
                    startActivity(new Intent(getActivity(),ViewPatientWalletReportActivity.class));
                }else if(position==7){
                    intent=new Intent(getActivity(), NotesPrintActivity.class);
                    intent.putExtra("notetype","CASE");
                    startActivity(intent);
                }else if(position==8){
                    intent=new Intent(getActivity(), NotesPrintActivity.class);
                    intent.putExtra("notetype","PROGRESS");
                    startActivity(intent);
                }else if(position==9){
                    intent=new Intent(getActivity(), NotesPrintActivity.class);
                    intent.putExtra("notetype","REMARK");
                    startActivity(intent);
                }

            }
        }
//            if (position == 6) {
//                startActivity(new Intent(getActivity(), ViewIncomeSessionwiseReport.class));
//            }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Report");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("start", "onpause");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }
}