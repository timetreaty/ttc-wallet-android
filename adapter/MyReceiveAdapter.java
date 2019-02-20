package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.MyReceiveBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/4/004.
 */

public class MyReceiveAdapter extends BaseAdapter {
    private List<MyReceiveBean.DataBean.ListBean> list;
    private Context context;

    public MyReceiveAdapter(Context context, List<MyReceiveBean.DataBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.my_receive_item, parent, false);
            holder = new ViewHolder();
            holder.my_send_zhufu = convertView.findViewById(R.id.my_receive_zhufu);
            holder.my_send_money = convertView.findViewById(R.id.my_receive_money);
            holder.my_receive_id = convertView.findViewById(R.id.my_receive_id);
            holder.my_receive_time = convertView.findViewById(R.id.my_receive_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.my_send_zhufu.setText(list.get(position).getBlress());
        holder.my_send_money.setText(list.get(position).getNum());
        holder.my_receive_id.setText(list.get(position).getBlockid());
        holder.my_receive_time.setText(list.get(position).getCreatetime());
        return convertView;
    }

    class ViewHolder {
        TextView my_send_zhufu;
        TextView my_send_money;
        TextView my_receive_id;
        TextView my_receive_time;
    }
}
