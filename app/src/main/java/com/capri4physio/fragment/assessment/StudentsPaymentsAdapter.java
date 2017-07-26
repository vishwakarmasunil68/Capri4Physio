package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.util.Utils;

import java.util.List;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class StudentsPaymentsAdapter extends RecyclerView.Adapter<StudentsPaymentsAdapter.MyViewHolder> {
    private List<InfoApps> dataList;
    public static String reason,patient_name,patient_Email;
Context con;
    Activity ctx;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,txt_data,year, genre;
        ImageView edit,mail,cancel,prescription,View;
        Button status;
        CardView cv;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_id);
            txt_data = (TextView) view.findViewById(R.id.txt_data);
//            genre = (TextView) view.findViewById(R.id.txt_patient_id);
            View= (ImageView) view.findViewById(R.id.view);
            edit= (ImageView) view.findViewById(R.id.edit);
            cancel= (ImageView) view.findViewById(R.id.cancel);
            mail= (ImageView) view.findViewById(R.id.dialog_mail);
//            cv = (CardView) view.findViewById(R.id.cv);
        }
    }


    public StudentsPaymentsAdapter(List<InfoApps> dataList, Activity ctx) {
        this.dataList = dataList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_students_adap, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps data = dataList.get(position);
        holder.title.setText(data.getSend_date());
        holder.txt_data.setText(data.getData());

        holder.edit.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.GONE);
        holder.mail.setVisibility(View.GONE);

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showError(ctx, ctx.getResources().getString(R.string.error), ctx.getResources().getString(R.string.err_clinic));

            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
