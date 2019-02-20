package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.InviteListBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/28/028.
 */

public class MyShouYiAdapter extends BaseAdapter {
    private List<InviteListBean.DataBean> data;
    private Context context;

    public MyShouYiAdapter(List<InviteListBean.DataBean> data, Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.invite_list_item, parent, false);
            holder = new ViewHolder();
            holder.shouyi_item_phone = convertView.findViewById(R.id.shouyi_item_phone);
            holder.shouyi_item_time = convertView.findViewById(R.id.shouyi_item_time);
            holder.friend_yaoqing_text = convertView.findViewById(R.id.friend_yaoqing_text);
            holder.friend_login_text = convertView.findViewById(R.id.friend_login_text);
            holder.all_fenhong_text = convertView.findViewById(R.id.all_fenhong_text);
            holder.zero_text = convertView.findViewById(R.id.zero_text);
            holder.have_shouyi_layout = convertView.findViewById(R.id.have_shouyi_layout);
            holder.shouyi_item_bidt = convertView.findViewById(R.id.shouyi_item_bidt);
            holder.shouyi_item_suanli = convertView.findViewById(R.id.shouyi_item_suanli);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String phoneNum = data.get(position).getPhoneNum();
        String createtime = data.get(position).getCreatetime();
        String tips = data.get(position).getTips();
        String prifit = data.get(position).getPrifit();
        holder.shouyi_item_phone.setText(phoneNum);
        holder.shouyi_item_time.setText(createtime);
        if (tips.equals("0")) {
            holder.friend_login_text.setVisibility(View.VISIBLE);
            holder.friend_yaoqing_text.setVisibility(View.GONE);
            holder.all_fenhong_text.setVisibility(View.GONE);
            holder.have_shouyi_layout.setVisibility(View.GONE);
            holder.zero_text.setVisibility(View.VISIBLE);
        } else if (tips.equals("1")) {
            holder.friend_login_text.setVisibility(View.GONE);
            holder.friend_yaoqing_text.setVisibility(View.VISIBLE);
            holder.all_fenhong_text.setVisibility(View.GONE);
            holder.have_shouyi_layout.setVisibility(View.VISIBLE);
            holder.zero_text.setVisibility(View.GONE);
            String power = data.get(position).getPower();
            holder.shouyi_item_bidt.setText(prifit + " BIDT");
            holder.shouyi_item_suanli.setText(power + " 算力");
        } else if (tips.equals("2")) {
            holder.friend_login_text.setVisibility(View.GONE);
            holder.friend_yaoqing_text.setVisibility(View.GONE);
            holder.all_fenhong_text.setVisibility(View.VISIBLE);
            holder.have_shouyi_layout.setVisibility(View.VISIBLE);
            holder.zero_text.setVisibility(View.GONE);
            String power = data.get(position).getPower();
            holder.shouyi_item_bidt.setText(prifit + " BIDT");
            holder.shouyi_item_suanli.setText(power + " 算力");
        }
        return convertView;
    }

    class ViewHolder {
        TextView shouyi_item_phone;
        TextView shouyi_item_time;
        TextView friend_yaoqing_text;
        TextView friend_login_text;
        TextView all_fenhong_text;
        TextView zero_text;
        LinearLayout have_shouyi_layout;
        TextView shouyi_item_bidt;
        TextView shouyi_item_suanli;
    }
}
