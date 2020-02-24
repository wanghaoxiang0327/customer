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

public class UploadApplyActivity extends BaseMVPActivity<UploadApplyPresenter> implements UploadApplyView {
    @BindView(R.id.et_message)
    EditText etMessage;
    private String content;

    @Override
    protected UploadApplyPresenter createPresenter() {
        return new UploadApplyPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_message_model;
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
                content = etMessage.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showMessage(mContext, "请输入消息内容");
                    return;
                }
                mPresenter.addMessageModel(content);
            }
            break;
        }
    }

    @Override
    public void addModelSuccess(String message) {
        ToastUtils.showMessage(mContext, message);
        finish();
    }
}
