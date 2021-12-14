package com.library.logutils.util

import android.util.Log
import com.library.logutils.LogUtil

class DebugLogUtil : LogUtil {
    override fun log(priority: Int, tag: String, message: String) {
        Log.println(priority, tag, message)
    }
}