package com.capri4physio.adapter.assessment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.MotorItem;
import com.capri4physio.model.assessment.PhysicalItem;

import java.util.List;

/**
 *Create to bind jobs in list
 * @see RecyclerView.Adapter
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class MotorExamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    private Context context;
    private List<MotorItem> mList;
    private ViewItemClickListener<MotorItem> mCallback;

    public MotorExamAdapter(Context context, List<MotorItem> mList, ViewItemClickListener<MotorItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_cheif_complaint, parent, false);
        viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        UserViewHolder stateViewHolder = (UserViewHolder) viewHolder;
        configureItemViewHolder(stateViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImg;
        private TextView mTxtTitle,mTxtDuration,mTxtPast;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            mTxtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            mTxtDuration = (TextView)itemView.findViewById(R.id.txt_duration);
            mTxtPast = (TextView)itemView.findViewById(R.id.txt_past);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getDate()));
        holder.mTxtDuration.setText(mList.get(position).getPatientId());
//        holder.mTxtPast.setText(mList.get(position).getReportType());

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCallback.onViewItemClick(null, position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });

    }
    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
