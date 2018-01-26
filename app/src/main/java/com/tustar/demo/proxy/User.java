package com.tustar.demo.proxy;

/**
 * Created by tustar on 18-1-26.
 */

public class User implements Buy {

    @Override
    public void buyHouse(long money) {
        System.out.println("买房了， 用了" + money);
    }
}
