package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.BidtWalletBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class BidtWalletAdapter extends BaseAdapter {

    private Context context;
    private List<BidtWalletBean.DataBean.ListBean> list;

    public BidtWalletAdapter(Context context, List<BidtWalletBean.DataBean.ListBean> list) {
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
            holder.bd_item_reason = convertView.findViewById(R.id.bd_item_reason);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bd_item_isinto.setVisibility(View.VISIBLE);
        String regulation = list.get(position).getRegulation();
        String transactionStatus = list.get(position).getTransactionStatus();
        if (transactionStatus != null) {
            if (transactionStatus.equals("0")) {
                holder.bd_item_reason.setVisibility(View.VISIBLE);
                holder.bd_item_reason.setTextColor(Color.parseColor("#FF3B32"));
                holder.bd_item_reason.setText("转出失败");
            } else if (transactionStatus.equals("1")) {
                holder.bd_item_reason.setVisibility(View.GONE);
            } else if (transactionStatus.equals("2")) {
                holder.bd_item_reason.setVisibility(View.VISIBLE);
                holder.bd_item_reason.setTextColor(Color.parseColor("#FF3B32"));
                holder.bd_item_reason.setText("转出校检中");
            }
        } else {
            holder.bd_item_reason.setVisibility(View.GONE);
        }
        if (regulation.equals("1")) {
            holder.bd_item_isinto.setText("进");
            holder.bd_item_isinto.setBackgroundColor(Color.parseColor("#5F76C0"));
            holder.bd_item_money.setTextColor(Color.parseColor("#6E86D3"));
            holder.bd_item_money.setText("+" + list.get(position).getCoinNum() + " BIDT");
        } else if (regulation.equals("0")) {
            holder.bd_item_isinto.setText("出");
            holder.bd_item_isinto.setBackgroundColor(Color.parseColor("#A8B7E4"));
            holder.bd_item_money.setTextColor(Color.parseColor("#FF3B32"));
            holder.bd_item_money.setText("-" + list.get(position).getCoinNum() + " BIDT");
        }
        if (list.get(position).getAddress() != null) {
            if (list.get(position).getAddress().equals("null") || list.get(position).getAddress().equals("")) {
                holder.bd_item_id.setText(list.get(position).getContent());
            } else {
                holder.bd_item_id.setText(list.get(position).getContent() + "-" + getAddress(list.get(position).getAddress()));
            }
        } else {
            holder.bd_item_id.setText(list.get(position).getContent());
        }
        holder.bd_item_time.setText(getDateTimeFromMillisecond(Long.valueOf(list.get(position).getTime()).longValue()));
        return convertView;

    }

    class ViewHolder {
        TextView bd_item_id;
        TextView bd_item_time;
        TextView bd_item_money;
        TextView bd_item_isinto;
        TextView bd_item_reason;
    }

    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public String getAddress(String address) {
        int a = 0;
        char[] chars = address.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i < 6 || i > chars.length - 7) {
                stringBuffer.append(chars[i]);
            } else {
                if (a < 4) {
                    stringBuffer.append(".");
                    a++;
                }
            }
        }
        String s = stringBuffer.toString();
        return s;

    }
}
