package com.tuhua.conference.base.viewholder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 项目中通用ViewHolder,在编写项目代码时不用每次编写一个viewHolder
 * Created by yangtufa on 2017/3/7.
 */

public class BaseViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    public BaseViewHolder(Context context, int layoutId) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        //setTag
        mConvertView.setTag(this);
    }

    /**
     * 向外提供一個方法獲取contentView
     *
     * @return contentView
     */
    public View getContentView() {
        return mConvertView;
    }

    /**
     * 根据viewId获取view对象
     *
     * @param viewId viewId
     * @param <T>
     * @return view实例
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
