package com.example.dapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DoctorListAdap extends BaseAdapter {


    private Context mcontext ;
    private List<DoctorInfoClass> mList ;


    public DoctorListAdap(Context mcontext, List<DoctorInfoClass> mList) {
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


        View v = View.inflate(mcontext , R.layout.doclistlayout , null);

        TextView Name = (TextView)v.findViewById(R.id.textView18);
        TextView Type = (TextView)v.findViewById(R.id.textView19);
        TextView Email = (TextView)v.findViewById(R.id.textView22);
        TextView Ph = (TextView)v.findViewById(R.id.textView21);
        TextView Add = (TextView)v.findViewById(R.id.textView20);

        Name.setText("Name : " + mList.get(position).getName());
        Type.setText("Category : " +mList.get(position).getType());
        Email.setText("Email : " +mList.get(position).getEmail());
        Add.setText("Address : " +mList.get(position).getAddress());
        Ph.setText("Number : " +mList.get(position).getPhone());

        return v;

    }
}
