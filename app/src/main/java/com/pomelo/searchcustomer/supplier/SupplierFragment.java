package com.pomelo.searchcustomer.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.adapter.ViewpagerAdapter;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.SupplierClassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class SupplierFragment extends BaseMvpFragment<SupplierFragmentPresenter> implements SupplierFragmentView, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.rb_my_visit_card)
    RadioButton rbMyVisitCard;
    @BindView(R.id.rb_visit_card_squary)
    RadioButton rbVisitCardSquary;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    List<Fragment> fragmentList;
    private ViewpagerAdapter adapter;
    private int currentFragment;

    @Override
    protected SupplierFragmentPresenter createPresenter() {
        return new SupplierFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_supplier;
    }

    @Override
    public void initView() {
        initFragment();
        adapter = new ViewpagerAdapter(getChildFragmentManager(), getContext(), fragmentList);
        viewPager.setAdapter(adapter);
        radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        viewPager.addOnPageChangeListener(this);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new SupplySquareFragment());
        fragmentList.add(new NationalBiddingFragment());
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initImmersionBar();
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).init();
    }


    @Override
    public void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.rb_my_visit_card);
                break;
            case 1:
                radioGroup.check(R.id.rb_visit_card_squary);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_my_visit_card:
                currentFragment = 0;
                break;
            case R.id.rb_visit_card_squary:
                currentFragment = 1;
                break;
        }
        viewPager.setCurrentItem(currentFragment);
    }


    @OnClick(R.id.tv_wangto_supply)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, WantToSupplyActivity.class);
        startActivity(intent);
    }
}
