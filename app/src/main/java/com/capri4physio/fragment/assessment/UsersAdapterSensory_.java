package com.capri4physio.fragment.assessment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.MotorItem;
import com.capri4physio.model.assessment.PhysicalItem;
import com.capri4physio.net.ApiConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterSensory_ extends RecyclerView.Adapter<UsersAdapterSensory_.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    CoordinatorLayout mSnackBarLayout;
    Dialog dialog;
    EditText editTextcontents;
    String posn,contents,id,note_iid;
    private List<MotorItem> mList;
    private ViewItemClickListener<PhysicalItem> mCallback;
    Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre,year;
        ImageView edit,medication,prescription;
        private ImageView mImg;
        private ImageView mImgAttachemnt,img_view;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_name);
            img_view = (ImageView)itemView.findViewById(R.id.img_view);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            mImgAttachemnt = (ImageView)itemView.findViewById(R.id.img_attachment);
            genre = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapterSensory_(List<InfoApps> moviesList, Context ctx, CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sensoryadapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        String movie = moviesList.get(position);
//        String date=movie.getNumber();
        holder.genre.setText(SensoryFragment.contactDetails1.get(position).getSend_date().toString());
        holder.title.setText("");
        /*holder.title.setText(movie.getNumber());
        Log.d("sunil", "" + movie.getData());
        */


        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InfoApps positiin = (SensoryFragment.contactDetails1.get(position).getAge().toString());
                 posn = SensoryFragment.contactDetails1.get(position).toString();
                note_iid = SensoryFragment.contactDetails1.get(position).getAge().toString();
                delepnotes(posn,note_iid);
//                Toast.makeText(ctx, "sgfdkjs"+positiin, Toast.LENGTH_LONG).show();
            }
        });
        holder.mImgAttachemnt.setOnClickListener(new View.OnClickListener(){
        @Override
         public void onClick(View v) {
            dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

            //setting custom layout to dialog
            dialog.setContentView(R.layout.sensory_dialogedit);
            dialog.setTitle("Edit - Sensory exam");

            //adding text dynamically

            final EditText sensory_bladder_rectum = (EditText) dialog.findViewById(R.id.sensory_bladder_rectum);
            final EditText  sensory_perianal = (EditText) dialog.findViewById(R.id.sensory_perianal);
            final  EditText sensory_limitedslr_achilles = (EditText) dialog.findViewById(R.id.sensory_limitedslr_achilles);
            final EditText sensory_knee_flexion = (EditText) dialog.findViewById(R.id.sensory_knee_flexion);
            final EditText sensory_popliteal_fossa = (EditText) dialog.findViewById(R.id.sensory_popliteal_fossa);
            final EditText sensory_limitedslr = (EditText) dialog.findViewById(R.id.sensory_limitedslr);
            final  EditText sensory_ankle_plantar = (EditText) dialog.findViewById(R.id.sensory_ankle_plantar);
            final EditText sensory_Lateral_heel = (EditText) dialog.findViewById(R.id.sensory_Lateral_heel);
            final EditText sensory_extensor = (EditText) dialog.findViewById(R.id.sensory_extensor);
            final EditText sensory_dorsum_foot = (EditText) dialog.findViewById(R.id.sensory_dorsum_foot);
            final EditText sensory_ankle_dorsi = (EditText) dialog.findViewById(R.id.sensory_ankle_dorsi);
            final EditText sensory_medial_malleolus = (EditText) dialog.findViewById(R.id.sensory_medial_malleolus);
            final EditText sensory_painslr = (EditText) dialog.findViewById(R.id.sensory_painslr);
            final EditText sensory_knee_extension = (EditText) dialog.findViewById(R.id.sensory_knee_extension);
            final EditText sensory_medial_epicondyle = (EditText) dialog.findViewById(R.id.sensory_medial_epicondyle);
            final EditText sensory_patellar = (EditText) dialog.findViewById(R.id.sensory_patellar);
            final EditText sensory_hip_flexion = (EditText) dialog.findViewById(R.id.sensory_hip_flexion);
            final EditText sensory_mid_anterior = (EditText) dialog.findViewById(R.id.sensory_mid_anterior);
            final EditText sensory_exam_date = (EditText) dialog.findViewById(R.id.sensory_exam_date);
            final EditText sensory_occipital_port = (EditText) dialog.findViewById(R.id.sensory_occipital_port);
            final EditText sensory_neck_musc = (EditText) dialog.findViewById(R.id.sensory_neck_musc);
            final EditText sensory_supra_fossa = (EditText) dialog.findViewById(R.id.sensory_supra_fossa);
            final EditText sensory_neck_lateral = (EditText) dialog.findViewById(R.id.sensory_neck_lateral);
            final EditText sensory_acrom_joint = (EditText) dialog.findViewById(R.id.sensory_acrom_joint);
            final EditText sensory_shoulder_elevation = (EditText) dialog.findViewById(R.id.sensory_shoulder_elevation);
            final EditText sensory_ante_fossa = (EditText) dialog.findViewById(R.id.sensory_ante_fossa);
            final EditText sensory_shoulder_abduction = (EditText) dialog.findViewById(R.id.sensory_shoulder_abduction);
            final EditText sensory_biceps_brachi = (EditText) dialog.findViewById(R.id.sensory_biceps_brachi);
            final EditText sensory_thumb = (EditText) dialog.findViewById(R.id.sensory_thumb);
            final EditText sensory_biceps_supin_wrist = (EditText) dialog.findViewById(R.id.sensory_biceps_supin_wrist);
            final EditText sensory_biceps_brachi1 = (EditText) dialog.findViewById(R.id.sensory_biceps_brachi1);
            final EditText sensory_middle_finger = (EditText) dialog.findViewById(R.id.sensory_middle_finger);
            final EditText sensory_wrist_flex= (EditText) dialog.findViewById(R.id.sensory_wrist_flex);
            final EditText sensory_triceps = (EditText) dialog.findViewById(R.id.sensory_triceps);
            final EditText sensory_little_finger = (EditText) dialog.findViewById(R.id.sensory_little_finger);
            final EditText sensory_thumb_extensors = (EditText) dialog.findViewById(R.id.sensory_thumb_extensors);
            final EditText sensory_triceps1 = (EditText) dialog.findViewById(R.id.sensory_triceps1);
            final EditText sensory_medial_side = (EditText) dialog.findViewById(R.id.sensory_medial_side);
            final EditText  sensory_apexaxilla = (EditText) dialog.findViewById(R.id.sensory_apexaxilla);
            final EditText sensory_nipples = (EditText) dialog.findViewById(R.id.sensory_nipples);
            final EditText sensory_xiphisternum = (EditText) dialog.findViewById(R.id.sensory_xiphisternum);
            final EditText sensory_umbilicus = (EditText) dialog.findViewById(R.id.sensory_umbilicus);
            final EditText sensory_midpoint_inguinal = (EditText) dialog.findViewById(R.id.sensory_midpoint_inguinal);



            sensory_exam_date.setText(SensoryFragment.contactDetails1.get(position).getSend_date().toString());
            sensory_occipital_port.setText(SensoryFragment.contactDetails1.get(position).getDesig().toString());
            sensory_neck_musc.setText(SensoryFragment.contactDetails1.get(position).getExp().toString());
            sensory_supra_fossa.setText(SensoryFragment.contactDetails1.get(position).getId().toString());
            sensory_neck_lateral.setText(SensoryFragment.contactDetails1.get(position).getAppname().toString());
            sensory_acrom_joint.setText(SensoryFragment.contactDetails1.get(position).getOne().toString());
            sensory_shoulder_elevation.setText(SensoryFragment.contactDetails1.get(position).getTwo().toString());
            sensory_ante_fossa.setText(SensoryFragment.contactDetails1.get(position).getThree().toString());
            sensory_shoulder_abduction.setText(SensoryFragment.contactDetails1.get(position).getFour().toString());
            sensory_biceps_brachi.setText(SensoryFragment.contactDetails1.get(position).getFive().toString());
            sensory_thumb.setText(SensoryFragment.contactDetails1.get(position).getSix().toString());
            sensory_biceps_supin_wrist.setText(SensoryFragment.contactDetails1.get(position).getSeven().toString());
            sensory_biceps_brachi1.setText(SensoryFragment.contactDetails1.get(position).getEight().toString());
            sensory_middle_finger.setText(SensoryFragment.contactDetails1.get(position).getNine().toString());
            sensory_wrist_flex.setText(SensoryFragment.contactDetails1.get(position).getTen().toString());
            sensory_triceps.setText(SensoryFragment.contactDetails1.get(position).getEleven().toString());
            sensory_little_finger.setText(SensoryFragment.contactDetails1.get(position).getFourteen().toString());
            sensory_thumb_extensors.setText(SensoryFragment.contactDetails1.get(position).getFiftn().toString());
            sensory_triceps1.setText(SensoryFragment.contactDetails1.get(position).getSixt().toString());
            sensory_medial_side.setText(SensoryFragment.contactDetails1.get(position).getSevent().toString());
            sensory_apexaxilla.setText(SensoryFragment.contactDetails1.get(position).getEightt().toString());
            sensory_nipples.setText(SensoryFragment.contactDetails1.get(position).getNinet().toString());
            sensory_xiphisternum.setText(SensoryFragment.contactDetails1.get(position).getTwenty().toString());
            sensory_umbilicus.setText(SensoryFragment.contactDetails1.get(position).getTwentyone().toString());
            sensory_midpoint_inguinal.setText(SensoryFragment.contactDetails1.get(position).getTwentytwo().toString());
            sensory_mid_anterior.setText(SensoryFragment.contactDetails1.get(position).getTwentythree().toString());
            sensory_hip_flexion.setText(SensoryFragment.contactDetails1.get(position).getTwentyfour().toString());
            sensory_limitedslr.setText(SensoryFragment.contactDetails1.get(position).getThirtysix().toString());
            sensory_ankle_plantar.setText(SensoryFragment.contactDetails1.get(position).getThirtyfive().toString());
            sensory_Lateral_heel.setText(SensoryFragment.contactDetails1.get(position).getThirtyfour().toString());
            sensory_extensor.setText(SensoryFragment.contactDetails1.get(position).getThirtythree().toString());
            sensory_dorsum_foot.setText(SensoryFragment.contactDetails1.get(position).getThirtytwo().toString());
            sensory_ankle_dorsi.setText(SensoryFragment.contactDetails1.get(position).getThirtyone().toString());
            sensory_medial_malleolus.setText(SensoryFragment.contactDetails1.get(position).getTwentynine().toString());
            sensory_painslr.setText(SensoryFragment.contactDetails1.get(position).getTwentyeight().toString());
            sensory_knee_extension.setText(SensoryFragment.contactDetails1.get(position).getTwentyseven().toString());
            sensory_medial_epicondyle.setText(SensoryFragment.contactDetails1.get(position).getTwentysix().toString());
            sensory_patellar.setText(SensoryFragment.contactDetails1.get(position).getTwentyfive().toString());
            sensory_bladder_rectum.setText(SensoryFragment.contactDetails1.get(position).getFourty().toString());
            sensory_perianal.setText(SensoryFragment.contactDetails1.get(position).getThirtynine().toString());
            sensory_limitedslr_achilles.setText(SensoryFragment.contactDetails1.get(position).getThirty().toString());
            sensory_popliteal_fossa.setText(SensoryFragment.contactDetails1.get(position).getThirtyseven().toString());
            sensory_knee_flexion.setText(SensoryFragment.contactDetails1.get(position).getThirtyeight().toString());



            //adding button click event
            Button dismissButton = (Button) dialog.findViewById(R.id.button);
            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getpnote(sensory_occipital_port.getText().toString(),sensory_neck_musc.getText().toString(),sensory_supra_fossa.getText().toString(),
                            sensory_neck_lateral.getText().toString(),sensory_acrom_joint.getText().toString(),sensory_shoulder_elevation.getText().toString(),
                            sensory_ante_fossa.getText().toString(),sensory_shoulder_abduction.getText().toString(),sensory_biceps_brachi.getText().toString(),
                            sensory_thumb.getText().toString(),sensory_biceps_supin_wrist.getText().toString(),sensory_biceps_brachi1.getText().toString(),
                            sensory_middle_finger.getText().toString(),sensory_wrist_flex.getText().toString(),sensory_triceps.getText().toString(),
                            sensory_little_finger.getText().toString(),sensory_thumb_extensors.getText().toString(),sensory_triceps1.getText().toString(),
                            sensory_medial_side.getText().toString(),sensory_apexaxilla.getText().toString(),sensory_nipples.getText().toString(),
                            sensory_xiphisternum.getText().toString(),sensory_umbilicus.getText().toString(),sensory_midpoint_inguinal.getText().toString(),
                            sensory_mid_anterior.getText().toString(),sensory_hip_flexion.getText().toString(),sensory_patellar.getText().toString(),
                            sensory_medial_epicondyle.getText().toString(),sensory_knee_extension.getText().toString(),sensory_painslr.getText().toString(),
                            sensory_medial_malleolus.getText().toString(),sensory_ankle_dorsi.getText().toString(),sensory_dorsum_foot.getText().toString(),
                            sensory_extensor.getText().toString(),sensory_Lateral_heel.getText().toString(),
                            sensory_ankle_plantar.getText().toString(),sensory_limitedslr.getText().toString(),sensory_popliteal_fossa.getText().toString(),
                            sensory_knee_flexion.getText().toString(),sensory_limitedslr_achilles.getText().toString(),
                            sensory_perianal.getText().toString(),sensory_bladder_rectum.getText().toString(),
                            SensoryFragment.contactDetails1.get(position).getAge().toString(),SensoryFragment.contactDetails1.get(position).getPatientid().toString(),sensory_exam_date.getText().toString()
                    );


                }
            });
            dialog.show();
                                                     }
        });
        holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*posn = CaseNotesFragment.contactDetails1.get(position).toString();
                contents = CaseNotesFragment.contactDetails1.get(position).getName().toString();
                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();*/
                dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.sensory_dialog_view);
                dialog.setTitle("View - Sensory exam");

                //adding text dynamically

                TextView sensory_bladder_rectum = (TextView) dialog.findViewById(R.id.sensory_bladder_rectum);
                TextView  sensory_perianal = (TextView) dialog.findViewById(R.id.sensory_perianal);
                TextView sensory_limitedslr_achilles = (TextView) dialog.findViewById(R.id.sensory_limitedslr_achilles);
                TextView sensory_knee_flexion = (TextView) dialog.findViewById(R.id.sensory_knee_flexion);
                TextView sensory_popliteal_fossa = (TextView) dialog.findViewById(R.id.sensory_popliteal_fossa);
                TextView sensory_limitedslr = (TextView) dialog.findViewById(R.id.sensory_limitedslr);
                TextView sensory_ankle_plantar = (TextView) dialog.findViewById(R.id.sensory_ankle_plantar);
                TextView sensory_Lateral_heel = (TextView) dialog.findViewById(R.id.sensory_Lateral_heel);
                TextView sensory_extensor = (TextView) dialog.findViewById(R.id.sensory_extensor);
                TextView sensory_dorsum_foot = (TextView) dialog.findViewById(R.id.sensory_dorsum_foot);
                TextView sensory_ankle_dorsi = (TextView) dialog.findViewById(R.id.sensory_ankle_dorsi);
                TextView sensory_medial_malleolus = (TextView) dialog.findViewById(R.id.sensory_medial_malleolus);
                TextView sensory_painslr = (TextView) dialog.findViewById(R.id.sensory_painslr);
                TextView sensory_knee_extension = (TextView) dialog.findViewById(R.id.sensory_knee_extension);
                TextView sensory_medial_epicondyle = (TextView) dialog.findViewById(R.id.sensory_medial_epicondyle);
                TextView sensory_patellar = (TextView) dialog.findViewById(R.id.sensory_patellar);
                TextView sensory_hip_flexion = (TextView) dialog.findViewById(R.id.sensory_hip_flexion);
                TextView sensory_mid_anterior = (TextView) dialog.findViewById(R.id.sensory_mid_anterior);
                TextView sensory_exam_date = (TextView) dialog.findViewById(R.id.sensory_exam_date);
                TextView sensory_occipital_port = (TextView) dialog.findViewById(R.id.sensory_occipital_port);
                TextView sensory_neck_musc = (TextView) dialog.findViewById(R.id.sensory_neck_musc);
                TextView sensory_supra_fossa = (TextView) dialog.findViewById(R.id.sensory_supra_fossa);
                TextView sensory_neck_lateral = (TextView) dialog.findViewById(R.id.sensory_neck_lateral);
                TextView sensory_acrom_joint = (TextView) dialog.findViewById(R.id.sensory_acrom_joint);
                TextView sensory_shoulder_elevation = (TextView) dialog.findViewById(R.id.sensory_shoulder_elevation);
                TextView sensory_ante_fossa = (TextView) dialog.findViewById(R.id.sensory_ante_fossa);
                TextView sensory_shoulder_abduction = (TextView) dialog.findViewById(R.id.sensory_shoulder_abduction);
                TextView sensory_biceps_brachi = (TextView) dialog.findViewById(R.id.sensory_biceps_brachi);
                TextView sensory_thumb = (TextView) dialog.findViewById(R.id.sensory_thumb);
                TextView sensory_biceps_supin_wrist = (TextView) dialog.findViewById(R.id.sensory_biceps_supin_wrist);
                TextView sensory_biceps_brachi1 = (TextView) dialog.findViewById(R.id.sensory_biceps_brachi1);
                TextView sensory_middle_finger = (TextView) dialog.findViewById(R.id.sensory_middle_finger);
                TextView sensory_wrist_flex= (TextView) dialog.findViewById(R.id.sensory_wrist_flex);
                TextView sensory_triceps = (TextView) dialog.findViewById(R.id.sensory_triceps);
                TextView sensory_little_finger = (TextView) dialog.findViewById(R.id.sensory_little_finger);
                TextView sensory_thumb_extensors = (TextView) dialog.findViewById(R.id.sensory_thumb_extensors);
                TextView sensory_triceps1 = (TextView) dialog.findViewById(R.id.sensory_triceps1);
                TextView sensory_medial_side = (TextView) dialog.findViewById(R.id.sensory_medial_side);
                TextView  sensory_apexaxilla = (TextView) dialog.findViewById(R.id.sensory_apexaxilla);
                TextView sensory_nipples = (TextView) dialog.findViewById(R.id.sensory_nipples);
                TextView sensory_xiphisternum = (TextView) dialog.findViewById(R.id.sensory_xiphisternum);
                TextView sensory_umbilicus = (TextView) dialog.findViewById(R.id.sensory_umbilicus);
                TextView sensory_midpoint_inguinal = (TextView) dialog.findViewById(R.id.sensory_midpoint_inguinal);

                sensory_exam_date.setText(SensoryFragment.contactDetails1.get(position).getSend_date().toString());
                sensory_occipital_port.setText(SensoryFragment.contactDetails1.get(position).getDesig().toString());
                sensory_neck_musc.setText(SensoryFragment.contactDetails1.get(position).getExp().toString());
                sensory_supra_fossa.setText(SensoryFragment.contactDetails1.get(position).getId().toString());
                sensory_neck_lateral.setText(SensoryFragment.contactDetails1.get(position).getAppname().toString());
                sensory_acrom_joint.setText(SensoryFragment.contactDetails1.get(position).getOne().toString());
                sensory_shoulder_elevation.setText(SensoryFragment.contactDetails1.get(position).getTwo().toString());
                sensory_ante_fossa.setText(SensoryFragment.contactDetails1.get(position).getThree().toString());
                sensory_shoulder_abduction.setText(SensoryFragment.contactDetails1.get(position).getFour().toString());
                sensory_biceps_brachi.setText(SensoryFragment.contactDetails1.get(position).getFive().toString());
                sensory_thumb.setText(SensoryFragment.contactDetails1.get(position).getSix().toString());
                sensory_biceps_supin_wrist.setText(SensoryFragment.contactDetails1.get(position).getSeven().toString());
                sensory_biceps_brachi1.setText(SensoryFragment.contactDetails1.get(position).getEight().toString());
                sensory_middle_finger.setText(SensoryFragment.contactDetails1.get(position).getNine().toString());
                sensory_wrist_flex.setText(SensoryFragment.contactDetails1.get(position).getTen().toString());
                sensory_triceps.setText(SensoryFragment.contactDetails1.get(position).getEleven().toString());
                sensory_little_finger.setText(SensoryFragment.contactDetails1.get(position).getFourteen().toString());
                sensory_thumb_extensors.setText(SensoryFragment.contactDetails1.get(position).getFiftn().toString());
                sensory_triceps1.setText(SensoryFragment.contactDetails1.get(position).getSixt().toString());
                sensory_medial_side.setText(SensoryFragment.contactDetails1.get(position).getSevent().toString());
                sensory_apexaxilla.setText(SensoryFragment.contactDetails1.get(position).getEightt().toString());
                sensory_nipples.setText(SensoryFragment.contactDetails1.get(position).getNinet().toString());
                sensory_xiphisternum.setText(SensoryFragment.contactDetails1.get(position).getTwenty().toString());
                sensory_umbilicus.setText(SensoryFragment.contactDetails1.get(position).getTwentyone().toString());
                sensory_midpoint_inguinal.setText(SensoryFragment.contactDetails1.get(position).getTwentytwo().toString());
                sensory_mid_anterior.setText(SensoryFragment.contactDetails1.get(position).getTwentythree().toString());
                sensory_hip_flexion.setText(SensoryFragment.contactDetails1.get(position).getTwentyfour().toString());
                sensory_limitedslr.setText(SensoryFragment.contactDetails1.get(position).getThirtysix().toString());
                sensory_ankle_plantar.setText(SensoryFragment.contactDetails1.get(position).getThirtyfive().toString());
                sensory_Lateral_heel.setText(SensoryFragment.contactDetails1.get(position).getThirtyfour().toString());
                sensory_extensor.setText(SensoryFragment.contactDetails1.get(position).getThirtythree().toString());
                sensory_dorsum_foot.setText(SensoryFragment.contactDetails1.get(position).getThirtytwo().toString());
                sensory_ankle_dorsi.setText(SensoryFragment.contactDetails1.get(position).getThirtyone().toString());
                sensory_medial_malleolus.setText(SensoryFragment.contactDetails1.get(position).getTwentynine().toString());
                sensory_painslr.setText(SensoryFragment.contactDetails1.get(position).getTwentyeight().toString());
                sensory_knee_extension.setText(SensoryFragment.contactDetails1.get(position).getTwentyseven().toString());
                sensory_medial_epicondyle.setText(SensoryFragment.contactDetails1.get(position).getTwentysix().toString());
                sensory_patellar.setText(SensoryFragment.contactDetails1.get(position).getTwentyfive().toString());
                sensory_bladder_rectum.setText(SensoryFragment.contactDetails1.get(position).getFourty().toString());
                sensory_perianal.setText(SensoryFragment.contactDetails1.get(position).getThirtynine().toString());
                sensory_limitedslr_achilles.setText(SensoryFragment.contactDetails1.get(position).getThirty().toString());
                sensory_popliteal_fossa.setText(SensoryFragment.contactDetails1.get(position).getThirtyseven().toString());
                sensory_knee_flexion.setText(SensoryFragment.contactDetails1.get(position).getThirtyeight().toString());

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }

    private void getpnote(final String sensory_occipital_port,final String sensory_neck_musc,
                          final String sensory_supra_fossa,final String sensory_neck_lateral,
                          final String sensory_acrom_joint,final String sensory_shoulder_elevation,
                          final String sensory_ante_fossa,final String sensory_shoulder_abduction,
                          final String sensory_biceps_brachi,final String sensory_thumb,
                          final String sensory_biceps_supin_wrist,final String sensory_biceps_brachi1,
                          final String sensory_middle_finger,final String sensory_wrist_flex,
                          final String sensory_triceps,final String sensory_little_finger,
                          final String sensory_thumb_extensors,final String sensory_triceps1,
                          final String sensory_medial_side,final String sensory_apexaxilla,
                          final String sensory_nipples,final String sensory_xiphisternum,
                          final String sensory_umbilicus,final String sensory_midpoint_inguinal,
                          final String sensory_mid_anterior,final String sensory_hip_flexion,
                          final String sensory_patellar,final String sensory_medial_epicondyle,
                          final String sensory_knee_extension,final String sensory_painslr,
                          final String sensory_medial_malleolus,final String sensory_ankle_dorsi,
                          final String sensory_dorsum_foot,final String sensory_extensor,
                          final String sensory_Lateral_heel,final String sensory_ankle_plantar,
                          final String sensory_limitedslr,final String sensory_popliteal_fossa,
                          final String sensory_knee_flexion,final String sensory_limitedslr_achilles,
                          final String sensory_perianal,final String sensory_bladder_rectum,final String REID,final String posn,final String sensory_exam_date
    ) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_SENSORY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                Log.e("result", response);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                    objresponse.put("sensory_exam_date", sensory_exam_date);
                    objresponse.put("patient_id", posn);
                    objresponse.put("sensory_occipital_port",sensory_occipital_port );
                    objresponse.put("sensory_neck_musc", sensory_neck_musc);
                    objresponse.put("sensory_supra_fossa",sensory_supra_fossa );
                    objresponse.put("sensory_neck_lateral",sensory_neck_lateral );
                    objresponse.put("sensory_acrom_joint", sensory_acrom_joint);
                    objresponse.put("sensory_shoulder_elevation",sensory_shoulder_elevation );
                    objresponse.put("sensory_ante_fossa", sensory_ante_fossa);
                    objresponse.put("sensory_shoulder_abduction",sensory_shoulder_abduction );
                    objresponse.put("sensory_biceps_brachi", sensory_biceps_brachi);
                    objresponse.put("sensory_thumb", sensory_thumb);
                    objresponse.put("sensory_biceps_supin_wrist", sensory_biceps_supin_wrist);
                    objresponse.put("sensory_biceps_brachi1",sensory_biceps_brachi1 );
                    objresponse.put("sensory_middle_finger", sensory_middle_finger);
                    objresponse.put("sensory_wrist_flex",sensory_wrist_flex );
                    objresponse.put("sensory_triceps", sensory_triceps);
                    objresponse.put("sensory_little_finger",sensory_little_finger );
                    objresponse.put("sensory_thumb_extensors",sensory_thumb_extensors );
                    objresponse.put("sensory_triceps1", sensory_triceps1);
                    objresponse.put("sensory_medial_side",sensory_medial_side );
                    objresponse.put("sensory_apexaxilla", sensory_apexaxilla);
                    objresponse.put("sensory_nipples", sensory_nipples);
                    objresponse.put("sensory_xiphisternum", sensory_xiphisternum);
                    objresponse.put("sensory_umbilicus", sensory_umbilicus);
                    objresponse.put("sensory_midpoint_inguinal",sensory_midpoint_inguinal );
                    objresponse.put("sensory_mid_anterior",sensory_mid_anterior );
                    objresponse.put("sensory_hip_flexion",sensory_hip_flexion );
                    objresponse.put("sensory_patellar", sensory_patellar);
                    objresponse.put("sensory_medial_epicondyle",sensory_medial_epicondyle );
                    objresponse.put("sensory_knee_extension", sensory_knee_extension);
                    objresponse.put("sensory_painslr",sensory_painslr );
                    objresponse.put("sensory_medial_malleolus",sensory_medial_malleolus );
                    objresponse.put("sensory_ankle_dorsi", sensory_ankle_dorsi);
                    objresponse.put("sensory_dorsum_foot", sensory_dorsum_foot);
                    objresponse.put("sensory_extensor", sensory_extensor);
                    objresponse.put("sensory_Lateral_heel",sensory_Lateral_heel );
                    objresponse.put("sensory_ankle_plantar",sensory_ankle_plantar );
                    objresponse.put("sensory_limitedslr", sensory_limitedslr);
                    objresponse.put("sensory_popliteal_fossa",sensory_popliteal_fossa );
                    objresponse.put("sensory_knee_flexion", sensory_knee_flexion);
                    objresponse.put("sensory_limitedslr_achilles",sensory_limitedslr_achilles );
                    objresponse.put("sensory_perianal", sensory_perianal);
                    objresponse.put("sensory_bladder_rectum",sensory_bladder_rectum );
                    objresponse.put("sensory_exam_id",REID);
                    return objresponse;

                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);
        }
    }


    private void getpnotes(final TextView textView){



//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_CASE_NOTES  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
//                            textView.setText(editTextcontents.getText().toString());
                            dialog.dismiss();

                            }

                    catch (Exception e) {
                            e.printStackTrace();
                        }
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
                Map<String,String> objresponse = new HashMap<String, String>();
//                objresponse.put("moter_exam_casedesc", casedesc);
//                objresponse.put("moter_exam_case_id", note_iid);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }


    private void delepnotes(final String textView,final String ID ){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_SENSORY_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            moviesList.remove(textView);
                            notifyDataSetChanged();
//                            Toast.makeText(ctx,"Record successfully deleted",Toast.LENGTH_SHORT).show();
                            showSnackMessage("Record successfully removed");

                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }
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
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("sensory_exam_id", ID);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(moviesList.size()));
        return moviesList.size();
    }

}
