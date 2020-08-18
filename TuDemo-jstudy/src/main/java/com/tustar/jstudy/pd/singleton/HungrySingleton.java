package com.tustar.jstudy.pd.singleton;

/**
 * 饿汉式(Hungry)
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全
 * 简单实用，推荐使用
 * 唯一缺点：不管用到与否，类加载时就完成实例化
 */
public class HungrySingleton {
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    /**
     * private static final Singleton02 INSTANCE;
     * <p>
     * static {
     * INSTANCE = new Singleton02();
     * }
     */

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        HungrySingleton s1 = HungrySingleton.getInstance();
        HungrySingleton s2 = HungrySingleton.getInstance();
        System.out.println(s1 == s2);
    }
}
