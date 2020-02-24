package com.pomelo.searchcustomer.basemvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.bean.AreasBean;
import com.pomelo.searchcustomer.bean.CityPickerBean;
import com.pomelo.searchcustomer.bean.IndustryBean;
import com.pomelo.searchcustomer.utils.GsonUtil;
import com.pomelo.searchcustomer.utils.PreferencesUtil;
import com.pomelo.searchcustomer.utils.ReadAssetsFileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * fragment的基类
 */

public abstract class BaseFragment extends ImmersionFragment {
    View view;
    Unbinder unbinder;
    public Context mContext;
    public final String TAG = getClass().getSimpleName();
    public boolean isLogin = false;
    //省市区数据
    public List<AreasBean> options1Items = new ArrayList<>();
    public List<List<String>> options2Items = new ArrayList<>();
    public List<List<String>> options2ItemsCode = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void initImmersionBar() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        }
        unbinder = ButterKnife.bind(this, view);
        isLogin = PreferencesUtil.getBoolean("isLogin");
        initView();
        initData();
        return view;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    public void getCityData() {
        String json = ReadAssetsFileUtil.getJson(getContext(), "newcity.json");
        CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
        String jsonAreas = GsonUtil.getString(bean.data.areas);
        initJsonData(jsonAreas);
    }

    public void getCityData1() {
        String json = ReadAssetsFileUtil.getJson(getContext(), "city.json");
        CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
        String jsonAreas = GsonUtil.getString(bean.data.areas);
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
                options1Items = GsonUtil.getBeanList(cityJson, new TypeToken<List<AreasBean>>() {
                });
                /**
                 * 添加省份数据
                 *
                 * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
                 * PickerView会通过getPickerViewText方法获取字符串显示出来。
                 */
                for (int a = 0; a < options1Items.size(); a++) {//遍历省份
                    List<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                    List<String> cityListCode = new ArrayList<>();//该省的城市列表（第二级）
                    List<List<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

                    for (int b = 0; b < options1Items.get(a).sub.size(); b++) {//遍历该省份的所有城市
                        String cityName = options1Items.get(a).sub.get(b).name;
                        int cityCode = options1Items.get(a).sub.get(b).code;
                        cityList.add(cityName);//添加城市
                        cityListCode.add(cityCode + "");//添加城市

                        List<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                        if (options1Items.get(a).sub.get(b).sub == null || options1Items.get(a).sub.get(b).sub.size() == 0) {
                            city_AreaList.add("");
                        } else {
                            for (int c = 0; c < options1Items.get(a).sub.get(b).sub.size(); c++) {//遍历该省份的所有城市
                                String city_Area = options1Items.get(a).sub.get(b).sub.get(c).name;
                                city_AreaList.add(city_Area);//添加城市
                            }
                        }
                        province_AreaList.add(city_AreaList);//添加该省所有地区数据
                    }

                    /**
                     * 添加城市数据
                     */
                    options2Items.add(cityList);
                    options2ItemsCode.add(cityListCode);
                    /**
                     * 添加地区数据
                     */
//                    options3Items.add(province_AreaList);
                }
                super.run();
            }
        }.start();
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public View getEmptyView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_empty, null);
        return view;
    }
}
