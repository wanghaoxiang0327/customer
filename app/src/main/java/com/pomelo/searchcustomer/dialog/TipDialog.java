package com.pomelo.searchcustomer.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipDialog extends AlertDialog {
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.dialog_content)
    TextView dialogContent;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;
    @BindView(R.id.confirm_btn)
    TextView confirmBtn;

    private View view;


    public TipDialog(@NonNull Context context) {
        super(context, R.style.common_custom_dialog);
        view = LayoutInflater.from(context).inflate(R.layout.common_tip_dialog, null);
        setView(view);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {

        confirmBtn.setOnClickListener(view -> {
            if (confirmListener != null) {
                confirmListener.onConfirm(this);
            } else {
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(view -> {
            if (cancelListener != null) {
                cancelListener.onCancle(this);
            } else {
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.8);
        getWindow().setAttributes(params);
    }

    public TipDialog setTitle(String title) {
        dialogTitle.setText(title);
        return this;
    }

    public TipDialog setContent(String content) {
        dialogContent.setText(content);
        return this;
    }

    public TipDialog setCancelVisible(int visible) {
        cancelBtn.setVisibility(visible);
        return this;
    }

    public TipDialog setCanaleText(String str) {
        cancelBtn.setText(str);
        return this;
    }

    public TipDialog setConfirmBtn(String string) {
        confirmBtn.setText(string);
        return this;
    }

    public interface OnConfirmListener {
        void onConfirm(TipDialog dialog);
    }

    public interface OnCancelListener {
        void onCancle(TipDialog dialog);
    }

    private OnConfirmListener confirmListener;
    private OnCancelListener cancelListener;


    public TipDialog setConfirmListener(OnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }


    public TipDialog setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

}
