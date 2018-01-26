package com.tustar.demo.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by tustar on 18-1-26.
 */

public class ProxyClient {
    public static void main(String[] args) {
        // 正常
        System.out.println("正常");
        Buy buy = new User();
        buy.buyHouse(100_000_000);
        // 静态代理
        System.out.println("静态代理");
        UserProxy userProxy = new UserProxy(buy);
        userProxy.buyHouse(100_000_000);
        // 动态代理
        System.out.println("动态代理");
        Buy dynamiclProxy = (Buy) Proxy.newProxyInstance(buy.getClass().getClassLoader(),
                buy.getClass().getInterfaces(), new DynamiclProxy(buy));
        dynamiclProxy.buyHouse(100_000_000);
    }
}
