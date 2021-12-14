package com.library.logutils

import android.os.Build
import android.os.Looper
import android.util.Log

object LogUtils {
    private val logUtils = mutableListOf<LogUtil>()
    private const val MAX_TAG_LENGTH = 23
    // The log content should be less than 4 * 1024, I reduce it since we want to print more extra info
    private const val MAX_LOG_LENGTH = 3500
    private const val DEFAULT_TAG = "UNKNOWN"

    @Synchronized
    fun add(logUtil: LogUtil) {
        logUtils.add(logUtil)
    }

    @Synchronized
    fun remove(logUtil: LogUtil) {
        logUtils.remove(logUtil)
    }

    fun v(message: String) = v(message, 0)
    fun v(message: String, stackOffset: Int) = v("", message, stackOffset)
    fun v(tag: String, message: String, stackOffset: Int = 0) = log(Log.VERBOSE, tag, message, stackOffset)

    fun d(message: String) = d(message, 0)
    fun d(message: String, stackOffset: Int) = d("", message, stackOffset)
    fun d(tag: String, message: String, stackOffset: Int = 0) = log(Log.DEBUG, tag, message, stackOffset)

    fun i(message: String) = i(message, 0)
    fun i(message: String, stackOffset: Int) = i("", message, stackOffset)
    fun i(tag: String, message: String, stackOffset: Int = 0) = log(Log.INFO, tag, message, stackOffset)

    fun w(message: String) = w(message, 0)
    fun w(message: String, stackOffset: Int) = w("", message, stackOffset)
    fun w(tag: String, message: String, stackOffset: Int = 0) = log(Log.WARN, tag, message, stackOffset)

    fun e(message: String) = e(message, 0)
    fun e(message: String, stackOffset: Int) = e("", message, stackOffset)
    fun e(tag: String, message: String, stackOffset: Int = 0) = log(Log.ERROR, tag, message, stackOffset)

    fun wtf(message: String) = wtf(message, 0)
    fun wtf(message: String, stackOffset: Int) = wtf("", message, stackOffset)
    fun wtf(tag: String, message: String, stackOffset: Int = 0) = log(Log.ASSERT, tag, message, stackOffset)

    private fun log(priority: Int, tag: String, message: String, stackOffset: Int) {
        var mutableStackOffset = stackOffset
        val element = Throwable().stackTrace.firstOrNull { it.className != this::class.java.name && mutableStackOffset-- == 0 }
        val printTag = if (tag.isEmpty()) findTag(element) else tag
        val thread = findThread()
        val location = findLocation(element)
        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = newline.coerceAtMost(i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                logUtils.forEach {
                    it.log(priority, printTag, "$part $thread, $location")
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    private fun findTag(trace: StackTraceElement?): String {
        trace ?: return DEFAULT_TAG
        val tag = trace.fileName?.split(".")?.firstOrNull() ?: DEFAULT_TAG
        // Tag length was limited before API 26
        if (tag.length > MAX_TAG_LENGTH && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return tag.substring(0, MAX_TAG_LENGTH)
        }
        return tag
    }

    private fun findThread(): String {
        return "Thread-Name: ${Thread.currentThread().name}, isMain: ${Looper.getMainLooper() == Looper.myLooper()}"
    }

    private fun findLocation(trace: StackTraceElement?): String {
        trace ?: return ""
        if (trace.methodName.isNullOrEmpty() || trace.fileName.isNullOrEmpty() || trace.lineNumber <= 0) return ""
        return "Location: ${trace.methodName}(${trace.fileName}:${trace.lineNumber})"
    }
}