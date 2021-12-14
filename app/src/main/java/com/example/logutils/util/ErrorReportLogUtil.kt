package com.example.logutils.util

import android.util.Log
import com.library.logutils.LogUtil

class ErrorReportLogUtil : LogUtil {
    override fun log(priority: Int, tag: String, message: String) {
        if (priority > Log.DEBUG) {
            // Report your error log to anywhere you want, such as save to file, upload to server...
            // Simply print again here
            Log.println(priority, tag, "Got an error message, $message")
        }
    }
}