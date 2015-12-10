package com.gopal.daogeneratortest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gopal.daogeneratortest.R;
import com.gopal.model.Task;

import java.util.List;

public class MainListAdapter extends ArrayAdapter<Task> {

    public MainListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        ViewHolder viewHolder;
        if (convertView != null) {
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        } else {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_item, parent, false);
            viewHolder = new ViewHolder(rootView);
            rootView.setTag(viewHolder);
        }

        Task task = getItem(position);
        viewHolder.txtTime.setText(task.getTime());
        viewHolder.txtDate.setText(task.getDate());
        viewHolder.txtTask.setText(task.getNote());

        return rootView;
    }

    /**
     * Items View Holder
     */

    private static class ViewHolder {

        public TextView txtTime, txtDate, txtTask;;

        public ViewHolder(View itemView) {
            txtTime = (TextView) itemView.findViewById(R.id.tv_time);
            txtDate = (TextView) itemView.findViewById(R.id.tv_date);
            txtTask = (TextView) itemView.findViewById(R.id.tv_task);
        }
    }
}
