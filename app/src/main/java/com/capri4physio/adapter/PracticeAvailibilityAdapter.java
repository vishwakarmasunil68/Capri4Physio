package com.capri4physio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.PracticeItem;
import com.capri4physio.util.Constants;

import java.util.List;

/**
 *Create to bind jobs in list
 * @see RecyclerView.Adapter
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class PracticeAvailibilityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    private Context context;
    private List<PracticeItem> mList;
    private ViewItemClickListener<PracticeItem> mCallback;

    public PracticeAvailibilityAdapter(Context context, List<PracticeItem> mList, ViewItemClickListener<PracticeItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_practice_hour, parent, false);
        viewHolder = new PracticeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        PracticeViewHolder stateViewHolder = (PracticeViewHolder) viewHolder;
        configureItemViewHolder(stateViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class PracticeViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtDay,mTxtStartTime, mTxtEndTime;
        private LinearLayout llViewItem;

        public PracticeViewHolder(View itemView) {
            super(itemView);
            llViewItem = (LinearLayout) itemView.findViewById(R.id.layout_row);
            mTxtDay = (TextView)itemView.findViewById(R.id.txt_day);
            mTxtStartTime = (TextView)itemView.findViewById(R.id.txt_start_time);
            mTxtEndTime = (TextView)itemView.findViewById(R.id.txt_end_time);
        }
    }

    private void configureItemViewHolder(final PracticeViewHolder holder, final int position) {

        holder.mTxtDay.setText(capsFirstLetter(mList.get(position).getDay()));
        holder.mTxtStartTime.setText(mList.get(position).getStartTime());
        holder.mTxtEndTime.setText(mList.get(position).getEndTime());

        holder.llViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_VIEW_CLICK);
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
