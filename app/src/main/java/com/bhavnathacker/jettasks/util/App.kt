package com.bhavnathacker.jettasks.util

import android.content.Context

class App {

    companion object {
        fun get_apk_version(ctx: Context):Pair<String,Long>
        {
            val packageManager = ctx.packageManager
            val packageName = ctx.packageName
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = packageInfo.longVersionCode
            return Pair(versionName,versionCode)
        }
    }
}