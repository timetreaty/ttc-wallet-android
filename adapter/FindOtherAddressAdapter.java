package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.FindOtherAddressBean;
import com.example.administrator.ttc.myself_activity.CompileOtherDressActivity;
import com.example.administrator.ttc.utlis.ToActivityUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/10/17/017.
 */

public class FindOtherAddressAdapter extends BaseAdapter {
    private Context context;
    private List<FindOtherAddressBean.DataBean.ListBean> list;

    public FindOtherAddressAdapter(Context context, List<FindOtherAddressBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.find_other_address_item, parent, false);
            holder = new ViewHolder();
            holder.find_other_dress_item_eth = convertView.findViewById(R.id.find_other_dress_item_eth);
            holder.find_other_dress_item_dress = convertView.findViewById(R.id.find_other_dress_item_dress);
            holder.find_other_dress_item_save = convertView.findViewById(R.id.find_other_dress_item_save);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String currency = list.get(position).getCurrency();
        final String address = list.get(position).getAddress();
        holder.find_other_dress_item_eth.setText(currency);
        holder.find_other_dress_item_dress.setText(address);
        holder.find_other_dress_item_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(context, CompileOtherDressActivity.class, new String[]{"currency", "address", "flag"}, new String[]{currency, address, "1"});
            }
        });
        return convertView;

    }

    class ViewHolder {
        TextView find_other_dress_item_eth;
        TextView find_other_dress_item_dress;
        TextView find_other_dress_item_save;
    }
}
