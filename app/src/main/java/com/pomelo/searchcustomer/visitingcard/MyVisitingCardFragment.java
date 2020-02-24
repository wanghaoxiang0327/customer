package com.pomelo.searchcustomer.visitingcard;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class MyVisitingCardFragment extends BaseMvpFragment<MyVisitingCardFragmentPresenter> implements MyVisitingCardFragmentView {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_all_info)
    TextView tvAllInfo;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_wechat_number)
    TextView tvWechatNumber;
    @BindView(R.id.tv_email_address)
    TextView tvEmailAddress;
    @BindView(R.id.tv_tell_number)
    TextView tvTellNumber;
    @BindView(R.id.tv_bumen)
    TextView tvBumen;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_card_pocket)
    TextView tvCardPocket;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_ok_count)
    TextView tvOkCount;
    @BindView(R.id.tv_look_count)
    TextView tvLookCount;
    @BindView(R.id.ll_detail_info)
    LinearLayout llDetailInfo;
    private boolean isVisiable = false;//默认不显示详情

    @Override
    protected MyVisitingCardFragmentPresenter createPresenter() {
        return new MyVisitingCardFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_card;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mPresenter.getMyVisitingCard();
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

    @OnClick({R.id.iv_setting, R.id.tv_all_info, R.id.tv_card_pocket, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting: {
                Intent intent = new Intent(mContext, EditVisitingCardActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_all_info: {
                if (isVisiable) {
                    llDetailInfo.setVisibility(View.GONE);
                    ivArrow.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_arrow_down));
                    isVisiable = false;
                    tvAllInfo.setText("展开名片全部信息");
                } else {
                    llDetailInfo.setVisibility(View.VISIBLE);
                    isVisiable = true;
                    ivArrow.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_arrow_up));
                    tvAllInfo.setText("收起以下名片信息");
                }
            }
            break;
            case R.id.tv_card_pocket: {
                Intent intent = new Intent(mContext, VisitingCardListActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_send: {

            }
            break;
        }
    }

    @Override
    public void getMyVisitingCardInfo(MyVisitinCardBean myVisitinCardBean) {
        if (myVisitinCardBean != null) {
            if (!TextUtils.isEmpty(myVisitinCardBean.name)) {
                tvUsername.setText(myVisitinCardBean.name);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.position)) {
                tvPost.setText(myVisitinCardBean.position);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.department)) {
                tvBumen.setText(myVisitinCardBean.department);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.company_name)) {
                tvCompany.setText(myVisitinCardBean.company_name);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.company_name)) {
                tvCompanyName.setText(myVisitinCardBean.company_name);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.mobile)) {
                tvPhone.setText(myVisitinCardBean.mobile);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.mobile)) {
                tvPhoneNumber.setText(myVisitinCardBean.mobile);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.email)) {
                tvEmail.setText(myVisitinCardBean.email);
                tvEmailAddress.setText(myVisitinCardBean.email);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.card_place)) {
                tvAddress.setText(myVisitinCardBean.card_place);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.company_address)) {
                tvAddressDetail.setText(myVisitinCardBean.company_address);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.tel_work)) {
                tvTellNumber.setText(myVisitinCardBean.tel_work);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.wechat)) {
                tvWechatNumber.setText(myVisitinCardBean.wechat);
            }
            if (!TextUtils.isEmpty(myVisitinCardBean.image)) {
                GlideUtils.loadImage(mContext, myVisitinCardBean.image, ivHead);
            }
            tvLookCount.setText("已有" + (TextUtils.isEmpty(myVisitinCardBean.card_browse) ? 0 : myVisitinCardBean.card_browse) + "个人浏览");
            tvCollectionCount.setText("收藏" + (TextUtils.isEmpty(myVisitinCardBean.card_collect) ? 0 : myVisitinCardBean.card_collect));
            tvOkCount.setText("靠谱" + (TextUtils.isEmpty(myVisitinCardBean.card_reliable) ? 0 : myVisitinCardBean.card_reliable));
        }
    }

}
