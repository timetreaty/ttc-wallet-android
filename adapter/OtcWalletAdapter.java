package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.OtcWalletBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class OtcWalletAdapter extends BaseAdapter {

    private Context context;
    private List<OtcWalletBean.DataBean.ListBean> list;

    public OtcWalletAdapter(Context context, List<OtcWalletBean.DataBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bd_wallet, parent, false);
            holder = new ViewHolder();
            holder.bd_item_id = convertView.findViewById(R.id.bd_item_id);
            holder.bd_item_time = convertView.findViewById(R.id.bd_item_time);
            holder.bd_item_money = convertView.findViewById(R.id.bd_item_money);
            holder.bd_item_isinto = convertView.findViewById(R.id.bd_item_isinto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bd_item_isinto.setVisibility(View.GONE);
        holder.bd_item_id.setText(list.get(position).getContent());
        holder.bd_item_time.setText(list.get(position).getCreateTime());
        holder.bd_item_money.setText(list.get(position).getDealNum() + list.get(position).getCurrencyType());
        return convertView;

    }

    class ViewHolder {
        TextView bd_item_id;
        TextView bd_item_time;
        TextView bd_item_money;
        TextView bd_item_isinto;
    }
}
