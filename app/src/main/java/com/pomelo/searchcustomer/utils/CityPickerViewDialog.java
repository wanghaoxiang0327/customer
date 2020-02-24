package com.pomelo.searchcustomer.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.view.BasePickerView;
import com.pomelo.searchcustomer.R;

import java.util.List;

public class CityPickerViewDialog<T> extends BasePickerView {
    private Context mContext;
    private setOnCitySelectListener onCitySelectListener;

    public CityPickerViewDialog(Context context, setOnCitySelectListener onCitySelectListener) {
        super(context);
        this.mContext = context;
        this.onCitySelectListener = onCitySelectListener;
    }

    private OptionsPickerView optionsPickerView(List<T> options1Items,
                                                List<List<T>> options2Items,
                                                List<List<List<T>>> options3Items) {
        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onCitySelectListener.onCitySelect(options1, options2, options3, v);
            }
        })
                //.setTitleText("城市选择")
                //.setDividerColor(getResources().getColor(R.color.title_name))
                .setContentTextSize(20)
                .setTextColorCenter(mContext.getResources().getColor(R.color.color_333333))//设置选中项的颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.color_4e96f3))//确定按钮文字颜色
                .setCancelColor(0XFF666666)//取消按钮文字颜色
                //.setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .build();
        optionsPickerView.setPicker(options1Items, options2Items, options3Items);
        return optionsPickerView;
    }

    public void show(List<T> options1Items) {//一级联动
        optionsPickerView(options1Items, null, null).show();
    }

    public void show(List<T> options1Items,
                     List<List<T>> options2Items) {//二级联动
        optionsPickerView(options1Items, options2Items, null).show();
    }

    public void show(List<T> options1Items,
                     List<List<T>> options2Items,
                     List<List<List<T>>> options3Items) {//三级联动
        optionsPickerView(options1Items, options2Items, options3Items).show();
    }

    public interface setOnCitySelectListener {
        void onCitySelect(int options1, int options2, int options3, View v);
    }


}
