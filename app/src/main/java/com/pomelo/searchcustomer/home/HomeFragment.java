package com.pomelo.searchcustomer.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.bean.LatLonBean;
import com.pomelo.searchcustomer.utils.CityPickerViewDialog;
import com.pomelo.searchcustomer.utils.GlideImageLoader;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.pomelo.searchcustomer.weight.NoScrollRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.recyclerView)
    NoScrollRecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.et_keyword)
    EditText eteyword;
    List<String> imgUrlList = new ArrayList<>();
    BaseQuickAdapter<HomeIndexBean.Source, BaseViewHolder> adapter;
    HomeIndexBean homeIndexBean;
    String searchKey, address = "北京-北京";

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
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
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.color_4e96f3).init();
    }

    @Override
    public void initView() {
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new BaseQuickAdapter<HomeIndexBean.Source, BaseViewHolder>(R.layout.item_customer_source) {
            @Override
            protected void convert(BaseViewHolder helper, HomeIndexBean.Source item) {
                ImageView ivSource = helper.getView(R.id.iv_customer);
                helper.setText(R.id.tv_customer_name, item.name);
                GlideUtils.loadImage(mContext, item.image, ivSource);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeIndexBean.Source source = (HomeIndexBean.Source) adapter.getItem(position);
                Intent intent = new Intent(mContext, CollectionActivity.class);
                intent.putExtra("id", source.id);
                intent.putExtra("name", source.name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    private void setBanner(List<String> imageList) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imageList);
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        banner.setDelayTime(2000);
        banner.isAutoPlay(true);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();

    }

    @Override
    public void initData() {
        mPresenter.getIndexData();
        getCityData();
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

    @OnClick({R.id.ll_hot_shop_push, R.id.ll_search, R.id.ll_help_center, R.id.tv_address, R.id.ll_btn_search, R.id.iv_source})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_hot_shop_push: {
                Intent intent = new Intent(mContext, ShopListActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll_help_center: {
                Intent intent = new Intent(mContext, HelpCenterActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_address: {
                hideSoftKeyboard(getActivity());
                selectCity();
            }
            break;
            case R.id.ll_btn_search: {
                searchKey = eteyword.getText().toString().trim();
                if (TextUtils.isEmpty(searchKey)) {
                    ToastUtils.showMessage(mContext, "请输入关键字");
                    return;
                }
                Intent intent = new Intent(mContext, CollectionActivity.class);
                intent.putExtra("searchKey", searchKey);
                intent.putExtra("address", address);
                startActivity(intent);
            }
            break;
            case R.id.ll_search: {
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.iv_source: {
                Intent intent = new Intent(mContext, SourceActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    private void selectCity() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(getContext(), new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx =
                        options1Items.get(options1).getPickerViewText() + " - " +
                                options2Items.get(options1).get(options2);
                address = tx;
                tvAddress.setText(tx);
            }
        });
        dialog.show(options1Items, options2Items);
    }

    @Override
    public void getIndexSuccess(HomeIndexBean homeIndexBean) {
        this.homeIndexBean = homeIndexBean;
        imgUrlList.clear();
        if (homeIndexBean.banner != null && homeIndexBean.banner.size() > 0) {
            for (HomeIndexBean.BannerBean bannerBean : homeIndexBean.banner) {
                imgUrlList.add(bannerBean.image);
            }
        }
        setBanner(imgUrlList);
        if (homeIndexBean.source != null && homeIndexBean.source.size() > 0) {
            adapter.setNewData(homeIndexBean.source);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
