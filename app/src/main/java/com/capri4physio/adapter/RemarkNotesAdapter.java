package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.pojo.notes.RemarkResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class RemarkNotesAdapter extends RecyclerView.Adapter<RemarkNotesAdapter.MyViewHolder> {

    private List<RemarkResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_name;
        public TextView tv_report;



        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_report = (TextView) view.findViewById(R.id.tv_report);
        }
    }


    public RemarkNotesAdapter(Activity activity, List<RemarkResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_notes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_date.setText(horizontalList.get(position).getMoterExamRemarkdate());
        holder.tv_name.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_report.setText(horizontalList.get(position).getMoterExamRemarkdesc());
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
