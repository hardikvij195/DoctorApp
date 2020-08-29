package com.example.dapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AppListAdapter extends BaseAdapter {


    private Context mcontext ;
    private List<DoctorAppClass> mList ;

    public AppListAdapter(Context mcontext, List<DoctorAppClass> mList) {
        this.mcontext = mcontext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = View.inflate(mcontext , R.layout.doctorapplistadap , null);

        TextView Name = (TextView)v.findViewById(R.id.textView24);
        TextView Ph = (TextView)v.findViewById(R.id.textView26);
        TextView Date = (TextView)v.findViewById(R.id.textView17);

        Name.setText("Name : " + mList.get(position).getName());
        Date.setText("Date : " + mList.get(position).getDate());
        Ph.setText("Number : " + mList.get(position).getNum());

        return v;

    }
}

