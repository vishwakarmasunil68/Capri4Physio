package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import com.capri4physio.pojo.exams.NeuroExamResultPOJO;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create to bind jobs in list
 *
 * @author prabhunathy
 * @version 1.0
 * @since 1/4/16.
 */

public class UsersAdapterNeuro_ extends RecyclerView.Adapter<UsersAdapterNeuro_.MyViewHolder> {
    private List<NeuroExamResultPOJO> neuroExamResultPOJOList;
    Dialog dialog;
    Activity activity;
    NeuroFragment neuroFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        ImageView edit, medication, prescription;
        private ImageView iv_delete;
        private ImageView iv_edit, img_view;
        Button status;

        public MyViewHolder(View view) {
            super(view);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            iv_delete = (ImageView) itemView.findViewById(R.id.img);
            iv_edit = (ImageView) itemView.findViewById(R.id.img_attachment);
            tv_date = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapterNeuro_(List<NeuroExamResultPOJO> neuroExamResultPOJOList, Activity activity, NeuroFragment neuroFragment) {
        this.neuroExamResultPOJOList = neuroExamResultPOJOList;
        this.activity = activity;
        this.neuroFragment = neuroFragment;
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

        holder.tv_date.setText(neuroExamResultPOJOList.get(position).getDate());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert(position, neuroExamResultPOJOList.get(position).getId());
            }
        });
        holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);

                dialog.setContentView(R.layout.neuro_dialog_view);
                dialog.setTitle("View - Neuro exam");

                TextView moter_examneuro_date = (TextView) dialog.findViewById(R.id.moter_examneuro_date);
                TextView moter_examneuro_glas_eye = (TextView) dialog.findViewById(R.id.moter_examneuro_glas_eye);
                TextView moter_examneuro_glas_verbal = (TextView) dialog.findViewById(R.id.moter_examneuro_glas_verbal);
                TextView moter_examneuro_glas_motor = (TextView) dialog.findViewById(R.id.moter_examneuro_glas_motor);
                TextView moter_examneuro_left_Ulnar = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Ulnar);
                TextView moter_examneuro_left_Radial = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Radial);
                TextView moter_examneuro_left_Median = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Median);
                TextView moter_examneuro_left_musculocutaneous = (TextView) dialog.findViewById(R.id.moter_examneuro_left_musculocutaneous);
                TextView moter_examneuro_left_sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_left_sciatic);
                TextView moter_examneuro_left_Tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Tibial);
                TextView moter_examneuro_left_Commanperonial = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Commanperonial);
                TextView moter_examneuro_left_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Femoral);
                TextView moter_examneuro_left_Lateralcutaneous = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Lateralcutaneous);
                TextView moter_examneuro_left_Obturator = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Obturator);
                TextView moter_examneuro_left_Sural = (TextView) dialog.findViewById(R.id.moter_examneuro_left_Sural);
                TextView moter_examneuro_right_Ulnar = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Ulnar);
                TextView moter_examneuro_right_Radial = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Radial);
                TextView moter_examneuro_right_Median = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Median);
                TextView moter_examneuro_right_Musculocutaneous = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Musculocutaneous);
                TextView moter_examneuro_right_Sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Sciatic);
                TextView moter_examneuro_right_Tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Tibial);
                TextView moter_examneuro_right_Comman = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Comman);
                TextView moter_examneuro_right_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Femoral);
                TextView moter_examneuro_right_Lateralcutaneous = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Lateralcutaneous);
                TextView moter_examneuro_right_Obturator = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Obturator);
                TextView moter_examneuro_right_Sural = (TextView) dialog.findViewById(R.id.moter_examneuro_right_Sural);
                TextView moter_examneuro_special_test = (TextView) dialog.findViewById(R.id.moter_examneuro_special_test);
                TextView moter_examneuro_special_desc = (TextView) dialog.findViewById(R.id.moter_examneuro_special_desc);
                TextView moter_examneuro_adl_name = (TextView) dialog.findViewById(R.id.moter_examneuro_adl_name);
                TextView moter_examneuro_adl_desc = (TextView) dialog.findViewById(R.id.moter_examneuro_adl_desc);
                TextView moter_examneuro_ntp_left_Ulnar = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Ulnar);
                TextView moter_examneuro_ntp_left_Radial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Radial);
                TextView moter_examneuro_ntp_left_Median = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Median);
                TextView moter_examneuro_ntp_left_Sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Sciatic);
                TextView moter_examneuro_ntp_left_tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_tibial);
                TextView moter_examneuro_ntp_left_peronial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_peronial);
                TextView moter_examneuro_ntp_left_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Femoral);
                TextView moter_examneuro_ntp_left_sural = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_sural);
                TextView moter_examneuro_ntp_left_Saphenous = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Saphenous);
                TextView moter_examneuro_ntp_right_Ulnar = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Ulnar);
                TextView moter_examneuro_ntp_right_Radial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Radial);
                TextView moter_examneuro_ntp_right_Median = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Median);
                TextView moter_examneuro_ntp_right_Sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sciatic);
                TextView moter_examneuro_ntp_right_Tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Tibial);

                TextView moter_examneuro_ntp_right_peronial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_peronial);
                TextView moter_examneuro_ntp_right_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Femoral);
                TextView moter_examneuro_ntp_right_Sural = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sural);
                TextView moter_examneuro_ntp_right_Saphenous = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Saphenous);
                TextView moter_examneuro_timetaken = (TextView) dialog.findViewById(R.id.moter_examneuro_timetaken);
                TextView moter_examneuro_timestep = (TextView) dialog.findViewById(R.id.moter_examneuro_timestep);
                TextView moter_examneuro_timeeror = (TextView) dialog.findViewById(R.id.moter_examneuro_timeeror);
                TextView moter_examneuro_timetaken_1 = (TextView) dialog.findViewById(R.id.moter_examneuro_timetaken_1);
                TextView moter_examneuro_timestep_1 = (TextView) dialog.findViewById(R.id.moter_examneuro_timestep_1);
                TextView moter_examneuro_timeeror_1 = (TextView) dialog.findViewById(R.id.moter_examneuro_timeeror_1);
                TextView moter_examneuro_timetaken_2 = (TextView) dialog.findViewById(R.id.moter_examneuro_timetaken_2);
                TextView moter_examneuro_timestep_2 = (TextView) dialog.findViewById(R.id.moter_examneuro_timestep_2);
                TextView moter_examneuro_timeeror_2 = (TextView) dialog.findViewById(R.id.moter_examneuro_timeeror_2);
                TextView moter_examneuro_noerror = (TextView) dialog.findViewById(R.id.moter_examneuro_noerror);
                TextView moter_examneuro_gait_surface = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_surface);
                TextView moter_examneuro_gait_speed = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_speed);
                TextView moter_examneuro_gait_hori_head = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_hori_head);
                TextView moter_examneuro_gait_veri_head = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_veri_head);
                TextView moter_examneuro_gait_piovt = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_piovt);
                TextView moter_examneuro_gait_overobstacle = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_overobstacle);
                TextView moter_examneuro_gait_aroundobstacles = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_aroundobstacles);
                final TextView sensory_saphenres_left = (TextView) dialog.findViewById(R.id.sensory_saphenres_left);
                final TextView sensory_saphenres_right = (TextView) dialog.findViewById(R.id.sensory_saphenres_right);
                TextView moter_examneuro_gait_steps = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_steps);
                TextView moter_examneuro_gait_balance_desc = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_balance_desc);
                TextView moter_examneuro_modifie_bowels = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bowels);
                TextView moter_examneuro_modifie_bladder = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bladder);
                TextView moter_examneuro_modifie_grooming = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_grooming);
                TextView moter_examneuro_modifie_toilet = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_toilet);
                TextView moter_examneuro_modifie_feeding = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_feeding);
                TextView moter_examneuro_modifie_transfer = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_transfer);
                TextView moter_examneuro_modifie_mobility = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_mobility);
                TextView moter_examneuro_modifie_dressing = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_dressing);
                TextView moter_examneuro_modifie_stairs = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_stairs);
                TextView moter_examneuro_modifie_bathing = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bathing);

                moter_examneuro_date.setText(neuroExamResultPOJOList.get(position).getDate().toString());
                moter_examneuro_glas_eye.setText(neuroExamResultPOJOList.get(position).getGcseye().toString());
                moter_examneuro_glas_verbal.setText(neuroExamResultPOJOList.get(position).getGcsverbal().toString());
                moter_examneuro_glas_motor.setText(neuroExamResultPOJOList.get(position).getGcsmotor().toString());
                moter_examneuro_left_Ulnar.setText(neuroExamResultPOJOList.get(position).getNdtulnarleft().toString());
                moter_examneuro_left_Radial.setText(neuroExamResultPOJOList.get(position).getNdtradialleft().toString());
                moter_examneuro_left_Median.setText(neuroExamResultPOJOList.get(position).getNdtmedianleft().toString());
                moter_examneuro_left_musculocutaneous.setText(neuroExamResultPOJOList.get(position).getNdtmuscleft().toString());
                moter_examneuro_left_sciatic.setText(neuroExamResultPOJOList.get(position).getNdtsciaticleft().toString());
                moter_examneuro_left_Tibial.setText(neuroExamResultPOJOList.get(position).getNdttibialleft().toString());
                moter_examneuro_left_Commanperonial.setText(neuroExamResultPOJOList.get(position).getNtpperonialleft().toString());
                moter_examneuro_left_Femoral.setText(neuroExamResultPOJOList.get(position).getNdtfemoralleft().toString());
                moter_examneuro_left_Lateralcutaneous.setText(neuroExamResultPOJOList.get(position).getNdtcutaneousleft().toString());
                moter_examneuro_left_Obturator.setText(neuroExamResultPOJOList.get(position).getNdtobturator().toString());
                moter_examneuro_left_Sural.setText(neuroExamResultPOJOList.get(position).getNdtsuralleft().toString());
                moter_examneuro_right_Ulnar.setText(neuroExamResultPOJOList.get(position).getNtpulnarright().toString());
                moter_examneuro_right_Radial.setText(neuroExamResultPOJOList.get(position).getNdtradialright().toString());
                moter_examneuro_right_Median.setText(neuroExamResultPOJOList.get(position).getNedtmedianright().toString());
                moter_examneuro_right_Musculocutaneous.setText(neuroExamResultPOJOList.get(position).getNdtmusright().toString());
                moter_examneuro_right_Sciatic.setText(neuroExamResultPOJOList.get(position).getNdtsciaticright().toString());
                moter_examneuro_right_Tibial.setText(neuroExamResultPOJOList.get(position).getNdttibialright().toString());
                moter_examneuro_right_Comman.setText(neuroExamResultPOJOList.get(position).getNtpperonialright().toString());
                moter_examneuro_right_Femoral.setText(neuroExamResultPOJOList.get(position).getNdtfemoralright().toString());
                moter_examneuro_right_Lateralcutaneous.setText(neuroExamResultPOJOList.get(position).getNdtcutaneousright().toString());
                moter_examneuro_right_Obturator.setText(neuroExamResultPOJOList.get(position).getNdtobturator().toString());
                moter_examneuro_right_Sural.setText(neuroExamResultPOJOList.get(position).getNdtsuralright().toString());
                moter_examneuro_special_test.setText(neuroExamResultPOJOList.get(position).getSt().toString());
                moter_examneuro_special_desc.setText(neuroExamResultPOJOList.get(position).getStdescription().toString());
                moter_examneuro_adl_name.setText(neuroExamResultPOJOList.get(position).getAdlname().toString());
                moter_examneuro_adl_desc.setText(neuroExamResultPOJOList.get(position).getAdldescription().toString());
                moter_examneuro_ntp_left_Ulnar.setText(neuroExamResultPOJOList.get(position).getNtpulnarleft().toString());
                moter_examneuro_ntp_left_Radial.setText(neuroExamResultPOJOList.get(position).getNdtradialleft().toString());
                moter_examneuro_ntp_left_Median.setText(neuroExamResultPOJOList.get(position).getNdtmedianleft().toString());
                moter_examneuro_ntp_left_Sciatic.setText(neuroExamResultPOJOList.get(position).getNdtsciaticleft().toString());
                moter_examneuro_ntp_left_tibial.setText(neuroExamResultPOJOList.get(position).getNdttibialleft().toString());
                moter_examneuro_ntp_left_peronial.setText(neuroExamResultPOJOList.get(position).getNtpperonialleft().toString());
                moter_examneuro_ntp_left_Femoral.setText(neuroExamResultPOJOList.get(position).getNtpfemoralleft().toString());
                moter_examneuro_ntp_left_sural.setText(neuroExamResultPOJOList.get(position).getNtpsuralleft().toString());
                moter_examneuro_ntp_left_Saphenous.setText(neuroExamResultPOJOList.get(position).getNtpsaphenousleft().toString());
                moter_examneuro_ntp_right_Ulnar.setText(neuroExamResultPOJOList.get(position).getNtpulnarright().toString());
                moter_examneuro_ntp_right_Radial.setText(neuroExamResultPOJOList.get(position).getNtpradialright().toString());
                moter_examneuro_ntp_right_Median.setText(neuroExamResultPOJOList.get(position).getNtpmedianright().toString());
                moter_examneuro_ntp_right_Sciatic.setText(neuroExamResultPOJOList.get(position).getNtpsciaticright().toString());

                moter_examneuro_ntp_right_Tibial.setText(neuroExamResultPOJOList.get(position).getNtptibialright().toString());
                moter_examneuro_ntp_right_peronial.setText(neuroExamResultPOJOList.get(position).getNtpperonialright().toString());
                moter_examneuro_ntp_right_Femoral.setText(neuroExamResultPOJOList.get(position).getNtpfemoralright().toString());
                moter_examneuro_ntp_right_Sural.setText(neuroExamResultPOJOList.get(position).getNtpsuralright().toString());
                moter_examneuro_ntp_right_Saphenous.setText(neuroExamResultPOJOList.get(position).getNtpsaphenousright().toString());
                moter_examneuro_timetaken.setText(neuroExamResultPOJOList.get(position).getCtfntimetaken().toString());
                moter_examneuro_noerror.setText(neuroExamResultPOJOList.get(position).getCtfnnoerr().toString());
                moter_examneuro_gait_surface.setText(neuroExamResultPOJOList.get(position).getGaillevel().toString());
                moter_examneuro_gait_speed.setText(neuroExamResultPOJOList.get(position).getGaitspeed().toString());
                moter_examneuro_gait_hori_head.setText(neuroExamResultPOJOList.get(position).getGaithorizontalhead().toString());
                moter_examneuro_gait_veri_head.setText(neuroExamResultPOJOList.get(position).getGaitveticalhead().toString());
                moter_examneuro_gait_piovt.setText(neuroExamResultPOJOList.get(position).getGaitveticalhead().toString());
                moter_examneuro_gait_overobstacle.setText(neuroExamResultPOJOList.get(position).getStepoverobstacle().toString());
                moter_examneuro_gait_aroundobstacles.setText(neuroExamResultPOJOList.get(position).getSterparoundobstacle().toString());
                moter_examneuro_gait_steps.setText(neuroExamResultPOJOList.get(position).getSteps().toString());
                moter_examneuro_gait_balance_desc.setText(neuroExamResultPOJOList.get(position).getBalanceandmovement().toString());
                moter_examneuro_modifie_bowels.setText(neuroExamResultPOJOList.get(position).getBowel().toString());
                moter_examneuro_modifie_bladder.setText(neuroExamResultPOJOList.get(position).getBladder().toString());
                moter_examneuro_modifie_grooming.setText(neuroExamResultPOJOList.get(position).getGromming().toString());
                moter_examneuro_modifie_toilet.setText(neuroExamResultPOJOList.get(position).getToilet().toString());
                moter_examneuro_modifie_feeding.setText(neuroExamResultPOJOList.get(position).getFeeding().toString());
                moter_examneuro_modifie_transfer.setText(neuroExamResultPOJOList.get(position).getTransfer().toString());
                moter_examneuro_modifie_mobility.setText(neuroExamResultPOJOList.get(position).getMobility().toString());
                moter_examneuro_modifie_dressing.setText(neuroExamResultPOJOList.get(position).getDressing().toString());
                moter_examneuro_modifie_stairs.setText(neuroExamResultPOJOList.get(position).getStairs().toString());
                moter_examneuro_modifie_bathing.setText(neuroExamResultPOJOList.get(position).getBathing().toString());

                moter_examneuro_timestep.setText(neuroExamResultPOJOList.get(position).getCthstimetaken().toString());
                moter_examneuro_timeeror.setText(neuroExamResultPOJOList.get(position).getCthsnoerr().toString());
                moter_examneuro_timetaken_1.setText(neuroExamResultPOJOList.get(position).getCtfntimetaken().toString());
