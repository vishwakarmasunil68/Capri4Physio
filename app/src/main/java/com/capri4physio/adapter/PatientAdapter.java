package com.capri4physio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.AddPatientWalletAmount;
import com.capri4physio.activity.IncomeReportPrintActivity;
import com.capri4physio.activity.ViewAllPatientBillingActivity;
import com.capri4physio.activity.ViewCaseReportActivity;
import com.capri4physio.model.patient.PatientResultPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {

    private List<PatientResultPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    private DatabaseReference root;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_patient_name,tv_patient_email;
        public CardView cv_patient;

        public MyViewHolder(View view) {
            super(view);
            tv_patient_name = (TextView) view.findViewById(R.id.tv_patient_name);
            tv_patient_email = (TextView) view.findViewById(R.id.tv_patient_email);
            cv_patient = (CardView) view.findViewById(R.id.cv_patient);
        }
    }


    public PatientAdapter(Activity activity, List<PatientResultPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_patients, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_patient_name.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_patient_email.setText(horizontalList.get(position).getEmail());
        holder.cv_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof AddPatientWalletAmount){
//                    AddPatientWalletAmount addPatientWalletAmount= (AddPatientWalletAmount) activity;
//                    addPatientWalletAmount.showPatientWallerDialog(horizontalList.get(position));
                    Intent intent=new Intent(activity, ViewAllPatientBillingActivity.class);
                    intent.putExtra("patientpojo",horizontalList.get(position));
                    activity.startActivity(intent);
                }else if(activity instanceof ViewCaseReportActivity){
                    Intent intent=new Intent(activity, IncomeReportPrintActivity.class);
                    intent.putExtra("type","patientcasereport");
                    intent.putExtra("patient_id",horizontalList.get(position).getId());
                    intent.putExtra("branch_code",horizontalList.get(position).getBracchCode());
                    activity.startActivity(intent);
                }
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
