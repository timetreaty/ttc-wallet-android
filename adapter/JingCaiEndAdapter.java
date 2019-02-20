package com.example.administrator.ttc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.bean.JingCaiListBean;
import com.example.administrator.ttc.utlis.ShowToastUtil;
import com.example.administrator.ttc.weight.MyGridView;
import com.example.administrator.ttc.weight.RoundTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/12/17/017.
 */

public class JingCaiEndAdapter extends BaseAdapter {
    private Context context;
    private List<JingCaiListBean.DataBean> data;
    //加载布局类型
    public static final int LAYOUTONE = 0;
    public static final int LAYOUTTWO = 1;

    public JingCaiEndAdapter(Context context, List<JingCaiListBean.DataBean> data) {
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
    public int getItemViewType(int position) {
        int status = data.get(position).getStatus();
        if (status == 1) {
            return LAYOUTONE;
        } else if (status == 2) {
            return LAYOUTTWO;
        }
        return LAYOUTONE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到加载布局的类型
        int viewType = getItemViewType(position);
        ViewHolder viewHolder = null;
        ViewWaiteHolder viewWaiteHolder = null;
        if (convertView == null) {
            //根据返回类型加载不同的布局文件和创建不同的缓存类ViewHolder
            switch (viewType) {
                case LAYOUTONE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_can_item, parent, false);
                    viewWaiteHolder = new ViewWaiteHolder();
                    viewWaiteHolder.jingcai_item_title = convertView.findViewById(R.id.jingcai_item_title);
                    viewWaiteHolder.jingcai_item_img = convertView.findViewById(R.id.jingcai_item_img);
                    viewWaiteHolder.jingcai_item_status_img = convertView.findViewById(R.id.jingcai_item_status_img);
                    viewWaiteHolder.jingcai_item_status_time = convertView.findViewById(R.id.jingcai_item_status_time);
                    viewWaiteHolder.jingcai_item_person = convertView.findViewById(R.id.jingcai_item_person);
                    viewWaiteHolder.jingcai_item_num = convertView.findViewById(R.id.jingcai_item_num);
                    viewWaiteHolder.jingcai_item_list = convertView.findViewById(R.id.jingcai_item_list);
                    convertView.setTag(viewWaiteHolder);
                    break;
                case LAYOUTTWO:
                    convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_finish_item, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.jingcai_end_item_title = convertView.findViewById(R.id.jingcai_end_item_title);
                    viewHolder.jingcai_end_iem_img = convertView.findViewById(R.id.jingcai_end_iem_img);
                    viewHolder.jingcai_end_item_status_img = convertView.findViewById(R.id.jingcai_end_item_status_img);
                    viewHolder.jingcai_end_item_status = convertView.findViewById(R.id.jingcai_end_item_status);
                    viewHolder.jingcai_end_item_result = convertView.findViewById(R.id.jingcai_end_item_result);
                    viewHolder.jingcai_end_item_person = convertView.findViewById(R.id.jingcai_end_item_person);
                    viewHolder.jingcai_end_item_bei = convertView.findViewById(R.id.jingcai_end_item_bei);
                    viewHolder.jingcai_end_item_num = convertView.findViewById(R.id.jingcai_end_item_num);
                    convertView.setTag(viewHolder);
                    break;
            }
        } else {
            switch (viewType) {
                case LAYOUTONE:
                    viewWaiteHolder = (ViewWaiteHolder) convertView.getTag();
                    break;
                case LAYOUTTWO:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
            }
        }
        //根据返回类型来展示不同的数据
        switch (viewType) {
            case LAYOUTONE:
                int optionType = data.get(position).getOptionType();
                if (optionType == 1) {
                    viewWaiteHolder.jingcai_item_list.setNumColumns(2);
                } else if (optionType == 2) {
                    viewWaiteHolder.jingcai_item_list.setNumColumns(1);
                }
                String bannerUrl = data.get(position).getBannerUrl();
                if (bannerUrl == null || bannerUrl.equals("")) {
                    viewWaiteHolder.jingcai_item_img.setVisibility(View.GONE);
                } else {
                    viewWaiteHolder.jingcai_item_img.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(String.valueOf(bannerUrl)).transform(new RoundTransform(6)).into(viewWaiteHolder.jingcai_item_img);
                }
                final List<JingCaiListBean.DataBean.ListBean> list = data.get(position).getList();
                JingCaiChooseAdapter jingCaiChooseAdapter = new JingCaiChooseAdapter(context, list);
                jingCaiChooseAdapter.disableAllItemChoser();
                viewWaiteHolder.jingcai_item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShowToastUtil.showShortToast(context, "竞猜已截止");
                    }
                });
                viewWaiteHolder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_dengdai);
                viewWaiteHolder.jingcai_item_status_time.setTextColor(Color.parseColor("#FE550A"));
                viewWaiteHolder.jingcai_item_status_time.setText("等待结果中");
                viewWaiteHolder.jingcai_item_list.setAdapter(jingCaiChooseAdapter);
                viewWaiteHolder.jingcai_item_title.setText(data.get(position).getTitle());
                String bidtnum = data.get(position).getBidtnum();
                Double aLong = Double.valueOf(bidtnum).doubleValue();
                double v = aLong / 20;
                viewWaiteHolder.jingcai_item_person.setText((int) v + "");
                viewWaiteHolder.jingcai_item_num.setText(data.get(position).getBidtnum());
                break;
            case LAYOUTTWO:
                String bannerUrl1 = data.get(position).getBannerUrl();
                if (bannerUrl1 == null || bannerUrl1.equals("")) {
                    viewHolder.jingcai_end_iem_img.setVisibility(View.GONE);
                } else {
                    viewHolder.jingcai_end_iem_img.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(String.valueOf(bannerUrl1)).transform(new RoundTransform(6)).into(viewHolder.jingcai_end_iem_img);
                }
                viewHolder.jingcai_end_item_title.setText(data.get(position).getTitle());
                viewHolder.jingcai_end_item_status_img.setImageResource(R.drawable.jingcai_jieshu);
                viewHolder.jingcai_end_item_status.setText("已结束");
                viewHolder.jingcai_end_item_status.setTextColor(Color.parseColor("#6E86D3"));
                viewHolder.jingcai_end_item_result.setText(data.get(position).getResult());
                String bidtnum1 = data.get(position).getBidtnum();
                Double aLong1 = Double.valueOf(bidtnum1).doubleValue();
                double v1 = aLong1 / 20;
                viewHolder.jingcai_end_item_person.setText((int) v1 + "");
                viewHolder.jingcai_end_item_num.setText(data.get(position).getBidtnum());
                viewHolder.jingcai_end_item_bei.setText(data.get(position).getMultiple());
                break;
        }
        return convertView;
    }

    class ViewWaiteHolder {
        TextView jingcai_item_title;
        ImageView jingcai_item_img;
        ImageView jingcai_item_status_img;
        TextView jingcai_item_status_time;
        TextView jingcai_item_person;
        TextView jingcai_item_num;
        MyGridView jingcai_item_list;
    }

    class ViewHolder {
        TextView jingcai_end_item_title;
        ImageView jingcai_end_iem_img;
        ImageView jingcai_end_item_status_img;
        TextView jingcai_end_item_status;
        TextView jingcai_end_item_result;
        TextView jingcai_end_item_person;
        TextView jingcai_end_item_bei;
        TextView jingcai_end_item_num;
    }
}
