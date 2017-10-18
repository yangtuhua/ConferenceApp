package com.tuhua.conference.base.activity;


import com.tuhua.conference.R;

import java.io.Serializable;

/**
 * 转场动画枚举
 * <p>
 * Created by Yangtuhua on 2017/3/29.
 */
public enum ActivityTransition implements Serializable {
    RIGHT_TO_LEFT(R.anim.in_from_right, R.anim.out_to_left), //从右到左
    LEFT_TO_RIGHT(R.anim.in_from_left, R.anim.out_to_right),
    DOWN_TO_UP(R.anim.pop_in_bottom, R.anim.pop_exit_bottom), //从底部到上面
    UP_TO_DOWN(0, R.anim.pop_exit_bottom),
    ALPHA_IN(R.anim.alpha_in, R.anim.alpha_out),
    AlPHA_OUT(0, R.anim.alpha_out);//逐渐消失

    public static final String TRANSITION_ANIMATION = "transition_animation";

    public int inAnimId;
    public int outAnimId;

    ActivityTransition(int inAnimId, int outAnimId) {
        this.inAnimId = inAnimId;
        this.outAnimId = outAnimId;
    }

    /**
     * 当使用者不设置转场动画时,默认为此动画
     *
     * @return 转场动画
     */
    public static ActivityTransition getDefaultInTransition() {
        return RIGHT_TO_LEFT;
    }

    public static ActivityTransition getDefaultOutTransition() {
        return LEFT_TO_RIGHT;
    }
}
