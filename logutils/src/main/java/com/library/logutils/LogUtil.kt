package com.library.logutils

interface LogUtil {
    fun log(priority: Int, tag: String, message: String)
}