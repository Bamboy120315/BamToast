package com.bamboy.bamtoast;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bamboy.bamtoast.bamtoast.BamToast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * BamToast
 * <p>
 * ┏━━━━━━━━━━━━━━━━━
 * ┃名词介绍：
 * ┃
 * ┃BToast：
 * ┃本人之前写的自定义Toast，
 * ┃与原生Toast一样可以全局显示。
 * ┃
 * ┃EToast：
 * ┃网上一个大神写的，
 * ┃可以在没有通知权限的情况下弹出的Toast。
 * ┃
 * ┃BamToast：
 * ┃EToast + BToast。
 * ┃根据当前通知权限状态自动选择，
 * ┃如果当前有通知权限就用BToast，
 * ┃没有就使用EToast。
 * ┗━━━━━━━━━━━━━━━━━
 * <p>
 * 正常Toast在用户关闭了通知权限后就无法弹出了，
 * BamToast解决了这个问题，
 * 方案采用网上的EToast + BToast。
 * <p>
 * 实现很简单，
 * 判断通知权限，
 * 如果有通知权限，
 * 就正常使用BToast，
 * 离开Activity也可以全局显示，
 * 否则使用EToast，
 * 保障用户能够看到Toast。
 * <p>
 * 至于EToast在未打开通知权限也能弹出的原理，
 * 其实很简单，
 * 通知权限未获取时，
 * Toast其实还是正常绘制的，
 * 只是由于权限问题无法显示而已，
 * EToast则是通过getView()，
 * 获取了绘制但未显示的Toast，
 * 然后将其在Activity中显示出来。
 * <p>
 * 那么和原本的Toast有什么区别呢？
 * <p>
 * 1、原生Toast在显示和退出时有渐变动画，
 * EToast没有，
 * 所以视觉上稍稍欠佳，
 * 不过没事，
 * BamToast的图标是有动画的，
 * 一定程度上有所弥补。
 * <p>
 * 2、原生的Toast是系统级的，
 * 所以Activity离开也能正常显示，
 * 而EToast是基于Activity的，
 * 所以若Activity离开，
 * Toast也会随之离开。
 * <p>
 * 3、因为EToast是基于Activity的，
 * 所以必须要使用本类的Context,
 * 不能使用getApplicationContext()，
 * 切记！
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_notification_type;
    private Button btn_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化显示Toast的按钮
        initToastBtn();


        tv_notification_type = findViewById(R.id.tv_notification_type);
        btn_notification = findViewById(R.id.btn_notification);

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotification();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isNotificationEnabled(this)) {
            tv_notification_type.setText("当前通知权限为打开状态，\n启用BToast模式，\n离开当前Activity也可全局显示。\n\n您也可以试试：");
            btn_notification.setText("关闭通知权限");
            btn_notification.setVisibility(View.VISIBLE);
        } else {
            tv_notification_type.setText("当前通知权限已被禁止，\n启用EToast模式，\n没有权限也能正常显示。\n\n您也可以试试：");
            btn_notification.setText("开启通知权限");
            btn_notification.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化显示Toast的按钮
     */
    private void initToastBtn() {
        findViewById(R.id.btn_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 显示纯文字Toast
                BamToast.showText(
                        MainActivity.this,
                        "纯文字Toast\n时间戳：" + new Date().getTime());
            }
        });

        findViewById(R.id.btn_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 显示带图标Toast
                BamToast.showText(
                        MainActivity.this,
                        "带图标Toast\n时间戳：" + new Date().getTime(),
                        true);
            }
        });

        findViewById(R.id.btn_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 显示长时间Toast
                BamToast.showText(
                        MainActivity.this,
                        "长时间Toast\n时间戳：" + new Date().getTime(),
                        Toast.LENGTH_LONG);
            }
        });

        findViewById(R.id.btn_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 显示带小文字Toast
                BamToast.showText(
                        MainActivity.this,
                        "小文字Toast",
                        "时间戳：" + new Date().getTime());
            }
        });
    }

    // =============================================================================================
    // ======================== 以 下 是 判 断 通 知 权 限 所 需 ======================================
    // =============================================================================================
    /**
     * 是否开启通知栏权限所需
     */
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    /**
     * 是否开启通知栏权限所需
     */
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 是否开启通知栏权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
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

    /**
     * 打开通知权限
     */
    public void openNotification() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

}
