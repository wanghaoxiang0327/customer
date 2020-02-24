package com.pomelo.searchcustomer.visitingcard;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.dialog.SelectDialog;
import com.pomelo.searchcustomer.utils.ContactUtils;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class OtherPersonVistingCardActivity extends BaseMVPActivity<OtherPersonVisitingCardPresenter> implements OtherPersonVisitingCardView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
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
    @BindView(R.id.ll_detail_info)
    LinearLayout llDetailInfo;
    @BindView(R.id.tv_card_pocket)
    TextView tvCardPocket;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_look_count)
    TextView tvLookCount;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_ok_count)
    TextView tvOkCount;
    @BindView(R.id.tv_collection_card)
    TextView tvCollectionCard;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_my_card)
    TextView tvMyCard;
    private boolean isVisiable = false;//默认不显示详情
    String id;
    String isCollection, isReliable, phone, name;

    @Override
    protected OtherPersonVisitingCardPresenter createPresenter() {
        return new OtherPersonVisitingCardPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other_person_visitin_card;
    }

    @Override
    public void initView() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getOtherPersonDetail(id);
    }

    @OnClick({R.id.iv_back, R.id.tv_all_info, R.id.tv_copy_concant, R.id.ll_call, R.id.tv_collection_card, R.id.tv_like, R.id.tv_my_card, R.id.ll_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
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
            case R.id.tv_collection_card: {
                if (isCollection.equals("0")) {
                    mPresenter.collectionCard(id);
                } else {
                    mPresenter.cancleCollectionCard(id);
                }
            }
            break;
            case R.id.tv_like: {
                mPresenter.careReliableCard(id);
            }
            break;
            case R.id.tv_my_card: {
                finish();
            }
            break;
            case R.id.tv_copy_concant: {
                addContact();
            }
            break;
            case R.id.ll_call: {
                callPhone(phone);
            }
            break;
            case R.id.ll_send: {
                UMImage image = new UMImage(this, R.mipmap.ic_launcher);//分享图标
                final UMWeb web = new UMWeb("你要分享的地址"); //切记切记 这里分享的链接必须是http开头
                web.setTitle("你要分享内容的标题");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("你要分享内容的描述");//描述
                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(web)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {

                            }
                        })
                        .share();
            }
            break;
        }
    }

    private void addContact() {
        List<String> names = new ArrayList<>();
        names.add("创建新联系人");
        names.add("添加到现有联系人");
        ContactUtils.requestPermisson(mContext);
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        ContactUtils.addNewContact(mContext, name, phone);
                    }
                    break;
                    case 1: {
                        ContactUtils.saveExistContact(mContext, name, phone);
                    }
                    break;
                }
            }
        }, names);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    public void getMyVisitingCardInfo(MyVisitinCardBean myVisitinCardBean) {
        if (myVisitinCardBean != null) {
            name = myVisitinCardBean.name;
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
            if (myVisitinCardBean.is_reliable.equals("0")) {
                tvLike.setText("靠谱");
            } else {
                tvLike.setText("取消靠谱");
            }
            if (myVisitinCardBean.is_collection.equals("0")) {
                tvCollectionCard.setText("收藏名片");
            } else {
                tvCollectionCard.setText("取消收藏");
            }
            phone = myVisitinCardBean.mobile;
            isCollection = myVisitinCardBean.is_collection;
            isReliable = myVisitinCardBean.is_reliable;
            GlideUtils.loadImage(mContext, myVisitinCardBean.image, ivHead);
            tvLookCount.setText("已有" + myVisitinCardBean.card_browse + "个人浏览");
            tvCollectionCount.setText("收藏" + myVisitinCardBean.card_collect);
            tvOkCount.setText("靠谱" + myVisitinCardBean.card_reliable);
        }
    }

    @Override
    public void collectionCardSuccess() {
        tvCollectionCard.setText("取消收藏");
        isCollection = "1";
    }

    @Override
    public void cancalcollectionCardSuccess() {
        tvCollectionCard.setText("收藏名片");
        isCollection = "0";
    }

    @Override
    public void careReliableCardSuccess() {
        if (isReliable.equals("0")) {
            isReliable = "1";
            tvLike.setText("取消靠谱");
        } else {
            isReliable = "0";
            tvLike.setText("靠谱");
        }
    }
}
