package com.pomelo.searchcustomer.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MessageModelBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class MessageModelActivity extends BaseMVPActivity<MessageModelPresenter> implements MessageModelView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BaseQuickAdapter<MessageModelBean.MessageModel, BaseViewHolder> adapter;

    @Override
    protected MessageModelPresenter createPresenter() {
        return new MessageModelPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_model;
    }

    @Override
    public void initView() {
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<MessageModelBean.MessageModel, BaseViewHolder>(R.layout.item_message_model) {
            @Override
            protected void convert(BaseViewHolder helper, MessageModelBean.MessageModel item) {
                helper.setText(R.id.tv_status, item.status);
                helper.setText(R.id.tv_message_content, item.content);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getModeleList();
    }

    @OnClick({R.id.iv_back, R.id.tv_add_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_add_message: {
                Intent intent = new Intent(this, UploadApplyActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void getMessageListSuccess(MessageModelBean messageModelBeans) {
        if (messageModelBeans.datum != null && messageModelBeans.datum.size() > 0) {
            adapter.setNewData(messageModelBeans.datum);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
