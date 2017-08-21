package com.capri4physio.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.ManageTherapistActivity;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.util.TagUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private List<DoctorResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    private DatabaseReference root;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user,tv_email,tv_time,tv_location;
        public LinearLayout ll_doctors;
        public ImageView iv_delete;


        public MyViewHolder(View view) {
            super(view);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_location = (TextView) view.findViewById(R.id.tv_location);
            tv_email = (TextView) view.findViewById(R.id.tv_email);
            ll_doctors = (LinearLayout) view.findViewById(R.id.ll_doctors);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        }
    }


    public DoctorAdapter(Activity activity, List<DoctorResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_doctors, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_user.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_email.setText(horizontalList.get(position).getEmail());
        holder.tv_time.setText(horizontalList.get(position).getFromTime()+" - "+horizontalList.get(position).getToTime());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDoctorDeleteDialog(horizontalList.get(position));
            }
        });

        holder.ll_doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageTherapistActivity manageTherapistActivity= (ManageTherapistActivity) activity;
                manageTherapistActivity.updateTherapist(horizontalList.get(position));

            }
        });
        Log.d(TagUtils.getTag(),"doctor id:-"+horizontalList.get(position).getId());
        //        DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
        root.child("therapist").child(horizontalList.get(position).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TagUtils.getTag(), "======="+postSnapshot.child("email").getValue());
//                    Log.e(TagUtils.getTag(), "======="+postSnapshot.child("name").getValue());
//                }
//                Log.d(TagUtils.getTag(),"email:-"+dataSnapshot.child("email").getValue());
//                Log.d(TagUtils.getTag(),"name:-"+dataSnapshot.child("name").getValue());
                try {
                    holder.tv_location.setText(dataSnapshot.child("address").getValue().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    public void showDoctorDeleteDialog(final DoctorResultPOJO doctorResultPOJO){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete "+doctorResultPOJO.getFirstName()+" "+doctorResultPOJO.getLastName());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ManageTherapistActivity manageTherapistActivity= (ManageTherapistActivity) activity;
                        manageTherapistActivity.deleteTherapist(doctorResultPOJO);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
