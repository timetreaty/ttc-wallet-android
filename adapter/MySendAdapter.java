package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.MySendBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/4/004.
 */

public class MySendAdapter extends BaseAdapter {
    private List<MySendBean.DataBean.ListBean> list;
    private Context context;

    public MySendAdapter(Context context, List<MySendBean.DataBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.my_send_item, parent, false);
            holder = new ViewHolder();
            holder.my_send_zhufu = convertView.findViewById(R.id.my_send_zhufu);
            holder.my_send_money = convertView.findViewById(R.id.my_send_money);
            holder.my_send_time = convertView.findViewById(R.id.my_send_time);
            holder.my_send_status = convertView.findViewById(R.id.my_send_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.my_send_zhufu.setText(list.get(position).getBlessinfo());
        holder.my_send_money.setText(list.get(position).getNum());
        holder.my_send_time.setText(list.get(position).getCreatetime());
        holder.my_send_status.setText(list.get(position).getMsg());
        return convertView;
    }

    class ViewHolder {
        TextView my_send_zhufu;
        TextView my_send_money;
        TextView my_send_time;
        TextView my_send_status;
    }
}
