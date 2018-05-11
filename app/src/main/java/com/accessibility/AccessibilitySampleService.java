package com.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.accessibility.utils.AccessibilityLog;

/**
 * Created by popfisher on 2017/7/6.
 */
public class AccessibilitySampleService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 通过代码可以动态配置，但是可配置项少一点
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
//                | AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
//        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
//        accessibilityServiceInfo.notificationTimeout = 0;
//        accessibilityServiceInfo.flags = AccessibilityServiceInfo.DEFAULT;
//        setServiceInfo(accessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
        String pkgName = event.getPackageName().toString();
        int eventType = event.getEventType();
        AccessibilityOperator.getInstance().updateEvent(this, event);
//        try {
//            Thread.sleep(200);
//        } catch (Exception e) {}
        AccessibilityLog.printLog("eventType: " + eventType + " pkgName: " + pkgName);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
        }
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
//        if()
        Log.d("TAG","the key event ...."+event.getKeyCode());
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 开机启动对应的系统服务
     */
    public void startService(){
        String enabledServicesSetting = Settings.Secure.getString(
                getContentResolver(),               Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        ComponentName selfComponentName = new ComponentName(getPackageName(),
                "Your AccessibilityService Class Name");
        String flattenToString = selfComponentName.flattenToString();
        if (enabledServicesSetting==null||
                !enabledServicesSetting.contains(flattenToString)) {
            enabledServicesSetting += flattenToString;
        }
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                enabledServicesSetting);
        Settings.Secure.putInt(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 1);
    }
}
