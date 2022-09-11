package com.acg.hotel.widget;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import com.acg.hotel.R;


/**
 * 自定义ActionBar
 * <p>
 * Author: en
 * Date: 2018-8-7 22:12:41
 */
public final class MyActionBar extends LinearLayout {

    private LinearLayout llActionbarRoot;//自定义ActionBar根节点
    private View vStatusBar;//状态栏位置
    private RelativeLayout rvLeft;//左边图标，文字容器容器
    private ImageView ivLeft;//左边图标
    private TextView tvLeft;//左边文字
    private TextView tvTitle;//中间标题
    private RelativeLayout rlRight;//右边图标，文字容器容器
    private ImageView ivRight;//右边图标
    private TextView tvRight;//右边文字

    public MyActionBar(Context context) {
        this(context, null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);//设置横向布局
        View contentView = inflate(getContext(), R.layout.widget_actionbar, this);
        //获取控件
        llActionbarRoot = contentView.findViewById(R.id.ll_actionbar_root);
        vStatusBar = contentView.findViewById(R.id.v_statusbar);
        rvLeft = contentView.findViewById(R.id.rl_actionbar_left);
        ivLeft = contentView.findViewById(R.id.iv_actionbar_left);
        tvLeft = contentView.findViewById(R.id.tv_actionbar_left);
        tvTitle = contentView.findViewById(R.id.tv_actionbar_title);
        rlRight = contentView.findViewById(R.id.rl_actionbar_right);
        tvRight = contentView.findViewById(R.id.tv_actionbar_right);
        ivRight = contentView.findViewById(R.id.iv_actionbar_right);
    }

    /**
     * 设置状态栏高度
     *
     * @param statusBarHeight 状态栏高度
     */
    public void setStatusBarHeight(int statusBarHeight) {
        ViewGroup.LayoutParams params = vStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        vStatusBar.setLayoutParams(params);
    }

    /**
     * 设置标题
     *
     * @param strTitle{String} 标题
     */
    public void setTitle(String strTitle) {
        //判断 不为空显示，为空不显示
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置透明度
     *
     * @param transAlpha{Integer} 0-255 之间
     */
    public void setTranslucent(int transAlpha) {
        //设置透明度
        llActionbarRoot.setBackgroundColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.colorPrimary), transAlpha));
        tvTitle.setAlpha(transAlpha);
        tvLeft.setAlpha(transAlpha);
        tvRight.setAlpha(transAlpha);
        ivLeft.setAlpha(transAlpha);
        ivRight.setAlpha(transAlpha);
    }


    /**
     * 设置数据
     *
     * @param strTitle   标题
     * @param resIdLeft  左边图标资源
     * @param resIdRight 右边图标
     * @param listener   点击事件监听
     */
    public void setData(String strTitle, int resIdLeft,int resIdRight,final ActionBarClickListener listener) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (resIdLeft == 0) {
            ivLeft.setVisibility(View.GONE);
        } else {
            ivLeft.setBackgroundResource(resIdLeft);
            ivLeft.setVisibility(View.VISIBLE);
        }

        if (resIdRight == 0) {
            ivRight.setVisibility(View.GONE);
        } else {
            ivRight.setBackgroundResource(resIdRight);
            ivRight.setVisibility(View.VISIBLE);
        }

        if (listener != null) {
            rvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeftClick();
                }
            });
            rlRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRightClick();
                }
            });
        }
    }
    /**
     * 设置数据
     *
     * @param strTitle   标题
     * @param resIdLeft  左边图标资源
     * @param strLeft    左边文字
     * @param resIdRight 右边图标
     * @param strRight   右边文字
     * @param listener   点击事件监听
     */
    public void setData(String strTitle, int resIdLeft, String strLeft, int resIdRight, String strRight, final ActionBarClickListener listener) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strLeft)) {
            tvLeft.setText(strLeft);
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strRight)) {
            tvRight.setText(strRight);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }

        if (resIdLeft == 0) {
            ivLeft.setVisibility(View.GONE);
        } else {
            ivLeft.setBackgroundResource(resIdLeft);
            ivLeft.setVisibility(View.VISIBLE);
        }

        if (resIdRight == 0) {
            ivRight.setVisibility(View.GONE);
        } else {
            ivRight.setBackgroundResource(resIdRight);
            ivRight.setVisibility(View.VISIBLE);
        }

        if (listener != null) {
            rvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeftClick();
                }
            });
            rlRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRightClick();
                }
            });
        }
    }

    public interface ActionBarClickListener {
        //左边点击
        void onLeftClick();
        //右边点击
        void onRightClick();
    }
}
