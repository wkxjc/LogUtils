package com.example.logutils.util

import com.example.logutils.MyApplication
import com.library.logutils.LogUtil
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PrintToFileLogUtil : LogUtil {
    private val logFile = File(MyApplication.application.filesDir, "log.txt")
    override fun log(priority: Int, tag: String, message: String) {
        if (!logFile.exists()) {
            val logFileCreated = logFile.createNewFile()
            if (!logFileCreated) throw Exception("Log file created failed.")
        }
        BufferedWriter(FileWriter(logFile, true)).use {
            it.write("${SimpleDateFormat.getDateTimeInstance().format(Date())} $tag $message\n")
        }
    }
}