package com.capri4physio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.ListCourseStudentsActivity;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private List<StudentCourseResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_applied_date,tv_id;
        public TextView tv_student_name;
        public LinearLayout ll_students;

        public MyViewHolder(View view) {
            super(view);
            tv_applied_date = (TextView) view.findViewById(R.id.tv_applied_date);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_student_name = (TextView) view.findViewById(R.id.tv_student_name);
            ll_students = (LinearLayout) view.findViewById(R.id.ll_students);

        }
    }


    public StudentAdapter(Activity activity, List<StudentCourseResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_students, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_applied_date.setText(horizontalList.get(position).getSc_date()+" "+horizontalList.get(position).getSc_time());
        holder.tv_student_name.setText(horizontalList.get(position).getScSname());
        holder.tv_id.setText(horizontalList.get(position).getScId()+" : ");
        holder.ll_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListCourseStudentsActivity listCourseStudentsActivity= (ListCourseStudentsActivity) activity;
                listCourseStudentsActivity.ViewStudentDetails(horizontalList.get(position));
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
