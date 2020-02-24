package com.pomelo.searchcustomer.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.UserInfoBean;
import com.pomelo.searchcustomer.home.MyCollectionActivity;
import com.pomelo.searchcustomer.main.LoginActivity;
import com.pomelo.searchcustomer.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class MineFragment extends BaseMvpFragment<MineFragmentPresenter> implements MineFragmentView {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_messgae_count)
    TextView tvMessgaeCount;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_data_collection_count)
    TextView tvDataCollectionCount;
    @BindView(R.id.tv_my_supply_count)
    TextView tvMySupplyCount;
    @BindView(R.id.tv_my_index_count)
    TextView tvMyIndexCount;

    @Override
    protected MineFragmentPresenter createPresenter() {
        return new MineFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
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
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.color_466fd7).init();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mPresenter.getUserInfo();
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

    @OnClick({R.id.tv_customer_pay, R.id.tv_my_message, R.id.tv_messgae_apply, R.id.tv_messgae_recharge, R.id.tv_about_us, R.id.tv_feed_back, R.id.tv_disclaimer, R.id.tv_modify_pwd, R.id.tv_complaints, R.id.ll_collection_data, R.id.ll_my_supply, R.id.ll_my_target})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_customer_pay: {
                Intent intent = new Intent(getContext(), AboutAsActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_my_message: {
                Intent intent = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_messgae_apply: {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MessageModelActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.tv_messgae_recharge: {
                Intent intent = new Intent(getContext(), AboutAsActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_about_us: {
                Intent intent = new Intent(getContext(), AboutAsActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_feed_back: {
                Intent intent = new Intent(getContext(), FeedbackActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_disclaimer: {
                Intent intent = new Intent(getContext(), DisclaimerActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_modify_pwd: {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), ModifyPwdActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.tv_complaints: {

            }
            break;
            case R.id.ll_collection_data: {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyCollectionActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.ll_my_supply: {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MySupplyActivity.class);
                    intent.putExtra("type", "2");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.ll_my_target: {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MySupplyActivity.class);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            break;
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean userInfoBean) {
        tvName.setText(userInfoBean.nick_name);
        tvPhoneNumber.setText(userInfoBean.mobile);
        tvMessgaeCount.setText(userInfoBean.merchant.message_num);
        GlideUtils.loadImage(mContext, userInfoBean.image, ivHead);
        tvData.setText(userInfoBean.merchant.expiretime);
        if (userInfoBean.merchant != null) {
            tvMessgaeCount.setText(userInfoBean.merchant.source_times);
            tvMySupplyCount.setText(userInfoBean.merchant.supply_times);
            tvMyIndexCount.setText(userInfoBean.merchant.need_times);
        }
    }
}
