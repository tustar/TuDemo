package com.tustar.demo.ui.ryg.ch2.binderpool;

import android.os.RemoteException;

import com.tustar.demo.ui.ryg.ch2.bindpool.ICompute;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
