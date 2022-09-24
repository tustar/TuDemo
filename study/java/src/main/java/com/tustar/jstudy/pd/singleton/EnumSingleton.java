package com.tustar.jstudy.pd.singleton;

/**
 * 枚举(Enum)
 */

enum Singleton {
    INSTANCE;
}

public class EnumSingleton {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(Singleton.INSTANCE.hashCode());
            }).start();
        }
    }
}
