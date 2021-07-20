package com.tustar.demo.permisson

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.tustar.demo.ex.containsIgnoreOpstr
import com.tustar.demo.util.Logger


class PermissionRequest constructor(
    val activity: AppCompatActivity,
    private val params: Params,
) : IRequest {

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                params.success
            }
        }

    override fun request() {
        if (activity.containsIgnoreOpstr(params.opstr)) {
            Logger.d("containsIgnoreOpstr")
            params.failure
            return
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, params.permission)) {
            Logger.d("shouldShowRequestPermissionRationale")
            requestPermissionLauncher.launch(params.permission)
            return
        }

        if (!isGranted()) {
            Logger.d("Permission denied")
            requestPermissionLauncher.launch(params.permission)
            return
        }
    }

    override fun isGranted() = ActivityCompat.checkSelfPermission(
        activity,
        params.permission
    ) == PackageManager.PERMISSION_GRANTED

    class Params(
        val permission: String,
        val opstr: String,
        val success: () -> Unit,
        val failure: () -> Unit,
    )
}