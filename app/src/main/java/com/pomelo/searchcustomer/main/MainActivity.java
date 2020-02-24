package com.pomelo.searchcustomer.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;


import com.gyf.immersionbar.ImmersionBar;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.UserInfoBean;
import com.pomelo.searchcustomer.home.HomeFragment;
import com.pomelo.searchcustomer.mine.MineFragment;
import com.pomelo.searchcustomer.supplier.SupplierFragment;
import com.pomelo.searchcustomer.utils.PreferencesUtil;
import com.pomelo.searchcustomer.visitingcard.VisitingCardFragment;
import com.pomelo.searchcustomer.weight.BottomNavigationViewHelper;
import com.pomelo.searchcustomer.weight.CustomViewPager;

import butterknife.BindView;

public class MainActivity extends BaseMVPActivity<MainPresenter> implements MainView, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.color_2058b5).init();
        //禁止横向滑动
        viewPager.setScanScroll(false);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item_tab1);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
//        setImmersionBar(false);
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter.getUserInfo();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_tab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.item_tab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.item_tab3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.item_tab4:
                viewPager.setCurrentItem(3);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            PreferencesUtil.putString("is_merchant", "0");
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments = new Fragment[]{new HomeFragment(), new VisitingCardFragment(), new SupplierFragment(), new MineFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
