package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.JingCaiJiLuBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17/017.
 */

public class JingCaiTwoAdapter extends BaseAdapter {

    private Context context;
    private List<JingCaiJiLuBean.DataBean.BetInfoVos2Bean> list;

    public JingCaiTwoAdapter(Context context, List<JingCaiJiLuBean.DataBean.BetInfoVos2Bean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_jilu_two_item, parent, false);
            holder = new ViewHolder();
            holder.jingcai_two_title = convertView.findViewById(R.id.jingcai_two_title);
            holder.jingcai_two_result = convertView.findViewById(R.id.jingcai_two_result);
            holder.jingcai_two_item_bei = convertView.findViewById(R.id.jingcai_two_item_bei);
            holder.jingcai_two_item_num = convertView.findViewById(R.id.jingcai_two_item_num);
            holder.jingcai_two_img = convertView.findViewById(R.id.jingcai_two_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.jingcai_two_title.setText(list.get(position).getTitle());
        holder.jingcai_two_result.setText("最终结果：" + list.get(position).getResult());
        holder.jingcai_two_item_bei.setText(list.get(position).getBetOptionVo().getMultiple());
        holder.jingcai_two_item_num.setText(list.get(position).getBetOptionVo().getProportion());
        holder.jingcai_two_img.setImageResource(R.drawable.jingcai_dui);
        return convertView;
    }

    class ViewHolder {
        TextView jingcai_two_title;
        TextView jingcai_two_result;
        TextView jingcai_two_item_bei;
        TextView jingcai_two_item_num;
        ImageView jingcai_two_img;
    }
}
