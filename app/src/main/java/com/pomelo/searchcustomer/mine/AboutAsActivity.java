package com.pomelo.searchcustomer.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.AboutUsBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class AboutAsActivity extends BaseMVPActivity<AboutUsPresenter> implements AboutUsView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_copy_wechat)
    TextView tvCopyWechat;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getInfo();
    }

    @OnClick({R.id.iv_back, R.id.tv_call, R.id.tv_copy_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_call: {
                callPhone(Contanst.PHONE_NUMBER);
            }
            break;
            case R.id.tv_copy_wechat:
                break;
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void linkusSuccess(List<AboutUsBean> list) {
        if (list != null) {
            tvPhone.setText(list.get(0).mobile);
            tvWechat.setText(list.get(0).wx);
            GlideUtils.loadImage(mContext, list.get(0).image, ivQrcode);
        }
    }
}
