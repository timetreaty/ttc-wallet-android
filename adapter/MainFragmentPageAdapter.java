package com.example.administrator.ttc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by Administrator on 2018/8/18/018.
 */

public class MainFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments; //Fragment集合

    public MainFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }


    public MainFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
