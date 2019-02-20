package com.example.administrator.ttc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.ttc.adapter.MainFragmentPageAdapter;
import com.example.administrator.ttc.bean.UpdateBean;
import com.example.administrator.ttc.bean.WeiHuBean;
import com.example.administrator.ttc.fragment.JyFragment;
import com.example.administrator.ttc.fragment.MoneyFragment;
import com.example.administrator.ttc.fragment.MyselfFragment;
import com.example.administrator.ttc.utlis.FixedSpeedScroller;
import com.example.administrator.ttc.utlis.GetApkMessageUtil;
import com.example.administrator.ttc.utlis.SharedPrefsUtil;
import com.example.administrator.ttc.utlis.ShowToastUtil;
import com.example.administrator.ttc.utlis.StatusBarUtil;
import com.example.administrator.ttc.utlis.TimeUtil;
import com.example.administrator.ttc.weight.UpdateDialog;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager main_viewpager;
    private RadioGroup main_radioGroup;
    private RadioButton main_tab_money;
    private RadioButton main_tab_jy;
    private RadioButton main_tab_myself;
    private Fragment moneyFragment;
    private Fragment jyFragment;
    private Fragment myselfFragment;
    private MainFragmentPageAdapter adapter;
    private boolean isExit;
    private String phoneVersion;
    private String phoneName;
    private String blockId;
    private String apkName;
    private String channel;
    private String name;
    private double latitude;
    private double longitude;
    private String province = "";
    private int flag = 0;
    private FixedSpeedScroller mScroller = null;
    private LocationClientOption option;
    private LocationClient mLocationClient;
    private UpdateDialog updateDialog;
    private UpdateDialog updateDialog1;
    private int dialogIsExit = 0;
    private LinearLayout weihu_layout;
    private TextView weihu_btn;
    private TextView weihu_text;
    private TextView weihu_time;
    private LinearLayout content_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initView();
        initViewPager();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
        }
    }

    private void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//设置状态栏黑色字体
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//恢复状态栏白色字体
            }
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected void initView() {
        main_viewpager = findViewById(R.id.main_viewPager);
        main_radioGroup = findViewById(R.id.main_radioGroup);
        main_tab_money = findViewById(R.id.main_tab_money);
        main_tab_jy = findViewById(R.id.main_tab_jy);
        main_tab_myself = findViewById(R.id.main_tab_myself);
        weihu_layout = findViewById(R.id.weihu_layout);
        weihu_btn = findViewById(R.id.weihu_btn);
        weihu_text = findViewById(R.id.weihu_text);
        weihu_time = findViewById(R.id.weihu_time);
        content_layout = findViewById(R.id.content_layout);
        weihu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        main_radioGroup.setOnCheckedChangeListener(this);
        //挨着给每个RadioButton加入drawable限制边距以控制显示大小
        Drawable[] drawables = main_tab_money.getCompoundDrawables();
        //获取drawables
        Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 3 / 15, drawables[1].getMinimumHeight() * 3 / 17);
        //定义一个Rect边界
        drawables[1].setBounds(r);
        //添加限制给控件
        main_tab_money.setCompoundDrawables(null, drawables[1], null, null);
        Drawable[] drawables1 = main_tab_jy.getCompoundDrawables();
        //获取drawables
        Rect r1 = new Rect(0, 0, drawables[1].getMinimumWidth() * 3 / 17, drawables[1].getMinimumHeight() * 3 / 17);
        //定义一个Rect边界
        drawables1[1].setBounds(r1);
        //添加限制给控件
        main_tab_jy.setCompoundDrawables(null, drawables1[1], null, null);
        Drawable[] drawables2 = main_tab_myself.getCompoundDrawables();
        //获取drawables
        Rect r2 = new Rect(0, 0, drawables[1].getMinimumWidth() * 3 / 17, drawables[1].getMinimumHeight() * 3 / 17);
        //定义一个Rect边界
        drawables2[1].setBounds(r2);
        //添加限制给控件
        main_tab_myself.setCompoundDrawables(null, drawables2[1], null, null);
        IsWeiHu();
    }

    private void IsWeiHu() {
        StatusBarUtil.changeStatusBarTextColor(this, false);
        OkHttpUtils.get().url(RequestUtils.CER_SERVER_CONTROL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("维护失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("是否维护：", response);
                        Gson gson = new Gson();
                        WeiHuBean weiHuBean = gson.fromJson(response, WeiHuBean.class);
                        if (weiHuBean.getState().getCode().equals("20000")) {
                            int status = weiHuBean.getData().getStatus();
                            if (status == 1) {
                                try {
                                    String context = weiHuBean.getData().getContext();
                                    long starttime_long = TimeUtil.stringToLong(weiHuBean.getData().getStarttime(), "yyyy-MM-dd HH:mm:ss");
                                    long endtime_long = TimeUtil.stringToLong(weiHuBean.getData().getEndtime(), "yyyy-MM-dd HH:mm:ss");
                                    String starttime = TimeUtil.longToString(starttime_long, "MM/dd HH:mm");
                                    String endtime = TimeUtil.longToString(endtime_long, "MM/dd HH:mm");
                                    weihu_text.setText(context);
                                    weihu_time.setText("维护时间：" + starttime + "~" + endtime);
                                    weihu_layout.setVisibility(View.VISIBLE);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                getUpdateMessage();
                                getLoginNum();
                                getLocation();
                            }
                        } else {
                            ShowToastUtil.showShortToast(MainActivity.this, weiHuBean.getState().getMsg());
                        }
                    }
                });
    }


    private void initViewPager() {
        moneyFragment = new MoneyFragment();
        jyFragment = new JyFragment();
        myselfFragment = new MyselfFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(moneyFragment);
        fragments.add(jyFragment);
        fragments.add(myselfFragment);
        //获取FragmentManager对象
        //获取FragmentPageAdapter对象
        adapter = new MainFragmentPageAdapter(getSupportFragmentManager(), fragments);
        //设置Adapter，使ViewPager 与 Adapter 进行绑定
        main_viewpager.setAdapter(adapter);
        //设置ViewPager默认显示第一个View
        main_viewpager.setCurrentItem(0);

        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(
                    main_viewpager.getContext(),
                    new AccelerateInterpolator());
            mScroller.setmDuration(50); // 1500ms
            mField.set(main_viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //设置ViewPager设置滑动动画
//        main_viewpager.setPageTransformer(true, new AccordionTransformer());
        //ViewPager页面切换监听
        main_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeStatusBarTextColor(false);
                        main_radioGroup.check(R.id.main_tab_money);
                        break;
                    case 1:
                        changeStatusBarTextColor(true);
                        main_radioGroup.check(R.id.main_tab_jy);
                        break;
                    case 2:
                        changeStatusBarTextColor(true);
                        main_radioGroup.check(R.id.main_tab_myself);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_tab_money: // 资产
                //显示第一个Fragment并关闭动画效果
                Log.e("点击", "资产");
                changeStatusBarTextColor(false);
                main_viewpager.setCurrentItem(0, true);
                break;
            case R.id.main_tab_jy: // 交易
                Log.e("点击", "交易");
                changeStatusBarTextColor(true);
                main_viewpager.setCurrentItem(1, true);
                break;
            case R.id.main_tab_myself: // 我的
                Log.e("点击", "我的");
                changeStatusBarTextColor(true);
                main_viewpager.setCurrentItem(2, true);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//处理返回按钮被按下
            if (!isExit) {
                ShowToastUtil.showShortToast(this, "再次点击退出程序");
                isExit = true;
//延时两秒发送
                mHandler.sendEmptyMessageDelayed(0, 2000);
                return true;
            } else {
                Log.i("log", "退出程序");
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    public void getLoginNum() {
        blockId = SharedPrefsUtil.getValue(this, "blockId", "blockId", "");
        OkHttpUtils.post().url(RequestUtils.REQUEST_HEAD + RequestUtils.ANDROID_LOGIN_NUM)
                .addParams("blockId", blockId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("登录数量", response);
                    }
                });
    }

    private void getVisibility() {
        flag = 0;
        blockId = SharedPrefsUtil.getValue(this, "blockId", "blockId", "");
        OkHttpUtils.post().url(RequestUtils.REQUEST_HEAD + RequestUtils.MOBILE_VERSION_NUMBER)
                .addParams("phoneModel", phoneName)
                .addParams("phoneVersionNumber", phoneVersion)
                .addParams("longitude", longitude + "")
                .addParams("latitude", latitude + "")
                .addParams("address", province)
                .addParams("ditchId", channel)
                .addParams("appVersion", apkName)
                .addParams("blockId", blockId)
                .addParams("type", "android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("失败：", e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("获取版本更新", response);
                        Gson gson = new Gson();
                        UpdateBean updateBean = gson.fromJson(response, UpdateBean.class);
                        if (updateBean.getState().getCode().equals("20000")) {
                            mLocationClient.stop();
                            Log.e("传值手机机型", phoneName);
                            Log.e("传值手机系统版本号", phoneVersion);
                            Log.e("传值渠道", channel);
                            Log.e("传值APP版本号", apkName);
                            Log.e("传值纬度", latitude + "");
                            Log.e("传值经度", longitude + "");
                            Log.e("传值地址", province);
                            int status = updateBean.getData().getStatus();
                            if (status == 0) {

                            } else if (status == 1) {
                                String time = updateBean.getData().getTime();
                                String appVersion = updateBean.getData().getAppVersion();
                                List<UpdateBean.DataBean.ListBean> list = updateBean.getData().getList();
                                name = channel + "-" + appVersion + ".apk";
                                Log.e("安装包路径", name);
                                updateDialog = new UpdateDialog(MainActivity.this, 0, name, time, appVersion, list);
                                updateDialog.setCanceledOnTouchOutside(false);
                                updateDialog.show();
                                updateDialog.getUpdate_cancel_text().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateDialog.dismiss();
                                    }
                                });
                            } else if (status == 2) {
                                String time = updateBean.getData().getTime();
                                String appVersion = updateBean.getData().getAppVersion();
                                List<UpdateBean.DataBean.ListBean> list = updateBean.getData().getList();
                                name = channel + "-" + appVersion + ".apk";
                                Log.e("安装包路径", name);
                                updateDialog1 = new UpdateDialog(MainActivity.this, 1, name, time, appVersion, list);
                                updateDialog1.setCanceledOnTouchOutside(false);
                                updateDialog1.show();
                                updateDialog1.getUpdate_cancel_text().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateDialog1.dismiss();
                                    }
                                });
                                updateDialog1.setCancelable(false);
                            }

                        } else {
                            ShowToastUtil.showShortToast(MainActivity.this, updateBean.getState().getMsg());
                        }
                    }
                });
    }

    public void getPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.REQUEST_INSTALL_PACKAGES}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        ShowToastUtil.showShortToast(this, "权限未申请");
                    }
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //获取手机机型，手机系统版本号，apk渠道id，纬度，经度，地理位置,apk版本号
    public void getUpdateMessage() {
        GetApkMessageUtil getApkMessageUtil = new GetApkMessageUtil(this);
        phoneName = getApkMessageUtil.getPhoneName();
        phoneVersion = getApkMessageUtil.getPhoneVersion();
        channel = getApkMessageUtil.getChannel();
        apkName = getApkMessageUtil.getApkName();
    }

    public void getLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取纬度信息
                latitude = bdLocation.getLatitude();//获取经度信息
                longitude = bdLocation.getLongitude();
                float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
                String coorType = bdLocation.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                int errorCode = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                String addr = bdLocation.getAddrStr();    //获取详细地址信息
                String country = bdLocation.getCountry();    //获取国家
                province = bdLocation.getProvince();    //获取省份
                String city = bdLocation.getCity();    //获取城市
                String district = bdLocation.getDistrict();    //获取区县
                String street = bdLocation.getStreet();    //获取街道信息
                Log.e("百度地图纬度", latitude + "");
                Log.e("百度地图经度", longitude + "");
                Log.e("百度地图地址", province);
                if (flag == 0) {
                    getVisibility();
                    flag = 1;
                }
            }
        });
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        option.setScanSpan(0);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(true);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
//
//    private void changeImageSize() {
//        //定义底部标签图片大小
//        Drawable drawableFirst = getResources().getDrawable(R.drawable.main_tab_money_style);
//        drawableFirst.setBounds(0, 0, dp2px(21), dp2px(17));//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        main_tab_money.setCompoundDrawables(null, drawableFirst, null, null);//只放上面
//
//        Drawable drawableSearch = getResources().getDrawable(R.drawable.main_tab_jy_style);
//        drawableSearch.setBounds(0, 0, dp2px(18), dp2px(17));//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        main_tab_jy.setCompoundDrawables(null, drawableSearch, null, null);//只放上面
//
//        Drawable drawableMe = getResources().getDrawable(R.drawable.main_tab_myself_style);
//        drawableMe.setBounds(0, 0, dp2px(18), dp2px(17));//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        main_tab_myself.setCompoundDrawables(null, drawableMe, null, null);//只放上面
//    }
}
