package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jatinder on 10-06-2016.
 */
public class AddRemarksFragment extends BaseFragment implements HttpUrlListener,WebServicesCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private static final String CALL_ADD_REMARK_NOTES_API = "call_add_remark_api";
    private String patientId = "";
    private String assessmentType = "";
    private AutoCompleteTextView et_remark;
    private Button mSave;
    private LinearLayout ll_add_notes;
    List<EditText> editTextList=new ArrayList<>();
    private DatabaseReference root;
    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    public static AddRemarksFragment newInstance(String patientId, String assessmentType) {
        AddRemarksFragment fragment = new AddRemarksFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddRemarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            assessmentType = getArguments().getString(KEY_TYPE);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_remark, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }
    ArrayAdapter<String> arrayAdapter;
    List<String> list_doses = new ArrayList<>();
    Set<String> set_doses= new HashSet<>();
    @Override
    protected void initView(View view) {
        super.initView(view);
        et_remark = (AutoCompleteTextView) view.findViewById(R.id.et_remark);
        ll_add_notes = (LinearLayout) view.findViewById(R.id.ll_add_notes);
        mSave = (Button) view.findViewById(R.id.btn_save);

        editTextList.add(et_remark);


        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        et_remark.setThreshold(1);
        //Setting adapter
        et_remark.setAdapter(arrayAdapter);

        root.child("remarknote").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_doses.clear();
                set_doses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TagUtils.getTag(), "doses datashapshot:-" + postSnapshot.getValue());
                    set_doses.add(postSnapshot.getValue().toString());
                }
                list_doses.addAll(set_doses);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Remarks");
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_remark.getText().toString().length() > 0) {
                    addRemarks();
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter Notes Properly.");
                }
            }
        });
    }

    public void addRemarks() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            for (EditText editText : editTextList) {
                if (editText.getText().toString().length() != 0) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("note_date", Utils.getCurrentDate());
                    jsonObject1.put("patient_id", patientId);
                    jsonObject1.put("note", editText.getText().toString());

                    array.put(jsonObject1);
                }
            }

            jsonObject.put("data", array);

            ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
            nameValuePairArrayList.add(new BasicNameValuePair("data", jsonObject.toString()));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_ADD_REMARK_NOTES_API).execute(ApiConfig.ADD_REMARK_NOTES);


            for(EditText editText:editTextList) {
                if(editText.getText().toString().length()!=0) {
                    String mGroupId = root.push().getKey();
                    root.child("remarknote").child(mGroupId).setValue(editText.getText().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMotorAPi() {
        final String name = et_remark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.REMARKS_NOTES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getActivity(), "Record Added Successfully", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                            et_remark.setText("");
                            Log.e("result", response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("moter_exam_remarkdate", Utils.getCurrentDate());
                objresponse.put("patient_id", patientId);
                objresponse.put("moter_exam_remarkdesc", name);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_branch, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:
                addEdittext();
                return false;
            default:
                break;
        }

        return false;
    }
    public void addEdittext() {
        AutoCompleteTextView editText = new AutoCompleteTextView(getActivity());
        editText.setHint("Enter Remarks");
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll_add_notes.addView(editText);
        editTextList.add(editText);

        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        editText.setThreshold(1);
        //Setting adapter
        editText.setAdapter(arrayAdapter);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_ADD_REMARK_NOTES_API:
                parseAddProgressNotesAPI(response);
                break;
        }
    }


    public void parseAddProgressNotesAPI(String response) {
        Log.d(TagUtils.getTag(), "case notes response:-" + response);
        try {
            if (new JSONObject(response).optString("success").equals("true")) {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Remarks Added Successfully");
                getFragmentManager().popBackStack();
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Failed to add Remarks notes");
            }
        } catch (Exception e) {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Server Down");
            e.printStackTrace();
        }
    }

}