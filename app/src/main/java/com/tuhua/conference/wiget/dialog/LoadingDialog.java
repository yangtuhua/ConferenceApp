package com.tuhua.conference.wiget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.tuhua.conference.R;


/**
 * 加载动画对话框
 * <p>
 * Created by yangtufa on 2017/4/11.
 */

public class LoadingDialog extends Dialog {
    private final View dialogView;

    public LoadingDialog(@NonNull Context context, int layoutResId) {
        super(context, R.style.commonDialogNoBackground);

        dialogView = LayoutInflater.from(context).inflate(layoutResId, null);

        setContentView(dialogView);
        setCancelable(false);
    }

    /**
     * 向外提供获取view的方法
     *
     * @param viewId viewId
     * @return view
     */
    public View getView(int viewId) {
        return dialogView.findViewById(viewId);
    }
}
