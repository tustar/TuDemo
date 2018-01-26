package com.tustar.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by tustar on 18-1-26.
 */

public class DynamiclProxy implements InvocationHandler {

    private Buy mBuy;

    public DynamiclProxy(Buy buy) {
        this.mBuy = buy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("buyHouse")) {
            long money = (long) args[0];
            long newMoney = (long) (money * 0.99);
            //
            long proxyMoney = money - newMoney;
            System.out.println("中介费：" + proxyMoney);
            args[0] = newMoney;
            //
            return method.invoke(mBuy, args);
        }

        return null;


    }
}
