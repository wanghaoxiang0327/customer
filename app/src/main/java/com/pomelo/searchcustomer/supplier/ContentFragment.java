package com.pomelo.searchcustomer.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.SullierBean;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.dialog.ComplaintDialog;
import com.pomelo.searchcustomer.home.ShopDetailActivity;
import com.pomelo.searchcustomer.mine.MySupplyDetailActivity;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.utils.NumberUtils;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.pomelo.searchcustomer.visitingcard.OtherPersonVistingCardActivity;
import com.pomelo.searchcustomer.weight.NoScrollRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class ContentFragment extends BaseMvpFragment<ContentFragmentPresenter> implements ContentFragmentView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    BaseQuickAdapter<SullierBean.Sullier, BaseViewHolder> adapter;
    List<SullierBean.Sullier> sulliers = new ArrayList<>();
    String id = "-1", type;
    int page = 1;
    ComplaintDialog complaintDialog;

    @Override
    protected ContentFragmentPresenter createPresenter() {
        return new ContentFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("needType", "-1");
            type = bundle.getString("type");
        }
        initAdapter();
        initListener();
    }


    public void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getSupplierList(type, page + "", id, "1");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                sulliers.clear();
                refreshLayout.setNoMoreData(false);
                mPresenter.getSupplierList(type, page + "", id, "1");
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<SullierBean.Sullier, BaseViewHolder>(R.layout.item_content) {
            @Override
            protected void convert(BaseViewHolder helper, SullierBean.Sullier item) {
                helper.setText(R.id.tv_username, item.nick_name);
                helper.setText(R.id.tv_shop_name, item.title);
                helper.setText(R.id.tv_shop_description, item.content);
                helper.setText(R.id.tv_address, item.card_place);
                helper.setText(R.id.tv_time, item.createtime);
                helper.setText(R.id.tv_look_count, "浏览" + item.click + "次");
                ImageView ivHead = helper.getView(R.id.iv_head);
                TextView tvGetDetail = helper.getView(R.id.tv_get_detail);
                TextView tvComplaint = helper.getView(R.id.tv_complaint);
                if (type.equals("1")) {
                    ivHead.setVisibility(View.GONE);
                } else {
                    ivHead.setVisibility(View.VISIBLE);
                }
                NoScrollRecyclerView imageRecycleView = helper.getView(R.id.imgrecycleview);
                GlideUtils.loadImage(mContext, item.image, ivHead);
                if (item.images != null && item.images.size() > 0) {
                    imageRecycleView.setVisibility(View.VISIBLE);
                    initImageAdaper(imageRecycleView, item.images);
                } else {
                    imageRecycleView.setVisibility(View.GONE);
                }
                tvGetDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, MySupplyDetailActivity.class);
                        intent.putExtra("id", item.id);
                        intent.putExtra("title", item.title);
                        startActivity(intent);
                    }
                });
                tvComplaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        complaintDialog = new ComplaintDialog(mContext);
                        complaintDialog.setConfirmListener(new ComplaintDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm(String content, ComplaintDialog dialog) {
                                mPresenter.complaint(item.id, content);
                            }
                        }).show();
                    }
                });
                ivHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, OtherPersonVistingCardActivity.class);
                        intent.putExtra("id", item.uid);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private void initImageAdaper(NoScrollRecyclerView recyclerView, List<String> images) {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        BaseQuickAdapter<String, BaseViewHolder> imageadapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item_image) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.iv_img);
                GlideUtils.loadImage(mContext, item, imageView);
            }
        };
        recyclerView.setAdapter(imageadapter);
        imageadapter.setNewData(images);
        imageadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                List<LocalMedia> localMediaList = new ArrayList<>();
//                for (int i = 0; i < images.size(); i++) {
//                    LocalMedia localMedia = new LocalMedia();
//                    localMedia.setPath(images.get(i));
//                    localMedia.setPictureType("image/png");
//                }
//                PictureSelector.create(getActivity()).externalPicturePreview(position, localMediaList);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getSupplierList(type, page + "", id, "1");
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
    public void getSuppliersListSuccess(SullierBean sullierBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        int allPage = NumberUtils.upCeil(sullierBean.count);
        if (sullierBean != null && sullierBean.library != null && sullierBean.library.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            sulliers.addAll(sullierBean.library);
        }
        if (sulliers.size() > 0) {
            adapter.setNewData(sulliers);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }

    @Override
    public void compliantSuccess(String s) {
        ToastUtils.showMessage(mContext, s);
        complaintDialog.dismiss();
    }
}
