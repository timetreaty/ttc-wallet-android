package com.example.administrator.ttc.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ttc.R;
import com.example.administrator.ttc.RequestUtils;
import com.example.administrator.ttc.bean.BetBean;
import com.example.administrator.ttc.bean.IsCanOutBean;
import com.example.administrator.ttc.bean.IsSetMoneyPwdBean;
import com.example.administrator.ttc.bean.JingCaiListBean;
import com.example.administrator.ttc.myself_activity.KycOneAttestationActivity;
import com.example.administrator.ttc.myself_activity.SetMoneyPwdActivity;
import com.example.administrator.ttc.utlis.MyEditTextChangeListener;
import com.example.administrator.ttc.utlis.SharedPrefsUtil;
import com.example.administrator.ttc.utlis.ShowToastUtil;
import com.example.administrator.ttc.utlis.TimeUtil;
import com.example.administrator.ttc.utlis.ToActivityUtil;
import com.example.administrator.ttc.weight.JingCaiPopupWindow;
import com.example.administrator.ttc.weight.MyGridView;
import com.example.administrator.ttc.weight.PayPopupWindow;
import com.example.administrator.ttc.weight.RoundTransform;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2018/12/17/017.
 */

public class JingCaiAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private List<JingCaiListBean.DataBean> data;
    private long remainderDate;
    private boolean isRun = true;
    private long day;
    private long hour;
    private long min;
    private long mSecond;
    private ViewHolder holder;
    private JingCaiChooseAdapter jingCaiChooseAdapter;
    private long zhu = 5;
    private int is_can;
    private String token;
    private String pwd;
    private PayPopupWindow payPopupWindow;
    private int id1;
    private boolean choos_flag;

    public JingCaiAdapter(Context context, Activity activity, List<JingCaiListBean.DataBean> data) {
        this.context = context;
        this.activity = activity;
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

    /**
     * Double 转string 去除科学记数法显示
     *
     * @param d
     * @return
     */
    public static String double2Str(Double d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return (nf.format(d));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jingcai_can_item, parent, false);
            holder = new ViewHolder();
            holder.jingcai_item_title = convertView.findViewById(R.id.jingcai_item_title);
            holder.jingcai_item_img = convertView.findViewById(R.id.jingcai_item_img);
            holder.jingcai_item_status_img = convertView.findViewById(R.id.jingcai_item_status_img);
            holder.jingcai_item_status_time = convertView.findViewById(R.id.jingcai_item_status_time);
            holder.jingcai_item_person = convertView.findViewById(R.id.jingcai_item_person);
            holder.jingcai_item_num = convertView.findViewById(R.id.jingcai_item_num);
            holder.jingcai_item_list = convertView.findViewById(R.id.jingcai_item_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String createtime = data.get(position).getCreatetime();
        String endtime = data.get(position).getEndtime();
        int optionType = data.get(position).getOptionType();
        String bannerUrl = data.get(position).getBannerUrl();
        if (bannerUrl == null || bannerUrl.equals("")) {
            holder.jingcai_item_img.setVisibility(View.GONE);
        } else {
            holder.jingcai_item_img.setVisibility(View.VISIBLE);
            Picasso.with(context).load(String.valueOf(bannerUrl)).transform(new RoundTransform(6)).into(holder.jingcai_item_img);
        }
        if (optionType == 1) {
            holder.jingcai_item_list.setNumColumns(2);
        } else if (optionType == 2) {
            holder.jingcai_item_list.setNumColumns(1);
        }
        final List<JingCaiListBean.DataBean.ListBean> list = data.get(position).getList();
        jingCaiChooseAdapter = new JingCaiChooseAdapter(context, list);
        holder.jingcai_item_list.setAdapter(jingCaiChooseAdapter);
        holder.jingcai_item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choos_flag) {
                    zhu = 5;
                    id1 = list.get(position).getId();
                    final String multiple = list.get(position).getMultiple();
                    final JingCaiPopupWindow jingCaiPopupWindow = new JingCaiPopupWindow(activity);
                    View inflate = LayoutInflater.from(context).inflate(R.layout.activity_jing_cai, null);
                    TextView chooseText = jingCaiPopupWindow.getChooseText();
                    final EditText zhuEdittext = jingCaiPopupWindow.getZhuEdittext();
                    final TextView bidtText = jingCaiPopupWindow.getBidtText();
                    final TextView shouYiText = jingCaiPopupWindow.getShouYiText();
                    final TextView zhuText = jingCaiPopupWindow.getZhuText();
                    chooseText.setText(list.get(position).getOpetion());
                    long bidt_num = zhu * 20;
                    double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                    bidtText.setText(bidt_num + "");
                    shouYiText.setText(double2Str(shouyi_num));
                    jingCaiPopupWindow.showPopupWindow(inflate);
                    zhuEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                zhuEdittext.setText(String.valueOf(zhu));
                            } else {

                            }
                        }
                    });
                    zhuEdittext.addTextChangedListener(new MyEditTextChangeListener() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String s = charSequence.toString();
                            if (s == null || s.length() == 0) {
                                zhu = 1;
                                long bidt_num = zhu * 20;
                                double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                                bidtText.setText(bidt_num + "");
                                ShowToastUtil.showShortToast(context, "投注数不能小于1");
                                shouYiText.setText(double2Str(shouyi_num));
                                zhuText.setText(zhu + "注");
                            } else {
                                Long aLong = Long.valueOf(s);
                                if (aLong > 10000) {
                                    ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                    zhu = 10000;
                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(zhuEdittext.getWindowToken(), 0);
                                } else {
                                    if (aLong < 1) {
                                        zhu = 1;
                                        ShowToastUtil.showShortToast(context, "投注数不能小于1");
                                    } else {
                                        zhu = aLong;
                                    }
                                }
                                long bidt_num = zhu * 20;
                                double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                                bidtText.setText(bidt_num + "");
                                shouYiText.setText(double2Str(shouyi_num));
                                zhuText.setText(zhu + "注");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    jingCaiPopupWindow.setOnNextListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jingCaiPopupWindow.dismiss();
                            getCanout();
                        }
                    });
                    jingCaiPopupWindow.setJianListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (zhu == 1) {
                                ShowToastUtil.showShortToast(context, "投注数不能小于1");
                            } else {
                                zhu--;
                                long bidt_num = zhu * 20;
                                double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                                bidtText.setText(bidt_num + "");
                                shouYiText.setText(double2Str(shouyi_num));
                                zhuText.setText(zhu + "注");
                                zhuEdittext.setText(String.valueOf(zhu));
                            }
                        }
                    });
                    jingCaiPopupWindow.setAddListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (zhu == 10000 || zhu > 10000) {
                                ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                zhu = 10000;
                            } else {
                                zhu++;
                            }
                            long bidt_num = zhu * 20;
                            double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                            bidtText.setText(bidt_num + "");
                            shouYiText.setText(double2Str(shouyi_num));
                            zhuText.setText(zhu + "注");
                            zhuEdittext.setText(String.valueOf(zhu));
                        }
                    });
                    jingCaiPopupWindow.setAddOneListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zhu = zhu + 10;
                            if (zhu > 10000) {
                                ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                zhu = 10000;
                            }
                            long bidt_num = zhu * 20;
                            double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                            bidtText.setText(bidt_num + "");
                            shouYiText.setText(double2Str(shouyi_num));
                            zhuText.setText(zhu + "注");
                            zhuEdittext.setText(String.valueOf(zhu));
                        }
                    });
                    jingCaiPopupWindow.setAddTowListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zhu = zhu + 50;
                            if (zhu > 10000) {
                                ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                zhu = 10000;
                            }
                            long bidt_num = zhu * 20;
                            double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                            bidtText.setText(bidt_num + "");
                            shouYiText.setText(double2Str(shouyi_num));
                            zhuText.setText(zhu + "注");
                            zhuEdittext.setText(String.valueOf(zhu));
                        }
                    });
                    jingCaiPopupWindow.setAddThreeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zhu = zhu + 100;
                            if (zhu > 10000) {
                                ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                zhu = 10000;
                            }
                            long bidt_num = zhu * 20;
                            double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                            bidtText.setText(bidt_num + "");
                            shouYiText.setText(double2Str(shouyi_num));
                            zhuText.setText(zhu + "注");
                            zhuEdittext.setText(String.valueOf(zhu));
                        }
                    });
                    jingCaiPopupWindow.setAddFourListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zhu = zhu + 500;
                            if (zhu > 10000) {
                                ShowToastUtil.showShortToast(context, "投注数不能大于1万");
                                zhu = 10000;
                            }
                            long bidt_num = zhu * 20;
                            double shouyi_num = zhu * 20 * Double.valueOf(multiple);
                            bidtText.setText(bidt_num + "");
                            shouYiText.setText(double2Str(shouyi_num));
                            zhuText.setText(zhu + "注");
                            zhuEdittext.setText(String.valueOf(zhu));
                        }
                    });
                    jingCaiPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {

                        }
                    });
                } else {
                    ShowToastUtil.showShortToast(context, "竞猜已截止");
                }
            }
        });
        holder.jingcai_item_title.setText(data.get(position).getTitle());
        String bidtnum = data.get(position).getBidtnum();
        Double aLong = Double.valueOf(bidtnum).doubleValue();
        double v = aLong / 20;
        holder.jingcai_item_person.setText((int) v + "");
        holder.jingcai_item_num.setText(data.get(position).getBidtnum());
        try {
            long creatDate = TimeUtil.stringToLong(createtime, "yyyy-MM-dd HH:mm:ss");
            long endDate = TimeUtil.stringToLong(endtime, "yyyy-MM-dd HH:mm:ss");
            long nowDate = System.currentTimeMillis();
            remainderDate = endDate - nowDate;
            if (remainderDate > 0) {
                choos_flag = true;
                day = remainderDate / (1000 * 60 * 60 * 24);
                hour = (remainderDate / (1000 * 60 * 60) - day * 24);
                min = ((remainderDate / (60 * 1000)) - day * 24 * 60 - hour * 60);
                mSecond = (remainderDate / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
                if (remainderDate > 3600000) {
                    if (day == 0) {
                        holder.jingcai_item_status_time.setText("距离结束：" + hour + "小时" + min + "分" + mSecond + "秒");
                    } else {
                        holder.jingcai_item_status_time.setText("距离结束：" + day + "天" + hour + "小时" + min + "分" + mSecond + "秒");
                    }
                    jingCaiChooseAdapter.enableItemChoser();
                    holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FEAA0A"));
                    holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_juli);
                } else {
                    jingCaiChooseAdapter.enableItemChoser();
                    holder.jingcai_item_status_time.setText("即将结束：" + min + "分" + mSecond + "秒");
                    holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FB4271"));
                    holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_jijiang);
                }
//                if (position == 0) {
//                    startRun();
//                }
            } else {
                choos_flag = false;
                jingCaiChooseAdapter.disableAllItemChoser();
                holder.jingcai_item_status_time.setText("等待结果中");
                holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FE550A"));
                holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_dengdai);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    //判断是否可以转账
    public void getCanout() {
        token = SharedPrefsUtil.getValue(context, "token", "token", "");
        OkHttpUtils.post().url(RequestUtils.REQUEST_HEAD + RequestUtils.WHETHER_KYC_OR_FUND_PWD)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("是否可以转账", response);
                        Gson gson = new Gson();
                        IsCanOutBean isCanOutBean = gson.fromJson(response, IsCanOutBean.class);
                        if (isCanOutBean.getState().getCode().equals("20000")) {
                            is_can = isCanOutBean.getData();
                            if (is_can == 0) {
                                pay();
                            } else if (is_can == 1) {
                                ShowToastUtil.showShortToast(context, "请先完成KYC二级身份认证");
                                ToActivityUtil.newInsance().toNextActivity(activity, KycOneAttestationActivity.class);
                            } else if (is_can == 2) {
                                ShowToastUtil.showShortToast(context, "请先设置安全密码！");
                                ToActivityUtil.newInsance().toNextActivity(activity, SetMoneyPwdActivity.class);
                            }
                        } else {
                            Toast.makeText(context, isCanOutBean.getState().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //支付弹框
    public void pay() {
        payPopupWindow = new PayPopupWindow(activity);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_jing_cai, null);
        payPopupWindow.showPopupWindow(view);
        payPopupWindow.setOnNextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText petPwd = payPopupWindow.getPetPwd();
                pwd = petPwd.getText().toString();
                if (pwd.equals("") || pwd == null) {
                    ShowToastUtil.showShortToast(context, "请输入资产密码");
                } else {
                    confirmPwd();
                }
            }
        });
    }

    //验证资产密码
    private void confirmPwd() {
        OkHttpUtils.post().url(RequestUtils.REQUEST_HEAD + RequestUtils.VERIFY_FUND_PWD)
                .addParams("token", token)
                .addParams("pwd", pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("验证资产密码", response);
                        Gson gson = new Gson();
                        IsSetMoneyPwdBean isSetMoneyPwdBean = gson.fromJson(response, IsSetMoneyPwdBean.class);
                        if (isSetMoneyPwdBean.getState().getCode().equals("20000")) {
                            payPopupWindow.dismiss();
                            sendZhu();
                        } else {
                            ShowToastUtil.showShortToast(context, isSetMoneyPwdBean.getState().getMsg());
                        }
                    }
                });
    }

    //投注
    public void sendZhu() {
        OkHttpUtils.post().url(RequestUtils.REQUEST_HEAD + RequestUtils.BET)
                .addParams("token", token)
                .addParams("betnum", String.valueOf(zhu))
                .addParams("oid", String.valueOf(id1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("投注：", response);
                        Gson gson = new Gson();
                        BetBean betBean = gson.fromJson(response, BetBean.class);
                        if (betBean.getState().getCode().equals("20000")) {
                            payPopupWindow.dismiss();
                            ShowToastUtil.showShortToast(context, "投注成功");
                        } else {
                            ShowToastUtil.showShortToast(context, betBean.getState().getMsg());
                        }
                    }
                });
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
                        choos_flag = true;
                        day = remainderDate / (1000 * 60 * 60 * 24);
                        hour = (remainderDate / (1000 * 60 * 60) - day * 24);
                        min = ((remainderDate / (60 * 1000)) - day * 24 * 60 - hour * 60);
                        mSecond = (remainderDate / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
                        if (remainderDate > 3600000) {
                            jingCaiChooseAdapter.enableItemChoser();
                            holder.jingcai_item_status_time.setText("距离结束：" + day + "天" + hour + "小时" + min + "分" + mSecond + "秒");
                            holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FEAA0A"));
                            holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_juli);
                        } else {
                            jingCaiChooseAdapter.enableItemChoser();
                            holder.jingcai_item_status_time.setText("即将结束：" + min + "分" + mSecond + "秒");
                            holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FB4271"));
                            holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_jijiang);
                        }
                    } else {
                        choos_flag = false;
                        holder.jingcai_item_status_time.setText("等待结果中");
                        jingCaiChooseAdapter.disableAllItemChoser();
                        holder.jingcai_item_status_time.setTextColor(Color.parseColor("#FE550A"));
                        holder.jingcai_item_status_img.setImageResource(R.drawable.jingcai_dengdai);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class ViewHolder {
        TextView jingcai_item_title;
        ImageView jingcai_item_img;
        ImageView jingcai_item_status_img;
        TextView jingcai_item_status_time;
        TextView jingcai_item_person;
        TextView jingcai_item_num;
        MyGridView jingcai_item_list;
    }
}
