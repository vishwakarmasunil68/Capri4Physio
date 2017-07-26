package com.capri4physio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capri4physio.R;
import java.util.List;

/**
 *Create to bind jobs in list
 * @see RecyclerView.Adapter
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class SlotAdapter extends BaseAdapter  {
    private LayoutInflater inflater;
    private Context context;
    private List<String> mList;

    public SlotAdapter(Context context, List<String> mList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mList = mList;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        SlotHolder holder = null;
        if (convertView == null) {
            view = inflater.inflate(R.layout.row_slot_item, null);
            holder = new SlotHolder();
            holder.txt = (TextView) view.findViewById(R.id.txt_slot);
            view.setTag(holder);
        } else {
            holder = (SlotHolder) view.getTag();
        }

        holder.txt.setText(mList.get(position));


        return view;
    }

    public static class SlotHolder {
        TextView txt;
    }
}

