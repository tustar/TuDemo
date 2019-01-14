// ISecurityCenter.aidl
package com.tustar.demo.ui.ryg.ch2.bindpool;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
