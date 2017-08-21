package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.TreatmentActivity;
import com.capri4physio.model.treatment.TreatmentResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.MyViewHolder> {

    private List<TreatmentResultPOJO> horizontalList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_treat_nam;
        public ImageView iv_delete;
        public CardView cv_treatment;


        public MyViewHolder(View view) {
            super(view);
            tv_treat_nam = (TextView) view.findViewById(R.id.tv_treat_nam);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            cv_treatment = (CardView) view.findViewById(R.id.cv_treatment);

        }
    }


    public TreatmentAdapter(Activity activity, List<TreatmentResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_treatments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_treat_nam.setText(horizontalList.get(position).getTreatment_name());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreatmentActivity treatmentActivity= (TreatmentActivity) activity;
                treatmentActivity.deleteTreatment(horizontalList.get(position).getId(),position);
            }
        });

        holder.cv_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreatmentActivity treatmentActivity= (TreatmentActivity) activity;
                treatmentActivity.updateTreatment(horizontalList.get(position));
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
