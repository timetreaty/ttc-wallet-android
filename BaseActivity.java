package com.example.administrator.ttc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by Administrator on 2018/11/12/012.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidrConfig config = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)//滑动起始方向
                .edge(true).edgeSize(0.3f)//边界占屏幕大小30%
                .build();
        Slidr.attach(this, config);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @SuppressLint("WrongConstant")
    public void showShortToast(String msg) {

        //自定义Toast控件
        View toastView = LayoutInflater.from(this).inflate(R.layout.toast_layout, null);
        TextView textView = toastView.findViewById(R.id.toast_text);
        textView.setText(msg);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();
    }

    protected abstract void initView(Bundle var1);

    protected abstract void setListener();

    protected abstract void processLogic(Bundle var1);

    @Override
    public void onClick(View v) {

    }
}
