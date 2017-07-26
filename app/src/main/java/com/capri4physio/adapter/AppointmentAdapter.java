package com.capri4physio.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.ViewAllAppointmentsActivity;
import com.capri4physio.model.appointment.AppointmentResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private List<AppointmentResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user,tv_date,tv_time,tv_visit_type;
        public LinearLayout ll_appointment;
        public ImageView iv_delete;


        public MyViewHolder(View view) {
            super(view);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_visit_type = (TextView) view.findViewById(R.id.tv_visit_type);
            ll_appointment = (LinearLayout) view.findViewById(R.id.ll_appointment);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        }
    }


    public AppointmentAdapter(Activity activity, List<AppointmentResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_appointments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_user.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_date.setText(horizontalList.get(position).getBookingDate());
        holder.tv_time.setText(horizontalList.get(position).getBookingStarttime());
        holder.tv_visit_type.setText(horizontalList.get(position).getVisitType());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllAppointmentsActivity viewAllAppointmentsActivity= (ViewAllAppointmentsActivity) activity;
                viewAllAppointmentsActivity.cancelAppointment(horizontalList.get(position).getId(),position);
            }
        });
        holder.ll_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppointmentDialog(horizontalList.get(position));
            }
        });
    }

    public void showAppointmentDialog(AppointmentResultPOJO appointmentResultPOJO){
        final Dialog dialog1 = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog1.setContentView(R.layout.dialog_appointment);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setTitle("Appointment Details");
        dialog1.setCancelable(true);
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv_patient_name= (TextView) dialog1.findViewById(R.id.tv_patient_name);
        TextView tv_booking_date= (TextView) dialog1.findViewById(R.id.tv_booking_date);
        TextView tv_time= (TextView) dialog1.findViewById(R.id.tv_time);
        TextView tv_reason= (TextView) dialog1.findViewById(R.id.tv_reason);
        TextView tv_email= (TextView) dialog1.findViewById(R.id.tv_email);
        Button btn_ok= (Button) dialog1.findViewById(R.id.btn_ok);


        tv_patient_name.setText(appointmentResultPOJO.getFirstName()+" "+appointmentResultPOJO.getLastName());
        tv_booking_date.setText(appointmentResultPOJO.getBookingDate());
        tv_time.setText(appointmentResultPOJO.getBookingStarttime());
        tv_reason.setText(appointmentResultPOJO.getReason());
        tv_email.setText(appointmentResultPOJO.getEmail());

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
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
