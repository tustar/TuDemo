package com.tustar.jstudy.pd.singleton;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class ContainerSingleton implements Serializable {
    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    private ContainerSingleton() {
    }

    public static Object getInstance(String className) {
        if (!ioc.containsKey(className)) {
            try {
                Object o = Class.forName(className).newInstance();
                ioc.put(className, o);
                return o;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ioc.get(className);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(ContainerSingleton.getInstance("java.lang.String").hashCode());
            }).start();
        }
    }
}
