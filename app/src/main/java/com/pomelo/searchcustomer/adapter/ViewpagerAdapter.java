package com.pomelo.searchcustomer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> list;

    public ViewpagerAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        this.context=context;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
