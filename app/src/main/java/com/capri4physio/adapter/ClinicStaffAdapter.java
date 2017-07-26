package com.capri4physio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.UserItem;
import com.capri4physio.util.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 *Create to bind jobs in list
 * @see RecyclerView.Adapter
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class ClinicStaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    private Context context;
    private List<UserItem> mList;
    private ViewItemClickListener<UserItem> mCallback;
    private DisplayImageOptions options;

    public ClinicStaffAdapter(Context context, List<UserItem> mList, ViewItemClickListener<UserItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_action_person)
                .showImageForEmptyUri(R.drawable.ic_action_person)
                .showImageOnFail(R.drawable.ic_action_person)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(50))
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_clinic_staff_item, parent, false);
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
        private ImageView mImgLogo;
        private TextView mTxtTitle,mTxtEmail;
        private RelativeLayout llViewItem;

        public UserViewHolder(View itemView) {
            super(itemView);
            llViewItem = (RelativeLayout) itemView.findViewById(R.id.layout_row);
            mImgLogo = (ImageView)itemView.findViewById(R.id.img_profile);
            mTxtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            mTxtEmail = (TextView)itemView.findViewById(R.id.txt_email);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getName()));
        holder.mTxtEmail.setText(mList.get(position).getEmail());

        holder.llViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_VIEW_CLICK);
            }
        });

        ImageLoader.getInstance().displayImage(mList.get(position).getProfilePic(), holder.mImgLogo, options);
    }

    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
