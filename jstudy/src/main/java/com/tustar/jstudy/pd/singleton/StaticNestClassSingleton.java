package com.tustar.jstudy.pd.singleton;

/**
 * 静态内部类(Static nest class)
 */
public class StaticNestClassSingleton {


   private static class SingletonHolder {
        private static final StaticNestClassSingleton INSTANCE = new StaticNestClassSingleton();
    }
    public static StaticNestClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 同一类对象hashcode是不同的，不同类对象hashcode有可能是相同的
                System.out.println(StaticNestClassSingleton.getInstance().hashCode());
            }).start();
        }
    }
}
