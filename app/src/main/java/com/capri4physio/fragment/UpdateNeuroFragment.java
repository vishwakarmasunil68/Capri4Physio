package com.capri4physio.fragment;

import android.app.Fragment;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class UpdateNeuroFragment extends Fragment{

}
//    private static final String KEY_PATIENT_ID = "patient_id";
//    private static final String KEY_TYPE = "type";
//    private static final String CALL_NEW_NEURO_FRAGMENT = "call_new_neuro_fragment";
//    private String patientId = "";
//    NeuroExamResultPOJO neuroExamResultPOJO;
//
//    @BindView(R.id.et_ndt_ulnar_left)
//    EditText et_ndt_ulnar_left;
//    @BindView(R.id.et_ndt_ulnar_right)
//    EditText et_ndt_ulnar_right;
//    @BindView(R.id.et_ndt_radial_left)
//    EditText et_ndt_radial_left;
//    @BindView(R.id.et_ndt_radial_right)
//    EditText et_ndt_radial_right;
//    @BindView(R.id.et_ndt_median_left)
//    EditText et_ndt_median_left;
//    @BindView(R.id.et_ndt_median_right)
//    EditText et_ndt_median_right;
//    @BindView(R.id.et_ndt_musculous_left)
//    EditText et_ndt_musculous_left;
//    @BindView(R.id.et_ndt_musculous_right)
//    EditText et_ndt_musculous_right;
//    @BindView(R.id.et_ndt_sciatic_left)
//    EditText et_ndt_sciatic_left;
//    @BindView(R.id.et_ndt_sciatic_right)
//    EditText et_ndt_sciatic_right;
//    @BindView(R.id.et_ndt_tibial_left)
//    EditText et_ndt_tibial_left;
//    @BindView(R.id.et_ndt_tibial_right)
//    EditText et_ndt_tibial_right;
//    @BindView(R.id.et_ndt_cp_left)
//    EditText et_ndt_cp_left;
//    @BindView(R.id.et_ndt_cp_right)
//    EditText et_ndt_cp_right;
//    @BindView(R.id.et_ndt_femoral_right)
//    EditText et_ndt_femoral_right;
//    @BindView(R.id.et_ndt_cutaneous_left)
//    EditText et_ndt_cutaneous_left;
//    @BindView(R.id.et_ndt_cutaneous_right)
//    EditText et_ndt_cutaneous_right;
//    @BindView(R.id.et_ndt_femoral_left)
//    EditText et_ndt_femoral_left;
//    @BindView(R.id.et_ndt_obt_left)
//    EditText et_ndt_obt_left;
//    @BindView(R.id.et_ndt_obt_right)
//    EditText et_ndt_obt_right;
//    @BindView(R.id.et_ndt_sural_left)
//    EditText et_ndt_sural_left;
//    @BindView(R.id.et_ndt_sural_right)
//    EditText et_ndt_sural_right;
//    @BindView(R.id.et_ndt_saph_left)
//    EditText et_ndt_saph_left;
//    @BindView(R.id.et_ndt_saph_right)
//    EditText et_ndt_saph_right;
//    @BindView(R.id.et_speical_test)
//    EditText et_speical_test;
//    @BindView(R.id.et_special_description)
//    EditText et_special_description;
//    @BindView(R.id.et_adl_name)
//    EditText et_adl_name;
//    @BindView(R.id.et_adl_description)
//    EditText et_adl_description;
//    @BindView(R.id.et_ntp_ulnar_left)
//    EditText et_ntp_ulnar_left;
//    @BindView(R.id.et_ntp_ulnar_right)
//    EditText et_ntp_ulnar_right;
//    @BindView(R.id.et_ntp_radial_left)
//    EditText et_ntp_radial_left;
//    @BindView(R.id.et_ntp_radial_right)
//    EditText et_ntp_radial_right;
//    @BindView(R.id.et_ntp_median_left)
//    EditText et_ntp_median_left;
//    @BindView(R.id.et_ntp_median_right)
//    EditText et_ntp_median_right;
//    @BindView(R.id.et_ntp_musc_left)
//    EditText et_ntp_musc_left;
//    @BindView(R.id.et_ntp_musc_right)
//    EditText et_ntp_musc_right;
//    @BindView(R.id.et_ntp_sciatic_left)
//    EditText et_ntp_sciatic_left;
//    @BindView(R.id.et_ntp_sciatic_right)
//    EditText et_ntp_sciatic_right;
//    @BindView(R.id.et_ntp_tibial_left)
//    EditText et_ntp_tibial_left;
//    @BindView(R.id.et_ntp_tibial_right)
//    EditText et_ntp_tibial_right;
//    @BindView(R.id.et_ntp_peronial_left)
//    EditText et_ntp_peronial_left;
//    @BindView(R.id.et_ntp_peronial_right)
//    EditText et_ntp_peronial_right;
//    @BindView(R.id.et_ntp_femoral_left)
//    EditText et_ntp_femoral_left;
//    @BindView(R.id.et_ntp_femoral_right)
//    EditText et_ntp_femoral_right;
//    @BindView(R.id.et_ntp_sura_left)
//    EditText et_ntp_sura_left;
//    @BindView(R.id.et_ntp_sural_right)
//    EditText et_ntp_sural_right;
//    @BindView(R.id.et_ntp_saph_left)
//    EditText et_ntp_saph_left;
//    @BindView(R.id.et_ntp_saph_right)
//    EditText et_ntp_saph_right;
//    @BindView(R.id.et_fn_time_taken)
//    EditText et_fn_time_taken;
//    @BindView(R.id.et_fn_speed)
//    EditText et_fn_speed;
//    @BindView(R.id.et_fn_noerr)
//    EditText et_fn_noerr;
//    @BindView(R.id.et_alsup_time_taken)
//    EditText et_alsup_time_taken;
//    @BindView(R.id.et_al_sup_speed)
//    EditText et_al_sup_speed;
//    @BindView(R.id.et_al_sup_noerr)
//    EditText et_al_sup_noerr;
//    @BindView(R.id.et_hs_time_taken)
//    EditText et_hs_time_taken;
//    @BindView(R.id.et_hs_speed)
//    EditText et_hs_speed;
//    @BindView(R.id.et_hs_noerr)
//    EditText et_hs_noerr;
//    @BindView(R.id.et_balance_left)
//    EditText et_balance_left;
//    @BindView(R.id.et_balance_right)
//    EditText et_balance_right;
//    @BindView(R.id.spinner_eye)
//    Spinner spinner_eye;
//    @BindView(R.id.spinner_verbal)
//    Spinner spinner_verbal;
//    @BindView(R.id.spinner_motor)
//    Spinner spinner_motor;
//    @BindView(R.id.spinner_giat_level)
//    Spinner spinner_giat_level;
//    @BindView(R.id.spinner_giat_speed)
//    Spinner spinner_giat_speed;
//    @BindView(R.id.spinner_giat_horizontal_head)
//    Spinner spinner_giat_horizontal_head;
//    @BindView(R.id.spinner_giat_vertical_head)
//    Spinner spinner_giat_vertical_head;
//    @BindView(R.id.spinner_giat_pivot)
//    Spinner spinner_giat_pivot;
//    @BindView(R.id.spinner_giat_over_obstacles)
//    Spinner spinner_giat_over_obstacles;
//    @BindView(R.id.spinner_giat_around_obstacles)
//    Spinner spinner_giat_around_obstacles;
//    @BindView(R.id.spinner_giat_steps)
//    Spinner spinner_giat_steps;
//    @BindView(R.id.spinner_bowel)
//    Spinner spinner_bowel;
//    @BindView(R.id.spinner_bladder)
//    Spinner spinner_bladder;
//    @BindView(R.id.spinner_grooming)
//    Spinner spinner_grooming;
//    @BindView(R.id.spinnet_toilet)
//    Spinner spinnet_toilet;
//    @BindView(R.id.spinner_feeding)
//    Spinner spinner_feeding;
//    @BindView(R.id.spinner_transfer)
//    Spinner spinner_transfer;
//    @BindView(R.id.spinner_mobility)
//    Spinner spinner_mobility;
//    @BindView(R.id.spinner_dressing)
//    Spinner spinner_dressing;
//    @BindView(R.id.spinner_stairs)
//    Spinner spinner_stairs;
//    @BindView(R.id.spinner_bathing)
//    Spinner spinner_bathing;
//    @BindView(R.id.btn_save)
//    Button btn_save;
//    public static UpdateNeuroFragment newInstance(String patientId, String assessmentType, NeuroExamResultPOJO neuroExamResultPOJO) {
//        UpdateNeuroFragment fragment = new UpdateNeuroFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_PATIENT_ID, patientId);
//        bundle.putString(KEY_TYPE, assessmentType);
//        bundle.putSerializable("examresult", neuroExamResultPOJO);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    public UpdateNeuroFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            patientId = getArguments().getString(KEY_PATIENT_ID);
//            neuroExamResultPOJO= (NeuroExamResultPOJO) getArguments().getSerializable("examresult");
////            assessmentType = getArguments().getString(KEY_TYPE);
//        }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.frag_new_neuro, container, false);
//        ButterKnife.bind(this, rootView);
//        initView(rootView);
//        setListener();
//        return rootView;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        super.initView(rootView);
//
//
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callNeuroAddAPI();
//            }
//        });
//
//        setValues();
//    }
//
//    public void setValues(){
//
//
//
//        setEdittext(et_ndt_ulnar_left,neuroExamResultPOJO.getNdtulnarleft());
//        setEdittext(et_ndt_ulnar_right,neuroExamResultPOJO.getNdtulnarright());
//        setEdittext(et_ndt_radial_left,neuroExamResultPOJO.getNdtradialleft());
//        setEdittext(et_ndt_radial_right,neuroExamResultPOJO.getNdtradialright());
//        setEdittext(et_ndt_median_left,neuroExamResultPOJO.getNdtmedianleft());
//        setEdittext(et_ndt_median_right,neuroExamResultPOJO.getNedtmedianright());
//        setEdittext(et_ndt_musculous_left,neuroExamResultPOJO.getNdtmuscleft());
//        setEdittext(et_ndt_musculous_right,neuroExamResultPOJO.getNdtmusright());
//        setEdittext(et_ndt_sciatic_left,neuroExamResultPOJO.getNdtsciaticleft());
//        setEdittext(et_ndt_sciatic_right,neuroExamResultPOJO.getNdtsciaticright());
//        setEdittext(et_ndt_tibial_left,neuroExamResultPOJO.getNdttibialleft());
//        setEdittext(et_ndt_tibial_right,neuroExamResultPOJO.getNdttibialright());
//        setEdittext(et_ndt_cp_left,neuroExamResultPOJO.getNdtcpleft());
//        setEdittext(et_ndt_cp_right,neuroExamResultPOJO.getNdtcpright());
//        setEdittext(et_ndt_femoral_left,neuroExamResultPOJO.getNdtfemoralleft());
//        setEdittext(et_ndt_femoral_right,neuroExamResultPOJO.getNdtfemoralright());
//        setEdittext(et_ndt_cutaneous_left,neuroExamResultPOJO.getNdtcutaneousleft());
//        setEdittext(et_ndt_cutaneous_right,neuroExamResultPOJO.getNdtcutaneousright());
//        setEdittext(et_ndt_obt_left,neuroExamResultPOJO.getNdtobturator());
////        setEdittext(et_ndt_obt_right,neuroExamResultPOJO.getNdtobturator());
//        setEdittext(et_ndt_sural_left,neuroExamResultPOJO.getNdtsuralleft());
//        setEdittext(et_ndt_sural_right,neuroExamResultPOJO.getNdtsuralright());
//        setEdittext(et_ndt_saph_left,neuroExamResultPOJO.getNdtsaphenousleft());
//        setEdittext(et_ndt_saph_right,neuroExamResultPOJO.getNdtsaphneousright());
//        setEdittext(et_speical_test,neuroExamResultPOJO.getSt());
//        setEdittext(et_special_description,neuroExamResultPOJO.getStdescription());
//        setEdittext(et_adl_name,neuroExamResultPOJO.getAdlname());
//        setEdittext(et_adl_description,neuroExamResultPOJO.getAdldescription());
//        setEdittext(et_ntp_ulnar_left,neuroExamResultPOJO.getNtpulnarleft());
//        setEdittext(et_ntp_ulnar_right,neuroExamResultPOJO.getNtpulnarright());
//        setEdittext(et_ntp_radial_left,neuroExamResultPOJO.getNtpradialleft());
//        setEdittext(et_ntp_radial_right,neuroExamResultPOJO.getNtpradialright());
//        setEdittext(et_ntp_median_left,neuroExamResultPOJO.getNtpmedianleft());
//        setEdittext(et_ndt_median_right,neuroExamResultPOJO.getNtpmedianright());
//        setEdittext(et_ntp_sciatic_left,neuroExamResultPOJO.getNtpsciaticleft());
//        setEdittext(et_ntp_sciatic_right,neuroExamResultPOJO.getNtpsciaticright());
//        setEdittext(et_ntp_tibial_left,neuroExamResultPOJO.getNtptibialleft());
//        setEdittext(et_ntp_sciatic_right,neuroExamResultPOJO.getNtpsciaticright());
//        setEdittext(et_ntp_peronial_left,neuroExamResultPOJO.getNtpperonialleft());
//        setEdittext(et_ntp_peronial_right,neuroExamResultPOJO.getNtpperonialright());
//        setEdittext(et_ntp_femoral_left,neuroExamResultPOJO.getNtpfemoralleft());
//        setEdittext(et_ntp_femoral_right,neuroExamResultPOJO.getNtpfemoralright());
//        setEdittext(et_ntp_sura_left,neuroExamResultPOJO.getNtpsuralleft());
//        setEdittext(et_ntp_sural_right,neuroExamResultPOJO.getNtpsuralright());
//        setEdittext(et_ntp_saph_left,neuroExamResultPOJO.getNtpsaphenousleft());
//        setEdittext(et_ntp_saph_right,neuroExamResultPOJO.getNtpsaphenousright());
//        setEdittext(et_fn_time_taken,neuroExamResultPOJO.getCtfntimetaken());
//        setEdittext(et_fn_speed,neuroExamResultPOJO.getCtfnspeed());
//        setEdittext(et_fn_noerr,neuroExamResultPOJO.getCtfnnoerr());
//        setEdittext(et_hs_time_taken,neuroExamResultPOJO.getCthstimetaken());
////        setEdittext(et_al_sup_speed,neuroExamResultPOJO.speed());
////        setEdittext(et_al_sup_noerr,neuroExamResultPOJO.al());
//
//
//        setSpinnerValues(spinner_eye,neuroExamResultPOJO.getGcseye(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_verbal,neuroExamResultPOJO.getGcsverbal(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_motor,neuroExamResultPOJO.getGcsmotor(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_level,neuroExamResultPOJO.getGaillevel(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_speed,neuroExamResultPOJO.getGaitspeed(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_horizontal_head,neuroExamResultPOJO.getGaithorizontalhead(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_vertical_head,neuroExamResultPOJO.getGaitveticalhead(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_over_obstacles,neuroExamResultPOJO.getStepoverobstacle(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_around_obstacles,neuroExamResultPOJO.getSterparoundobstacle(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_giat_steps,neuroExamResultPOJO.getSteps(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_bladder,neuroExamResultPOJO.getBladder(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_grooming,neuroExamResultPOJO.getGromming(),R.array.neuro_of_pain);
//        setSpinnerValues(spinnet_toilet,neuroExamResultPOJO.getToilet(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_feeding,neuroExamResultPOJO.getFeeding(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_transfer,neuroExamResultPOJO.getTransfer(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_mobility,neuroExamResultPOJO.getMobility(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_dressing,neuroExamResultPOJO.getDressing(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_stairs,neuroExamResultPOJO.getStairs(),R.array.neuro_of_pain);
//        setSpinnerValues(spinner_bathing,neuroExamResultPOJO.getBathing(),R.array.neuro_of_pain);
//
//
//
//
//    }
//
//    public void setSpinnerValues( Spinner spinner,String value,int id){
//        String[] spinnerarray=getActivity().getResources().getStringArray(id);
//        List<String> spinnerList = new ArrayList<String>(Arrays.asList(spinnerarray));
//        if(spinnerList.contains(value)){
//            spinner.setSelection(spinnerList.indexOf(value));
//        }
//    }
//
//    public void setEdittext(EditText et,String value){
//        et.setText(value);
//    }
//
//    public void callNeuroAddAPI(){
//
//
//        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
//        nameValuePairArrayList.add(new BasicNameValuePair("patient_id",neuroExamResultPOJO.getPatientId()));
//        nameValuePairArrayList.add(new BasicNameValuePair("date",neuroExamResultPOJO.getDate()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gcseye",spinner_eye.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gcsverbal",spinner_verbal.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gcsmotor",spinner_motor.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarleft",et_ndt_ulnar_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarright",et_ndt_ulnar_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtradialleft",et_ndt_radial_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtradialright",et_ndt_radial_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianleft",et_ndt_median_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("nedtmedianright",et_ndt_median_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtmuscleft",et_ndt_musculous_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtmusright",et_ndt_musculous_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticleft",et_ndt_sciatic_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticright",et_ndt_sciatic_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialleft",et_ndt_tibial_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialright",et_ndt_tibial_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpleft",et_ndt_cp_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpright",et_ndt_cp_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralleft",et_ndt_femoral_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralright",et_ndt_femoral_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousleft",et_ndt_cutaneous_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousright",et_ndt_cutaneous_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorleft",et_ndt_obt_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorright",et_ndt_obt_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralleft",et_ndt_sural_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralright",et_ndt_sural_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphenousleft",et_ndt_saph_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphneousright",et_ndt_saph_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("st",et_speical_test.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("stdescription",et_special_description.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("adlname",et_adl_name.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("adldescription",et_adl_description.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnarleft",et_ntp_ulnar_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnar",et_ntp_ulnar_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpradialleft",et_ntp_radial_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpradialright",et_ntp_radial_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianleft",et_ntp_median_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianright",et_ndt_median_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticleft",et_ntp_sciatic_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticright",et_ntp_sciatic_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialleft",et_ntp_tibial_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialright",et_ntp_sciatic_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpperonialleft",et_ntp_peronial_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpperonialright",et_ntp_peronial_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralleft",et_ntp_femoral_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralright",et_ntp_femoral_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralleft",et_ntp_sura_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralright",et_ntp_sural_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousleft",et_ntp_saph_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousright",et_ntp_saph_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ctfntimetaken",et_fn_time_taken.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ctfnspeed",et_fn_speed.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ctfnnoerr",et_fn_noerr.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("cthstimetaken",et_hs_time_taken.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("cthsspeed",et_al_sup_speed.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("cthsnoerr",et_al_sup_noerr.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gaillevel",spinner_giat_level.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gaitspeed",spinner_giat_speed.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gaithorizontalhead",spinner_giat_horizontal_head.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gaitveticalhead",spinner_giat_vertical_head.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("stepoverobstacle",spinner_giat_over_obstacles.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("sterparoundobstacle",spinner_giat_around_obstacles.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("steps",spinner_giat_steps.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("balanceandmovement",et_balance_left.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("bowel",et_balance_right.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("bladder",spinner_bladder.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("gromming",spinner_grooming.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("toilet",spinnet_toilet.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("feeding",spinner_feeding.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("transfer",spinner_transfer.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("mobility",spinner_mobility.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("dressing",spinner_dressing.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("stairs",spinner_stairs.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("bathing",spinner_bathing.getSelectedItem().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("id",neuroExamResultPOJO.getId()));
//
//        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.UPDATE_NEURO_EXAM);
//    }
//
//    @Override
//    protected void setListener() {
//        super.setListener();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        ActionBar actionBar = activity.getSupportActionBar();
//        actionBar.setTitle("Add Neuro Fragment");
//    }
//
//    @Override
//    public void onGetMsg(String[] msg) {
//        String response=msg[1];
//        Log.d(TagUtils.getTag(),"add neuro exam:-"+response);
//        try{
//            if(new JSONObject(response).optString("success").equals("true")){
//                getFragmentManager().popBackStack();
//                ToastClass.showShortToast(getActivity().getApplicationContext(),"Report Updated");
//            }else{
//                ToastClass.showShortToast(getActivity(),"Failed to update report");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            ToastClass.showShortToast(getActivity(),"Server Down");
//        }
//    }
//}
