package com.pomelo.searchcustomer.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanghaoxiang on 2020-02-06.
 */

public class ComplaintDialog extends AlertDialog {
    @BindView(R.id.tv_cancle)
    TextView cancelBtn;
    @BindView(R.id.tv_confirm)
    TextView confirmBtn;
    @BindView(R.id.et_content)
    EditText etContent;
    View view;
    String content;
    private Context context;

    public ComplaintDialog(@NonNull Context context) {
        super(context, R.style.common_custom_dialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_complaint, null);
        setView(view);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        confirmBtn.setOnClickListener(view -> {
            content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.showMessage(context, "请输入投诉原因，如身份造假，发布虚拟信息");
                return;
            }
            if (confirmListener != null) {
                confirmListener.onConfirm(content, this);
            } else {
                dismiss();
            }
        });
    }

    public interface OnConfirmListener {
        void onConfirm(String content, ComplaintDialog dialog);
    }

    private OnConfirmListener confirmListener;

    public ComplaintDialog setConfirmListener(OnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }


}
