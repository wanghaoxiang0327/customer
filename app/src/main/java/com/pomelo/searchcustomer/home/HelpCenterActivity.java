package com.pomelo.searchcustomer.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.HelpCenterBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class HelpCenterActivity extends BaseMVPActivity<HelpCenterPresenter> implements HelpCenterView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BaseQuickAdapter<HelpCenterBean, BaseViewHolder> adapter;

    @Override
    protected HelpCenterPresenter createPresenter() {
        return new HelpCenterPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initView() {
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<HelpCenterBean, BaseViewHolder>(R.layout.item_help_center) {
            @Override
            protected void convert(BaseViewHolder helper, HelpCenterBean item) {
                helper.setText(R.id.tv_name, (helper.getPosition() + 1) + item.title);
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HelpCenterBean helpCenterBean = (HelpCenterBean) adapter.getItem(position);
                if (helpCenterBean != null) {
                    Intent intent = new Intent(mContext, HelpDetailActivity.class);
                    intent.putExtra("helpcenter", helpCenterBean);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter.getHelpCenterData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getHelpcenterSuccess(List<HelpCenterBean> helpCenterBeans) {
        if (helpCenterBeans != null && helpCenterBeans.size() > 0) {
            adapter.setNewData(helpCenterBeans);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
