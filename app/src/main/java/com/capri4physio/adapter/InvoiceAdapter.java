package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.model.invoice.InvoiceResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder> {

    private List<InvoiceResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_name;
        public TextView tv_id;
        public TextView tv_amount;



        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
        }
    }


    public InvoiceAdapter(Activity activity, List<InvoiceResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_invoice_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_date.setText(horizontalList.get(position).getInvo_date());
        holder.tv_name.setText(horizontalList.get(position).getInvoPatientName());
        holder.tv_amount.setText(horizontalList.get(position).getInvoAmount());
        holder.tv_id.setText(String.valueOf(position+1));
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
