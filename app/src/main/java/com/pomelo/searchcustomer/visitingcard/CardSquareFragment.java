package com.pomelo.searchcustomer.visitingcard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMvpFragment;
import com.pomelo.searchcustomer.bean.AreasBean;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.IndestryPickBean;
import com.pomelo.searchcustomer.bean.IndustryBean;
import com.pomelo.searchcustomer.utils.CityPickerViewDialog;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.utils.GsonUtil;
import com.pomelo.searchcustomer.utils.LogUtils;
import com.pomelo.searchcustomer.utils.NumberUtils;
import com.pomelo.searchcustomer.utils.ReadAssetsFileUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class CardSquareFragment extends BaseMvpFragment<CardSquareFragmentPresenter> implements CardSquareFragmentView {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_trade)
    TextView tvTrade;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private String keyword, code = "000000", cardHyId = "0";
    BaseQuickAdapter<CardSquareBean.Card, BaseViewHolder> adapter;
    //行业一级
    public List<IndustryBean> industryOne = new ArrayList<>();
    //行业二级
    public List<List<String>> industryTwo = new ArrayList<>();
    //行业三级
    private List<List<List<String>>> industryThree = new ArrayList<>();
    //行业三级code
    private List<List<List<String>>> industryThreeCode = new ArrayList<>();
    List<CardSquareBean.Card> cardList = new ArrayList<>();

    @Override
    protected CardSquareFragmentPresenter createPresenter() {
        return new CardSquareFragmentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_square;
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
                LogUtils.d("sss", "cardsquare=======" + page);
                mPresenter.cardSquare(page + "", code, cardHyId, keyword);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                refreshLayout.setNoMoreData(false);
                cardList.clear();
                mPresenter.cardSquare(page + "", code, cardHyId, keyword);
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<CardSquareBean.Card, BaseViewHolder>(R.layout.item_visting_square) {
            @Override
            protected void convert(BaseViewHolder helper, CardSquareBean.Card item) {
                helper.setText(R.id.tv_name, item.card_info.name);
                helper.setText(R.id.tv_post, item.card_info.position);
                helper.setText(R.id.tv_company_name, item.card_info.company_name);
                helper.setText(R.id.tv_address, item.card_info.company_address);
                helper.setText(R.id.tv_hangye, item.card_info.industry_category);
                ImageView ivHead = helper.getView(R.id.iv_head);
                GlideUtils.loadImage(mContext, item.image, ivHead);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CardSquareBean.Card card = (CardSquareBean.Card) adapter.getItem(position);
                if (card != null) {
                    Intent intent = new Intent(mContext, OtherPersonVistingCardActivity.class);
                    intent.putExtra("id", card.id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initData() {
        getCityData1();
        getIndustryData();
        mPresenter.cardSquare(page + "", code, cardHyId, keyword);
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

    @OnClick({R.id.tv_search, R.id.tv_trade, R.id.tv_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search: {
                page = 1;
                keyword = etSearch.getText().toString().trim();
                cardList.clear();
                mPresenter.cardSquare(page + "", code, cardHyId, keyword);
            }
            break;
            case R.id.tv_trade: {
                hideSoftKeyboard(getActivity());
                selectIndustry();
            }
            break;
            case R.id.tv_area: {
                hideSoftKeyboard(getActivity());
                selectCity();
            }
            break;
        }
    }

    private void selectCity() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(getContext(), new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx =
                        options1Items.get(options1).getPickerViewText() + " - " +
                                options2Items.get(options1).get(options2);
                code = options2ItemsCode.get(options1).get(options2);
                tvArea.setText(tx);
                page = 1;
                cardList.clear();
                mPresenter.cardSquare(page + "", code, cardHyId, keyword);
            }
        });
        dialog.show(options1Items, options2Items);
    }


    private void selectIndustry() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(getContext(), new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = industryThree.get(options1).get(options2).get(options3);
                cardHyId = industryThreeCode.get(options1).get(options2).get(options3);
                tvTrade.setText(tx);
                page = 1;
                cardList.clear();
                mPresenter.cardSquare(page + "", code, cardHyId, keyword);
            }
        });
        dialog.show(industryOne, industryTwo, industryThree);
    }

    @Override
    public void getCardSquareSuccess(CardSquareBean cardSquareBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        int allPage = NumberUtils.upCeil(cardSquareBean.count);
        if (cardSquareBean != null && cardSquareBean.list != null && cardSquareBean.list.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            cardList.addAll(cardSquareBean.list);
        }
        if (cardList.size() > 0) {
            adapter.setNewData(cardList);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }

    public void getIndustryData() {
        String json = ReadAssetsFileUtil.getJson(getContext(), "industry.json");
        IndestryPickBean bean = GsonUtil.getBean(json, IndestryPickBean.class);
        String jsonAreas = GsonUtil.getString(bean.data.industry);
        initJsonData(jsonAreas);
    }

    /**
     * 获取省市区数据后的逻辑处理
     *
     * @param cityJson
     */
    private void initJsonData(final String cityJson) {
        new Thread() {
            @Override
            public void run() {
                industryOne = GsonUtil.getBeanList(cityJson, new TypeToken<List<IndustryBean>>() {
                });
                /**
                 * 添加省份数据
                 *
                 * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
                 * PickerView会通过getPickerViewText方法获取字符串显示出来。
                 */
                for (int a = 0; a < industryOne.size(); a++) {//遍历省份
                    List<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                    List<List<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）
                    List<List<String>> province_AreaListCode = new ArrayList<>();//该省的所有地区列表（第三级）

                    for (int b = 0; b < industryOne.get(a).sub.size(); b++) {//遍历该省份的所有城市
                        String cityName = industryOne.get(a).sub.get(b).name;
                        cityList.add(cityName);//添加城市

                        List<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                        List<String> city_AreaListCode = new ArrayList<>();//该城市的所有地区列表
                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                        if (industryOne.get(a).sub.get(b).sub == null || industryOne.get(a).sub.get(b).sub.size() == 0) {
                            city_AreaList.add("");
                            city_AreaListCode.add("");
                        } else {
                            for (int c = 0; c < industryOne.get(a).sub.get(b).sub.size(); c++) {//遍历该省份的所有城市
                                String city_Area = industryOne.get(a).sub.get(b).sub.get(c).name;
                                String city_AreaCode = industryOne.get(a).sub.get(b).sub.get(c).id;
                                city_AreaList.add(city_Area);//添加城市
                                city_AreaListCode.add(city_AreaCode);//添加城市
                            }
                        }
                        province_AreaList.add(city_AreaList);//添加该省所有地区数据
                        province_AreaListCode.add(city_AreaListCode);//添加该省所有地区数据
                    }

                    /**
                     * 行业二级
                     */
                    industryTwo.add(cityList);
                    /**
                     *行业三级
                     */
                    industryThree.add(province_AreaList);
                    industryThreeCode.add(province_AreaListCode);
                }
                super.run();
            }
        }.start();
    }
}
