package com.tustar.jstudy.pd.singleton;

/**
 * 加锁懒汉式(synchronized)
 * synchronized解决了线程安全问题，同时带来了效率的下降
 */
public class LockSingleton {
    private static LockSingleton sInstance;

    private LockSingleton() {
    }

    public static synchronized LockSingleton getInstance() {
        if (sInstance == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sInstance = new LockSingleton();
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(LockSingleton.getInstance().hashCode());
            }).start();
        }
    }
}
