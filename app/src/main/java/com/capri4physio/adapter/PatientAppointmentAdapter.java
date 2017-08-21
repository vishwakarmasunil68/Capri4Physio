package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.ManagePatientAppointmentsActivity;
import com.capri4physio.model.newappointment.NewAppointmentResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class PatientAppointmentAdapter extends RecyclerView.Adapter<PatientAppointmentAdapter.MyViewHolder> {

    private List<NewAppointmentResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date,tv_time,tv_status;
        public LinearLayout ll_appointment;
        public ImageView iv_delete;


        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            ll_appointment = (LinearLayout) view.findViewById(R.id.ll_appointment);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        }
    }


    public PatientAppointmentAdapter(Activity activity, List<NewAppointmentResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_patient_appointments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       holder.tv_date.setText(horizontalList.get(position).getBookingDate());
       holder.tv_time.setText(horizontalList.get(position).getBookingStarttime());
        if(horizontalList.get(position).getStatus().equals("1")){
            holder.tv_status.setText("Appointment Confirmed");
        }else{
            holder.tv_status.setText("Appointment Pending");
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagePatientAppointmentsActivity viewAllAppointmentsActivity= (ManagePatientAppointmentsActivity) activity;
                viewAllAppointmentsActivity.cancelAppointment(horizontalList.get(position).getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (horizontalList != null) {
            return horizontalList.size();
        } else {
            return 0;
        }
    }
}
