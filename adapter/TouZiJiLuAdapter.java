package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.TouZiJiLuBean;
import com.example.administrator.ttc.utlis.TimeUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class TouZiJiLuAdapter extends BaseAdapter {

    private Context context;
    private List<TouZiJiLuBean.DataBean> list;

    public TouZiJiLuAdapter(Context context, List<TouZiJiLuBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.touzi_jilu_item, parent, false);
            holder = new ViewHolder();
            holder.touzi_jilu_item_title = convertView.findViewById(R.id.touzi_jilu_item_title);
            holder.touzi_jilu_item_bidt = convertView.findViewById(R.id.touzi_jilu_item_bidt);
            holder.touzi_jilu_item_time = convertView.findViewById(R.id.touzi_jilu_item_time);
            holder.touzi_jilu_item_state = convertView.findViewById(R.id.touzi_jilu_item_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //周期
        String cycle = list.get(position).getCycle();
        //状态
        int state = list.get(position).getState();
        //时间
        String createtime = list.get(position).getCreatetime();
        //bidt
        String bidtNum = list.get(position).getBidtNum();

        holder.touzi_jilu_item_title.setText(cycle + "天稳健收益投资");
        holder.touzi_jilu_item_bidt.setText(getMoney(bidtNum) + "BIDT");
        try {
            holder.touzi_jilu_item_time.setText(getDateTimeFromMillisecond(TimeUtil.stringToLong(createtime, "yyyy-MM-dd HH:mm:ss")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (state) {
            case 0:
                holder.touzi_jilu_item_state.setText("投资确认成功");
                break;
            case 1:
                holder.touzi_jilu_item_state.setText("计息中");
                break;
            case 2:
                holder.touzi_jilu_item_state.setText("投资确认中");
                break;
            case 4:
                holder.touzi_jilu_item_state.setText("投资无效 已退回");
                break;
            case 5:
                holder.touzi_jilu_item_state.setText("收益发放中");
                break;
            case 6:
                holder.touzi_jilu_item_state.setText("本息已到账");
                break;
        }
        return convertView;

    }

    class ViewHolder {
        TextView touzi_jilu_item_title;
        TextView touzi_jilu_item_bidt;
        TextView touzi_jilu_item_time;
        TextView touzi_jilu_item_state;
    }

    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public String getMoney(String money) {
        if (money.equals("0.0")) {
            return "0.00";
        }
        float f = Float.parseFloat(money);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(f);
        return p;
    }

}
