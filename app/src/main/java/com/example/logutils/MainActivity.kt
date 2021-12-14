package com.example.logutils

import android.app.Activity
import android.os.Bundle
import com.example.logutils.databinding.MainActivityBinding
import com.library.logutils.LogUtils
import java.lang.StringBuilder

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnVerbose.setOnClickListener {
            LogUtils.v("Verbose")
        }
        binding.btnDebug.setOnClickListener {
            LogUtils.d("Debug")
        }
        binding.btnInfo.setOnClickListener {
            LogUtils.i("Info")
        }
        binding.btnWarn.setOnClickListener {
            LogUtils.w("Warn")
        }
        binding.btnError.setOnClickListener {
            LogUtils.e("Error")
        }
        binding.btnWtf.setOnClickListener {
            LogUtils.wtf("What a terrible failure.")
        }
        binding.btnStackOffset.setOnClickListener {
            testStackOffset()
        }
    }

    private fun testStackOffset() {
        LogUtils.d("Stack Offset", 1)
    }
}