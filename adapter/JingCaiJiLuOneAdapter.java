package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.JingCaiJiLuBean;
import com.example.administrator.ttc.utlis.TimeUtil;
import com.example.administrator.ttc.weight.MyGridView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20/020.
 */

public class JingCaiJiLuOneAdapter extends BaseAdapter {
    private Context context;
    private List<JingCaiJiLuBean.DataBean.BetInfoVos1Bean> list;
    private boolean isRun = true;
    long remainderDate;
    long day;
    long hour;
    long min;
    long mSecond;
    private ViewHolder holder;

    public JingCaiJiLuOneAdapter(Context context, List<JingCaiJiLuBean.DataBean.BetInfoVos1Bean> betInfoVos1) {
        this.context = context;
        this.list = betInfoVos1;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_jilu_one_item, parent, false);
            holder = new ViewHolder();
            holder.jingcai_jilu_one_item_title = convertView.findViewById(R.id.jingcai_jilu_one_item_title);
            holder.jingcai_jilu_one_item_status_img = convertView.findViewById(R.id.jingcai_jilu_one_item_status_img);
            holder.jingcai_jilu_one_item_status = convertView.findViewById(R.id.jingcai_jilu_one_item_status);
            holder.jingcau_jilu_item_listView = convertView.findViewById(R.id.jingcau_jilu_item_listView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = list.get(position).getTitle();
        List<JingCaiJiLuBean.DataBean.BetInfoVos1Bean.ListBean> list = this.list.get(position).getList();
        JingCaiOneItemAdapter jingCaiOneItemAdapter = new JingCaiOneItemAdapter(context, list);
        holder.jingcau_jilu_item_listView.setAdapter(jingCaiOneItemAdapter);
        holder.jingcai_jilu_one_item_title.setText(title);
        String endtime = this.list.get(position).getEndtime();
        try {
            long endDate = TimeUtil.stringToLong(endtime, "yyyy-MM-dd HH:mm:ss");
            long nowDate = System.currentTimeMillis();
            remainderDate = endDate - nowDate;
            if (remainderDate > 0) {
                day = remainderDate / (1000 * 60 * 60 * 24);
                hour = (remainderDate / (1000 * 60 * 60) - day * 24);
                min = ((remainderDate / (60 * 1000)) - day * 24 * 60 - hour * 60);
                mSecond = (remainderDate / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
                if (remainderDate > 3600000) {
                    holder.jingcai_jilu_one_item_status.setText("距离结束：" + day + "天" + hour + "小时" + min + "分" + mSecond + "秒");
                    holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FEAA0A"));
                    holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_juli);
                } else {
                    holder.jingcai_jilu_one_item_status.setText("即将结束：" + min + "分" + mSecond + "秒");
                    holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FB4271"));
                    holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_jijiang);
                }
            } else {
                holder.jingcai_jilu_one_item_status.setText("等待结果中");
                holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FE550A"));
                holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_dengdai);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView jingcai_jilu_one_item_title;
        ImageView jingcai_jilu_one_item_status_img;
        TextView jingcai_jilu_one_item_status;
        MyGridView jingcau_jilu_item_listView;
    }

    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 6;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 6:
                    remainderDate = remainderDate - 1000;
                    if (remainderDate > 0) {
                        day = remainderDate / (1000 * 60 * 60 * 24);
                        hour = (remainderDate / (1000 * 60 * 60) - day * 24);
                        min = ((remainderDate / (60 * 1000)) - day * 24 * 60 - hour * 60);
                        mSecond = (remainderDate / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
                        if (remainderDate > 3600000) {
                            holder.jingcai_jilu_one_item_status.setText("距离结束：" + day + "天" + hour + "小时" + min + "分" + mSecond + "秒");
                            holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FEAA0A"));
                            holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_juli);
                        } else {
                            holder.jingcai_jilu_one_item_status.setText("即将结束：" + min + "分" + mSecond + "秒");
                            holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FB4271"));
                            holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_jijiang);
                        }
                    } else {
                        holder.jingcai_jilu_one_item_status.setText("等待结果中");
                        holder.jingcai_jilu_one_item_status.setTextColor(Color.parseColor("#FE550A"));
                        holder.jingcai_jilu_one_item_status_img.setImageResource(R.drawable.jingcai_dengdai);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
