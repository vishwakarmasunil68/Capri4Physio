package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.SelectCourseActivity;
import com.capri4physio.model.cources.CourcesResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class StudentCourseAdapter extends RecyclerView.Adapter<StudentCourseAdapter.MyViewHolder> {

    private List<CourcesResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cource_name;
        public TextView tv_seat_available;
        public TextView tv_dates;
        public TextView tv_place;
        public LinearLayout ll_cource;

        public MyViewHolder(View view) {
            super(view);
            tv_cource_name = (TextView) view.findViewById(R.id.tv_cource_name);
            tv_seat_available = (TextView) view.findViewById(R.id.tv_seat_available);
            tv_dates = (TextView) view.findViewById(R.id.tv_dates);
            tv_place = (TextView) view.findViewById(R.id.tv_place);
            ll_cource = (LinearLayout) view.findViewById(R.id.ll_cource);
        }
    }


    public StudentCourseAdapter(Activity activity, List<CourcesResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_cources, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_cource_name.setText(horizontalList.get(position).getC_name());
        holder.tv_seat_available.setText(horizontalList.get(position).getC_sheet_available()+ " seats available");
        holder.tv_dates.setText(horizontalList.get(position).getC_from_date()+" - "+horizontalList.get(position).getC_to_date());
        holder.tv_place.setText(horizontalList.get(position).getC_place());
        holder.ll_cource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCourseActivity selectCourseActivity= (SelectCourseActivity) activity;
                selectCourseActivity.checkAppliedCourse(horizontalList.get(position));
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
