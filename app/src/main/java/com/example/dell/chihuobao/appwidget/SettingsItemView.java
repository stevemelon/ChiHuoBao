package com.example.dell.chihuobao.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.chihuobao.R;


/**
 * Created by fanghao on 2016/2/29.
 */
public class SettingsItemView extends RelativeLayout {
    private ImageView mLeftImageView;
    private TextView mTextView;
    private ImageView mRightImageView;

    // 布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftParams, mTextParams, mRightParams;

    // 左的属性值，即我们在atts.xml文件中定义的属性
    private Drawable mLeftBackground;

    // 右的属性值，即我们在atts.xml文件中定义的属性
    private Drawable mRightBackground;

    // 标题的属性值，即我们在atts.xml文件中定义的属性
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;

    // 映射传入的接口对象
    private settingClickListener mListener;

    public SettingsItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public SettingsItemView(Context context) {
        super(context);
    }

    public SettingsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 设置topbar的背景
        setBackgroundColor(0xffffffff);
        // 通过这个方法，将你在atts.xml中定义的declare-styleable
        // 的所有属性的值存储到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SettingsItemView);
        // 从TypedArray中取出对应的值来为要设置的属性赋值
        mLeftBackground = ta.getDrawable(
                R.styleable.SettingsItemView_leftBackground);

        mRightBackground = ta.getDrawable(
                R.styleable.SettingsItemView_rightBackground);

        mTitleTextSize = ta.getDimension(
                R.styleable.SettingsItemView_settingTitleTextSize, 20);
        mTitleTextColor = ta.getColor(
                R.styleable.SettingsItemView_settingTitleTextColor, 0);
        mTitle = ta.getString(R.styleable.SettingsItemView_settingTitle);

        // 获取完TypedArray的值后，一般要调用
        // recyle方法来避免重新创建的时候的错误
        ta.recycle();

        mLeftImageView = new ImageView(context);
        mRightImageView = new ImageView(context);
        mTextView = new TextView(context);

        // 为创建的组件元素赋值
        // 值就来源于我们在引用的xml文件中给对应属性的赋值

        mLeftImageView.setImageDrawable(mLeftBackground);
        mLeftImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mRightImageView.setImageDrawable(mRightBackground);
        mLeftImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mTextView.setText(mTitle);
        mTextView.setTextColor(mTitleTextColor);
        mTextView.setTextSize(mTitleTextSize);
        mTextView.setGravity(Gravity.CENTER);

        // 为组件元素设置相应的布局元素
        mLeftParams = new LayoutParams(
                60,
                60);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        mLeftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        // 添加到ViewGroup
        addView(mLeftImageView, mLeftParams);

        mRightParams = new LayoutParams(
                150,
                50);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        mRightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mRightImageView, mRightParams);

        mTextParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mTextParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        addView(mTextView, mTextParams);

        // 按钮的点击事件，不需要具体的实现，
        // 只需调用接口的方法，回调的时候，会有具体的实现
        mRightImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });


    }
    // 暴露一个方法给调用者来注册接口回调
    // 通过接口来获得回调者对接口方法的实现
    public void setOnSettingClickListener(settingClickListener mListener) {
        this.mListener = mListener;
    }
    // 接口对象，实现回调机制，在回调方法中
    // 通过映射的接口对象调用接口中的方法
    // 而不用去考虑如何实现，具体的实现由调用者去创建
    public interface settingClickListener {

        // 右点击事件
        void rightClick();
    }
}
