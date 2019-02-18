package com.bamboy.bamtoast.bamtoast.etoast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bamboy.bamtoast.bamtoast.btoast.BToast;

import java.util.Timer;
import java.util.TimerTask;

import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_ERROR;
import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_NONE;
import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_SUCCEED;

/**
 * Author: Blincheng.
 * Date: 2017/6/30.
 * Description:EToast2.0
 */

public class EToast {

    private WindowManager manger;
    private Long time = 2000L;
    private static View contentView;
    private WindowManager.LayoutParams params;
    private static Timer timer;
    private BToast toast;
    private static Toast oldToast;
    private static Handler handler;

    public EToast(Context context, CharSequence text, CharSequence subText, int time,
                  int iconType) {
        manger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        toast = BToast.getToast(context, text, subText, time, iconType);
        contentView = toast.getView();

        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = -1;
        params.setTitle("EToast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        params.y = 200;
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    EToast.this.cancel();
                }
            };
        }
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context  上下文
     * @param stringId 显示的文本的Id
     */
    public static EToast makeText(Context context, int stringId) {
        return new EToast(context, context.getString(stringId), "", 0, ICONTYPE_NONE);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    public static EToast makeText(Context context, CharSequence... text) {
        String text_0 = "";
        String text_1 = "";
        if (text != null && text.length > 0) {
            if (text.length >= 1)
                text_0 = text[0].toString();

            if (text.length >= 2)
                text_1 = text[1].toString();
        }
        return new EToast(context, text_0, text_1, 0, ICONTYPE_NONE);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param stringId  显示的文本的Id
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, int stringId, boolean isSucceed) {
        return new EToast(context, context.getString(stringId), "", 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, CharSequence text,
                                  boolean isSucceed) {
        return new EToast(context, text, "", 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param subText   显示的副标题
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, CharSequence text, CharSequence subText,
                                  boolean isSucceed) {
        return new EToast(context, text, subText, 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context  上下文
     * @param stringId 显示的文本的Id
     * @param time     持续的时间
     */
    public static EToast makeText(Context context, int stringId, int time) {
        return new EToast(context, context.getString(stringId), "", time, ICONTYPE_NONE);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    持续的时间
     */
    public static EToast makeText(Context context, CharSequence text, int time) {
        return new EToast(context, text, "", time, ICONTYPE_NONE);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param subText 显示的副标题
     * @param time    持续的时间
     */
    public static EToast makeText(Context context, CharSequence text, CharSequence subText, int time) {
        return new EToast(context, text, subText, time, ICONTYPE_NONE);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param stringId  显示的文本的Id
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, int stringId, int time,
                                  boolean isSucceed) {
        return new EToast(context, context.getString(stringId), "", time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, CharSequence text, int time,
                                  boolean isSucceed) {
        return new EToast(context, text, "", time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param subText   显示的副标题
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static EToast makeText(Context context, CharSequence text, CharSequence subText, int time,
                                  boolean isSucceed) {
        return new EToast(context, text, subText, time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR);
    }

    public void show() {
        oldToast = toast;
        manger.addView(contentView, params);
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    EToast.this.cancel();
                }
            };
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                }
            }
        }, time);
    }

    public void cancel() {
        try {
            if (manger != null)
                manger.removeView(contentView);
        } catch (IllegalArgumentException e) {
            //这边由于上下文被销毁后removeView可能会抛出IllegalArgumentException
            //暂时这么处理，因为EToast2是轻量级的，不想和Context上下文的生命周期绑定在一块儿
            //其实如果真的想这么做，可以参考博文2的第一种实现方式，添加一个空的fragment来做生命周期绑定
        }
        if (timer != null)
            timer.cancel();

        if (oldToast != null)
            oldToast.cancel();

        timer = null;
        toast = null;
        oldToast = null;
        contentView = null;
        handler = null;
    }

    public void setText(CharSequence s) {
        toast.setText(s);
    }
}