package com.capri4physio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONObject;

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
       /* mList = new ArrayList<>();
        mAdapter =new UsersAdapter(getActivity(), mList, this);*/
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("1")) {
            IteamList = new String[]{"Case Report"};
        } else {
            IteamList = new String[]{"Case Report", "Income Statement", "Income(Treatment Wise)", "Expense Report ", "Balance Sheet", "Productivity(From Invoice)", "Staff Productivity(Session Wise)"};
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
                    int click = (Integer) v.getId();
                    if (position == 0) {
                        startActivity(new Intent(getActivity(), ViewCaseReport.class));
                    } else if (position == 1) {
                        startActivity(new Intent(getActivity(), ViewIncomeReport.class));
                    } else if (position == 2) {
                        startActivity(new Intent(getActivity(), ViewIncomeTreatmentwiseReport.class));
                    } else if (position == 3) {
                        startActivity(new Intent(getActivity(), ViewExpenseReport.class));
                    } else if (position == 4) {
                        startActivity(new Intent(getActivity(), ViewBalanceReport.class));
                    }
                    if (position == 5) {
                        startActivity(new Intent(getActivity(), ViewStaff_Pro_InvoReport.class));
                    }
                    if (position == 6) {
                        startActivity(new Intent(getActivity(), ViewIncomeSessionwiseReport.class));
                    }
                }
            });
            return convertView;
        }
    }

    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONObject jsonobject = new JSONObject(s);
                String status_msg = jsonobject.getString("status");
                status = status_msg;
            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
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