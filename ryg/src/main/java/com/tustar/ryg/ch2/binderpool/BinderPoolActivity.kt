package com.tustar.ryg.ch2.binderpool

import android.os.Bundle
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import com.tustar.util.Logger
import com.tustar.ryg.R
import com.tustar.ryg.ch2.bindpool.ICompute
import com.tustar.ryg.ch2.bindpool.ISecurityCenter


class BinderPoolActivity : AppCompatActivity() {

    companion object {
        private val TAG = BinderPoolActivity::class.java.simpleName
    }

    private var mSecurityCenter: ISecurityCenter? = null
    private var mCompute: ICompute? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_binder_pool)
        title = getString(R.string.ryg_ch2_binder_pool)

        Thread({ doWork() }).start()
    }

    private fun doWork() {
        var binderPool = BinderPool.getInstance(this)
        var securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER)
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder)
        Logger.d(TAG, "doWork :: visit ISecurityCenter")
        val msg = "helloworld-安卓"
        Logger.d(TAG, "content:" + msg)
        try {
            val password = mSecurityCenter!!.encrypt(msg)
            Logger.d(TAG, "encrypt:" + password)
            Logger.d(TAG, "decrypt:" + mSecurityCenter!!.decrypt(password))
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        Logger.d(TAG, "visit ICompute")
        val computeBinder = binderPool
                .queryBinder(BinderPool.BINDER_COMPUTE)

        mCompute = ComputeImpl.asInterface(computeBinder)
        try {
            Logger.d(TAG, "3+5=" + mCompute!!.add(3, 5))
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }
}
