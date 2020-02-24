package com.pomelo.searchcustomer.visitingcard;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.IndestryPickBean;
import com.pomelo.searchcustomer.bean.IndustryBean;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.utils.CityPickerViewDialog;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.utils.GsonUtil;
import com.pomelo.searchcustomer.utils.ReadAssetsFileUtil;
import com.pomelo.searchcustomer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-14.
 */

public class EditVisitingCardActivity extends BaseMVPActivity<EditVisitinCardPresenter> implements EditVisitingCardView {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_one_key_get)
    TextView tvOneKeyGet;
    @BindView(R.id.tv_company_name)
    EditText tvCompanyName;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_post)
    EditText tvPost;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.tv_destai_address)
    EditText tvDestaiAddress;
    @BindView(R.id.tv_more_info)
    TextView tvMoreInfo;
    @BindView(R.id.tv_email)
    EditText tvEmail;
    @BindView(R.id.tv_department)
    EditText tvDepartment;
    @BindView(R.id.tv_tell)
    EditText tvTell;
    @BindView(R.id.tv_wechat)
    EditText tvWechat;
    @BindView(R.id.tv_website)
    EditText tvWebsite;
    @BindView(R.id.ll_more_info)
    LinearLayout llMoreInfo;
    @BindView(R.id.swh_status)
    Switch swhStatus;
    String name, mobile, companyname, industry, post, comaddress, detailaddress, email, department, telwork, wechat, websit, hangyeID, cardplace, cardplacecode, cardstatus = "2";
    //行业一级
    public List<IndustryBean> industryOne = new ArrayList<>();
    //行业二级
    public List<List<String>> industryTwo = new ArrayList<>();
    //行业三级
    private List<List<List<String>>> industryThree = new ArrayList<>();
    //行业三级code
    private List<List<List<String>>> industryThreeCode = new ArrayList<>();

    @Override
    protected EditVisitinCardPresenter createPresenter() {
        return new EditVisitinCardPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_card;
    }

    @Override
    public void initView() {
        swhStatus.setChecked(true);
        swhStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cardstatus = "1";
                } else {
                    cardstatus = "0";
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        getCityData(mContext);
        getIndustryData();
        mPresenter.getMyVisitingCard();
    }

    @Override
    public void getMyVisitingCardInfo(MyVisitinCardBean myVisitinCardBean) {
        if (myVisitinCardBean != null) {
            tvName.setText(myVisitinCardBean.name);
            tvPhone.setText(myVisitinCardBean.mobile);
            tvCompanyName.setText(myVisitinCardBean.company_name);
            tvIndustry.setText(myVisitinCardBean.industry_category);
            tvPost.setText(myVisitinCardBean.position);
            tvCompanyAddress.setText(myVisitinCardBean.company_address);
            tvDestaiAddress.setText(myVisitinCardBean.company_address);
            tvEmail.setText(myVisitinCardBean.email);
            tvDepartment.setText(myVisitinCardBean.department);
            tvTell.setText(myVisitinCardBean.tel_work);
            tvWechat.setText(myVisitinCardBean.wechat);
            tvWebsite.setText(myVisitinCardBean.company_url);
            GlideUtils.loadImage(mContext, myVisitinCardBean.image, ivHead);
        }
    }

    @Override
    public void editSuccess() {
        ToastUtils.showMessage(mContext, "修改成功");
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_save, R.id.tv_more_info, R.id.tv_industry, R.id.tv_company_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_save: {
                if (isCheck()) {
                    mPresenter.editCard(name, mobile, companyname, industry, post, comaddress, email, department, telwork, wechat, websit, cardstatus, hangyeID, cardplacecode, cardplace);
                }
            }
            break;
            case R.id.tv_more_info: {
                llMoreInfo.setVisibility(View.VISIBLE);
                tvMoreInfo.setVisibility(View.GONE);
            }
            break;
            case R.id.tv_industry: {
                hideSoftKeyboard(this);
                selectIndustry();
            }
            break;
            case R.id.tv_company_address: {
                hideSoftKeyboard(this);
                selectCity();
            }
            break;
        }
    }

    private boolean isCheck() {
        name = tvName.getText().toString().trim();
        mobile = tvPhone.getText().toString().trim();
        companyname = tvCompanyName.getText().toString().trim();
        industry = tvIndustry.getText().toString().trim();
        post = tvPost.getText().toString().trim();
        comaddress = tvCompanyAddress.getText().toString().trim();
        detailaddress = tvDestaiAddress.getText().toString().trim();
        email = tvEmail.getText().toString().trim();
        department = tvDepartment.getText().toString().trim();
        telwork = tvTell.getText().toString().trim();
        wechat = tvWechat.getText().toString().trim();
        websit = tvWebsite.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(mContext, "请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showMessage(mContext, "请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(companyname)) {
            ToastUtils.showMessage(mContext, "请输入公司名称");
            return false;
        }
        if (TextUtils.isEmpty(industry)) {
            ToastUtils.showMessage(mContext, "请输入行业分类");
            return false;
        }
        if (TextUtils.isEmpty(post)) {
            ToastUtils.showMessage(mContext, "请输入职位头衔");
            return false;
        }
        if (TextUtils.isEmpty(comaddress)) {
            ToastUtils.showMessage(mContext, "请输入公司地址");
            return false;
        }
        if (TextUtils.isEmpty(detailaddress)) {
            ToastUtils.showMessage(mContext, "请输入详细地址");
            return false;
        }
        return true;
    }

    private void selectCity() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(this, new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx =
                        options1Items.get(options1).getPickerViewText() + " - " +
                                options2Items.get(options1).get(options2);
                cardplacecode = options2ItemsCode.get(options1).get(options2);
                cardplace = tx;
                tvCompanyAddress.setText(tx);
            }
        });
        dialog.show(options1Items, options2Items);
    }


    private void selectIndustry() {
        CityPickerViewDialog dialog = new CityPickerViewDialog(this, new CityPickerViewDialog.setOnCitySelectListener() {
            @Override
            public void onCitySelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = industryThree.get(options1).get(options2).get(options3);
                hangyeID = industryThreeCode.get(options1).get(options2).get(options3);
                tvIndustry.setText(tx);
            }
        });
        dialog.show(industryOne, industryTwo, industryThree);
    }

    public void getIndustryData() {
        String json = ReadAssetsFileUtil.getJson(this, "industry.json");
        IndestryPickBean bean = GsonUtil.getBean(json, IndestryPickBean.class);
        String jsonAreas = GsonUtil.getString(bean.data.industry);
        initIndustryJsonData(jsonAreas);
    }


    /**
     * 获取省市区数据后的逻辑处理
     *
     * @param cityJson
     */
    private void initIndustryJsonData(final String cityJson) {
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
