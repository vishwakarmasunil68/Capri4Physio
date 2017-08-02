package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.ImageUtil;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class AddNeuroFragment extends BaseFragment implements HttpUrlListener {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    public static String Neuro1,Neuro2,Neuro3,Neuro4,Neuro5,Neuro6,Neuro7,Neuro8,Neuro9,Neuro10,Neuro11,Neuro12,Neuro13,Neuro14,Neuro15,Neuro16,Neuro17,Neuro18,Neuro19,Neuro20
            ,Neuro21,Neuro22,Neuro23,Neuro24,Neuro25,Neuro26,Neuro27,Neuro28,Neuro29,Neuro30,Neuro31,Neuro32,Neuro33,Neuro34,Neuro35,Neuro36,Neuro37,Neuro38,Neuro39,Neuro40,Neuro41,Neuro42,Neuro43,Neuro44
    ,Neuro45,Neuro46,Neuro47,Neuro48,Neuro49,Neuro50,Neuro51,Neuro52,Neuro53,Neuro54,Neuro55,Neuro56,Neuro57,Neuro58,Neuro59,Neuro60,Neuro61,Neuro62,Neuro63,Neuro64,Neuro65,Neuro66,Neuro67,Neuro68,Neuro69,Neuro70;
    public static EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10,ed11,ed12,ed13,ed14,ed15,ed16,ed17,ed18,ed19,ed20
            ,ed21,ed22,ed23,ed24,ed25,ed26,ed27,ed28,ed29,ed30,ed31,ed32,ed33,ed34,ed35,ed36,ed37,ed38,ed39,ed40,ed41,ed42,ed43
            ,ed46,ed47,ed48,ed49,ed50,ed51,ed52,ed53,ed54,ed55,Name,Description4,ed44,ed45;
    public static EditText sapp_right,sapp_left;
    Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9,spinner10,spinner11,spinner12,spinner13,spinner14,spinner15,spinner16
            ,spinner17,spinner18,spinner19,spinner20,spinner21,spinner22;
    ScrollView bott;
    String jsonfile;
    private String patientId = "7";
    String json;
    private String assessmentType = "";
    private EditText mBloodPresure;
    private EditText mTemp;
    private EditText mHrate;
    private EditText mRespiratoryRate;
    private EditText mPosture;
    private EditText mGait;
    private EditText mScarType;
    private EditText mDescription;
    private Spinner mBuiltOfThePatient;
    private Button mSave;
    ProgressDialog pDialog;
    private String mbuiltpataient = "";
    ListView listView1;
    String[] items = { "Head,Neck and trunk", "Combined Movement Assesment of spine", "Cervical spine", "Thoraccic spine", "Lumbar spine","Hip","Knee","Ankle","Toes","Halux"
            ,"Scapula","Shoulder","Elbow","Fore Arm","Wrist","Fingers","Thumb","Respiration","Measure"};
    ArrayAdapter<String> adapter;
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

    public static AddNeuroFragment newInstance(String patientId, String assessmentType) {
        AddNeuroFragment fragment = new AddNeuroFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddNeuroFragment() {
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
//            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_neuro, container, false);

        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
        bott=(ScrollView)rootView.findViewById(R.id.sv);
        spinner2=(Spinner)rootView.findViewById(R.id.spinner2);
        spinner3=(Spinner)rootView.findViewById(R.id.spinner3);
        spinner4=(Spinner)rootView.findViewById(R.id.spinner7_1);
        spinner5=(Spinner)rootView.findViewById(R.id.spinner7_2);
        spinner6=(Spinner)rootView.findViewById(R.id.spinner7_3);
        spinner7=(Spinner)rootView.findViewById(R.id.spinner7_4);
        spinner8=(Spinner)rootView.findViewById(R.id.spinner7_5);
        spinner9=(Spinner)rootView.findViewById(R.id.spinner7_6);
        spinner10=(Spinner)rootView.findViewById(R.id.spinner7_7);
        spinner11=(Spinner)rootView.findViewById(R.id.spinner7_8);
        spinner12=(Spinner)rootView.findViewById(R.id.spinner8_1);
        spinner13=(Spinner)rootView.findViewById(R.id.spinner8_2);
        spinner14=(Spinner)rootView.findViewById(R.id.spinner8_3);
        spinner15=(Spinner)rootView.findViewById(R.id.spinner8_4);
        spinner16=(Spinner)rootView.findViewById(R.id.spinner8_5);
        spinner17=(Spinner)rootView.findViewById(R.id.spinner8_6);
        spinner18=(Spinner)rootView.findViewById(R.id.spinner8_7);
        spinner19=(Spinner)rootView.findViewById(R.id.spinner8_8);
        spinner20=(Spinner)rootView.findViewById(R.id.spinner8_9);
        spinner21=(Spinner)rootView.findViewById(R.id.spinner8_10);
        ed1=(EditText)rootView.findViewById(R.id.Ulnar_left);
        ed2=(EditText)rootView.findViewById(R.id.Ulnar_right);
        ed3=(EditText)rootView.findViewById(R.id.Radial_left);
        ed4=(EditText)rootView.findViewById(R.id.Radial_right);
        ed7=(EditText)rootView.findViewById(R.id.Musculocutaneous1);
        ed8=(EditText)rootView.findViewById(R.id.Musculocutaneous2);
        ed9=(EditText)rootView.findViewById(R.id.Sciatic2_1);
        ed10=(EditText)rootView.findViewById(R.id.Sciatic2_2);
        ed11=(EditText)rootView.findViewById(R.id.Tibial1);
        ed12=(EditText)rootView.findViewById(R.id.Tibia2);
        ed13=(EditText)rootView.findViewById(R.id.Comman_peronial1);
        ed14=(EditText)rootView.findViewById(R.id.Comman_peronial2);
        ed15=(EditText)rootView.findViewById(R.id.Femoral1);
        ed16=(EditText)rootView.findViewById(R.id.Femoral2);
        ed17=(EditText)rootView.findViewById(R.id.Lateral_cutaneous1);
        ed18=(EditText)rootView.findViewById(R.id.Lateral_cutaneous2);
        ed19=(EditText)rootView.findViewById(R.id.Obturator1);
        ed20=(EditText)rootView.findViewById(R.id.Obturator2);
        ed21=(EditText)rootView.findViewById(R.id.Sural1);
        ed22=(EditText)rootView.findViewById(R.id.Sural2);
        ed23=(EditText)rootView.findViewById(R.id.Special_Tests);
        ed24=(EditText)rootView.findViewById(R.id.Description1);
        ed25=(EditText)rootView.findViewById(R.id.Ulnar5_1);
        ed26=(EditText)rootView.findViewById(R.id.Ulnar5_2);
        ed27=(EditText)rootView.findViewById(R.id.Radial5_1);
        ed28=(EditText)rootView.findViewById(R.id.Radial5_2);
        ed29=(EditText)rootView.findViewById(R.id.Median5_1);
        ed30=(EditText)rootView.findViewById(R.id.Median5_2);
        ed31=(EditText)rootView.findViewById(R.id.Musculocutaneous5_1);
        ed32=(EditText)rootView.findViewById(R.id.Musculocutaneous5_2);
        ed33=(EditText)rootView.findViewById(R.id.Sciatic5_1);
        ed34=(EditText)rootView.findViewById(R.id.Sciatic5_2);
        ed35=(EditText)rootView.findViewById(R.id.Tibial5_1);
        ed36=(EditText)rootView.findViewById(R.id.Tibial5_2);
        ed37=(EditText)rootView.findViewById(R.id.Comman_peronial5_1);
        ed38=(EditText)rootView.findViewById(R.id.Comman_peronial5_2);
        ed39=(EditText)rootView.findViewById(R.id.Femoral5_1);
        ed40=(EditText)rootView.findViewById(R.id.Femoral5_2);
        ed43=(EditText)rootView.findViewById(R.id.Obturator5_1);
        ed44=(EditText)rootView.findViewById(R.id.Obturator5_2);
        ed41=(EditText)rootView.findViewById(R.id.Sural5_1);
        ed42=(EditText)rootView.findViewById(R.id.Sural5_2);
        ed45=(EditText)rootView.findViewById(R.id.Description9_time1);
        ed46=(EditText)rootView.findViewById(R.id.Description10_time1);
        ed47=(EditText)rootView.findViewById(R.id.Description11_time1);
        ed48=(EditText)rootView.findViewById(R.id.Description12_time1);
        ed49=(EditText)rootView.findViewById(R.id.Description13_time1);
        ed50=(EditText)rootView.findViewById(R.id.Description14_time1);
        ed51=(EditText)rootView.findViewById(R.id.Description15_time1);
        ed52=(EditText)rootView.findViewById(R.id.Description16_time1);
        ed53=(EditText)rootView.findViewById(R.id.Description17_time1);
        ed54=(EditText)rootView.findViewById(R.id.Lateral_cutaneous5_1);
        ed55=(EditText)rootView.findViewById(R.id.Lateral_cutaneous5_2);
        ed5=(EditText)rootView.findViewById(R.id.Median1);
        Name=(EditText)rootView.findViewById(R.id.Name);
        Description4=(EditText)rootView.findViewById(R.id.Description4);
        ed6=(EditText)rootView.findViewById(R.id.Median2);
        sapp_left = (EditText) rootView.findViewById(R.id.sapp_left);
        sapp_right = (EditText) rootView.findViewById(R.id.sapp_right);
        mSave=(Button)rootView.findViewById(R.id.btn_save);

    }
    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_base64 = ImageUtil.encodeTobase64(takeScreenShots(bott));
                Toast.makeText(getActivity(), "saved image", Toast.LENGTH_SHORT).show();
                initProgressDialog("Please wait..");
                addMotorAPi(main_base64);
            }
        });
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void addMotorAPi(final String base_string){
        Neuro1 = spinner1.getSelectedItem().toString().trim();
        Neuro2 = spinner2.getSelectedItem().toString().trim();
        Neuro3  = spinner3.getSelectedItem().toString().trim();
        Neuro4  = spinner4.getSelectedItem().toString().trim();
        Neuro5=spinner5.getSelectedItem().toString().trim();
        Neuro6=spinner6.getSelectedItem().toString().trim();
        Neuro7=spinner7.getSelectedItem().toString().trim();
        Neuro8=spinner8.getSelectedItem().toString().trim();
        Neuro17=spinner17.getSelectedItem().toString().trim();
        Neuro18 = spinner18.getSelectedItem().toString().trim();
        Neuro9 = spinner9.getSelectedItem().toString().trim();
        Neuro10 = spinner10.getSelectedItem().toString().trim();
        Neuro11 = spinner11.getSelectedItem().toString().trim();
        Neuro12=spinner12.getSelectedItem().toString().trim();
        Neuro13=spinner13.getSelectedItem().toString().trim();
        Neuro14=spinner14.getSelectedItem().toString().trim();
        Neuro15=spinner15.getSelectedItem().toString().trim();
        Neuro16=spinner16.getSelectedItem().toString().trim();
        Neuro19=spinner19.getSelectedItem().toString().trim();
        Neuro20 =spinner20.getSelectedItem().toString().trim();
        Neuro21=spinner21.getSelectedItem().toString().trim();
        Neuro69=ed1.getText().toString().trim();
        Neuro22=ed2.getText().toString().trim();
        Neuro23=ed3.getText().toString().trim();
        Neuro24=ed4.getText().toString().trim();
        Neuro25=ed5.getText().toString().trim();
        Neuro26=ed6.getText().toString().trim();
        Neuro27=ed7.getText().toString().trim();
        Neuro28=ed8.getText().toString().trim();
        Neuro29=ed9.getText().toString().trim();
        Neuro30=ed10.getText().toString().trim();
        Neuro31=ed11.getText().toString().trim();
        Neuro32=ed12.getText().toString().trim();
        Neuro33=ed13.getText().toString().trim();
        Neuro34=ed14.getText().toString().trim();
        Neuro35=ed15.getText().toString().trim();
        Neuro36=ed16.getText().toString().trim();
        Neuro37=ed17.getText().toString().trim();
        Neuro38=ed18.getText().toString().trim();
        Neuro39=ed19.getText().toString().trim();
        Neuro40=ed20.getText().toString().trim();
        Neuro41=ed21.getText().toString().trim();
        Neuro42=ed22.getText().toString().trim();
        Neuro43=ed23.getText().toString().trim();
        Neuro44=ed24.getText().toString().trim();
        Neuro45=ed25.getText().toString().trim();
        Neuro46=ed26.getText().toString().trim();
        Neuro47=ed27.getText().toString().trim();
        Neuro48=ed28.getText().toString().trim();
        Neuro49=ed29.getText().toString().trim();
        Neuro50=ed30.getText().toString().trim();
        Neuro51=ed20.getText().toString().trim();
        Neuro52=ed20.getText().toString().trim();
        Neuro53=ed30.getText().toString().trim();
        Neuro54=ed31.getText().toString().trim();
        Neuro55=ed32.getText().toString().trim();
        Neuro56=ed33.getText().toString().trim();
        Neuro57=ed34.getText().toString().trim();
        Neuro58=ed35.getText().toString().trim();
        Neuro59=ed36.getText().toString().trim();
        Neuro60=ed37.getText().toString().trim();
        Neuro61=ed38.getText().toString().trim();
        Neuro62=ed39.getText().toString().trim();
        Neuro63=ed40.getText().toString().trim();
        Neuro64=ed41.getText().toString().trim();
        Neuro65=ed42.getText().toString().trim();
        Neuro66=ed43.getText().toString().trim();
        Neuro67=ed44.getText().toString().trim();
        Neuro68=ed45.getText().toString().trim();
        final String  Neuro69=ed44.getText().toString().trim();
        final String Neuro70=ed45.getText().toString().trim();
        final String Neuro71=ed46.getText().toString().trim();
        final String Neuro72=ed47.getText().toString().trim();
        final String Neuro73=ed48.getText().toString().trim();
        final String Neuro74=ed49.getText().toString().trim();
        final String Neuro75=ed50.getText().toString().trim();
        final String Neuro76=ed51.getText().toString().trim();
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR_NEURO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            pDialog.dismiss();
                            Toast.makeText(getActivity(),"Record added successfully", Toast.LENGTH_LONG).show();
                            getActivity().getFragmentManager().popBackStack();

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
                }){


            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("moter_examneuro_date", Utils.getCurrentDate());
                params.put("patient_id", patientId);

                params.put("moter_examneuro_glas_eye", spinner1.getSelectedItem().toString());
                params.put("moter_examneuro_glas_verbal", spinner2.getSelectedItem().toString());
                params.put("moter_examneuro_glas_motor", spinner3.getSelectedItem().toString());
                params.put("moter_examneuro_left_Ulnar",ed1.getText().toString());
                params.put("moter_examneuro_right_Ulnar", ed2.getText().toString());
                params.put("moter_examneuro_gait_surface",Neuro4);
                params.put("moter_examneuro_modifie_bathing",spinner21.getSelectedItem().toString());
                params.put("moter_examneuro_gait_speed",spinner5.getSelectedItem().toString());
                params.put("moter_examneuro_gait_hori_head",spinner6.getSelectedItem().toString());
                params.put("moter_examneuro_gait_veri_head",Neuro7);
                params.put("moter_examneuro_gait_piovt",Neuro8);
                params.put("moter_examneuro_gait_overobstacle",spinner9.getSelectedItem().toString());
                params.put("moter_examneuro_gait_aroundobstacles", spinner10.getSelectedItem().toString());
                params.put("moter_examneuro_gait_steps", spinner11.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_bowels", spinner12.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_bladder", spinner13.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_grooming", spinner14.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_toilet", spinner15.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_feeding", spinner16.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_transfer", spinner17.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_mobility",spinner18.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_dressing",spinner19.getSelectedItem().toString());
                params.put("moter_examneuro_modifie_stairs", spinner20.getSelectedItem().toString());


                params.put("moter_examneuro_left_Radial",ed3.getText().toString());
                params.put("moter_examneuro_right_Radial", ed4.getText().toString());
                params.put("moter_examneuro_left_musculocutaneous", ed7.getText().toString());
                params.put("moter_examneuro_right_Musculocutaneous", ed8.getText().toString());
                params.put("moter_examneuro_left_sciatic",ed9.getText().toString());
                params.put("moter_examneuro_right_Sciatic", ed10.getText().toString());
                params.put("moter_examneuro_left_Tibial", ed11.getText().toString());
                params.put("moter_examneuro_right_Tibial", ed12.getText().toString());
                params.put("moter_examneuro_left_Commanperonial",ed13.getText().toString());
                params.put("moter_examneuro_right_Comman", ed14.getText().toString());
                params.put("moter_examneuro_left_Femoral",ed15.getText().toString());
                params.put("moter_examneuro_right_Femoral",ed16.getText().toString());
                params.put("moter_examneuro_left_Lateralcutaneous",ed17.getText().toString());
                params.put("moter_examneuro_right_Lateralcutaneous", ed18.getText().toString());
                params.put("moter_examneuro_left_Obturator", ed19.getText().toString());
                params.put("moter_examneuro_right_Obturator", ed20.getText().toString());
                params.put("moter_examneuro_left_Sural", ed21.getText().toString());
                params.put("moter_examneuro_right_Sural", ed22.getText().toString());
                params.put("moter_examneuro_special_test", ed23.getText().toString());
                params.put("moter_examneuro_special_desc", ed24.getText().toString());
                params.put("moter_examneuro_ntp_left_Ulnar", ed25.getText().toString());
                params.put("moter_examneuro_ntp_right_Ulnar", ed26.getText().toString());
                params.put("moter_examneuro_ntp_left_Radial", ed27.getText().toString());
                params.put("moter_examneuro_ntp_right_Radial", ed28.getText().toString());
                params.put("moter_examneuro_ntp_left_Median", ed29.getText().toString());
                params.put("moter_examneuro_ntp_right_Median", ed30.getText().toString());
                params.put("moter_examneuro_gait_balance_desc", ed54.getText().toString());
                params.put("moter_examneuro_adl_name", Name.getText().toString());
                params.put("moter_examneuro_adl_desc", Description4.getText().toString());
                params.put("moter_examneuro_ntp_left_Sciatic", ed33.getText().toString());
                params.put("moter_examneuro_ntp_right_Sciatic", ed34.getText().toString());
                params.put("moter_examneuro_ntp_left_tibial", ed35.getText().toString());
                params.put("moter_examneuro_ntp_right_Tibial", ed36.getText().toString());
                params.put("moter_examneuro_ntp_left_peronial", ed37.getText().toString());
                params.put("moter_examneuro_ntp_right_peronial", ed38.getText().toString());
                params.put("moter_examneuro_ntp_left_Femoral", ed39.getText().toString());
                params.put("moter_examneuro_ntp_right_Femoral", ed40.getText().toString());
                params.put("moter_examneuro_ntp_left_sural", ed41.getText().toString());
                params.put("moter_examneuro_ntp_right_Sural", ed42.getText().toString());
                params.put("moter_examneuro_ntp_left_Saphenous", ed43.getText().toString());
                params.put("moter_examneuro_ntp_right_Saphenous", ed44.getText().toString());
                params.put("moter_examneuro_timetaken", ed45.getText().toString());
                params.put("moter_examneuro_noerror", ed55.getText().toString());
                params.put("moter_examneuro_left_Median", ed5.getText().toString());
                params.put("moter_examneuro_right_Median", ed6.getText().toString());
                params.put("moter_examneuro_step", ed45.getText().toString());
                params.put("moter_examneuro_no_of_error", ed46.getText().toString());
                params.put("moter_examneuro_timetaken1", ed47.getText().toString());
                params.put("moter_examneuro_step1", ed48.getText().toString());
                params.put("moter_examneuro_no_of_error1", ed49.getText().toString());
                params.put("moter_examneuro_timetaken2", ed50.getText().toString());
                params.put("moter_examneuro_step2", ed51.getText().toString());
                params.put("moter_examneuro_no_of_error2", ed52.getText().toString());
                params.put("moterexamsneuro_saphenres_left",sapp_left.getText().toString());
                params.put("moterexamsneuro_saphenres_right",sapp_right.getText().toString());
                params.put("moterexamsneuro_images",base_string);


                Log.d(TagUtils.getTag(),"params:-"+params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public Bitmap takeScreenShots(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    }
