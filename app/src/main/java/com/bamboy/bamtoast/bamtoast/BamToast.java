package com.bamboy.bamtoast.bamtoast;

import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.bamboy.bamtoast.bamtoast.btoast.BToast;
import com.bamboy.bamtoast.bamtoast.etoast.EToast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_ERROR;
import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_NONE;
import static com.bamboy.bamtoast.bamtoast.btoast.BToast.ICONTYPE_SUCCEED;

/**
 * Author: Blincheng.
 * Date: 2017/6/30.
 * Description:
 */
public class BamToast {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static int checkNotification = 0;
    private static Object mToast;

    private BamToast(Context context, CharSequence text, CharSequence subText, int time,
                     int iconType) {
        if (mToast != null) {
            cancel();
        }

        if (context instanceof Application)
            checkNotification = 0;
        else
            checkNotification = isNotificationEnabled(context) ? 0 : 1;

        if (checkNotification == 1) {
            mToast = new EToast(context, text, subText, time, iconType);
        } else {
            mToast = BToast.getToast(context, text, subText, time, iconType);
        }
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context  上下文
     * @param stringId 显示的文本的Id
     */
    public static void showText(Context context, int stringId) {
        new BamToast(context, context.getString(stringId), "", 0, ICONTYPE_NONE).show();
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    public static void showText(Context context, CharSequence... text) {
        String text_0 = "";
        String text_1 = "";
        if (text != null && text.length > 0) {
            if (text.length >= 1)
                text_0 = text[0].toString();

            if (text.length >= 2)
                text_1 = text[1].toString();
        }
        new BamToast(context, text_0, text_1, 0, ICONTYPE_NONE).show();
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param stringId  显示的文本在String.xml中的name
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, int stringId, boolean isSucceed) {
        new BamToast(context, context.getString(stringId), "", 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text,
                                boolean isSucceed) {
        new BamToast(context, text, "", 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    /**
     * 显示一个带小字、带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param subText   显示的副标题
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text, CharSequence subText,
                                boolean isSucceed) {
        new BamToast(context, text, subText, 0, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    /**
     * 显示一个纯文本吐司，可以控制显示时间长一点
     *
     * @param context  上下文
     * @param stringId 显示的文本的Id
     * @param time     持续的时间
     */
    public static void showText(Context context, int stringId, int time) {
        new BamToast(context, context.getString(stringId), "", time, ICONTYPE_NONE).show();
    }

    /**
     * 显示一个纯文本吐司，可以控制显示时间长一点
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    持续的时间
     */
    public static void showText(Context context, CharSequence text, int time) {
        new BamToast(context, text, "", time, ICONTYPE_NONE).show();
    }

    /**
     * 显示一个纯文本、带小字吐司，可以控制显示时间长一点
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param subText 显示的副标题
     * @param time    持续的时间
     */
    public static void showText(Context context, CharSequence text, CharSequence subText, int time) {
        new BamToast(context, text, subText, time, ICONTYPE_NONE).show();
    }

    /**
     * 显示一个带图标的吐司，可以控制显示时间长一点
     *
     * @param context   上下文
     * @param stringId  显示的文本的Id
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, int stringId, int time,
                                boolean isSucceed) {
        new BamToast(context, context.getString(stringId), "", time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    /**
     * 显示一个带图标的吐司
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text, int time,
                                boolean isSucceed) {
        new BamToast(context, text, "", time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    /**
     * 显示一个带小字、带图标的吐司，可以控制显示时间长一点
     *
     * @param context   上下文
     * @param text      显示的文本
     * @param subText   显示的副标题
     * @param time      持续的时间
     * @param isSucceed 显示【对号图标】还是【叉号图标】
     */
    public static void showText(Context context, CharSequence text, CharSequence subText, int time,
                                boolean isSucceed) {
        new BamToast(context, text, subText, time, isSucceed ? ICONTYPE_SUCCEED : ICONTYPE_ERROR).show();
    }

    public void show() {
        if (mToast instanceof EToast) {
            ((EToast) mToast).show();
        } else if (mToast instanceof Toast) {
            ((Toast) mToast).show();
        }
    }

    public void cancel() {
        if (mToast instanceof EToast) {
            ((EToast) mToast).cancel();
        } else if (mToast instanceof Toast) {
            ((Toast) mToast).cancel();
        }
    }

    /**
     * 用来判断是否开启通知权限
     */
    private static boolean isNotificationEnabled(Context context) {
        if (context == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            boolean isOpened = manager.areNotificationsEnabled();
            return isOpened;
        } else if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager appOps =
                    (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                        Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (int) opPostNotificationValue.get(Integer.class);
                return ((int) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg)
                        == AppOpsManager.MODE_ALLOWED);
            } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException
                    | InvocationTargetException | IllegalAccessException | RuntimeException e) {
                return true;
            }
        } else {
            return true;
        }
    }
}