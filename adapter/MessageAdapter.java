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
import com.example.administrator.ttc.bean.MessageTestBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageTestBean.DataBean.ListBean> list;

    public MessageAdapter(Context context, List<MessageTestBean.DataBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
            holder = new ViewHolder();
            holder.message_iem_icon = convertView.findViewById(R.id.message_iem_icon);
            holder.message_item_title = convertView.findViewById(R.id.message_item_title);
            holder.message_item_time = convertView.findViewById(R.id.message_item_time);
            holder.message_item_content = convertView.findViewById(R.id.message_item_content);
//            holder.message_item_id = convertView.findViewById(R.id.message_item_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int isRead = list.get(position).getIsRead();
        int type = list.get(position).getType();
        //未查看
        if (isRead == 0) {
            holder.message_iem_icon.setImageResource(R.drawable.tongzhi_no);
            holder.message_item_title.setTextColor(Color.parseColor("#FF7225"));
        } else if (isRead == 1) {
            holder.message_iem_icon.setImageResource(R.drawable.tongzhi_yes);
            holder.message_item_title.setTextColor(Color.parseColor("#999999"));
        }
        if (type == 1) {
            holder.message_item_title.setText("公告");
        }
        holder.message_item_time.setText(list.get(position).getAnnouncementCreateTime());
        holder.message_item_content.setText(list.get(position).getAnnouncementTitle());
        return convertView;

    }

    class ViewHolder {
        ImageView message_iem_icon;
        TextView message_item_title;
        TextView message_item_time;
        TextView message_item_content;
//        TextView message_item_id;
    }
}
