package com.tustar.demo.proxy;

import com.tustar.common.util.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by tustar on 18-1-26.
 */


public class NotificationProxy implements InvocationHandler {

    private static final String TAG = "NotificationProxy";
    private Object mObject;

    public NotificationProxy(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.d(TAG, "invoke :: " + method.getName());
        if (method.getName().equals("enqueueNotificationWithTag")) {
            //具体的逻辑
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    Logger.d(TAG, "invoke :: 参数为:" + args[i].toString());
                }
            }
            //做些其他事情，然后替换参数之类
            return method.invoke(mObject, args);
        }
        return null;
    }
}