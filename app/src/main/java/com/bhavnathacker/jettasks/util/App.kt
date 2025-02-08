package com.bhavnathacker.jettasks.util

import android.content.Context
import android.os.Build

class App {

    companion object {
        fun get_apk_version(ctx: Context):Pair<String?,Long>
        {
            val packageManager = ctx.packageManager
            val packageName = ctx.packageName
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            var versionCode = 0L
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = packageInfo.longVersionCode
            }else {
                versionCode = packageInfo.versionCode.toLong()
            }
            return Pair(versionName,versionCode)
        }
    }
}