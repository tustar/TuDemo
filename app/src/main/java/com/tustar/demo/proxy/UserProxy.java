package com.tustar.demo.proxy;

/**
 * Created by tustar on 18-1-26.
 */

public class UserProxy implements Buy {

    private Buy mBuy;

    public UserProxy(Buy buy) {
        this.mBuy = buy;
    }

    @Override
    public void buyHouse(long money) {
        long newMoney = (long) (money * 0.99);
        //
        long proxyMoney = money - newMoney;
        System.out.println("中介费：" + proxyMoney);
        //
        mBuy.buyHouse(newMoney);
    }
}
