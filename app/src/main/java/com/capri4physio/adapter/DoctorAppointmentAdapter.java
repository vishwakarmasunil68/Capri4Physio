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
import com.capri4physio.activity.ManageAppointmentActivity;
import com.capri4physio.model.newappointment.NewAppointmentResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.MyViewHolder> {

    private List<NewAppointmentResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date,tv_time,tv_status,tv_confirm;
        public LinearLayout ll_appointment;
        public ImageView iv_delete;


        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            ll_appointment = (LinearLayout) view.findViewById(R.id.ll_appointment);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        }
    }


    public DoctorAppointmentAdapter(Activity activity, List<NewAppointmentResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_doctor_appointments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       holder.tv_date.setText(horizontalList.get(position).getBookingDate());
       holder.tv_time.setText(horizontalList.get(position).getBookingStarttime());
        if(horizontalList.get(position).getStatus().equals("1")){
            holder.tv_status.setText("Appointment Confirmed");
            holder.tv_confirm.setVisibility(View.GONE);
        }else{
            holder.tv_status.setText("Appointment Pending");
            holder.tv_confirm.setVisibility(View.VISIBLE);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageAppointmentActivity viewAllAppointmentsActivity= (ManageAppointmentActivity) activity;
                viewAllAppointmentsActivity.cancelAppointment(horizontalList.get(position).getId(),horizontalList.get(position).getPatientId(),position);
            }
        });

        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageAppointmentActivity viewAllAppointmentsActivity= (ManageAppointmentActivity) activity;
                viewAllAppointmentsActivity.updateAppointmentStatus(horizontalList.get(position),holder.tv_status,holder.tv_confirm);
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
