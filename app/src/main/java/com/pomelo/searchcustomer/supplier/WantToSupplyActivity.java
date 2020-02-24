package com.pomelo.searchcustomer.supplier;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.adapter.ImagePickerAdapter;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.dialog.SelectDialog;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.pomelo.searchcustomer.weight.NoScrollRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class WantToSupplyActivity extends BaseMVPActivity<WantToSupplyPresenter> implements WantToSupplyView, ImagePickerAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.tv_info_category)
    TextView tvInfoCategory;
    @BindView(R.id.et_theme)
    EditText etTheme;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.recyclerView)
    NoScrollRecyclerView recyclerView;
    @BindView(R.id.et_content)
    EditText etContent;
    String title, content, mobile;
    OptionsPickerView categoryPickView;
    private List<String> categoryList = new ArrayList<>();
    private List<SupplierClassBean.SupplierClass> supplierClassList;
    private SupplierClassBean.SupplierClass selectSupplier;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;

    @Override

    protected WantToSupplyPresenter createPresenter() {
        return new WantToSupplyPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_want_to_supply;
    }

    @Override
    public void initView() {
        initWidget();
    }

    private void initWidget() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
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

    private void getCategory() {
        categoryPickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                selectSupplier = supplierClassList.get(options1);
                tvInfoCategory.setText(categoryList.get(options1));
            }
        }).setContentTextSize(20).setCancelColor(getResources().getColor(R.color.color_333333)).setSubmitColor(getResources().getColor(R.color.color_4e96f3)).build();
        categoryPickView.setPicker(categoryList);
        categoryPickView.show();
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getSupplierClass();
    }

    @OnClick({R.id.iv_back, R.id.tv_add_message, R.id.tv_info_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_add_message: {
                addSupplier();
            }
            break;
            case R.id.tv_info_category: {
                getCategory();
            }
            break;
        }
    }

    private void addSupplier() {
        title = etTheme.getText().toString().trim();
        content = etContent.getText().toString().trim();
        mobile = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showMessage(mContext, "请输入推广主题");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showMessage(mContext, "请输入联系方式");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showMessage(mContext, "请输入描述文字");
            return;
        }
        if (selImageList.size() <= 0) {
            ToastUtils.showMessage(mContext, "请上传图片");
            return;
        }
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < selImageList.size(); i++) {
            File file = new File(selImageList.get(i).path);
            bodyMap.put("file" + i + "\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        }
        mPresenter.addSupply(title, content, mobile, bodyMap);
    }

    @Override
    public void getSupplierClassSuccess(SupplierClassBean supplierClassBean) {
        if (supplierClassBean.supply_arr != null && supplierClassBean.supply_arr.size() > 0) {
            supplierClassList = supplierClassBean.supply_arr;
            for (SupplierClassBean.SupplierClass supplierClass : supplierClassBean.supply_arr) {
                categoryList.add(supplierClass.name);
            }
        }
    }

    @Override
    public void addSupplySuccess() {
        ToastUtils.showMessage(mContext, "发布成功");
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(mContext, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
}
