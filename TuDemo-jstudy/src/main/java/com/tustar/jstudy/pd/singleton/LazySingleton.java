package com.tustar.jstudy.pd.singleton;

/**
 * 懒汉式(Lazy)
 * 延迟加载，线程不安全
 */
public class LazySingleton {
    private static LazySingleton sInstance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (sInstance == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sInstance = new LazySingleton();
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(LazySingleton.getInstance().hashCode());
            }).start();
        }
    }
}
