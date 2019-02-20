package com.example.administrator.ttc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.RedPacketZhuFuBean;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2018/12/4/004.
 */

public class ZhufuAdapter extends BaseAdapter {
    private List<RedPacketZhuFuBean.DataBean> list;
    private Context context;

    public ZhufuAdapter(Context context, List<RedPacketZhuFuBean.DataBean> list) {
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.zhufu_item, parent, false);
            holder = new ViewHolder();
            holder.zhufu_text = convertView.findViewById(R.id.zhufu_text);
            holder.zhufu_layout = convertView.findViewById(R.id.zhufu_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.zhufu_text.setText(list.get(position).getBlessing());
        return convertView;
    }

    class ViewHolder {
        TextView zhufu_text;
        AutoLinearLayout zhufu_layout;
    }
}
