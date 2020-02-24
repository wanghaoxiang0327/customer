package com.pomelo.searchcustomer.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MyCollectionBean;
import com.pomelo.searchcustomer.bean.MyCollectionInfoBean;
import com.pomelo.searchcustomer.dialog.SelectDialog;
import com.pomelo.searchcustomer.dialog.TipDialog;
import com.pomelo.searchcustomer.mine.MessageModelActivity;
import com.pomelo.searchcustomer.utils.ContactUtils;
import com.pomelo.searchcustomer.utils.LogUtils;
import com.pomelo.searchcustomer.utils.NumberUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class MyCollectionActivity extends BaseMVPActivity<MyCollectionPresenter> implements MyCollectionView {
    @BindView(R.id.tv_collection_type)
    TextView tvCollectionType;
    @BindView(R.id.tv_collection_class)
    TextView tvCollectionClass;
    @BindView(R.id.tv_result_count)
    TextView tvResultCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    OptionsPickerView typePickview;
    OptionsPickerView contentPickview;
    List<String> typeList = new ArrayList<>();
    List<String> contentsList = new ArrayList<>();
    String type, content;
    int page = 1;
    BaseQuickAdapter<MyCollectionBean.Data, BaseViewHolder> adapter;
    List<MyCollectionBean.Data> dataList = new ArrayList<>();

    @Override
    protected MyCollectionPresenter createPresenter() {
        return new MyCollectionPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    public void initView() {
        initAdapter();
        initListener();
    }

    public void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.seachCollection(type, content, page + "");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                refreshLayout.setNoMoreData(false);
                page = 1;
                dataList.clear();
                mPresenter.seachCollection(type, content, page + "");
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<MyCollectionBean.Data, BaseViewHolder>(R.layout.item_collection) {
            @Override
            protected void convert(BaseViewHolder helper, MyCollectionBean.Data item) {
                TextView addCustomer = helper.getView(R.id.tv_add_contact);
                helper.setText(R.id.tv_name, item.company_name);
                helper.setText(R.id.tv_address, item.company_address);
                helper.setText(R.id.tv_call, item.company_phone);
                addCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyCollectionBean.Data data = (MyCollectionBean.Data) adapter.getItem(position);
                List<String> names = new ArrayList<>();
                names.add("创建新联系人");
                names.add("添加到现有联系人");
                ContactUtils.requestPermisson(mContext);
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: {
                                ContactUtils.addNewContact(mContext, data.company_name, data.company_phone);
                            }
                            break;
                            case 1: {
                                ContactUtils.saveExistContact(mContext, data.company_name, data.company_phone);
                            }
                            break;
                        }
                    }
                }, names);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getCollectionInfo();
    }

    @OnClick({R.id.iv_back, R.id.tv_collection_type, R.id.tv_collection_class, R.id.tv_send_message, R.id.tv_one_key_out, R.id.tv_delete_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_collection_type: {
                setType();
            }
            break;
            case R.id.tv_collection_class: {
                setContents();
            }
            break;
            case R.id.tv_send_message: {
                Intent intent = new Intent(this, MessageModelActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_one_key_out: {
                mPresenter.exportData(type, content);
            }
            break;
            case R.id.tv_delete_date: {
                mPresenter.deleteCollectionData(type, content);
            }
            break;
        }
    }

    @Override
    public void getMyCollectionInfo(MyCollectionInfoBean myCollectionInfoBean) {
        if (myCollectionInfoBean != null) {
            if (myCollectionInfoBean.types != null && myCollectionInfoBean.types.size() > 0) {
                typeList.clear();
                typeList.addAll(myCollectionInfoBean.types);
            }
            if (myCollectionInfoBean.contents != null && myCollectionInfoBean.contents.size() > 0) {
                contentsList.clear();
                contentsList.addAll(myCollectionInfoBean.contents);
            }
        }
    }

    @Override
    public void getCollectionData(MyCollectionBean myCollectionBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        tvResultCount.setText(myCollectionBean.count + "");
        int allPage = NumberUtils.upCeil(myCollectionBean.count);
        if (myCollectionBean != null && myCollectionBean.data != null && myCollectionBean.data.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            dataList.addAll(myCollectionBean.data);
        }
        if (dataList.size() > 0) {
            adapter.setNewData(dataList);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
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


    @Override
    public void deleteSuccess() {
        dataList.clear();
        adapter.setEmptyView(getEmptyView());
    }

    @Override
    public void exportDatSuccess(String url) {
        new TipDialog(this)
                .setContent("下载提示")
                .setCanaleText("直接打开")
                .setConfirmBtn("复制")
                .setCancelListener(dialog -> {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    dialog.dismiss();
                })
                .setConfirmListener(dialog -> {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", url);
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    dialog.dismiss();
                })
                .show();
    }


    private void setType() {
        typePickview = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                type = typeList.get(options1);
                tvCollectionType.setText(type);
            }
        }).setContentTextSize(20).setCancelColor(getResources().getColor(R.color.color_333333)).setSubmitColor(getResources().getColor(R.color.color_4e96f3)).build();
        typePickview.setPicker(typeList);
        typePickview.show();
    }

    private void setContents() {
        contentPickview = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                content = contentsList.get(options1);
                tvCollectionClass.setText(content);
                page = 1;
                dataList.clear();
                mPresenter.seachCollection(type, content, page + "");
            }
        }).setContentTextSize(20).setCancelColor(getResources().getColor(R.color.color_333333)).setSubmitColor(getResources().getColor(R.color.color_4e96f3)).build();
        contentPickview.setPicker(contentsList);
        contentPickview.show();
    }
}
