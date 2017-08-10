// ISecurityCenter.aidl
package com.tustar.demo.module.ryg.ch2.bindpool;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
