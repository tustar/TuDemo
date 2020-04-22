package com.tustar.demo.proxy;

import android.app.NotificationManager;
import android.content.Context;

import com.tustar.util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by tustar on 18-1-26.
 */

public class ProxyUtils {

    private static final String TAG = "ProxyUtils";

    public static void hookNotificationManager(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        try {
            Method method = nm.getClass().getDeclaredMethod("getService");
            method.setAccessible(true);
            // 获取代理对象
            final Object sService = method.invoke(nm);
            Logger.d(TAG, "hookNotificationManager :: sService = " + sService);
            Class<?> INotificationManagerClazz = Class.forName("android.app.INotificationManager");
            Object proxy = Proxy.newProxyInstance(INotificationManagerClazz.getClassLoader(),
                    new Class[]{INotificationManagerClazz}, new NotificationProxy(sService));
            // 获取原来的对象
            Field mServiceField = nm.getClass().getDeclaredField("sService");
            mServiceField.setAccessible(true);
            // 替换
            mServiceField.set(nm, proxy);
            Logger.d(TAG, "hookNotificationManager :: Hook NotificationManager Success");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
