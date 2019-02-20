package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.ChongBiChooseBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8/008.
 */

public class ChongBiChooseAdapter extends BaseAdapter {
    private List<ChongBiChooseBean.DataBean> data;
    private Context context;

    public ChongBiChooseAdapter(List<ChongBiChooseBean.DataBean> data, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.chong_bi_choose_item, parent, false);
            holder = new ViewHolder();
            holder.chongbi_choose_img = convertView.findViewById(R.id.chongbi_choose_img);
            holder.chongbi_choose_name = convertView.findViewById(R.id.chongbi_choose_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(data.get(position).getCoinImage()).into(holder.chongbi_choose_img);
        holder.chongbi_choose_name.setText(data.get(position).getCoinInfo());
        return convertView;
    }

    class ViewHolder {
        ImageView chongbi_choose_img;
        TextView chongbi_choose_name;
    }
}
