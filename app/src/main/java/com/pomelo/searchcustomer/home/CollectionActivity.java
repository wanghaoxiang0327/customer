package com.pomelo.searchcustomer.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.bean.LatLonBean;
import com.pomelo.searchcustomer.dialog.SelectDialog;
import com.pomelo.searchcustomer.utils.CityPickerViewDialog;
import com.pomelo.searchcustomer.utils.ContactUtils;
import com.pomelo.searchcustomer.utils.LogUtils;
import com.pomelo.searchcustomer.utils.PreferencesUtil;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class CollectionActivity extends BaseMVPActivity<CollectionPresenter> implements CollectionView, TencentLocationListener {
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_start_collection)
    TextView tvstartCollection;
    @BindView(R.id.tv_hascollection)
    TextView tvHascollection;
    @BindView(R.id.swh_status)
    Switch swhtatus;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.tv_count)
    TextView tvCount;
    String searchKey, name, address = "北京-北京", id = "-1", length = "0";
    String isMerchant = "0";
    int page = 1;
    private TencentMap tencentMap;
    BaseQuickAdapter<CustomerBean.Markers, BaseViewHolder> adapter;
    List<CustomerBean.Markers> markersLis = new ArrayList<>();
    int currentCount = 0;//
    int number = 0;//每次请求的个数
    int allCount = 0;//左上角显示已采集个数
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                currentCount++;
                Bundle bundle = msg.getData();
                CustomerBean.Markers markers = (CustomerBean.Markers) bundle.getSerializable("markers");
                addMarkerToMap(markers.title, markers.latitude, markers.longitude);
                tvHascollection.setText("已采集：" + (allCount));
                tvCount.setText("共" + (allCount) + "条消息");
                if (!"1".equals(isMerchant)) {
                    if (currentCount < 5) {
                        tvstartCollection.setEnabled(false);
                        tvstartCollection.setText("正在采集");
                    } else {
                        tvstartCollection.setEnabled(false);
                        tvstartCollection.setText("采集完成");
                    }
                } else {
                    if ((allCount) == number) {
                        tvstartCollection.setEnabled(true);
                        tvstartCollection.setText("开始采集");
                    } else {
                        tvstartCollection.setEnabled(false);
                        tvstartCollection.setText("正在采集");
                    }
                }
                LogUtils.e("current", currentCount + "-----" + allCount);
            }
        }
    };
    private LatLng latLng;

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {
        searchKey = getIntent().getStringExtra("searchKey");
        address = getIntent().getStringExtra("address");
        name = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(address)) {
            address = "北京-北京";
        }
        if (!TextUtils.isEmpty(name)) {
            tvTitle.setText(name + "采集");
        }
        tvSelectAddress.setText(address);
        etKeyword.setText(searchKey);
        isMerchant = PreferencesUtil.getString("is_merchant");
        if (!"1".equals(isMerchant)) {//非会员
            length = "12";
        }
        getCityData(mContext);
        initMap();
        initAdapter();
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

    @Override
    public void initData() {
        super.initData();
        mPresenter.getLatLon(address);
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

    private void initMap() {
        //获取TencentMap实例
        tencentMap = mapview.getMap();
        tencentMap.setInfoWindowAdapter(new TencentMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_map, null);
                TextView tvdes = view.findViewById(R.id.tv_des);
                tvdes.setText(marker.getTitle());
                return view;
            }

            @Override
            public void onInfoWindowDettached(Marker marker, View view) {

            }
        });
        swhtatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mapview.setVisibility(View.VISIBLE);
                    llList.setVisibility(View.GONE);
                    tvModel.setText("地图模式");
                } else {
                    llList.setVisibility(View.VISIBLE);
                    tvModel.setText("列表模式");
                    mapview.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapview.onResume();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.tv_start_collection, R.id.tv_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_start_collection: {
                number = 0;
                if (TextUtils.isEmpty(searchKey)) {
                    ToastUtils.showMessage(mContext, "请输入搜索关键字");
                    return;
                }
                mPresenter.collectionData(searchKey, address, id, page + "", length);
                page++;
            }
            break;
            case R.id.tv_select_address: {
                selectCity();
            }
            break;
        }
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }


    private void selectCity() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(this, new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                address =
                        options1Items.get(options1).getPickerViewText() + "-" +
                                options2Items.get(options1).get(options2);
                tvSelectAddress.setText(address);
                initData();
                page = 1;
            }
        });
        dialog.show(options1Items, options2Items);
    }


    @Override
    public void getCollectionData(CustomerBean customerBean) {
        currentCount = 0;
        isMerchant = "1";
        if (!"1".equals(isMerchant)) {//非会员
            if (customerBean != null && customerBean.markers != null && customerBean.markers.size() > 0) {
                markersLis.addAll(customerBean.markers);
                if (markersLis.size() > 5) {
                    markersLis = markersLis.subList(0, 5);
                }
            }
            if (markersLis.size() > 0) {
                addMarker(markersLis);
            }
        } else {//会员
            if (customerBean != null && customerBean.markers != null && customerBean.markers.size() > 0) {
                //计算出总页码数
                //表示还有更多数据 可以加载更多
                addMarker(customerBean.markers);
                markersLis.addAll(customerBean.markers);
            }
            number = customerBean.markers.size();
        }
        length += customerBean.markers.size();
        if (markersLis != null && markersLis.size() > 0) {
            adapter.setNewData(markersLis);
        } else {
            adapter.setEmptyView(getEmptyView());
        }

    }

    @Override
    public void getLatLonSuccess(LatLonBean latLonBean) {
        latLng = new LatLng(Double.parseDouble(latLonBean.lat), Double.parseDouble(latLonBean.lng));
        tencentMap.setCenter(latLng);
    }

    private void addMarker(List<CustomerBean.Markers> markers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentCount < markers.size()) {
                    try {
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("number", allCount);
                        bundle.putSerializable("markers", markers.get(currentCount));
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    allCount++;
                }
            }
        }).start();
    }

    private void addMarkerToMap(String title, double lat, double log) {
        LatLng latLng = new LatLng(lat, log);
        Marker marker = tencentMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker())
                .draggable(true));
        marker.showInfoWindow();
    }
}
