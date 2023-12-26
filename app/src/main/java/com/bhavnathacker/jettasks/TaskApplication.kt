package com.bhavnathacker.jettasks

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.MultiLang.init
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskApplication.applicationContext = this
        applicationHandler = Handler((applicationContext as TaskApplication).getMainLooper())
        init(applicationContext as TaskApplication)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        MultiLang.onConfigurationChanged(newConfig)
    }

    companion object {
        @JvmField
        var applicationContext: Context?=null
        @JvmField
        var applicationHandler:Handler?=null
    }
}