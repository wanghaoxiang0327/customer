package com.pomelo.searchcustomer.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-09-22.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    List<String> titlsList;

    public TabViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titlsList) {
        super(fm);
        this.list = list;
        this.titlsList = titlsList;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return titlsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlsList.get(position);
    }
}
