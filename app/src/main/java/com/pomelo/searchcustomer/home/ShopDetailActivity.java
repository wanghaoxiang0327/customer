package com.pomelo.searchcustomer.home;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.weight.NoScrollRecyclerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class ShopDetailActivity extends BaseMVPActivity<ShopDetailPresenter> implements ShopDetailView {
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_description)
    WebView tvDescription;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.recyclerView)
    NoScrollRecyclerView recyclerView;
    String id, name, content;
    BaseQuickAdapter<String, BaseViewHolder> adapter;

    @Override
    protected ShopDetailPresenter createPresenter() {
        return new ShopDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        id = getIntent().getStringExtra("id");
        initAdapter();
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getShopDetail(id);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_shop_detail) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.iv_shop);
                GlideUtils.loadImage(mContext, item, imageView);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_share_wechat, R.id.tv_share_wechat_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_share_wechat: {
                UMImage image = new UMImage(this, R.mipmap.ic_launcher);//分享图标
                final UMWeb web = new UMWeb("你要分享的地址"); //切记切记 这里分享的链接必须是http开头
                web.setTitle(name);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(content);//描述
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
            case R.id.tv_share_wechat_circle:
                UMImage image = new UMImage(this, R.mipmap.ic_launcher);//分享图标
                final UMWeb web = new UMWeb("你要分享的地址"); //切记切记 这里分享的链接必须是http开头
                web.setTitle(name);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(content);//描述
                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
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
                break;
        }
    }

    @Override
    public void getShopDetailSuccess(ShopDetailBean shopDetailBean) {
        name = shopDetailBean.store_name;
        content = shopDetailBean.store_content;
        tvShopName.setText(shopDetailBean.store_name);
        tvDescription.loadData(shopDetailBean.store_content, "text/html", "UTF-8");
        tvPhoneNumber.setText(shopDetailBean.mobile);
        adapter.setNewData(shopDetailBean.store_images);
    }
}
