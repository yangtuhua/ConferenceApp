package com.tuhua.conference.wiget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tuhua.conference.R;


/**
 * 底部弹出的选择框
 * Created by yangtufa on 2017/9/17.
 */

public class BottomChoiceDialog {
    private final Dialog dialog;

    public BottomChoiceDialog(@NonNull Context context, View contentView) {
        dialog = new Dialog(context, R.style.bottomChoiceDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 布局参数
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.windowAnimations = R.style.bottomShowAnimStyle;
            window.setAttributes(params);
        }
        dialog.setContentView(contentView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    public BottomChoiceDialog show() {
        if (dialog != null) {
            dialog.show();
        }
        return this;
    }

    public void setContentView(View contentView) {
        if (dialog != null)
            dialog.setContentView(contentView);

    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
