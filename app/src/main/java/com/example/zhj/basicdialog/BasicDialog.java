package com.example.zhj.basicdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 此dialog分为：titile，content，leftLayout，rightLayout四个区域。每个区域分为：文言，文言字体颜色，背景颜色，文字大小四个属性。 可以根据需求组合构造风格的dialog。
 * @author zhj
 */
public class BasicDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "BasicDialog";
    private static final int TYPE_SINGLE = 1;
    private static final int TYPE_DOUBLE = 2;
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
    private Activity mActivity;
    private View mVerticalLine;
    private int mListenerType = 0;

    public BasicDialog(@NonNull Context context, String content, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, null, null, null, null,
                -1, -1, -1, -1,
                -1, -1, -1, -1,
                doubleClickListener, null, activity);
    }


    /**
     * 无标题，单选dialog，不重定义按钮
     *
     * @param context
     * @param leftBtnName
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, Activity activity) {
        this(context, null, content, leftBtnName, activity);
    }

    /**
     * 无标题，单选dialog，重定义按钮
     *
     * @param context
     * @param leftBtnName
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, SingleClickListener singleClickListener, Activity activity) {
        this(context, null, content, leftBtnName, singleClickListener, activity);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言
     *
     * @param context
     * @param leftBtnName
     * @param singleClickListener
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, SingleClickListener singleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, -1, singleClickListener, activity);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言,不重定义按钮回调
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, Activity activity) {
        this(context, title, content, leftBtnName, -1, null, activity);
    }

    /**
     * 无标题，单选dialog，重定义按钮文言和按钮背景色
     *
     * @param context
     * @param content
     * @param leftBtnName
     * @param leftBackgroundColor
     * @param singleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, int leftBackgroundColor, SingleClickListener singleClickListener, Activity activity) {
        this(context, null, content, leftBtnName, leftBackgroundColor, singleClickListener, activity);
    }

    /**
     * 有标题，单选dialog，重定义按钮文言和按钮背景色
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param leftBackgroundColor
     * @param singleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, int leftBackgroundColor, SingleClickListener singleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, null,
                -1, -1, -1, -1,
                -1, -1, leftBackgroundColor, -1,
                null, singleClickListener, activity);
    }

    /**
     * 无标题，重定义左右两个按钮名称的双选dialog，重定义按钮文言
     *
     * @param context
     * @param leftBtnName
     * @param rightBtnName
     * @param doubleClickListener
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, String rightBtnName, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, content, null, leftBtnName, rightBtnName, doubleClickListener, activity);
    }

    /**
     * 有标题，重定义左右两个按钮名称的双选dialog，重定义按钮
     *
     * @param context
     * @param title
     * @param leftBtnName
     * @param rightBtnName
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String title, String leftBtnName, String rightBtnName, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName,
                -1, -1, -1, -1,
                -1, -1, -1, -1,
                doubleClickListener, null, activity);
    }

    /**
     * 无标题 双选 除各模块文字颜色以外的元素全部重定义
     *
     * @param context
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param titleTextColor
     * @param contentTextColor
     * @param leftTextColor
     * @param rightTextColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       DoubleClickListener doubleClickListener, Activity activity) {
        this(context, content, leftBtnName, rightBtnName, contentTextColor, leftTextColor, rightTextColor,
                -1, -1, -1, doubleClickListener, activity);
    }

    /**
     * 有标题 双选 除各模块背景色以外的元素全部重定义
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param titleTextColor
     * @param contentTextColor
     * @param leftTextColor
     * @param rightTextColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                -1, -1, -1, -1,
                doubleClickListener, activity);
    }

    /**
     * 无标题 双选 重定义双选按钮的背景色
     *
     * @param context
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, String rightBtnName,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, null, content, leftBtnName, rightBtnName,
                leftBackgroundColor, rightBackgroundColor, doubleClickListener, activity);
    }

    /**
     * 有标题 双选 重定义双选按钮的背景色
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName,
                -1, leftBackgroundColor, rightBackgroundColor, doubleClickListener, activity);
    }

    /**
     * 有标题 双选 除文字颜色以外的元素全部重定义
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param titleBackgroundColor
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int titleBackgroundColor, int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName,
                -1, -1, -1, -1, titleBackgroundColor,
                leftBackgroundColor, rightBackgroundColor, doubleClickListener, activity);
    }

    /**
     * 有标题 双选 除内容区背景色以外的元素全部重定义
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param titleTextColor
     * @param contentTextColor
     * @param leftTextColor
     * @param rightTextColor
     * @param titleBackgroundColor
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor, int titleBackgroundColor,
                       int leftBackgroundColor, int rightBackgroundColor, DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                titleBackgroundColor, -1, leftBackgroundColor, rightBackgroundColor,
                doubleClickListener, null, activity);
    }

    /**
     * 无标题 双选 元素全部重定义
     *
     * @param context
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param contentTextColor
     * @param leftTextColor
     * @param rightTextColor
     * @param contentBackgroundColor
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String content, String leftBtnName, String rightBtnName,
                       int contentTextColor, int leftTextColor, int rightTextColor,
                       int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener, Activity activity) {
        this(context, null, content, leftBtnName, rightBtnName, -1, contentTextColor, leftTextColor, rightTextColor,
                -1, contentBackgroundColor, leftBackgroundColor, rightBackgroundColor, doubleClickListener, activity);
    }

    /**
     * 双选 元素全部重定义
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @param titleTextColor
     * @param contentTextColor
     * @param leftTextColor
     * @param rightTextColor
     * @param titleBackgroundColor
     * @param contentBackgroundColor
     * @param leftBackgroundColor
     * @param rightBackgroundColor
     * @param doubleClickListener
     * @param activity
     */
    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       int titleBackgroundColor, int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener, Activity activity) {
        this(context, title, content, leftBtnName, rightBtnName, titleTextColor, contentTextColor, leftTextColor, rightTextColor,
                titleBackgroundColor, contentBackgroundColor, leftBackgroundColor, rightBackgroundColor, doubleClickListener, null, activity);
    }


    public BasicDialog(@NonNull Context context, String title, String content, String leftBtnName, String rightBtnName,
                       int titleTextColor, int contentTextColor, int leftTextColor, int rightTextColor,
                       int titleBackgroundColor, int contentBackgroundColor, int leftBackgroundColor, int rightBackgroundColor,
                       DoubleClickListener doubleClickListener, SingleClickListener singleClickLiatener, Activity activity) {
        super(context, R.style.Dialog);
        builder();
        if (activity != null) {
            mActivity = activity;
        }

        if (TextUtils.isEmpty(title)) {
            mTitleLayout.setVisibility(View.GONE);
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
            mVerticalLine.setVisibility(View.VISIBLE);
            mLeftTextView.setText(leftBtnName);
        }
        if (TextUtils.isEmpty(rightBtnName)) {
            mRightLayout.setVisibility(View.GONE);
            mVerticalLine.setVisibility(View.GONE);
        } else {
            mRightLayout.setVisibility(View.VISIBLE);
            mVerticalLine.setVisibility(View.VISIBLE);
            mRightTextView.setText(rightBtnName);
        }
        if (titleTextColor != -1 && (!TextUtils.isEmpty(title))) {
            mTitleTextView.setTextColor(titleTextColor);
        }
        if (titleBackgroundColor != -1 && (!TextUtils.isEmpty(title))) {
            mTitleLayout.setBackgroundColor(titleBackgroundColor);
        }
        if (contentTextColor != -1 && (!TextUtils.isEmpty(content))) {
            mContentTextView.setTextColor(contentTextColor);
        }
        if (contentBackgroundColor != -1 && (!TextUtils.isEmpty(content))) {
            mContentLayout.setBackgroundColor(titleBackgroundColor);
        }
        if (leftTextColor != -1 && (!TextUtils.isEmpty(leftBtnName))) {
            mLeftTextView.setTextColor(leftTextColor);
        }
        if (leftBackgroundColor != -1 && (!TextUtils.isEmpty(leftBtnName))) {
            mLeftLayout.setBackgroundColor(leftBackgroundColor);
        }
        if (rightTextColor != -1 && (!TextUtils.isEmpty(rightBtnName))) {
            mRightTextView.setTextColor(rightTextColor);
        }
        if (rightBackgroundColor != -1 && (!TextUtils.isEmpty(rightBtnName))) {
            mRightLayout.setBackgroundColor(rightBackgroundColor);
        }
        if (doubleClickListener != null) {
            this.mDoubleClickListener = doubleClickListener;
            mListenerType = TYPE_DOUBLE;
        }
        if (singleClickLiatener != null) {
            this.mSingleClickListener = singleClickLiatener;
            mListenerType = TYPE_SINGLE;
        }
    }

    private void builder() {
        //加载布局
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.basic_dialog, null);
        setContentView(view);
        //初始化控件
        mTitleTextView = (TextView) findViewById(R.id.title_textview);
        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        mContentTextView = (TextView) findViewById(R.id.content_textview);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mLeftTextView = (TextView) findViewById(R.id.cancel_textview);
        mRightTextView = (TextView) findViewById(R.id.ensure_textview);
        mLeftLayout = (LinearLayout) findViewById(R.id.cancel_layout);
        mRightLayout = (LinearLayout) findViewById(R.id.ensure_layout);
        mVerticalLine = findViewById(R.id.vertical_line);
        mLeftLayout.setOnClickListener(this);
        mRightLayout.setOnClickListener(this);
        setCancelable(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mListenerType == TYPE_SINGLE && mSingleClickListener != null) {
            mSingleClickListener.clickSingleBtn();
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_layout:
                dismiss();
                if (mListenerType == TYPE_SINGLE) {
                    mSingleClickListener.clickSingleBtn();
                } else if (mListenerType == TYPE_DOUBLE) {
                    mDoubleClickListener.clickLeftBtn();
                }
                break;
            case R.id.ensure_layout:
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
        WindowManager windowManager = mActivity.getWindowManager();
        // 获取屏幕宽、高度
        Display display = windowManager.getDefaultDisplay();
        if (window != null) {
            // 获取对话框当前的参数值
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            // 高度设置为屏幕的0.25，根据实际情况调整
            layoutParams.height = (int) (display.getHeight() * 0.25);
            // 宽度设置为屏幕的0.8，根据实际情况调整
            layoutParams.width = (int) (display.getWidth() * 0.8);
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
