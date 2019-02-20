package com.example.administrator.ttc;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;

/**
 * Created by Administrator on 2018/8/14/014.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
//        MobSDK.init(this);

        /**
         * 极光分享
         */
        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
                .setWechat("申请的AppID", "申请的APPSecret")
                .setQQ("101523237", "34821b90ae75a04d26d3e63afc411241")
                .setWechat("wx0595c319d869aeea", "409053a3caa3d521c34124a22e2044d7");
//                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn");
//                .setFacebook("1847959632183996", "JShareDemo")
//                .setTwitter("eRJyErWUhRZVqBzADAbUnNWx5", "Oo7DJMiBwBHGFWglFrML1ULZCUDlH990RlJlQDdfepm3lToiMC");
        JShareInterface.init(this, platformConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}