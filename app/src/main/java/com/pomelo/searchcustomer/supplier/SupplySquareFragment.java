package com.pomelo.searchcustomer.supplier;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.adapter.TabViewPagerAdapter;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.SupplierClassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class SupplySquareFragment extends BaseMvpFragment<SupplierSquareFragmentPresenter> implements SupplierSquareFragmentView {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String> titleList;

    @Override
    protected SupplierSquareFragmentPresenter createPresenter() {
        return new SupplierSquareFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_supply_square;
    }

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        titleList.add("全部");
    }

    @Override
    public void initData() {
        mPresenter.getSupplierClass();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        for (int i = 0; i < titleList.size(); i++) {
            ContentFragment contentFragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", "2");
            contentFragment.setArguments(bundle);
            fragments.add(contentFragment);
        }
        viewPager.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(), fragments, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void getSupplierClassSuccess(SupplierClassBean supplierClassBean) {
        if (supplierClassBean.supply_arr != null && supplierClassBean.supply_arr.size() > 0) {
            for (SupplierClassBean.SupplierClass s : supplierClassBean.supply_arr) {
                titleList.add(s.name);
            }
        }
        for (int i = 0; i < titleList.size(); i++) {
            if (i == 0) {
                ContentFragment contentFragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "2");
                contentFragment.setArguments(bundle);
                fragments.add(contentFragment);
            } else {
                ContentFragment contentFragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "2");
                bundle.putString("needType", supplierClassBean.supply_arr.get(i).id);
                contentFragment.setArguments(bundle);
                fragments.add(contentFragment);
            }
        }
        viewPager.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(), fragments, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }
}
