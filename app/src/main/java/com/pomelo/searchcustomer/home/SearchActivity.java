package com.pomelo.searchcustomer.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.dialog.SelectDialog;
import com.pomelo.searchcustomer.mine.MessageModelActivity;
import com.pomelo.searchcustomer.utils.CityPickerViewDialog;
import com.pomelo.searchcustomer.utils.ContactUtils;
import com.pomelo.searchcustomer.utils.NumberUtils;
import com.pomelo.searchcustomer.utils.PreferencesUtil;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class SearchActivity extends BaseMVPActivity<SearchPresenter> implements SearchView {
    @BindView(R.id.tv_select_class)
    TextView tvSelectClass;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_result_count)
    TextView tvResultCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    OptionsPickerView categoryPickView;
    private List<String> categoryList = new ArrayList<>();
    String catrgory, place = "北京-北京", id = "-1", length = "0";
    int page = 1;
    String isMerchant = "0";
    BaseQuickAdapter<CustomerBean.Markers, BaseViewHolder> adapter;
    List<CustomerBean.Markers> markersLis = new ArrayList<>();

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        initAdapter();
        getCityData(this);
        isMerchant = PreferencesUtil.getString("is_merchant");
        if (!"1".equals(isMerchant)) {//非会员
            length = "12";
        }
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<CustomerBean.Markers, BaseViewHolder>(R.layout.item_collection) {
            @Override
            protected void convert(BaseViewHolder helper, CustomerBean.Markers item) {
                TextView addCustomer = helper.getView(R.id.tv_add_contact);
                helper.setText(R.id.tv_name, item.name);
                helper.setText(R.id.tv_address, item.address);
                helper.setText(R.id.tv_call, item.tel);
                addCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<String> names = new ArrayList<>();
                        names.add("创建新联系人");
                        names.add("添加到现有联系人");
                        ContactUtils.requestPermisson(mContext);
                        showDialog(new SelectDialog.SelectDialogListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0: {
                                        ContactUtils.addNewContact(mContext, item.name, item.tel);
                                    }
                                    break;
                                    case 1: {
                                        ContactUtils.saveExistContact(mContext, item.name, item.tel);
                                    }
                                    break;
                                }
                            }
                        }, names);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

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
    public void initData() {
        super.initData();
        mPresenter.getSearchType();
    }

    @OnClick({R.id.iv_back, R.id.tv_select_class, R.id.tv_select_address, R.id.tv_search, R.id.tv_send_message, R.id.tv_one_key_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_select_class: {
                getCategory();
            }
            break;
            case R.id.tv_select_address: {
                selectCity();
            }
            break;
            case R.id.tv_search: {
                if (TextUtils.isEmpty(catrgory)) {
                    ToastUtils.showMessage(mContext, "请选择分类");
                    return;
                }
                mPresenter.search(catrgory, place, id, page + "", length);
            }
            break;
            case R.id.tv_send_message: {
                Intent intent = new Intent(this, MessageModelActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_one_key_out: {
                Intent intent = new Intent(this, MyCollectionActivity.class);
                startActivity(intent);
            }
            break;
        }
    }


    private void selectCity() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(this, new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                place = options1Items.get(options1).getPickerViewText() + "-" +
                        options2Items.get(options1).get(options2);
                tvSelectAddress.setText(place);
            }
        });
        dialog.show(options1Items, options2Items);
    }

    private void getCategory() {
        categoryPickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                catrgory = categoryList.get(options1);
                tvSelectClass.setText(catrgory);
            }
        }).setContentTextSize(20).setCancelColor(getResources().getColor(R.color.color_333333)).setSubmitColor(getResources().getColor(R.color.color_4e96f3)).build();
        categoryPickView.setPicker(categoryList);
        categoryPickView.show();
    }

    @Override
    public void getSearchClassSuccess(List<String> list) {
        categoryList.clear();
        categoryList.addAll(list);
    }

    @Override
    public void getcollectSuccess(CustomerBean customerBean) {
        int allPage = NumberUtils.upCeil(customerBean.count);
        length = ((page - 1) * 50 + customerBean.markers.size()) + "";
        if (customerBean != null && customerBean.markers != null && customerBean.markers.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            markersLis.addAll(customerBean.markers);
        }
        if (markersLis.size() > 0) {
            adapter.setNewData(markersLis);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
        if (!"1".equals(isMerchant)) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (page < allPage) {
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }
}
