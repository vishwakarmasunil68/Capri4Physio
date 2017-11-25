package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.exams.SensoryExam;

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
    private List<SensoryExam> sensoryExamList;
    Activity activity;
    Fragment fragment;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        private ImageView img_delete,img_view,img_edit;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.txt_patient_id);
            img_view = (ImageView)itemView.findViewById(R.id.img_view);
            img_delete = (ImageView)itemView.findViewById(R.id.img);
            img_edit = (ImageView)itemView.findViewById(R.id.img_attachment);
        }
    }


    public UsersAdapterSensory_(List<SensoryExam> sensoryExamList, Activity activity,Fragment fragment) {
        this.sensoryExamList = sensoryExamList;
        this.activity=activity;
        this.fragment=fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_sensory, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_date.setText(sensoryExamList.get(position).getSensoryExamDate().toString());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert(position,sensoryExamList.get(position).getSensoryExamId());
            }
        });

        holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*posn = CaseNotesFragment.contactDetails1.get(position).toString();
                contents = CaseNotesFragment.contactDetails1.get(position).getName().toString();
                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();*/
                final Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);

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

                sensory_exam_date.setText(sensoryExamList.get(position).getSensoryExamDate().toString());
                sensory_occipital_port.setText(sensoryExamList.get(position).getSensoryOccipitalPort().toString());
                sensory_neck_musc.setText(sensoryExamList.get(position).getSensoryNeckMusc().toString());
                sensory_supra_fossa.setText(sensoryExamList.get(position).getSensorySupraFossa().toString());
                sensory_neck_lateral.setText(sensoryExamList.get(position).getSensoryLateralHeel().toString());
                sensory_acrom_joint.setText(sensoryExamList.get(position).getSensoryAcromJoint().toString());
                sensory_shoulder_elevation.setText(sensoryExamList.get(position).getSensoryShoulderElevation().toString());
                sensory_ante_fossa.setText(sensoryExamList.get(position).getSensoryAnteFossa().toString());
                sensory_shoulder_abduction.setText(sensoryExamList.get(position).getSensoryShoulderAbduction().toString());
                sensory_biceps_brachi.setText(sensoryExamList.get(position).getSensoryBicepsBrachi().toString());
                sensory_thumb.setText(sensoryExamList.get(position).getSensoryThumb().toString());
                sensory_biceps_supin_wrist.setText(sensoryExamList.get(position).getSensoryBicepsSupinWrist().toString());
                sensory_biceps_brachi1.setText(sensoryExamList.get(position).getSensoryBicepsBrachi1().toString());
                sensory_middle_finger.setText(sensoryExamList.get(position).getSensoryMiddleFinger().toString());
                sensory_wrist_flex.setText(sensoryExamList.get(position).getSensoryWristFlex().toString());
                sensory_triceps.setText(sensoryExamList.get(position).getSensoryTriceps().toString());
                sensory_little_finger.setText(sensoryExamList.get(position).getSensoryLittleFinger().toString());
                sensory_thumb_extensors.setText(sensoryExamList.get(position).getSensoryThumbExtensors().toString());
                sensory_triceps1.setText(sensoryExamList.get(position).getSensoryTriceps1().toString());
                sensory_medial_side.setText(sensoryExamList.get(position).getSensoryMedialSide().toString());
                sensory_apexaxilla.setText(sensoryExamList.get(position).getSensoryApexaxilla().toString());
                sensory_nipples.setText(sensoryExamList.get(position).getSensoryNipples().toString());
                sensory_xiphisternum.setText(sensoryExamList.get(position).getSensoryXiphisternum().toString());
                sensory_umbilicus.setText(sensoryExamList.get(position).getSensoryUmbilicus().toString());
                sensory_midpoint_inguinal.setText(sensoryExamList.get(position).getSensoryMidpointInguinal().toString());
                sensory_mid_anterior.setText(sensoryExamList.get(position).getSensoryMidAnterior().toString());
                sensory_hip_flexion.setText(sensoryExamList.get(position).getSensoryHipFlexion().toString());
                sensory_limitedslr.setText(sensoryExamList.get(position).getSensoryLimitedslr().toString());
                sensory_ankle_plantar.setText(sensoryExamList.get(position).getSensoryAnklePlantar().toString());
                sensory_Lateral_heel.setText(sensoryExamList.get(position).getSensoryLateralHeel().toString());
                sensory_extensor.setText(sensoryExamList.get(position).getSensoryExtensor().toString());
                sensory_dorsum_foot.setText(sensoryExamList.get(position).getSensoryDorsumFoot().toString());
                sensory_ankle_dorsi.setText(sensoryExamList.get(position).getSensoryAnkleDorsi().toString());
                sensory_medial_malleolus.setText(sensoryExamList.get(position).getSensoryMedialMalleolus().toString());
                sensory_painslr.setText(sensoryExamList.get(position).getSensoryPainslr().toString());
                sensory_knee_extension.setText(sensoryExamList.get(position).getSensoryKneeExtension().toString());
                sensory_medial_epicondyle.setText(sensoryExamList.get(position).getSensoryMedialEpicondyle().toString());
                sensory_patellar.setText(sensoryExamList.get(position).getSensoryPatellar().toString());
                sensory_bladder_rectum.setText(sensoryExamList.get(position).getSensoryBladderRectum().toString());
                sensory_perianal.setText(sensoryExamList.get(position).getSensoryPerianal().toString());
                sensory_limitedslr_achilles.setText(sensoryExamList.get(position).getSensoryLimitedslr().toString());
                sensory_popliteal_fossa.setText(sensoryExamList.get(position).getSensoryPoplitealFossa().toString());
                sensory_knee_flexion.setText(sensoryExamList.get(position).getSensoryKneeFlexion().toString());

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

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment instanceof SensoryFragment){
                    SensoryFragment sensoryFragment= (SensoryFragment) fragment;
                    sensoryFragment.updateExam(sensoryExamList.get(position));
                }
            }
        });
    }
    private void deleteAlert(final int position, final String ID ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure, you want to delete");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delepnotes(position, ID);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }
    private void delepnotes(final int position, final String ID ){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_SENSORY_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            sensoryExamList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(activity.getApplicationContext(),"Record successfully deleted",Toast.LENGTH_SHORT).show();

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

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(sensoryExamList.size()));
        return sensoryExamList.size();
    }

}
