package com.example.administrator.ttc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.JingCaiListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17/017.
 */

public class JingCaiChooseAdapter extends BaseAdapter {

    private Context context;
    private List<JingCaiListBean.DataBean.ListBean> list;

    public JingCaiChooseAdapter(Context context, List<JingCaiListBean.DataBean.ListBean> list) {
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

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_choose_item, parent, false);
            holder = new ViewHolder();
            holder.jingcai_choose_text = convertView.findViewById(R.id.jingcai_choose_text);
            holder.jingcai_choose_bei = convertView.findViewById(R.id.jingcai_choose_bei);
            holder.jingcai_choose_layout = convertView.findViewById(R.id.jingcai_choose_layout);
            holder.jingcai_choose_item_layout = convertView.findViewById(R.id.jingcai_choose_item_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.jingcai_choose_text.setText(list.get(position).getOpetion());
        holder.jingcai_choose_bei.setText(" " + list.get(position).getMultiple() + "倍");
        // 控制是否置灰
        if (!isAllItemEnable) {
            holder.jingcai_choose_text.setTextColor(Color.parseColor("#999999"));
            holder.jingcai_choose_item_layout.setBackgroundResource(R.drawable.jingcai_item_choose_no_style);
            holder.jingcai_choose_bei.setTextColor(Color.parseColor("#999999"));
        } else {
            holder.jingcai_choose_text.setTextColor(context.getResources().getColorStateList(R.drawable.jingcai_choose_item_text_font_selector));
            holder.jingcai_choose_item_layout.setBackgroundResource(R.drawable.jingcai_item_selector_style);
            holder.jingcai_choose_bei.setTextColor(context.getResources().getColorStateList(R.drawable.jingcai_choose_item_bei_font_selector));
        }
        return convertView;
    }

    private boolean isAllItemEnable = true;

//    @Override
//    public boolean isEnabled(int position) {
//        return isAllItemEnable;
//    }

    public void disableAllItemChoser() {
        isAllItemEnable = false;
        notifyDataSetChanged();
    }

    public void enableItemChoser() {
        isAllItemEnable = true;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView jingcai_choose_text;
        TextView jingcai_choose_bei;
        LinearLayout jingcai_choose_layout;
        LinearLayout jingcai_choose_item_layout;
    }
}
