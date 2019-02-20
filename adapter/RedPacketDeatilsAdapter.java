package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.RedPacketDetailsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/4/004.
 */

public class RedPacketDeatilsAdapter extends BaseAdapter {
    private List<RedPacketDetailsBean.DataBean.UserListBean> list;
    private Context context;
    private int flag;

    public RedPacketDeatilsAdapter(Context context, List<RedPacketDetailsBean.DataBean.UserListBean> list, int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.packet_details_item, parent, false);
            holder = new ViewHolder();
            holder.packet_details_id = convertView.findViewById(R.id.packet_details_id);
            holder.packet_details_time = convertView.findViewById(R.id.packet_details_time);
            holder.packet_details_money = convertView.findViewById(R.id.packet_details_money);
            holder.shouqi_img = convertView.findViewById(R.id.shouqi_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.packet_details_id.setText(list.get(position).getBlockid());
        holder.packet_details_time.setText(list.get(position).getCreatetime());
        holder.packet_details_money.setText(list.get(position).getNum());
        int state = list.get(position).getState();
        if (flag != -1) {
            if (position == flag) {
                holder.shouqi_img.setVisibility(View.VISIBLE);
            } else {
                holder.shouqi_img.setVisibility(View.GONE);
            }
        } else {
            holder.shouqi_img.setVisibility(View.GONE);
        }
        if (state == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFF6F4"));
        }
        return convertView;
    }

    class ViewHolder {
        TextView packet_details_id;
        TextView packet_details_time;
        TextView packet_details_money;
        ImageView shouqi_img;
    }
}
