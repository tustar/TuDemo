package com.tustar.jstudy.pd.singleton;

/**
 * 双重检查模式 （DCL）
 * synchronized解决了线程安全问题
 */
public class Singleton04 {
    private static volatile Singleton04 sInstance;

    private Singleton04() {
    }

    public static Singleton04 getInstance() {
        if (sInstance == null) {
            synchronized (Singleton04.class) {
                if (sInstance == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sInstance = new Singleton04();
                }
            }
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(Singleton04.getInstance().hashCode());
            }).start();
        }
    }
}
