package com.capri4physio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.util.Constants;

import java.util.List;

/**
 * Create to bind jobs in list
 *
 * @author prabhunathy
 * @version 1.0
 * @see RecyclerView.Adapter
 * @since 1/4/16.
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<String> mList;
    private ViewItemClickListener<String> mCallback;

    public MenuAdapter(Context context, List<String> mList, ViewItemClickListener<String> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_menu_item, parent, false);
        viewHolder = new MenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) viewHolder;
        configureItemViewHolder(menuViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgLogo;
        private TextView mTxtTitle;
        private RelativeLayout llViewItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            llViewItem = (RelativeLayout) itemView.findViewById(R.id.layout_row);
            mImgLogo = (ImageView) itemView.findViewById(R.id.img_profile);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }

    private void configureItemViewHolder(final MenuViewHolder holder, final int position) {

        holder.mTxtTitle.setText(mList.get(position));

        holder.llViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_VIEW_CLICK);
            }
        });

    }
}
