package com.tustar.demo.permisson

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tustar.demo.ex.containsIgnoreOpstrs
import com.tustar.demo.ex.isPermissionsGranted
import com.tustar.demo.ex.shouldShowRequestPermissionsRationale
import com.tustar.demo.util.Logger


class PermissionsRequest constructor(
    val activity: AppCompatActivity,
    private val params: Params,
) : IRequest {

    private val requestPermissionsLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                params.success()
            }
        }

    override fun request() {
        if (activity.containsIgnoreOpstrs(params.opstrs)) {
            Logger.d("containsIgnoreOpstrs")
            params.failure()
            return
        }

        if (activity.shouldShowRequestPermissionsRationale(params.permissions)) {
            Logger.d("shouldShowRequestPermissionsRationale")
            requestPermissionsLauncher.launch(params.permissions)
            return
        }

        if (!isGranted()) {
            Logger.d("Permissions denied")
            requestPermissionsLauncher.launch(params.permissions)
            return
        }
    }

    override fun isGranted() = activity.isPermissionsGranted(params.permissions)

    class Params(
        val permissions: Array<String>,
        val opstrs: Array<String>,
        val success: () -> Unit,
        val failure: () -> Unit,
    )
}