//                moter_examneuro_timestep_1.setText(neuroExamResultPOJOList.get(position).getFiftyfive().toString());
//                moter_examneuro_timeeror_1.setText(neuroExamResultPOJOList.get(position).getFiftysix().toString());
//                moter_examneuro_timetaken_2.setText(neuroExamResultPOJOList.get(position).getFiftyseven().toString());
//                moter_examneuro_timestep_2.setText(neuroExamResultPOJOList.get(position).getFiftyeight().toString());
//                moter_examneuro_timeeror_2.setText(neuroExamResultPOJOList.get(position).getFiftynine().toString());
//                sensory_saphenres_left.setText(neuroExamResultPOJOList.get(position).getTwentysix().toString());
//                sensory_saphenres_right.setText(neuroExamResultPOJOList.get(position).getTwentyseven().toString());


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

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    neuroFragment.updateNeuroReport(neuroExamResultPOJOList.get(position));
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }
        });

//    holder.iv_edit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//                /*posn = CaseNotesFragment.contactDetails1.get(position).toString();
//                contents = CaseNotesFragment.contactDetails1.get(position).getName().toString();
//                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();*/
//            dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
//
//            //setting custom layout to dialog
//            dialog.setContentView(R.layout.neuro_dialogedit);
//            dialog.setTitle("Edit- Neuro exam");
//
//            //adding text dynamically
//
//            final EditText moter_examneuro_date = (EditText) dialog.findViewById(R.id.moter_examneuro_date);
//            final EditText moter_examneuro_glas_eye = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_eye);
//            final EditText moter_examneuro_glas_verbal = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_verbal);
//            final EditText moter_examneuro_glas_motor = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_motor);
//            final EditText moter_examneuro_left_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Ulnar);
//            final EditText moter_examneuro_left_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Radial);
//            final EditText moter_examneuro_left_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Median);
//            final EditText moter_examneuro_left_musculocutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_left_musculocutaneous);
//            final EditText moter_examneuro_left_sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_left_sciatic);
//            final EditText moter_examneuro_left_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Tibial);
//            final EditText moter_examneuro_left_Commanperonial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Commanperonial);
//            final EditText moter_examneuro_left_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Femoral);
//            final EditText moter_examneuro_left_Lateralcutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Lateralcutaneous);
//            final EditText moter_examneuro_left_Obturator = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Obturator);
//            final EditText moter_examneuro_left_Sural = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Sural);
//            final EditText moter_examneuro_right_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Ulnar);
//            final EditText moter_examneuro_right_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Radial);
//            final EditText moter_examneuro_right_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Median);
//            final EditText moter_examneuro_right_Musculocutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Musculocutaneous);
//            final EditText moter_examneuro_right_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Sciatic);
//            final EditText moter_examneuro_right_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Tibial);
//            final EditText moter_examneuro_right_Comman = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Comman);
//            final EditText moter_examneuro_right_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Femoral);
//            final EditText moter_examneuro_right_Lateralcutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Lateralcutaneous);
//            final EditText moter_examneuro_right_Obturator = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Obturator);
//            final EditText moter_examneuro_right_Sural = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Sural);
//            final EditText moter_examneuro_special_test = (EditText) dialog.findViewById(R.id.moter_examneuro_special_test);
//            final EditText moter_examneuro_special_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_special_desc);
//            final EditText moter_examneuro_adl_name = (EditText) dialog.findViewById(R.id.moter_examneuro_adl_name);
//            final EditText moter_examneuro_adl_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_adl_desc);
//            final EditText moter_examneuro_ntp_left_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Ulnar);
//            final EditText moter_examneuro_ntp_left_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Radial);
//            final EditText moter_examneuro_ntp_left_Median= (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Median);
//            final EditText moter_examneuro_ntp_left_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Sciatic);
//            final EditText moter_examneuro_ntp_left_tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_tibial);
//            final EditText moter_examneuro_ntp_left_peronial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_peronial);
//            final EditText moter_examneuro_ntp_left_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Femoral);
//            final EditText moter_examneuro_ntp_left_sural = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_sural);
//            final EditText  moter_examneuro_ntp_left_Saphenous = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Saphenous);
//            final EditText moter_examneuro_ntp_right_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Ulnar);
//            final EditText moter_examneuro_ntp_right_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Radial);
//            final EditText moter_examneuro_ntp_right_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Median);
//            final EditText moter_examneuro_ntp_right_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sciatic);
//            final EditText moter_examneuro_ntp_right_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Tibial);
//
//            final EditText moter_examneuro_ntp_right_peronial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_peronial);
//            final EditText moter_examneuro_ntp_right_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Femoral);
//            final EditText moter_examneuro_ntp_right_Sural= (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sural);
//            final EditText moter_examneuro_ntp_right_Saphenous = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Saphenous);
//            final EditText moter_examneuro_timetaken = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken);
//            final EditText moter_examneuro_timestep = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep);
//            final EditText moter_examneuro_timeeror = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror);
//            final EditText moter_examneuro_timetaken_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken_1);
//            final EditText moter_examneuro_timestep_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep_1);
//            final EditText moter_examneuro_timeeror_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror_1);
//            final EditText moter_examneuro_timetaken_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken_2);
//            final EditText moter_examneuro_timestep_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep_2);
//            final EditText moter_examneuro_timeeror_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror_2);
//            final EditText moter_examneuro_noerror = (EditText) dialog.findViewById(R.id.moter_examneuro_noerror);
//            final EditText moter_examneuro_gait_surface = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_surface);
//            final EditText moter_examneuro_gait_speed = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_speed);
//            final EditText moter_examneuro_gait_hori_head = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_hori_head);
//            final EditText moter_examneuro_gait_veri_head = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_veri_head);
//            final EditText moter_examneuro_gait_piovt = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_piovt);
//            final EditText moter_examneuro_gait_overobstacle = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_overobstacle);
//            final EditText moter_examneuro_gait_aroundobstacles = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_aroundobstacles);
//
//            final EditText moter_examneuro_gait_steps = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_steps);
//            final EditText moter_examneuro_gait_balance_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_balance_desc);
//            final EditText moter_examneuro_modifie_bowels= (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bowels);
//            final EditText moter_examneuro_modifie_bladder = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bladder);
//            final EditText moter_examneuro_modifie_grooming = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_grooming);
//            final  EditText moter_examneuro_modifie_toilet = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_toilet);
//            final EditText moter_examneuro_modifie_feeding = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_feeding);
//            final EditText moter_examneuro_modifie_transfer = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_transfer);
//            final EditText  moter_examneuro_modifie_mobility = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_mobility);
//            final EditText moter_examneuro_modifie_dressing = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_dressing);
//            final EditText moter_examneuro_modifie_stairs = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_stairs);
//            final EditText moter_examneuro_modifie_bathing = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bathing);
//            final EditText sensory_saphenres_left = (EditText) dialog.findViewById(R.id.sensory_saphenres_left);
//            final EditText sensory_saphenres_right = (EditText) dialog.findViewById(R.id.sensory_saphenres_right);
//
//            moter_examneuro_date.setText(NeuroFragment.contactDetails1.get(position).getSend_date().toString());
//            moter_examneuro_glas_eye.setText(NeuroFragment.contactDetails1.get(position).getOne().toString());
//            moter_examneuro_glas_verbal.setText(NeuroFragment.contactDetails1.get(position).getTwo().toString());
//            moter_examneuro_glas_motor.setText(NeuroFragment.contactDetails1.get(position).getThree().toString());
//            moter_examneuro_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFour().toString());
//            moter_examneuro_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getSix().toString());
//            moter_examneuro_left_Median.setText(NeuroFragment.contactDetails1.get(position).getEight().toString());
//            moter_examneuro_left_musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getTen().toString());
//            moter_examneuro_left_sciatic.setText(NeuroFragment.contactDetails1.get(position).getTwelve().toString());
//            moter_examneuro_left_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourteen().toString());
//            moter_examneuro_left_Commanperonial.setText(NeuroFragment.contactDetails1.get(position).getSixt().toString());
//            moter_examneuro_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getEightt().toString());
//            moter_examneuro_left_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwenty().toString());
//            moter_examneuro_left_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentytwo().toString());
//            moter_examneuro_left_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfour().toString());
//            moter_examneuro_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFive().toString());
//            moter_examneuro_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getSeven().toString());
//            moter_examneuro_right_Median.setText(NeuroFragment.contactDetails1.get(position).getNine().toString());
//            moter_examneuro_right_Musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getEleven().toString());
//            moter_examneuro_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirteen().toString());
//            moter_examneuro_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFiftn().toString());
//            moter_examneuro_right_Comman.setText(NeuroFragment.contactDetails1.get(position).getSevent().toString());
//            moter_examneuro_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getNinet().toString());
//            moter_examneuro_right_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwentyone().toString());
//            moter_examneuro_right_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentythree().toString());
//            moter_examneuro_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfive().toString());
//            moter_examneuro_special_test.setText(NeuroFragment.contactDetails1.get(position).getTwentyeight().toString());
//            moter_examneuro_special_desc.setText(NeuroFragment.contactDetails1.get(position).getTwentynine().toString());
//            moter_examneuro_adl_name.setText(NeuroFragment.contactDetails1.get(position).getThirty().toString());
//            moter_examneuro_adl_desc.setText(NeuroFragment.contactDetails1.get(position).getThirtyone().toString());
//            moter_examneuro_ntp_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtytwo().toString());
//            moter_examneuro_ntp_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfour().toString());
//            moter_examneuro_ntp_left_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtysix().toString());
//            moter_examneuro_ntp_left_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirtyeight().toString());
//            moter_examneuro_ntp_left_tibial.setText(NeuroFragment.contactDetails1.get(position).getFourty().toString());
//            moter_examneuro_ntp_left_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtytwo().toString());
//            moter_examneuro_ntp_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfour().toString());
//            moter_examneuro_ntp_left_sural.setText(NeuroFragment.contactDetails1.get(position).getFourtysix().toString());
//            moter_examneuro_ntp_left_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFourtynine().toString());
//            moter_examneuro_ntp_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtythree().toString());
//            moter_examneuro_ntp_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfive().toString());
//            moter_examneuro_ntp_right_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtyseven().toString());
//            moter_examneuro_ntp_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());
//
//            moter_examneuro_ntp_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourtyone().toString());
//            moter_examneuro_ntp_right_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtythree().toString());
//            moter_examneuro_ntp_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfive().toString());
//            moter_examneuro_ntp_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());
//            moter_examneuro_ntp_right_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFifty().toString());
//            moter_examneuro_timetaken.setText(NeuroFragment.contactDetails1.get(position).getFiftyone().toString());
//            moter_examneuro_noerror.setText(NeuroFragment.contactDetails1.get(position).getSixtynine().toString());
//            moter_examneuro_gait_surface.setText(NeuroFragment.contactDetails1.get(position).getSixty().toString());
//            moter_examneuro_gait_speed.setText(NeuroFragment.contactDetails1.get(position).getSixtyone().toString());
//            moter_examneuro_gait_hori_head.setText(NeuroFragment.contactDetails1.get(position).getSixtytwo().toString());
//            moter_examneuro_gait_veri_head.setText(NeuroFragment.contactDetails1.get(position).getSixtythree().toString());
//            moter_examneuro_gait_piovt.setText(NeuroFragment.contactDetails1.get(position).getSixtyfour().toString());
//            moter_examneuro_gait_overobstacle.setText(NeuroFragment.contactDetails1.get(position).getSixtyfive().toString());
//            moter_examneuro_gait_aroundobstacles.setText(NeuroFragment.contactDetails1.get(position).getSixtysix().toString());
//            moter_examneuro_gait_steps.setText(NeuroFragment.contactDetails1.get(position).getSixtyseven().toString());
//            moter_examneuro_gait_balance_desc.setText(NeuroFragment.contactDetails1.get(position).getSixtyeight().toString());
//            moter_examneuro_modifie_bowels.setText(NeuroFragment.contactDetails1.get(position).getSeventy().toString());
//            moter_examneuro_modifie_bladder.setText(NeuroFragment.contactDetails1.get(position).getSeventyone().toString());
//            moter_examneuro_modifie_grooming.setText(NeuroFragment.contactDetails1.get(position).getSeventytwo().toString());
//            moter_examneuro_modifie_toilet.setText(NeuroFragment.contactDetails1.get(position).getSeventythree().toString());
//            moter_examneuro_modifie_feeding.setText(NeuroFragment.contactDetails1.get(position).getSeventyfour().toString());
//            moter_examneuro_modifie_transfer.setText(NeuroFragment.contactDetails1.get(position).getSeventyfive().toString());
//            moter_examneuro_modifie_mobility.setText(NeuroFragment.contactDetails1.get(position).getSeventysix().toString());
//            moter_examneuro_modifie_dressing.setText(NeuroFragment.contactDetails1.get(position).getSeventyseven().toString());
//            moter_examneuro_modifie_stairs.setText(NeuroFragment.contactDetails1.get(position).getSeventyeight().toString());
//            moter_examneuro_modifie_bathing.setText(NeuroFragment.contactDetails1.get(position).getSeventynine().toString());
//
//            moter_examneuro_timestep.setText(NeuroFragment.contactDetails1.get(position).getFiftytwo().toString());
//            moter_examneuro_timeeror.setText(NeuroFragment.contactDetails1.get(position).getFiftythree().toString());
//            moter_examneuro_timetaken_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfour().toString());
//            moter_examneuro_timestep_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfive().toString());
//            moter_examneuro_timeeror_1.setText(NeuroFragment.contactDetails1.get(position).getFiftysix().toString());
//            moter_examneuro_timetaken_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyseven().toString());
//            moter_examneuro_timestep_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyeight().toString());
//            moter_examneuro_timeeror_2.setText(NeuroFragment.contactDetails1.get(position).getFiftynine().toString());
//            sensory_saphenres_left.setText(NeuroFragment.contactDetails1.get(position).getTwentysix().toString());
//            sensory_saphenres_right.setText(NeuroFragment.contactDetails1.get(position).getTwentyseven().toString());
//
//            //adding button click event
//            Button dismissButton = (Button) dialog.findViewById(R.id.button);
//            dismissButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    getpnote(moter_examneuro_glas_eye.getText().toString(),moter_examneuro_glas_verbal.getText().toString(),
//                            moter_examneuro_glas_motor.getText().toString(),moter_examneuro_left_Ulnar.getText().toString(),
//                            moter_examneuro_left_Radial.getText().toString(),moter_examneuro_left_Median.getText().toString(),
//                            moter_examneuro_left_musculocutaneous.getText().toString(),moter_examneuro_left_sciatic.getText().toString(),
//                            moter_examneuro_left_Tibial.getText().toString(),moter_examneuro_left_Commanperonial.getText().toString(),
//                            moter_examneuro_left_Femoral.getText().toString(),moter_examneuro_left_Lateralcutaneous.getText().toString(),
//                            moter_examneuro_left_Obturator.getText().toString(),moter_examneuro_left_Sural.getText().toString(),
//                            moter_examneuro_right_Ulnar.getText().toString(),moter_examneuro_right_Radial.getText().toString(),
//                            moter_examneuro_right_Median.getText().toString(),moter_examneuro_right_Musculocutaneous.getText().toString(),
//                            moter_examneuro_right_Sciatic.getText().toString(),moter_examneuro_right_Tibial.getText().toString(),
//                            moter_examneuro_right_Comman.getText().toString(),moter_examneuro_right_Femoral.getText().toString(),
//                            moter_examneuro_right_Lateralcutaneous.getText().toString(),moter_examneuro_right_Obturator.getText().toString(),
//                            moter_examneuro_right_Sural.getText().toString(),moter_examneuro_special_test.getText().toString(),
//                            moter_examneuro_special_desc.getText().toString(),moter_examneuro_adl_name.getText().toString(),
//                            moter_examneuro_adl_desc.getText().toString(), moter_examneuro_ntp_left_Ulnar.getText().toString(),
//                            moter_examneuro_ntp_left_Radial.getText().toString(), moter_examneuro_ntp_left_Median.getText().toString(),
//                            moter_examneuro_ntp_left_Sciatic.getText().toString(),moter_examneuro_ntp_left_tibial.getText().toString(),
//                            moter_examneuro_ntp_left_peronial.getText().toString(),moter_examneuro_ntp_left_Femoral.getText().toString(),
//                            moter_examneuro_ntp_left_sural.getText().toString(),moter_examneuro_ntp_left_Saphenous.getText().toString(),
//                            moter_examneuro_ntp_right_Ulnar.getText().toString(),moter_examneuro_ntp_right_Radial.getText().toString(),
//                            moter_examneuro_ntp_right_Median.getText().toString(),moter_examneuro_ntp_right_Sciatic.getText().toString(),
//                            moter_examneuro_ntp_right_Tibial.getText().toString(),moter_examneuro_ntp_right_peronial.getText().toString(),
//                            moter_examneuro_ntp_right_Femoral.getText().toString(),moter_examneuro_ntp_right_Sural.getText().toString(),moter_examneuro_ntp_right_Saphenous.getText().toString(),
//                            moter_examneuro_timetaken.getText().toString(),moter_examneuro_noerror.getText().toString(),
//                            moter_examneuro_gait_surface.getText().toString(),moter_examneuro_gait_speed.getText().toString(),
//                            moter_examneuro_gait_hori_head.getText().toString(),moter_examneuro_gait_veri_head.getText().toString(),
//                            moter_examneuro_gait_piovt.getText().toString(),moter_examneuro_gait_overobstacle.getText().toString(),
//                            moter_examneuro_gait_aroundobstacles.getText().toString(),moter_examneuro_gait_steps.getText().toString(),
//                            moter_examneuro_gait_balance_desc.getText().toString(),moter_examneuro_modifie_bowels.getText().toString(),
//                            moter_examneuro_modifie_bladder.getText().toString(),moter_examneuro_modifie_grooming.getText().toString(),
//                            moter_examneuro_modifie_toilet.getText().toString(),moter_examneuro_modifie_feeding.getText().toString(),
//                            moter_examneuro_modifie_transfer.getText().toString(),moter_examneuro_modifie_mobility.getText().toString(),
//                            moter_examneuro_modifie_dressing.getText().toString(),moter_examneuro_modifie_stairs.getText().toString(),
//                            moter_examneuro_modifie_bathing.getText().toString(),NeuroFragment.contactDetails1.get(position).getPatientid().toString(),
//                            NeuroFragment.contactDetails1.get(position).getStr4().toString(),moter_examneuro_date.getText().toString()
//                            ,moter_examneuro_timestep.getText().toString(),moter_examneuro_timeeror.getText().toString()
//                            ,moter_examneuro_timetaken_1.getText().toString(),moter_examneuro_timestep_1.getText().toString()
//                    ,moter_examneuro_timeeror_1.getText().toString(),moter_examneuro_timetaken_2.getText().toString()
//                    ,moter_examneuro_timestep_2.getText().toString(),moter_examneuro_timeeror_2.getText().toString()
//                    ,sensory_saphenres_left.getText().toString(),sensory_saphenres_right.getText().toString());
//
//
//                }
//            });
//            dialog.show();
//        }
//    });
    }


    private void deleteAlert(final int position, final String ID) {

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

    private void delepnotes(final int position, final String ID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_NEURO_EXAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            if (new JSONObject(response).optString("success").equals("true")) {
                                neuroExamResultPOJOList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(activity.getApplicationContext(), "Record successfully deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity.getApplicationContext(), "Record Deletion Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
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
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("id", ID);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre", String.valueOf(neuroExamResultPOJOList.size()));
        return neuroExamResultPOJOList.size();
    }

}
