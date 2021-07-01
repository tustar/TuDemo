package com.tustar.jstudy.pd.singleton;

import java.io.Serializable;

/**
 *
 */
public class SerializableSingleton implements Serializable {
    private static SerializableSingleton sInstance = new SerializableSingleton();

    private SerializableSingleton() {
    }

    public static SerializableSingleton getInstance() {
        return sInstance;
    }

    public Object readResolve() {
        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(SerializableSingleton.getInstance().hashCode());
            }).start();
        }
    }
}
