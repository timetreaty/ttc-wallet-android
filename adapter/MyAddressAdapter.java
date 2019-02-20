package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.MyAlwaysAddressBean;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2018/10/17/017.
 */

public class MyAddressAdapter extends BaseAdapter {
    private Context context;
    private List<MyAlwaysAddressBean.DataBean> list;

    public MyAddressAdapter(Context context, List<MyAlwaysAddressBean.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_always_dress_tiem, parent, false);
            holder = new ViewHolder();
            holder.my_always_dress_beizhu = convertView.findViewById(R.id.my_always_dress_beizhu);
            holder.my_always_dress_content = convertView.findViewById(R.id.my_always_dress_content);
            holder.my_address_item = convertView.findViewById(R.id.my_address_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String remark = list.get(position).getRemark();
        if (remark == null || remark.equals("")) {
            holder.my_always_dress_beizhu.setText(list.get(position).getCurrency());
        } else {
            holder.my_always_dress_beizhu.setText(list.get(position).getCurrency() + "-" + remark);
        }
        holder.my_always_dress_content.setText(list.get(position).getAddress());
        return convertView;

    }

    class ViewHolder {
        TextView my_always_dress_beizhu;
        TextView my_always_dress_content;
        AutoLinearLayout my_address_item;
    }
}
