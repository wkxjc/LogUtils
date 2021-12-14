package com.example.logutils

import android.app.Application
import com.example.logutils.util.ErrorReportLogUtil
import com.example.logutils.util.PrintToFileLogUtil
import com.library.logutils.LogUtils
import com.library.logutils.util.DebugLogUtil

class MyApplication : Application() {
    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        LogUtils.apply {
            add(DebugLogUtil())
            add(ErrorReportLogUtil())
            add(PrintToFileLogUtil())
        }
    }
}