package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.JingCaiJiLuBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17/017.
 */

public class JingCaiOneItemAdapter extends BaseAdapter {

    private Context context;
    private List<JingCaiJiLuBean.DataBean.BetInfoVos1Bean.ListBean> list;

    public JingCaiOneItemAdapter(Context context, List<JingCaiJiLuBean.DataBean.BetInfoVos1Bean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.jc_jilu_one_item_list_item, parent, false);
            holder = new ViewHolder();
            holder.jingcai_one_item_choose_text = convertView.findViewById(R.id.jingcai_one_item_choose_text);
            holder.jingcai_one_item_bei_text = convertView.findViewById(R.id.jingcai_one_item_bei_text);
            holder.jingcai_one_item_num_text = convertView.findViewById(R.id.jingcai_one_item_num_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String proportion = list.get(position).getProportion();
        if (!proportion.equals("0")) {
            holder.jingcai_one_item_choose_text.setTextColor(Color.parseColor("#6E86D3"));
            holder.jingcai_one_item_bei_text.setTextColor(Color.parseColor("#6E86D3"));
            holder.jingcai_one_item_num_text.setTextColor(Color.parseColor("#6E86D3"));
        } else {
            holder.jingcai_one_item_choose_text.setTextColor(Color.parseColor("#333333"));
            holder.jingcai_one_item_bei_text.setTextColor(Color.parseColor("#333333"));
            holder.jingcai_one_item_num_text.setTextColor(Color.parseColor("#333333"));
        }
        holder.jingcai_one_item_choose_text.setText(list.get(position).getOpetion());
        holder.jingcai_one_item_bei_text.setText(list.get(position).getMultiple());
        holder.jingcai_one_item_num_text.setText(proportion);
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    class ViewHolder {
        TextView jingcai_one_item_choose_text;
        TextView jingcai_one_item_bei_text;
        TextView jingcai_one_item_num_text;
    }
}
