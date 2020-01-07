package com.example.breakpaddemo

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.breakpad.BreakpadInit
import java.io.File

class MainActivity : Activity() {

    companion object {
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100

        init {
            System.loadLibrary("crash-lib")
        }
    }

    private var externalReportPath: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
        } else {
            initExternalReportPath()
        }
        findViewById<View>(R.id.crash_btn).setOnClickListener {
            initBreakPad()
            crash()
            // copy core dump to sdcard
        }
    }

    /**
     * 一般来说，crash 捕获初始化都会放到 Application 中，这里主要是为了大家有机会可以把崩溃文件输出到 sdcard 中做进一步的分析
     */
    private fun initBreakPad() {
        if (externalReportPath == null || !externalReportPath!!.exists()) {
            externalReportPath = File(filesDir, "crashDump")
            if (!externalReportPath!!.exists()) {
                externalReportPath!!.mkdirs()
            }
        }
        BreakpadInit.initBreakpad(externalReportPath!!.absolutePath)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        initExternalReportPath()
    }

    private fun initExternalReportPath() {
        externalReportPath = File(Environment.getExternalStorageDirectory(), "crashDump")
        if (!externalReportPath!!.exists()) {
            externalReportPath!!.mkdirs()
        }
    }

    external fun crash()
}