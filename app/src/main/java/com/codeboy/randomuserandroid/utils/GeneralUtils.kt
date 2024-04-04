package com.codeboy.randomuserandroid.utils

import android.util.Log

object GeneralUtils {

    var IS_DEBUG_MODE_ENABLE = true
    fun printLogD(tag: String, message: String) {
        if (IS_DEBUG_MODE_ENABLE) {
            Log.d(tag, message)
        }
    }
}