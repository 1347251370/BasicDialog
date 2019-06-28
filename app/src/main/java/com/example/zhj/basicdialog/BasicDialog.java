package com.example.zhj.basicdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Zhaohj
 * 此dialog分为：title，content，leftLayout，rightLayout四个区域。每个区域分为：文言，文言字体颜色，背景颜色，文字大小四个属性。
 * 支持构造成单选dialog。
 */
public class BasicDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = BasicDialog.class.getSimpleName();
    private static final int INVALID_VALUE = -1;
    private static final int TYPE_SINGLE = 1;
    private static final int TYPE_DOUBLE = 2;
    private static int PX_HEIGHT = 191;
    private static int PX_WIDTH = 323;
    private int mListenerType = 0;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private TextView mLeftTextView;
    private TextView mRightTextView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private RelativeLayout mTitleLayout;
    private LinearLayout mContentLayout;
    private DoubleClickListener mDoubleClickListener;
    private SingleClickListener mSingleClickListener;
    private View mVerticalLine;
    private Activity mActivity;

    public BasicDialog(@NonNull Activity activity, String content, DoubleClickListener doubleClickListener) {
        this(activity, null, content, null, null,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                doubleClickListener, null);
    }


    /**
     * 无标题，单选dialog，不重定义按钮
     *
     * @param activity    活动
     * @param leftBtnName 左侧按钮文言
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName) {
        this(activity, null, content, leftBtnName);
    }

    /**
     * 无标题，单选dialog，重定义按钮
     *
     * @param activity     活动
     * @param rightBtnName 右侧按钮文言
     */
    public BasicDialog(@NonNull Activity activity, String content, String rightBtnName, SingleClickListener singleClickListener) {
        this(activity, null, content, rightBtnName, singleClickListener);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言
     *
     * @param activity            活动
     * @param rightBtnName        右侧按钮文言
     * @param singleClickListener 单个按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String rightBtnName, SingleClickListener singleClickListener) {
        this(activity, title, content, rightBtnName, INVALID_VALUE, singleClickListener);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言,不重定义按钮回调
     *
     * @param activity    活动
     * @param title       标题
     * @param content     主体内容
     * @param leftBtnName 左侧按钮文言
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName) {
        this(activity, title, content, leftBtnName, INVALID_VALUE, null);
    }

    /**
     * 无标题，单选dialog，重定义按钮文言和按钮背景色
     *
     * @param activity            活动
     * @param content             主体内容
     * @param leftBtnName         左侧按钮文言
     * @param leftBackgroundColor 左侧按钮背景色
     * @param singleClickListener 单个按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName, int leftBackgroundColor, SingleClickListener singleClickListener) {
        this(activity, null, content, leftBtnName, leftBackgroundColor, singleClickListener);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言和按钮背景色
     *
     * @param activity             活动
     * @param title                标题
     * @param content              主体内容
     * @param rightBtnName         右侧按钮文言
     * @param rightBackgroundColor 右侧按钮背景色
     * @param singleClickListener  单个按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String rightBtnName, int rightBackgroundColor, SingleClickListener singleClickListener) {
        this(activity, title, content, null, rightBtnName,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, rightBackgroundColor,
                null, singleClickListener);
    }

    /**
     * 无标题，重定义左右两个按钮名称的双选dialog，重定义按钮文言
     *
     * @param activity            活动
     * @param leftBtnName         左侧按钮文言
     * @param rightBtnName        右侧按钮文言
     * @param doubleClickListener 双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName, String rightBtnName, DoubleClickListener doubleClickListener) {
        this(activity, content, null, leftBtnName, rightBtnName, doubleClickListener);
    }

    /**
     * 有标题，重定义左右两个按钮名称的双选dialog，重定义按钮
     *
     * @param activity            活动
     * @param title               标题
     * @param leftBtnName         左侧按钮文言
     * @param rightBtnName        右侧按钮文言
     * @param doubleClickListener 双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String title, String leftBtnName, String rightBtnName, DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                doubleClickListener, null);
    }

    /**
     * 无标题 双选 除各模块文字颜色以外的元素全部重定义
     *
     * @param activity            活动
     * @param content             主体内容
     * @param leftBtnName         左侧按钮文言
     * @param rightBtnName        右侧按钮文言
     * @param contentTextColor    主体内容文字颜色
     * @param leftTextColor       左侧按钮文字颜色
     * @param rightTextColor      右侧按钮文字颜色
     * @param doubleClickListener 双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName, String rightBtnName,
                       int contentTextColor, int leftTextColor, int rightTextColor,
                       DoubleClickListener doubleClickListener) {
        this(activity, content, leftBtnName, rightBtnName, contentTextColor, leftTextColor, rightTextColor,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, doubleClickListener);
    }

    /**
     * 有标题 双选 除各模块背景色以外的元素全部重定义
     *
     * @param activity            活动
     * @param title               标题
     * @param content             主体内容
     * @param leftBtnName         左侧按钮文言
     * @param rightBtnName        右侧按钮文言
     * @param titleTextColor      标题文字颜色
     * @param contentTextColor    主体内容文字颜色
     * @param leftTextColor       左侧按钮文字颜色
     * @param rightTextColor      右侧按钮文字颜色
     * @param doubleClickListener 双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE,
                doubleClickListener);
    }

    /**
     * 无标题 双选 重定义双选按钮的背景色
     *
     * @param activity             活动
     * @param content              主体内容
     * @param leftBtnName          左侧按钮文言
     * @param rightBtnName         右侧按钮文言
     * @param leftBackgroundColor  左侧按钮背景色
     * @param rightBackgroundColor 右侧按钮背景色
     * @param doubleClickListener  双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName, String rightBtnName,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener) {
        this(activity, null, content, leftBtnName, rightBtnName,
                leftBackgroundColor, rightBackgroundColor, doubleClickListener);
    }

    /**
     * 有标题 双选 重定义双选按钮的背景色
     *
     * @param activity             活动
     * @param title                标题
     * @param content              主体内容
     * @param leftBtnName          左侧按钮文言
     * @param rightBtnName         右侧按钮文言
     * @param leftBackgroundColor  左侧按钮背景色
     * @param rightBackgroundColor 右侧按钮背景色
     * @param doubleClickListener  双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName,
                INVALID_VALUE, leftBackgroundColor, rightBackgroundColor, doubleClickListener);
    }

    /**
     * 有标题 双选 除文字颜色以外的元素全部重定义
     *
     * @param activity             活动
     * @param title                标题
     * @param content              主体内容
     * @param leftBtnName          左侧按钮文言
     * @param rightBtnName         右侧按钮文言
     * @param titleBackgroundColor 标题背景颜色
     * @param leftBackgroundColor  左侧按钮背景色
     * @param rightBackgroundColor 右侧按钮背景色
     * @param doubleClickListener  双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int titleBackgroundColor, int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName,
                INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, titleBackgroundColor,
                leftBackgroundColor, rightBackgroundColor, doubleClickListener);
    }

    /**
     * 有标题 双选 除内容区背景色以外的元素全部重定义
     *
     * @param activity             活动
     * @param title                标题
     * @param content              主体内容
     * @param leftBtnName          左侧按钮文言
     * @param rightBtnName         右侧按钮文言
     * @param titleTextColor       标题文字颜色
     * @param contentTextColor     主体内容文字颜色
     * @param leftTextColor        左侧按钮文字颜色
     * @param rightTextColor       右侧按钮文字颜色
     * @param titleBackgroundColor 标题背景颜色
     * @param leftBackgroundColor  左侧按钮背景色
     * @param rightBackgroundColor 右侧按钮背景色
     * @param doubleClickListener  双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor, int titleBackgroundColor,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                titleBackgroundColor, INVALID_VALUE, leftBackgroundColor, rightBackgroundColor,
                doubleClickListener, null);
    }

    /**
     * 无标题 双选 元素全部重定义
     *
     * @param activity               活动
     * @param content                主体内容
     * @param leftBtnName            左侧按钮文言
     * @param rightBtnName           右侧按钮文言
     * @param contentTextColor       主体内容文字颜色
     * @param leftTextColor          左侧按钮文字颜色
     * @param rightTextColor         右侧按钮文字颜色
     * @param contentBackgroundColor 主体内容背景颜色
     * @param leftBackgroundColor    左侧按钮背景色
     * @param rightBackgroundColor   右侧按钮背景色
     * @param doubleClickListener    双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String content, String leftBtnName, String rightBtnName,
                       int contentTextColor, int leftTextColor, int rightTextColor,
                       int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener) {
        this(activity, null, content, leftBtnName, rightBtnName, INVALID_VALUE, contentTextColor, leftTextColor, rightTextColor,
                INVALID_VALUE, contentBackgroundColor, leftBackgroundColor, rightBackgroundColor, doubleClickListener);
    }

    /**
     * 双选 元素全部重定义
     *
     * @param activity               活动
     * @param title                  标题
     * @param content                主体内容
     * @param leftBtnName            左侧按钮文言
     * @param rightBtnName           右侧按钮文言
     * @param titleTextColor         标题文字颜色
     * @param contentTextColor       主体内容文字颜色
     * @param leftTextColor          左侧按钮文字颜色
     * @param rightTextColor         右侧按钮文字颜色
     * @param titleBackgroundColor   标题背景颜色
     * @param contentBackgroundColor 主体内容背景颜色
     * @param leftBackgroundColor    左侧按钮背景色
     * @param rightBackgroundColor   右侧按钮背景色
     * @param doubleClickListener    双按钮点击回调
     */
    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       int titleBackgroundColor, int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener) {
        this(activity, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                titleBackgroundColor, contentBackgroundColor, leftBackgroundColor, rightBackgroundColor, doubleClickListener, null);
    }


    public BasicDialog(@NonNull Activity activity, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       int titleBackgroundColor, int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener, SingleClickListener singleClickListener) {
        super(activity, R.style.dialog);
        builder();

        mActivity = activity;
        if (TextUtils.isEmpty(title)) {
            mTitleLayout.setVisibility(View.GONE);
            mContentLayout.setGravity(Gravity.CENTER);
        } else {
            mTitleLayout.setVisibility(View.VISIBLE);
            mTitleTextView.setText(title);
        }
        if (TextUtils.isEmpty(content)) {
            Log.e(TAG, "dialog content is null");
            return;
        } else {
            mContentTextView.setText(content);
        }
        if (TextUtils.isEmpty(leftBtnName)) {
            mLeftLayout.setVisibility(View.GONE);
            mVerticalLine.setVisibility(View.GONE);
        } else {
            mLeftLayout.setVisibility(View.VISIBLE);
//            mVerticalLine.setVisibility(View.VISIBLE);
            mLeftTextView.setText(leftBtnName);
        }
        if (TextUtils.isEmpty(rightBtnName)) {
            mRightLayout.setVisibility(View.GONE);
            mVerticalLine.setVisibility(View.GONE);
        } else {
            mRightLayout.setVisibility(View.VISIBLE);
//            mVerticalLine.setVisibility(View.VISIBLE);
            mRightTextView.setText(rightBtnName);
        }
        if (titleTextColor != INVALID_VALUE && (!TextUtils.isEmpty(title))) {
            mTitleTextView.setTextColor(getContext().getResources().getColor(titleTextColor));
        }
        if (titleBackgroundColor != INVALID_VALUE && (!TextUtils.isEmpty(title))) {
            mTitleLayout.setBackgroundColor(titleBackgroundColor);
        }
        if (contentTextColor != INVALID_VALUE && (!TextUtils.isEmpty(content))) {
            mContentTextView.setTextColor(getContext().getResources().getColor(contentTextColor));
        }
        if (contentBackgroundColor != INVALID_VALUE && (!TextUtils.isEmpty(content))) {
            mContentLayout.setBackgroundColor(titleBackgroundColor);
        }
        if (leftTextColor != INVALID_VALUE && (!TextUtils.isEmpty(leftBtnName))) {
            mLeftTextView.setTextColor(getContext().getResources().getColor(leftTextColor));
        }
        if (leftBackgroundColor != INVALID_VALUE && (!TextUtils.isEmpty(leftBtnName))) {
            mLeftLayout.setBackgroundColor(leftBackgroundColor);
        }
        if (rightTextColor != INVALID_VALUE && (!TextUtils.isEmpty(rightBtnName))) {
            mRightTextView.setTextColor(getContext().getResources().getColor(rightTextColor));
        }
        if (rightBackgroundColor != INVALID_VALUE && (!TextUtils.isEmpty(rightBtnName))) {
            mRightLayout.setBackgroundColor(rightBackgroundColor);
        }
        if (doubleClickListener != null) {
            this.mDoubleClickListener = doubleClickListener;
            mListenerType = TYPE_DOUBLE;
        } else {
            mVerticalLine.setVisibility(View.GONE);
        }
        if (singleClickListener != null) {
            this.mSingleClickListener = singleClickListener;
            mListenerType = TYPE_SINGLE;
        }
    }

    private void builder() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_basic, null);
        setContentView(view);
        mTitleTextView = findViewById(R.id.title_textview);
        mTitleLayout = findViewById(R.id.title_layout);
        mContentTextView = findViewById(R.id.content_textview);
        mContentLayout = findViewById(R.id.content_layout);
        mLeftTextView = findViewById(R.id.left_btn_textview);
        mRightTextView = findViewById(R.id.right_btn_textview);
        mLeftLayout = findViewById(R.id.left_btn_layout);
        mRightLayout = findViewById(R.id.right_btn_layout);
        mVerticalLine = findViewById(R.id.vertical_line);
        mLeftLayout.setOnClickListener(this);
        mRightLayout.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mListenerType == TYPE_SINGLE && mSingleClickListener != null) {
            mSingleClickListener.clickSingleBtn();
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_btn_layout:
                dismiss();
                if (mListenerType == TYPE_SINGLE) {
                    mSingleClickListener.clickSingleBtn();
                } else if (mListenerType == TYPE_DOUBLE) {
                    mDoubleClickListener.clickLeftBtn();
                }
                break;
            case R.id.right_btn_layout:
                dismiss();
                if (mListenerType == TYPE_SINGLE) {
                    mSingleClickListener.clickSingleBtn();
                } else if (mListenerType == TYPE_DOUBLE) {
                    mDoubleClickListener.clickRightBtn();
                }
                break;
            default:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();

        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.height = UIUtil.px2dp(mActivity, PX_HEIGHT);
            layoutParams.width = UIUtil.px2dp(mActivity, PX_WIDTH);
            window.setAttributes(layoutParams);
        }
    }

    public interface DoubleClickListener {
        void clickLeftBtn();

        void clickRightBtn();
    }

    public interface SingleClickListener {
        void clickSingleBtn();
    }
}
