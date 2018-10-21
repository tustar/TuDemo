package com.tustar.common.util

import android.os.Build

object SystemUtils {

    @JvmStatic
    val isOOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @JvmStatic
    val isKitKatOrLater: Boolean
        get() = Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2

    @JvmStatic
    val isMarshmallowOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @JvmStatic
    val isMOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @JvmStatic
    val isLOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    @JvmStatic
    val isNOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= 24

}