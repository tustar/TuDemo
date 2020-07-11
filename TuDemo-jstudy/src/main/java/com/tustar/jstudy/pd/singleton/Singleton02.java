package com.tustar.jstudy.pd.singleton;

/**
 * 懒汉式
 * 延迟加载，线程不安全
 */
public class Singleton02 {
    private static Singleton02 sInstance;

    private Singleton02() {
    }

    public static Singleton02 getInstance() {
        if (sInstance == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sInstance = new Singleton02();
        }

        return sInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(Singleton02.getInstance().hashCode());
            }).start();
        }
    }
}
