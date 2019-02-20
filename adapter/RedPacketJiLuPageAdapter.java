package com.example.administrator.ttc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/8/18/018.
 */

public class RedPacketJiLuPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments; //Fragment集合
    private List<String> list; //Fragment集合

    public RedPacketJiLuPageAdapter(FragmentManager fm) {
        super(fm);
    }


    public RedPacketJiLuPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> list) {
        super(fm);
        this.fragments = fragments;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
