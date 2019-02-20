package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.ChongBiJiLuBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8/008.
 */

public class ChongBiJiLuAdapter extends BaseAdapter {
    private List<ChongBiJiLuBean.DataBean> data;
    private Context context;

    public ChongBiJiLuAdapter(List<ChongBiJiLuBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chongbi_jilu_item, parent, false);
            holder = new ViewHolder();
            holder.chongbi_jilu_item_img = convertView.findViewById(R.id.chongbi_jilu_item_img);
            holder.chongbi_jilu_item_name = convertView.findViewById(R.id.chongbi_jilu_item_name);
            holder.chongbi_jilu_item_time = convertView.findViewById(R.id.chongbi_jilu_item_time);
            holder.chongbi_jilu_item_num = convertView.findViewById(R.id.chongbi_jilu_item_num);
            holder.chongbi_jilu_item_state = convertView.findViewById(R.id.chongbi_jilu_item_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(data.get(position).getHomeimage()).into(holder.chongbi_jilu_item_img);
        holder.chongbi_jilu_item_name.setText(data.get(position).getCoinType());
        holder.chongbi_jilu_item_time.setText(data.get(position).getCreatetime());
        holder.chongbi_jilu_item_num.setText("+" + data.get(position).getBidtnum());
        int state = data.get(position).getState();
        switch (state) {
            case 0:
                //审核中
                holder.chongbi_jilu_item_state.setText("充币转入验证中");
                break;
            case 1:
                //成功
                holder.chongbi_jilu_item_state.setText("已到账");
                break;
            case 2:
                //失败
                holder.chongbi_jilu_item_state.setText("充币失败");
                break;
        }
        return convertView;
    }

    class ViewHolder {
        ImageView chongbi_jilu_item_img;
        TextView chongbi_jilu_item_name;
        TextView chongbi_jilu_item_time;
        TextView chongbi_jilu_item_num;
        TextView chongbi_jilu_item_state;
    }
}
