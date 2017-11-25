package com.capri4physio.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.addbranch.BranchListFragment;
import com.capri4physio.model.branch.BranchPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class BranchAdapte extends RecyclerView.Adapter<BranchAdapte.MyViewHolder> {

    private List<BranchPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    private DatabaseReference root;
    Fragment fragment;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_branch_name;
        public LinearLayout ll_branch;


        public MyViewHolder(View view) {
            super(view);
            tv_branch_name = (TextView) view.findViewById(R.id.tv_branch_name);
            ll_branch = (LinearLayout) view.findViewById(R.id.ll_branch);
        }
    }


    public BranchAdapte(Activity activity, Fragment fragment, List<BranchPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        this.fragment=fragment;
        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_branch, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_branch_name.setText(horizontalList.get(position).getBranch_name()+" ("+horizontalList.get(position).getBranch_code()+") ");

        holder.ll_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBranchDialog(horizontalList.get(position), position);
            }
        });
    }

    public void showBranchDialog(final BranchPOJO branchPOJO, final int position){
        final Dialog dialog1 = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_branch_menu);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final Button btn_update= (Button) dialog1.findViewById(R.id.btn_update);
        Button btn_delete= (Button) dialog1.findViewById(R.id.btn_delete);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showBranchDeleteAlert(branchPOJO,position);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                if(fragment instanceof BranchListFragment){
                    BranchListFragment branchListFragment= (BranchListFragment) fragment;
                    branchListFragment.callUpdateBranch(branchPOJO);
                }

            }
        });

    }
    public void showBranchDeleteAlert(final BranchPOJO branchPOJO, final int position){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete "+branchPOJO.getBranch_name()+" branch?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(fragment instanceof BranchListFragment) {
                            BranchListFragment branchListFragment= (BranchListFragment) fragment;
                            branchListFragment.callBranchDeleteAPI(branchPOJO, position);
                        }
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
