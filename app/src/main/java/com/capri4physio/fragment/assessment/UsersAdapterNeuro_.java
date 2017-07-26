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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.MotorItem;
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

public class UsersAdapterNeuro_ extends RecyclerView.Adapter<UsersAdapterNeuro_.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    CoordinatorLayout mSnackBarLayout;
    Dialog dialog;
    EditText editTextcontents;
    String posn,contents,id,note_iid;
    private List<MotorItem> mList;
    private ViewItemClickListener<MotorItem> mCallback;
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


    public UsersAdapterNeuro_(List<InfoApps> moviesList, Context ctx, CoordinatorLayout mSnackBarLayou) {
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

        holder.genre.setText(NeuroFragment.contactDetails1.get(position).getSend_date().toString());
        Log.d("sunil", "" + NeuroFragment.contactDetails1.get(position).getSend_date().toString());
        holder.title.setText("");


        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoApps  posn = NeuroFragment.contactDetails1.get(position);
                note_iid = NeuroFragment.contactDetails1.get(position).getStr4().toString();
                delepnotes(posn,note_iid);
//                Toast.makeText(ctx, "sgfdkjs"+positiin, Toast.LENGTH_LONG).show();
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
                dialog.setContentView(R.layout.neuro_dialog_view);
                dialog.setTitle("View - Neuro exam");

                //adding text dynamically

                TextView moter_examneuro_date = (TextView) dialog.findViewById(R.id.moter_examneuro_date);
                TextView  moter_examneuro_glas_eye = (TextView) dialog.findViewById(R.id.moter_examneuro_glas_eye);
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
                TextView moter_examneuro_ntp_left_Median= (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Median);
                TextView moter_examneuro_ntp_left_Sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Sciatic);
                TextView moter_examneuro_ntp_left_tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_tibial);
                TextView moter_examneuro_ntp_left_peronial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_peronial);
                TextView moter_examneuro_ntp_left_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Femoral);
                TextView moter_examneuro_ntp_left_sural = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_sural);
                TextView  moter_examneuro_ntp_left_Saphenous = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_left_Saphenous);
                TextView moter_examneuro_ntp_right_Ulnar = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Ulnar);
                TextView moter_examneuro_ntp_right_Radial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Radial);
                TextView moter_examneuro_ntp_right_Median = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Median);
                TextView moter_examneuro_ntp_right_Sciatic = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sciatic);
                TextView moter_examneuro_ntp_right_Tibial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Tibial);

                TextView moter_examneuro_ntp_right_peronial = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_peronial);
                TextView moter_examneuro_ntp_right_Femoral = (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Femoral);
                TextView moter_examneuro_ntp_right_Sural= (TextView) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sural);
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
                TextView  moter_examneuro_gait_hori_head = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_hori_head);
                TextView moter_examneuro_gait_veri_head = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_veri_head);
                TextView moter_examneuro_gait_piovt = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_piovt);
                TextView moter_examneuro_gait_overobstacle = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_overobstacle);
                TextView moter_examneuro_gait_aroundobstacles = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_aroundobstacles);
                final TextView sensory_saphenres_left = (TextView) dialog.findViewById(R.id.sensory_saphenres_left);
                final TextView sensory_saphenres_right = (TextView) dialog.findViewById(R.id.sensory_saphenres_right);
                TextView moter_examneuro_gait_steps = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_steps);
                TextView moter_examneuro_gait_balance_desc = (TextView) dialog.findViewById(R.id.moter_examneuro_gait_balance_desc);
                TextView moter_examneuro_modifie_bowels= (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bowels);
                TextView moter_examneuro_modifie_bladder = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bladder);
                TextView moter_examneuro_modifie_grooming = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_grooming);
                TextView moter_examneuro_modifie_toilet = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_toilet);
                TextView moter_examneuro_modifie_feeding = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_feeding);
                TextView moter_examneuro_modifie_transfer = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_transfer);
                TextView  moter_examneuro_modifie_mobility = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_mobility);
                TextView moter_examneuro_modifie_dressing = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_dressing);
                TextView moter_examneuro_modifie_stairs = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_stairs);
                TextView moter_examneuro_modifie_bathing = (TextView) dialog.findViewById(R.id.moter_examneuro_modifie_bathing);

                moter_examneuro_date.setText(NeuroFragment.contactDetails1.get(position).getSend_date().toString());
                moter_examneuro_glas_eye.setText(NeuroFragment.contactDetails1.get(position).getOne().toString());
                moter_examneuro_glas_verbal.setText(NeuroFragment.contactDetails1.get(position).getTwo().toString());
                moter_examneuro_glas_motor.setText(NeuroFragment.contactDetails1.get(position).getThree().toString());
                moter_examneuro_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFour().toString());
                moter_examneuro_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getSix().toString());
                moter_examneuro_left_Median.setText(NeuroFragment.contactDetails1.get(position).getEight().toString());
                moter_examneuro_left_musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getTen().toString());
                moter_examneuro_left_sciatic.setText(NeuroFragment.contactDetails1.get(position).getTwelve().toString());
                moter_examneuro_left_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourteen().toString());
                moter_examneuro_left_Commanperonial.setText(NeuroFragment.contactDetails1.get(position).getSixt().toString());
                moter_examneuro_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getEightt().toString());
                moter_examneuro_left_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwenty().toString());
                moter_examneuro_left_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentytwo().toString());
                moter_examneuro_left_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfour().toString());
                moter_examneuro_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFive().toString());
                moter_examneuro_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getSeven().toString());
                moter_examneuro_right_Median.setText(NeuroFragment.contactDetails1.get(position).getNine().toString());
                moter_examneuro_right_Musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getEleven().toString());
                moter_examneuro_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirteen().toString());
                moter_examneuro_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFiftn().toString());
                moter_examneuro_right_Comman.setText(NeuroFragment.contactDetails1.get(position).getSevent().toString());
                moter_examneuro_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getNinet().toString());
                moter_examneuro_right_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwentyone().toString());
                moter_examneuro_right_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentythree().toString());
                moter_examneuro_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfive().toString());
                moter_examneuro_special_test.setText(NeuroFragment.contactDetails1.get(position).getTwentyeight().toString());
                moter_examneuro_special_desc.setText(NeuroFragment.contactDetails1.get(position).getTwentynine().toString());
                moter_examneuro_adl_name.setText(NeuroFragment.contactDetails1.get(position).getThirty().toString());
                moter_examneuro_adl_desc.setText(NeuroFragment.contactDetails1.get(position).getThirtyone().toString());
                moter_examneuro_ntp_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtytwo().toString());
                moter_examneuro_ntp_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfour().toString());
                moter_examneuro_ntp_left_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtysix().toString());
                moter_examneuro_ntp_left_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirtyeight().toString());
                moter_examneuro_ntp_left_tibial.setText(NeuroFragment.contactDetails1.get(position).getFourty().toString());
                moter_examneuro_ntp_left_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtytwo().toString());
                moter_examneuro_ntp_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfour().toString());
                moter_examneuro_ntp_left_sural.setText(NeuroFragment.contactDetails1.get(position).getFourtysix().toString());
                moter_examneuro_ntp_left_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFourtynine().toString());
                moter_examneuro_ntp_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtythree().toString());
                moter_examneuro_ntp_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfive().toString());
                moter_examneuro_ntp_right_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtyseven().toString());
                moter_examneuro_ntp_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());

                moter_examneuro_ntp_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourtyone().toString());
                moter_examneuro_ntp_right_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtythree().toString());
                moter_examneuro_ntp_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfive().toString());
                moter_examneuro_ntp_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());
                moter_examneuro_ntp_right_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFifty().toString());
                moter_examneuro_timetaken.setText(NeuroFragment.contactDetails1.get(position).getFiftyone().toString());
                moter_examneuro_noerror.setText(NeuroFragment.contactDetails1.get(position).getSixtynine().toString());
                moter_examneuro_gait_surface.setText(NeuroFragment.contactDetails1.get(position).getSixty().toString());
                moter_examneuro_gait_speed.setText(NeuroFragment.contactDetails1.get(position).getSixtyone().toString());
                moter_examneuro_gait_hori_head.setText(NeuroFragment.contactDetails1.get(position).getSixtytwo().toString());
                moter_examneuro_gait_veri_head.setText(NeuroFragment.contactDetails1.get(position).getSixtythree().toString());
                moter_examneuro_gait_piovt.setText(NeuroFragment.contactDetails1.get(position).getSixtyfour().toString());
                moter_examneuro_gait_overobstacle.setText(NeuroFragment.contactDetails1.get(position).getSixtyfive().toString());
                moter_examneuro_gait_aroundobstacles.setText(NeuroFragment.contactDetails1.get(position).getSixtysix().toString());
                moter_examneuro_gait_steps.setText(NeuroFragment.contactDetails1.get(position).getSixtyseven().toString());
                moter_examneuro_gait_balance_desc.setText(NeuroFragment.contactDetails1.get(position).getSixtyeight().toString());
                moter_examneuro_modifie_bowels.setText(NeuroFragment.contactDetails1.get(position).getSeventy().toString());
                moter_examneuro_modifie_bladder.setText(NeuroFragment.contactDetails1.get(position).getSeventyone().toString());
                moter_examneuro_modifie_grooming.setText(NeuroFragment.contactDetails1.get(position).getSeventytwo().toString());
                moter_examneuro_modifie_toilet.setText(NeuroFragment.contactDetails1.get(position).getSeventythree().toString());
                moter_examneuro_modifie_feeding.setText(NeuroFragment.contactDetails1.get(position).getSeventyfour().toString());
                moter_examneuro_modifie_transfer.setText(NeuroFragment.contactDetails1.get(position).getSeventyfive().toString());
                moter_examneuro_modifie_mobility.setText(NeuroFragment.contactDetails1.get(position).getSeventysix().toString());
                moter_examneuro_modifie_dressing.setText(NeuroFragment.contactDetails1.get(position).getSeventyseven().toString());
                moter_examneuro_modifie_stairs.setText(NeuroFragment.contactDetails1.get(position).getSeventyeight().toString());
                moter_examneuro_modifie_bathing.setText(NeuroFragment.contactDetails1.get(position).getSeventynine().toString());

                moter_examneuro_timestep.setText(NeuroFragment.contactDetails1.get(position).getFiftytwo().toString());
                moter_examneuro_timeeror.setText(NeuroFragment.contactDetails1.get(position).getFiftythree().toString());
                moter_examneuro_timetaken_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfour().toString());
                moter_examneuro_timestep_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfive().toString());
                moter_examneuro_timeeror_1.setText(NeuroFragment.contactDetails1.get(position).getFiftysix().toString());
                moter_examneuro_timetaken_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyseven().toString());
                moter_examneuro_timestep_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyeight().toString());
                moter_examneuro_timeeror_2.setText(NeuroFragment.contactDetails1.get(position).getFiftynine().toString());
                sensory_saphenres_left.setText(NeuroFragment.contactDetails1.get(position).getTwentysix().toString());
                sensory_saphenres_right.setText(NeuroFragment.contactDetails1.get(position).getTwentyseven().toString());

               /* bill_number.setText(mList.get(position).getDate());
                staff_name.setText(mList.get(position).getId());
                patient_name.setText(mList.get(position).getSurgicalHistory());

                diabetes.setText(mList.get(position).getDiabetes());
                blood_pressure.setText(mList.get(position).getBlood_pressure());
                smoking.setText(mList.get(position).getSmoking());
                fever_and_chill.setText(mList.get(position).getFever_and_chill());

                heart_diseases.setText(mList.get(position).getHeart_diseases());
                bleeding_disorder.setText(mList.get(position).getBleeding_disorder());
                recent_infection.setText(mList.get(position).getRecent_infection());
                pregnancy.setText(mList.get(position).getPregnancy());

                htn.setText(mList.get(position).getHtn());
                tb.setText(mList.get(position).getTb());
                cancer.setText(mList.get(position).getCancer());
                hiv_aids.setText(mList.get(position).getHiv_aids());

                past_surgery.setText(mList.get(position).getPast_surgery());
                allergies.setText(mList.get(position).getAllergies());
                osteoporotic.setText(mList.get(position).getOsteoporotic());
                depression.setText(mList.get(position).getDepression());

                Hepatitis.setText(mList.get(position).getHepatitis());
                any_implants.setText(mList.get(position).getAny_implants());
                hereditary_disease.setText(mList.get(position).getHereditary_disease());*/

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

    holder.mImgAttachemnt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*posn = CaseNotesFragment.contactDetails1.get(position).toString();
                contents = CaseNotesFragment.contactDetails1.get(position).getName().toString();
                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();*/
            dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

            //setting custom layout to dialog
            dialog.setContentView(R.layout.neuro_dialogedit);
            dialog.setTitle("Edit- Neuro exam");

            //adding text dynamically

            final EditText moter_examneuro_date = (EditText) dialog.findViewById(R.id.moter_examneuro_date);
            final EditText moter_examneuro_glas_eye = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_eye);
            final EditText moter_examneuro_glas_verbal = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_verbal);
            final EditText moter_examneuro_glas_motor = (EditText) dialog.findViewById(R.id.moter_examneuro_glas_motor);
            final EditText moter_examneuro_left_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Ulnar);
            final EditText moter_examneuro_left_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Radial);
            final EditText moter_examneuro_left_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Median);
            final EditText moter_examneuro_left_musculocutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_left_musculocutaneous);
            final EditText moter_examneuro_left_sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_left_sciatic);
            final EditText moter_examneuro_left_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Tibial);
            final EditText moter_examneuro_left_Commanperonial = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Commanperonial);
            final EditText moter_examneuro_left_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Femoral);
            final EditText moter_examneuro_left_Lateralcutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Lateralcutaneous);
            final EditText moter_examneuro_left_Obturator = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Obturator);
            final EditText moter_examneuro_left_Sural = (EditText) dialog.findViewById(R.id.moter_examneuro_left_Sural);
            final EditText moter_examneuro_right_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Ulnar);
            final EditText moter_examneuro_right_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Radial);
            final EditText moter_examneuro_right_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Median);
            final EditText moter_examneuro_right_Musculocutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Musculocutaneous);
            final EditText moter_examneuro_right_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Sciatic);
            final EditText moter_examneuro_right_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Tibial);
            final EditText moter_examneuro_right_Comman = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Comman);
            final EditText moter_examneuro_right_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Femoral);
            final EditText moter_examneuro_right_Lateralcutaneous = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Lateralcutaneous);
            final EditText moter_examneuro_right_Obturator = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Obturator);
            final EditText moter_examneuro_right_Sural = (EditText) dialog.findViewById(R.id.moter_examneuro_right_Sural);
            final EditText moter_examneuro_special_test = (EditText) dialog.findViewById(R.id.moter_examneuro_special_test);
            final EditText moter_examneuro_special_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_special_desc);
            final EditText moter_examneuro_adl_name = (EditText) dialog.findViewById(R.id.moter_examneuro_adl_name);
            final EditText moter_examneuro_adl_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_adl_desc);
            final EditText moter_examneuro_ntp_left_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Ulnar);
            final EditText moter_examneuro_ntp_left_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Radial);
            final EditText moter_examneuro_ntp_left_Median= (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Median);
            final EditText moter_examneuro_ntp_left_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Sciatic);
            final EditText moter_examneuro_ntp_left_tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_tibial);
            final EditText moter_examneuro_ntp_left_peronial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_peronial);
            final EditText moter_examneuro_ntp_left_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Femoral);
            final EditText moter_examneuro_ntp_left_sural = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_sural);
            final EditText  moter_examneuro_ntp_left_Saphenous = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_left_Saphenous);
            final EditText moter_examneuro_ntp_right_Ulnar = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Ulnar);
            final EditText moter_examneuro_ntp_right_Radial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Radial);
            final EditText moter_examneuro_ntp_right_Median = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Median);
            final EditText moter_examneuro_ntp_right_Sciatic = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sciatic);
            final EditText moter_examneuro_ntp_right_Tibial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Tibial);

            final EditText moter_examneuro_ntp_right_peronial = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_peronial);
            final EditText moter_examneuro_ntp_right_Femoral = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Femoral);
            final EditText moter_examneuro_ntp_right_Sural= (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Sural);
            final EditText moter_examneuro_ntp_right_Saphenous = (EditText) dialog.findViewById(R.id.moter_examneuro_ntp_right_Saphenous);
            final EditText moter_examneuro_timetaken = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken);
            final EditText moter_examneuro_timestep = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep);
            final EditText moter_examneuro_timeeror = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror);
            final EditText moter_examneuro_timetaken_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken_1);
            final EditText moter_examneuro_timestep_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep_1);
            final EditText moter_examneuro_timeeror_1 = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror_1);
            final EditText moter_examneuro_timetaken_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timetaken_2);
            final EditText moter_examneuro_timestep_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timestep_2);
            final EditText moter_examneuro_timeeror_2 = (EditText) dialog.findViewById(R.id.moter_examneuro_timeeror_2);
            final EditText moter_examneuro_noerror = (EditText) dialog.findViewById(R.id.moter_examneuro_noerror);
            final EditText moter_examneuro_gait_surface = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_surface);
            final EditText moter_examneuro_gait_speed = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_speed);
            final EditText moter_examneuro_gait_hori_head = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_hori_head);
            final EditText moter_examneuro_gait_veri_head = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_veri_head);
            final EditText moter_examneuro_gait_piovt = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_piovt);
            final EditText moter_examneuro_gait_overobstacle = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_overobstacle);
            final EditText moter_examneuro_gait_aroundobstacles = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_aroundobstacles);

            final EditText moter_examneuro_gait_steps = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_steps);
            final EditText moter_examneuro_gait_balance_desc = (EditText) dialog.findViewById(R.id.moter_examneuro_gait_balance_desc);
            final EditText moter_examneuro_modifie_bowels= (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bowels);
            final EditText moter_examneuro_modifie_bladder = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bladder);
            final EditText moter_examneuro_modifie_grooming = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_grooming);
            final  EditText moter_examneuro_modifie_toilet = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_toilet);
            final EditText moter_examneuro_modifie_feeding = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_feeding);
            final EditText moter_examneuro_modifie_transfer = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_transfer);
            final EditText  moter_examneuro_modifie_mobility = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_mobility);
            final EditText moter_examneuro_modifie_dressing = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_dressing);
            final EditText moter_examneuro_modifie_stairs = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_stairs);
            final EditText moter_examneuro_modifie_bathing = (EditText) dialog.findViewById(R.id.moter_examneuro_modifie_bathing);
            final EditText sensory_saphenres_left = (EditText) dialog.findViewById(R.id.sensory_saphenres_left);
            final EditText sensory_saphenres_right = (EditText) dialog.findViewById(R.id.sensory_saphenres_right);

            moter_examneuro_date.setText(NeuroFragment.contactDetails1.get(position).getSend_date().toString());
            moter_examneuro_glas_eye.setText(NeuroFragment.contactDetails1.get(position).getOne().toString());
            moter_examneuro_glas_verbal.setText(NeuroFragment.contactDetails1.get(position).getTwo().toString());
            moter_examneuro_glas_motor.setText(NeuroFragment.contactDetails1.get(position).getThree().toString());
            moter_examneuro_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFour().toString());
            moter_examneuro_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getSix().toString());
            moter_examneuro_left_Median.setText(NeuroFragment.contactDetails1.get(position).getEight().toString());
            moter_examneuro_left_musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getTen().toString());
            moter_examneuro_left_sciatic.setText(NeuroFragment.contactDetails1.get(position).getTwelve().toString());
            moter_examneuro_left_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourteen().toString());
            moter_examneuro_left_Commanperonial.setText(NeuroFragment.contactDetails1.get(position).getSixt().toString());
            moter_examneuro_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getEightt().toString());
            moter_examneuro_left_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwenty().toString());
            moter_examneuro_left_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentytwo().toString());
            moter_examneuro_left_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfour().toString());
            moter_examneuro_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getFive().toString());
            moter_examneuro_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getSeven().toString());
            moter_examneuro_right_Median.setText(NeuroFragment.contactDetails1.get(position).getNine().toString());
            moter_examneuro_right_Musculocutaneous.setText(NeuroFragment.contactDetails1.get(position).getEleven().toString());
            moter_examneuro_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirteen().toString());
            moter_examneuro_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFiftn().toString());
            moter_examneuro_right_Comman.setText(NeuroFragment.contactDetails1.get(position).getSevent().toString());
            moter_examneuro_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getNinet().toString());
            moter_examneuro_right_Lateralcutaneous.setText(NeuroFragment.contactDetails1.get(position).getTwentyone().toString());
            moter_examneuro_right_Obturator.setText(NeuroFragment.contactDetails1.get(position).getTwentythree().toString());
            moter_examneuro_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getTwentyfive().toString());
            moter_examneuro_special_test.setText(NeuroFragment.contactDetails1.get(position).getTwentyeight().toString());
            moter_examneuro_special_desc.setText(NeuroFragment.contactDetails1.get(position).getTwentynine().toString());
            moter_examneuro_adl_name.setText(NeuroFragment.contactDetails1.get(position).getThirty().toString());
            moter_examneuro_adl_desc.setText(NeuroFragment.contactDetails1.get(position).getThirtyone().toString());
            moter_examneuro_ntp_left_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtytwo().toString());
            moter_examneuro_ntp_left_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfour().toString());
            moter_examneuro_ntp_left_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtysix().toString());
            moter_examneuro_ntp_left_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getThirtyeight().toString());
            moter_examneuro_ntp_left_tibial.setText(NeuroFragment.contactDetails1.get(position).getFourty().toString());
            moter_examneuro_ntp_left_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtytwo().toString());
            moter_examneuro_ntp_left_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfour().toString());
            moter_examneuro_ntp_left_sural.setText(NeuroFragment.contactDetails1.get(position).getFourtysix().toString());
            moter_examneuro_ntp_left_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFourtynine().toString());
            moter_examneuro_ntp_right_Ulnar.setText(NeuroFragment.contactDetails1.get(position).getThirtythree().toString());
            moter_examneuro_ntp_right_Radial.setText(NeuroFragment.contactDetails1.get(position).getThirtyfive().toString());
            moter_examneuro_ntp_right_Median.setText(NeuroFragment.contactDetails1.get(position).getThirtyseven().toString());
            moter_examneuro_ntp_right_Sciatic.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());

            moter_examneuro_ntp_right_Tibial.setText(NeuroFragment.contactDetails1.get(position).getFourtyone().toString());
            moter_examneuro_ntp_right_peronial.setText(NeuroFragment.contactDetails1.get(position).getFourtythree().toString());
            moter_examneuro_ntp_right_Femoral.setText(NeuroFragment.contactDetails1.get(position).getFourtyfive().toString());
            moter_examneuro_ntp_right_Sural.setText(NeuroFragment.contactDetails1.get(position).getFourtyseven().toString());
            moter_examneuro_ntp_right_Saphenous.setText(NeuroFragment.contactDetails1.get(position).getFifty().toString());
            moter_examneuro_timetaken.setText(NeuroFragment.contactDetails1.get(position).getFiftyone().toString());
            moter_examneuro_noerror.setText(NeuroFragment.contactDetails1.get(position).getSixtynine().toString());
            moter_examneuro_gait_surface.setText(NeuroFragment.contactDetails1.get(position).getSixty().toString());
            moter_examneuro_gait_speed.setText(NeuroFragment.contactDetails1.get(position).getSixtyone().toString());
            moter_examneuro_gait_hori_head.setText(NeuroFragment.contactDetails1.get(position).getSixtytwo().toString());
            moter_examneuro_gait_veri_head.setText(NeuroFragment.contactDetails1.get(position).getSixtythree().toString());
            moter_examneuro_gait_piovt.setText(NeuroFragment.contactDetails1.get(position).getSixtyfour().toString());
            moter_examneuro_gait_overobstacle.setText(NeuroFragment.contactDetails1.get(position).getSixtyfive().toString());
            moter_examneuro_gait_aroundobstacles.setText(NeuroFragment.contactDetails1.get(position).getSixtysix().toString());
            moter_examneuro_gait_steps.setText(NeuroFragment.contactDetails1.get(position).getSixtyseven().toString());
            moter_examneuro_gait_balance_desc.setText(NeuroFragment.contactDetails1.get(position).getSixtyeight().toString());
            moter_examneuro_modifie_bowels.setText(NeuroFragment.contactDetails1.get(position).getSeventy().toString());
            moter_examneuro_modifie_bladder.setText(NeuroFragment.contactDetails1.get(position).getSeventyone().toString());
            moter_examneuro_modifie_grooming.setText(NeuroFragment.contactDetails1.get(position).getSeventytwo().toString());
            moter_examneuro_modifie_toilet.setText(NeuroFragment.contactDetails1.get(position).getSeventythree().toString());
            moter_examneuro_modifie_feeding.setText(NeuroFragment.contactDetails1.get(position).getSeventyfour().toString());
            moter_examneuro_modifie_transfer.setText(NeuroFragment.contactDetails1.get(position).getSeventyfive().toString());
            moter_examneuro_modifie_mobility.setText(NeuroFragment.contactDetails1.get(position).getSeventysix().toString());
            moter_examneuro_modifie_dressing.setText(NeuroFragment.contactDetails1.get(position).getSeventyseven().toString());
            moter_examneuro_modifie_stairs.setText(NeuroFragment.contactDetails1.get(position).getSeventyeight().toString());
            moter_examneuro_modifie_bathing.setText(NeuroFragment.contactDetails1.get(position).getSeventynine().toString());

            moter_examneuro_timestep.setText(NeuroFragment.contactDetails1.get(position).getFiftytwo().toString());
            moter_examneuro_timeeror.setText(NeuroFragment.contactDetails1.get(position).getFiftythree().toString());
            moter_examneuro_timetaken_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfour().toString());
            moter_examneuro_timestep_1.setText(NeuroFragment.contactDetails1.get(position).getFiftyfive().toString());
            moter_examneuro_timeeror_1.setText(NeuroFragment.contactDetails1.get(position).getFiftysix().toString());
            moter_examneuro_timetaken_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyseven().toString());
            moter_examneuro_timestep_2.setText(NeuroFragment.contactDetails1.get(position).getFiftyeight().toString());
            moter_examneuro_timeeror_2.setText(NeuroFragment.contactDetails1.get(position).getFiftynine().toString());
            sensory_saphenres_left.setText(NeuroFragment.contactDetails1.get(position).getTwentysix().toString());
            sensory_saphenres_right.setText(NeuroFragment.contactDetails1.get(position).getTwentyseven().toString());

            //adding button click event
            Button dismissButton = (Button) dialog.findViewById(R.id.button);
            dismissButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getpnote(moter_examneuro_glas_eye.getText().toString(),moter_examneuro_glas_verbal.getText().toString(),
                            moter_examneuro_glas_motor.getText().toString(),moter_examneuro_left_Ulnar.getText().toString(),
                            moter_examneuro_left_Radial.getText().toString(),moter_examneuro_left_Median.getText().toString(),
                            moter_examneuro_left_musculocutaneous.getText().toString(),moter_examneuro_left_sciatic.getText().toString(),
                            moter_examneuro_left_Tibial.getText().toString(),moter_examneuro_left_Commanperonial.getText().toString(),
                            moter_examneuro_left_Femoral.getText().toString(),moter_examneuro_left_Lateralcutaneous.getText().toString(),
                            moter_examneuro_left_Obturator.getText().toString(),moter_examneuro_left_Sural.getText().toString(),
                            moter_examneuro_right_Ulnar.getText().toString(),moter_examneuro_right_Radial.getText().toString(),
                            moter_examneuro_right_Median.getText().toString(),moter_examneuro_right_Musculocutaneous.getText().toString(),
                            moter_examneuro_right_Sciatic.getText().toString(),moter_examneuro_right_Tibial.getText().toString(),
                            moter_examneuro_right_Comman.getText().toString(),moter_examneuro_right_Femoral.getText().toString(),
                            moter_examneuro_right_Lateralcutaneous.getText().toString(),moter_examneuro_right_Obturator.getText().toString(),
                            moter_examneuro_right_Sural.getText().toString(),moter_examneuro_special_test.getText().toString(),
                            moter_examneuro_special_desc.getText().toString(),moter_examneuro_adl_name.getText().toString(),
                            moter_examneuro_adl_desc.getText().toString(), moter_examneuro_ntp_left_Ulnar.getText().toString(),
                            moter_examneuro_ntp_left_Radial.getText().toString(), moter_examneuro_ntp_left_Median.getText().toString(),
                            moter_examneuro_ntp_left_Sciatic.getText().toString(),moter_examneuro_ntp_left_tibial.getText().toString(),
                            moter_examneuro_ntp_left_peronial.getText().toString(),moter_examneuro_ntp_left_Femoral.getText().toString(),
                            moter_examneuro_ntp_left_sural.getText().toString(),moter_examneuro_ntp_left_Saphenous.getText().toString(),
                            moter_examneuro_ntp_right_Ulnar.getText().toString(),moter_examneuro_ntp_right_Radial.getText().toString(),
                            moter_examneuro_ntp_right_Median.getText().toString(),moter_examneuro_ntp_right_Sciatic.getText().toString(),
                            moter_examneuro_ntp_right_Tibial.getText().toString(),moter_examneuro_ntp_right_peronial.getText().toString(),
                            moter_examneuro_ntp_right_Femoral.getText().toString(),moter_examneuro_ntp_right_Sural.getText().toString(),moter_examneuro_ntp_right_Saphenous.getText().toString(),
                            moter_examneuro_timetaken.getText().toString(),moter_examneuro_noerror.getText().toString(),
                            moter_examneuro_gait_surface.getText().toString(),moter_examneuro_gait_speed.getText().toString(),
                            moter_examneuro_gait_hori_head.getText().toString(),moter_examneuro_gait_veri_head.getText().toString(),
                            moter_examneuro_gait_piovt.getText().toString(),moter_examneuro_gait_overobstacle.getText().toString(),
                            moter_examneuro_gait_aroundobstacles.getText().toString(),moter_examneuro_gait_steps.getText().toString(),
                            moter_examneuro_gait_balance_desc.getText().toString(),moter_examneuro_modifie_bowels.getText().toString(),
                            moter_examneuro_modifie_bladder.getText().toString(),moter_examneuro_modifie_grooming.getText().toString(),
                            moter_examneuro_modifie_toilet.getText().toString(),moter_examneuro_modifie_feeding.getText().toString(),
                            moter_examneuro_modifie_transfer.getText().toString(),moter_examneuro_modifie_mobility.getText().toString(),
                            moter_examneuro_modifie_dressing.getText().toString(),moter_examneuro_modifie_stairs.getText().toString(),
                            moter_examneuro_modifie_bathing.getText().toString(),NeuroFragment.contactDetails1.get(position).getPatientid().toString(),
                            NeuroFragment.contactDetails1.get(position).getStr4().toString(),moter_examneuro_date.getText().toString()
                            ,moter_examneuro_timestep.getText().toString(),moter_examneuro_timeeror.getText().toString()
                            ,moter_examneuro_timetaken_1.getText().toString(),moter_examneuro_timestep_1.getText().toString()
                    ,moter_examneuro_timeeror_1.getText().toString(),moter_examneuro_timetaken_2.getText().toString()
                    ,moter_examneuro_timestep_2.getText().toString(),moter_examneuro_timeeror_2.getText().toString()
                    ,sensory_saphenres_left.getText().toString(),sensory_saphenres_right.getText().toString());


                }
            });
            dialog.show();
        }
    });
}

    private void getpnote(final String moter_examneuro_glas_eye,final String moter_examneuro_glas_verbal,final String moter_examneuro_glas_motor,
                          final String moter_examneuro_left_Ulnar,final String moter_examneuro_left_Radial,final String moter_examneuro_left_Median,
                          final String moter_examneuro_left_musculocutaneous,final String moter_examneuro_left_sciatic,final String moter_examneuro_left_Tibial,
                          final String moter_examneuro_left_Commanperonial,final String moter_examneuro_left_Femoral,final String moter_examneuro_left_Lateralcutaneous,
                          final String moter_examneuro_left_Obturator,final String moter_examneuro_left_Sural,final String moter_examneuro_right_Ulnar,
                          final String  moter_examneuro_right_Radial,final String moter_examneuro_right_Median,final String moter_examneuro_right_Musculocutaneous,
                          final String  moter_examneuro_right_Sciatic,final String moter_examneuro_right_Tibial,final String moter_examneuro_right_Comman,
                          final String  moter_examneuro_right_Femoral,final String moter_examneuro_right_Lateralcutaneous,final String moter_examneuro_right_Obturator,
                          final String   moter_examneuro_right_Sural,final String moter_examneuro_special_test,final String moter_examneuro_special_desc,
                          final String  moter_examneuro_adl_name,final String moter_examneuro_adl_desc,final String moter_examneuro_ntp_left_Ulnar,
                          final String  moter_examneuro_ntp_left_Radial,final String moter_examneuro_ntp_left_Median,final String moter_examneuro_ntp_left_Sciatic,
                          final String  moter_examneuro_ntp_left_tibial,final String moter_examneuro_ntp_left_peronial,final String moter_examneuro_ntp_left_Femoral,
                          final String  moter_examneuro_ntp_left_sural,final String moter_examneuro_ntp_left_Saphenous,final String moter_examneuro_ntp_right_Ulnar,
                          final String  moter_examneuro_ntp_right_Radial,final String moter_examneuro_ntp_right_Median,final String moter_examneuro_ntp_right_Sciatic,
                          final String  moter_examneuro_ntp_right_Tibial,final String moter_examneuro_ntp_right_peronial,final String moter_examneuro_ntp_right_Femoral,
                          final String  moter_examneuro_ntp_right_Sural,final String moter_examneuro_ntp_right_Saphenous,final String moter_examneuro_timetaken,
                          final String  moter_examneuro_noerror,final String moter_examneuro_gait_surface,final String moter_examneuro_gait_speed,
                          final String  moter_examneuro_gait_hori_head,final String moter_examneuro_gait_veri_head,final String moter_examneuro_gait_piovt,
                          final String  moter_examneuro_gait_overobstacle,final String moter_examneuro_gait_aroundobstacles,final String moter_examneuro_gait_steps,
                          final String  moter_examneuro_gait_balance_desc,final String moter_examneuro_modifie_bowels,final String moter_examneuro_modifie_bladder,
                          final String  moter_examneuro_modifie_grooming,final String moter_examneuro_modifie_toilet,final String moter_examneuro_modifie_feeding,
                          final String  moter_examneuro_modifie_transfer,final String moter_examneuro_modifie_mobility,final String moter_examneuro_modifie_stairs,
                          final String moter_examneuro_modifie_dressing,
                          final String  moter_examneuro_modifie_bathing,final String patient_id,final String moter_examneuro_id,final String moter_examneuro_date
                          ,final String  moter_examneuro_timestep,final String moter_examneuro_timeeror,final String moter_examneuro_timetaken_1
    ,final String  moter_examneuro_timestep_1,final String moter_examneuro_timeeror_1,final String moter_examneuro_timetaken_2,final String moter_examneuro_timestep_2
    ,final String  moter_examneuro_timeeror_2,final String sensory_saphenres_left,final String sensory_saphenres_right)
    {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_NEURO_,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.e("result", response);
                                dialog.dismiss();
                                Toast.makeText(ctx, "Saved Successfull", Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("moter_examneuro_date", moter_examneuro_date);
                    params.put("patient_id", patient_id);
                    params.put("moter_examneuro_glas_eye", moter_examneuro_glas_eye);
                    params.put("moter_examneuro_glas_verbal", moter_examneuro_glas_verbal);
                    params.put("moter_examneuro_glas_motor", moter_examneuro_glas_motor);
                    params.put("moter_examneuro_gait_surface", moter_examneuro_gait_surface);
                    params.put("moter_examneuro_modifie_bathing", moter_examneuro_modifie_bathing);
                    params.put("moter_examneuro_gait_speed", moter_examneuro_gait_speed);
                    params.put("moter_examneuro_gait_hori_head", moter_examneuro_gait_hori_head);
                    params.put("moter_examneuro_gait_veri_head ", moter_examneuro_gait_veri_head);
                    params.put("moter_examneuro_gait_piovt ", moter_examneuro_gait_piovt);
                    params.put("moter_examneuro_gait_overobstacle", moter_examneuro_gait_overobstacle);
                    params.put("moter_examneuro_gait_aroundobstacles", moter_examneuro_gait_aroundobstacles);
                    params.put("moter_examneuro_gait_steps", moter_examneuro_gait_steps);
                    params.put("moter_examneuro_modifie_bowels", moter_examneuro_modifie_bowels);
                    params.put("moter_examneuro_modifie_bladder", moter_examneuro_modifie_bladder);
                    params.put("moter_examneuro_modifie_grooming", moter_examneuro_modifie_grooming);
                    params.put("moter_examneuro_modifie_toilet", moter_examneuro_modifie_toilet);
                    params.put("moter_examneuro_modifie_feeding", moter_examneuro_modifie_feeding);
                    params.put("moter_examneuro_modifie_transfer", moter_examneuro_modifie_transfer);
                    params.put("moter_examneuro_modifie_mobility", moter_examneuro_modifie_mobility);
                    params.put("moter_examneuro_adl_name", moter_examneuro_adl_name);
                    params.put("moter_examneuro_adl_desc", moter_examneuro_adl_desc);
                    params.put("moter_examneuro_modifie_dressing", moter_examneuro_modifie_dressing);
                    params.put("moter_examneuro_modifie_stairs", moter_examneuro_modifie_stairs);
                    params.put("moter_examneuro_left_Ulnar", moter_examneuro_left_Ulnar);
                    params.put("moter_examneuro_right_Ulnar", moter_examneuro_right_Ulnar);
                    params.put("moter_examneuro_left_Radial", moter_examneuro_left_Radial);
                    params.put("moter_examneuro_right_Radial", moter_examneuro_right_Radial);
                    params.put("moter_examneuro_left_musculocutaneous", moter_examneuro_left_musculocutaneous);
                    params.put("moter_examneuro_right_Musculocutaneous", moter_examneuro_right_Musculocutaneous);
                    params.put("moter_examneuro_left_sciatic", moter_examneuro_left_sciatic);
                    params.put("moter_examneuro_right_Sciatic", moter_examneuro_right_Sciatic);
                    params.put("moter_examneuro_left_Tibial", moter_examneuro_left_Tibial);
                    params.put("moter_examneuro_right_Tibial", moter_examneuro_right_Tibial);
                    params.put("moter_examneuro_left_Commanperonial", moter_examneuro_left_Commanperonial);
                    params.put("moter_examneuro_right_Comman", moter_examneuro_right_Comman);
                    params.put("moter_examneuro_left_Femoral", moter_examneuro_left_Femoral);
                    params.put("moter_examneuro_right_Femoral", moter_examneuro_right_Femoral);
                    params.put("moter_examneuro_left_Lateralcutaneous", moter_examneuro_left_Lateralcutaneous);
                    params.put("moter_examneuro_right_Lateralcutaneous", moter_examneuro_right_Lateralcutaneous);
                    params.put("moter_examneuro_left_Obturator", moter_examneuro_left_Obturator);
                    params.put("moter_examneuro_right_Obturator", moter_examneuro_right_Obturator);
                    params.put("moter_examneuro_left_Sural", moter_examneuro_left_Sural);
                    params.put("moter_examneuro_right_Sural", moter_examneuro_right_Sural);
                    params.put("moter_examneuro_special_test", moter_examneuro_special_test);
                    params.put("moter_examneuro_special_desc", moter_examneuro_special_desc);
                    params.put("moter_examneuro_ntp_left_Ulnar", moter_examneuro_ntp_left_Ulnar);
                    params.put("moter_examneuro_ntp_right_Ulnar", moter_examneuro_ntp_right_Ulnar);
                    params.put("moter_examneuro_ntp_left_Radial", moter_examneuro_ntp_left_Radial);
                    params.put("moter_examneuro_ntp_right_Radial", moter_examneuro_ntp_right_Radial);
                    params.put("moter_examneuro_ntp_left_Median", moter_examneuro_ntp_left_Median);
                    params.put("moter_examneuro_ntp_right_Median", moter_examneuro_ntp_right_Median);
                    params.put("moter_examneuro_gait_balance_desc", moter_examneuro_gait_balance_desc);
//                params.put("moter_examhip_right_rom6", Neuro50);
                    params.put("moter_examneuro_ntp_left_Sciatic", moter_examneuro_ntp_left_Sciatic);
                    params.put("moter_examneuro_ntp_right_Sciatic", moter_examneuro_ntp_right_Sciatic);
                    params.put("moter_examneuro_ntp_left_tibial", moter_examneuro_ntp_left_tibial);
                    params.put("moter_examneuro_ntp_right_Tibial", moter_examneuro_ntp_right_Tibial);
                    params.put("moter_examneuro_ntp_left_peronial", moter_examneuro_ntp_left_peronial);
                    params.put("moter_examneuro_ntp_right_peronial", moter_examneuro_ntp_right_peronial);
                    params.put("moter_examneuro_ntp_left_Femoral", moter_examneuro_ntp_left_Femoral);
                    params.put("moter_examneuro_ntp_right_Femoral", moter_examneuro_ntp_right_Femoral);
                    params.put("moter_examneuro_ntp_left_sural", moter_examneuro_ntp_left_sural);
                    params.put("moter_examneuro_ntp_right_Sural", moter_examneuro_ntp_right_Sural);
                    params.put("moter_examneuro_ntp_left_Saphenous", moter_examneuro_ntp_left_Saphenous);
                    params.put("moter_examneuro_ntp_right_Saphenous", moter_examneuro_ntp_right_Saphenous);
                    params.put("moter_examneuro_timetaken", moter_examneuro_timetaken);
                    params.put("moter_examneuro_noerror", moter_examneuro_noerror);
                    params.put("moter_examneuro_modifie_bathing", moter_examneuro_modifie_bathing);
                    params.put("moter_examneuro_left_Median", moter_examneuro_left_Median);
                    params.put("moter_examneuro_right_Median", moter_examneuro_right_Median);
                    params.put("moter_examneuro_left_Ulnar", moter_examneuro_left_Ulnar);
                    params.put("moter_examneuro_id", moter_examneuro_id);

                    params.put("moter_examneuro_timestep", moter_examneuro_timestep);
                    params.put("moter_examneuro_timeeror", moter_examneuro_timeeror);
                    params.put("moter_examneuro_timetaken_1", moter_examneuro_timetaken_1);
                    params.put("moter_examneuro_timestep_1", moter_examneuro_timestep_1);
                    params.put("moter_examneuro_timeeror_1", moter_examneuro_timeeror_1);
                    params.put("moter_examneuro_timetaken2", moter_examneuro_timetaken_2);
                    params.put("moter_examneuro_timestep_2", moter_examneuro_timestep_2);
                    params.put("moter_examneuro_timeeror_2", moter_examneuro_timeeror_2);
                    params.put("sensory_saphenres_left",sensory_saphenres_left );
                    params.put("sensory_saphenres_right",sensory_saphenres_right);

                /*params.put("sfirst_name",name);
                params.put("slast_name",lastName);
                params.put("sdob",dob);
                params.put("sage", "23");
                params.put("sdatejoing",doj);
                params.put("senddate", endingdateofcontract);
                params.put("sgender", rate);
                params.put("smarital_status", rate1);
                params.put("sdesignation",designation);
                params.put("saddress", address);
                params.put("scity", city);
                params.put("spincode", pin_code);
                params.put("smobile", phone);
                params.put("semail", email_id);
                params.put("squalifation", degree);
                params.put("sexprience", experienceduration);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                    return params;
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


    private void delepnotes(final InfoApps textView , final String ID){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_NEURO_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            moviesList.remove(textView);
                            notifyDataSetChanged();
                            Toast.makeText(ctx,"Record successfully deleted",Toast.LENGTH_SHORT).show();
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
                objresponse.put("moter_examneuro_id", ID);
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
