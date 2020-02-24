package com.pomelo.searchcustomer.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class FeedbackActivity extends BaseMVPActivity<FeedBackPresenter> implements FeedBackView {
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    String message, name, phonenumber;

    @Override
    protected FeedBackPresenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.iv_back, R.id.tv_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_upload: {
                submit();
            }
            break;
        }
    }


    private void submit() {
        message = etMessage.getText().toString().trim();
        name = etName.getText().toString().trim();
        phonenumber = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.showMessage(this, "请输入您宝贵的意见");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(this, "请输入您姓名");
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            ToastUtils.showMessage(this, "请输入您电话号码");
            return;
        }
        mPresenter.feedbackSubmit(name, message, phonenumber);
    }

    @Override
    public void subitSuccess() {
        finish();
    }
}
