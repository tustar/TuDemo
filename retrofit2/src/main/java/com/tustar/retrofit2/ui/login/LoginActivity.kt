package com.tustar.retrofit2.ui.login

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.tustar.common.util.Logger
import com.tustar.common.util.MobileUtils
import com.tustar.retrofit2.R
import com.tustar.retrofit2.base.BaseActivity
import com.tustar.retrofit2.util.CommonDefine
import org.jetbrains.anko.toast
import java.lang.ref.WeakReference


class LoginActivity : BaseActivity(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    private lateinit var phoneEditText: EditText
    private lateinit var phoneClearBtn: ImageButton
    private lateinit var codeEditText: EditText
    private lateinit var loginCodeGet: TextView
    private lateinit var loginSubmit: Button

    // Timer
    private var timer = CommonDefine.CODE_TIMER
    private lateinit var handler: TimerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        handler = TimerHandler(this)

        initViews()
    }

    override fun initViews() {
        setTitle(R.string.login_title)

        phoneEditText = findViewById(R.id.login_phone_editText)
        phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s?.length == 0) {
                    phoneClearBtn.visibility = View.INVISIBLE
                } else {
                    phoneClearBtn.visibility = View.VISIBLE
                }
            }
        })

        phoneClearBtn = findViewById(R.id.login_phone_clear)
        phoneClearBtn.setOnClickListener {
            phoneEditText.text.clear()
        }

        codeEditText = findViewById(R.id.login_code_editText)

        loginCodeGet = findViewById(R.id.login_code_btn)
        loginCodeGet.setOnClickListener {

            presenter.sendCode(this, phoneEditText.text.toString())
        }


        loginSubmit = findViewById(R.id.login_submit)
        loginSubmit.setOnClickListener {
            if (!MobileUtils.isMobileOk(phoneEditText.text.toString().trim())) {
                showToast(R.string.login_phone_err)
            } else {
                presenter.login(this, phoneEditText.text.toString(), codeEditText.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        handler?.removeMessages(MSG_CODE_TIMER)
    }


    override fun showToast(resId: Int) {
        toast(resId)
    }

    override fun showVerificationCode(vCode: String) {
        Logger.d("vCode = $vCode")
        codeEditText.setText(vCode)
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(CommonDefine.EXTRA_VCODE, vCode)
        }
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, 0)
        val builder = NotificationCompat.Builder(this,
                CommonDefine.CHANNEL_ID).apply {
            setContentTitle(getString(R.string.login_code_notify_title))
            setContentText(getString(R.string.login_code_notify_text, vCode))
            setDefaults(NotificationCompat.DEFAULT_SOUND)
            setSmallIcon(R.drawable.ic_launcher)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher))
            setContentIntent(pendingIntent)
            NotificationCompat.PRIORITY_DEFAULT
            setFullScreenIntent(pendingIntent, true)
            setAutoCancel(true)
            setWhen(System.currentTimeMillis())
        }

        val notification = builder.build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0,notification)
    }

    override fun setSubmitEnable(enable: Boolean) {
        loginSubmit.isEnabled = enable
    }

    override fun setCodeGetEnable(enable: Boolean) {
        loginCodeGet.isEnabled = enable
    }

    override fun startCodeTimer() {
        timer = CommonDefine.CODE_TIMER
        handler.removeMessages(MSG_CODE_TIMER)
        handler.sendEmptyMessageDelayed(MSG_CODE_TIMER, 1000)
    }

    override fun toMainUI() {
        finish()
    }

    companion object {
        const val MSG_CODE_TIMER = 0x1
    }

    inner class TimerHandler(activity: BaseActivity) : Handler() {

        private var activity: WeakReference<BaseActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message?) {
            val wa = activity.get()
            wa?.let {
                when (msg?.what) {
                    MSG_CODE_TIMER -> {
                        timer--
                        if (timer > 0) {
                            val text = getString(R.string.login_code_timer, timer)
                            loginCodeGet.text = text
                            handler.removeMessages(MSG_CODE_TIMER)
                            handler.sendEmptyMessageDelayed(MSG_CODE_TIMER, 1000)
                        } else {
                            loginCodeGet.text = getString(R.string.login_code_get)
                            loginCodeGet.isEnabled = true
                        }

                    }
                    else -> {
                        super.handleMessage(msg)
                    }
                }
            }

        }
    }
}
