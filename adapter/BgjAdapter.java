package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.BgjBean;
import com.example.administrator.ttc.utlis.ToActivityUtil;
import com.example.administrator.ttc.zichan_activity.TouZiActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class BgjAdapter extends BaseAdapter {

    private Context context;
    private List<BgjBean.DataBean> data;

    public BgjAdapter(Context context, List<BgjBean.DataBean> data) {
        this.context = context;
        this.data = data;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bgj_item, parent, false);
            holder = new ViewHolder();
            holder.bgj_item_left = convertView.findViewById(R.id.bgj_item_left);
            holder.bgj_item_lilv = convertView.findViewById(R.id.bgj_item_lilv);
            holder.bgj_item_time = convertView.findViewById(R.id.bgj_item_time);
            holder.bgj_item_shouyi = convertView.findViewById(R.id.bgj_item_shouyi);
            holder.bgj_item_button = convertView.findViewById(R.id.bgj_item_button);
            holder.bgj_item_img_waite = convertView.findViewById(R.id.bgj_item_img_waite);
            holder.bgj_item_img_finish = convertView.findViewById(R.id.bgj_item_img_finish);
            holder.bgj_item_layout = convertView.findViewById(R.id.bgj_item_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String projectCycle = data.get(position).getProjectCycle();//周期
        String profit = data.get(position).getProfit();//利率
        String profitNum = data.get(position).getProfitNum();//最高收益
        final int status = data.get(position).getStatus();//最高收益
        holder.bgj_item_time.setText(projectCycle + "天稳健收益投资");
        double v = Double.valueOf(profit) * 100;
        holder.bgj_item_lilv.setText(getMoney(String.valueOf(v)) + "%");
        holder.bgj_item_shouyi.setText("零风险  满期收益高达" + profitNum);
        //已开启
        if (status == 1) {
            holder.bgj_item_button.setVisibility(View.VISIBLE);
            holder.bgj_item_img_waite.setVisibility(View.GONE);
            holder.bgj_item_img_finish.setVisibility(View.GONE);
        } else if (status == 2) {//待开启
            holder.bgj_item_button.setVisibility(View.GONE);
            holder.bgj_item_img_waite.setVisibility(View.VISIBLE);
            holder.bgj_item_img_finish.setVisibility(View.GONE);
        } else if (status == 3) {//已结束
            holder.bgj_item_button.setVisibility(View.GONE);
            holder.bgj_item_img_waite.setVisibility(View.GONE);
            holder.bgj_item_img_finish.setVisibility(View.VISIBLE);
        } else if (status == 0) {
            long time = System.currentTimeMillis();
            try {
                long startTime = stringToLong(data.get(position).getStartTime(), "yyyy-MM-dd HH:mm:ss");
                long endTime = stringToLong(data.get(position).getEndTime(), "yyyy-MM-dd HH:mm:ss");
                if (time > startTime) {
                    if (time < endTime) {
                        holder.bgj_item_button.setVisibility(View.VISIBLE);
                        holder.bgj_item_img_waite.setVisibility(View.GONE);
                        holder.bgj_item_img_finish.setVisibility(View.GONE);
                    } else {
                        holder.bgj_item_button.setVisibility(View.GONE);
                        holder.bgj_item_img_waite.setVisibility(View.GONE);
                        holder.bgj_item_img_finish.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.bgj_item_button.setVisibility(View.GONE);
                    holder.bgj_item_img_waite.setVisibility(View.VISIBLE);
                    holder.bgj_item_img_finish.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        switch (position) {
            case 0:
                holder.bgj_item_left.setBackgroundColor(Color.parseColor("#587BE8"));
                break;
            case 1:
                holder.bgj_item_left.setBackgroundColor(Color.parseColor("#7090F3"));
                break;
            case 2:
                holder.bgj_item_left.setBackgroundColor(Color.parseColor("#90A9F8"));
                break;
            case 3:
                holder.bgj_item_left.setBackgroundColor(Color.parseColor("#A9BDFC"));
                break;
            case 4:
                holder.bgj_item_left.setBackgroundColor(Color.parseColor("#C1D0FF"));
                break;
        }
        holder.bgj_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已开启
                if (status == 1) {
                    ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag"}, new String[]{String.valueOf(data.get(position).getId()), "1"});
                } else if (status == 2) {//待开启
                    ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag"}, new String[]{String.valueOf(data.get(position).getId()), "2"});
                } else if (status == 3) {//已结束
                    ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag"}, new String[]{String.valueOf(data.get(position).getId()), "3"});
                } else if (status == 0) {
                    long time = System.currentTimeMillis();
                    try {
                        long startTime = stringToLong(data.get(position).getStartTime(), "yyyy-MM-dd HH:mm:ss");
                        long endTime = stringToLong(data.get(position).getEndTime(), "yyyy-MM-dd HH:mm:ss");
                        if (time > startTime) {
                            if (time < endTime) {
                                ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag", "xiangmu_num"}, new String[]{String.valueOf(data.get(position).getId()), "1", String.valueOf(position)});
                            } else {
                                ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag", "xiangmu_num"}, new String[]{String.valueOf(data.get(position).getId()), "3", String.valueOf(position)});
                            }
                        } else {
                            ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag", "xiangmu_num"}, new String[]{String.valueOf(data.get(position).getId()), "2", String.valueOf(position)});
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.bgj_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(context, TouZiActivity.class, new String[]{"id", "touzi_flag", "xiangmu_num"}, new String[]{String.valueOf(data.get(position).getId()), "1", String.valueOf(position)});
            }
        });
        return convertView;

    }

    class ViewHolder {
        AutoLinearLayout bgj_item_left;
        TextView bgj_item_lilv;
        TextView bgj_item_time;
        TextView bgj_item_shouyi;
        Button bgj_item_button;
        ImageView bgj_item_img_waite;
        ImageView bgj_item_img_finish;
        AutoLinearLayout bgj_item_layout;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
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
