package com.bhavnathacker.jettasks.util

import android.content.Context
import android.content.res.Configuration
import com.bhavnathacker.jettasks.TaskApplication
import com.timecat.component.locale.LangAction
import com.timecat.component.locale.LocaleInfo
import com.timecat.component.locale.MLang
import com.timecat.component.locale.MLang.FinishLoadCallback
import com.timecat.component.locale.Util
import java.io.File
import javax.annotation.Nullable


object MultiLang {

    private var filesDir = getFilesDirFixed(getContext())

    private var action: LangAction =
        MultiLangAction()

    fun init(applicationContext: Context) {
        try {
            getInstance(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun saveLanguageKeyInLocal(language: String?) {
        val preferences = getContext()?.getSharedPreferences("language_locale", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.putString("language", language)
        editor?.apply()
    }

    @Nullable
    @JvmStatic
    fun loadLanguageKeyInLocal(): String? {
        val preferences = getContext()?.getSharedPreferences("language_locale", Context.MODE_PRIVATE)
        return preferences?.getString("language", null)
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        getInstance().onDeviceConfigurationChange(getContext(), newConfig)
    }

    fun getContext(): Context? {
        return TaskApplication.applicationContext
    }

    fun getInstance(): MLang {
        return getInstance(getContext())
    }

    fun getInstance(context: Context?): MLang {
        return MLang.getInstance(context, filesDir, action)
    }

    fun getFilesDirFixed(context: Context?): File {
        return Util.getFilesDirFixed(context, "/data/data/com.locale.ui/files")
    }

    fun loadRemoteLanguages(context: Context?, callback: FinishLoadCallback?) {
        getInstance().loadRemoteLanguages(context, callback)
    }

    fun applyLanguage(context: Context?, localeInfo: LocaleInfo?) {
        getInstance().applyLanguage(context, localeInfo)
    }

    fun getSystemLocaleStringIso639(): String? {
        return getInstance().systemLocaleStringIso639
    }

    fun getLocaleStringIso639(): String? {
        return getInstance().localeStringIso639
    }

    fun getLocaleAlias(code: String?): String? {
        return MLang.getLocaleAlias(code)
    }

    fun getCurrentLanguageName(): String? {
        return getInstance().getCurrentLanguageName(getContext())
    }

    fun getServerString(key: String?): String? {
        return getInstance().getServerString(getContext(), key)
    }

    fun getString(key: String?, res: Int): String {
        var ret =  getInstance().getString(getContext(), key, res)
        if(ret.isEmpty()) {
            return ""
        }else {
            return ret
        }
    }

    fun getString(key: String?): String? {
        return getInstance().getString(getContext(), key)
    }

    fun getPluralString(key: String?, plural: Int): String? {
        return getInstance().getPluralString(getContext(), key, plural)
    }

    fun formatPluralString(key: String?, plural: Int): String? {
        return getInstance().formatPluralString(getContext(), key, plural)
    }

    fun formatPluralStringComma(key: String?, plural: Int): String? {
        return getInstance().formatPluralStringComma(getContext(), key, plural)
    }

    fun formatString(key: String?, res: Int, vararg args: Any?): String? {
        return getInstance().formatString(getContext(), key, res, *args)
    }

    fun formatTTLString(ttl: Int): String? {
        return getInstance().formatTTLString(getContext(), ttl)
    }

    fun formatStringSimple(string: String?, vararg args: Any?): String? {
        return getInstance().formatStringSimple(getContext(), string, *args)
    }

    fun formatCallDuration(duration: Int): String? {
        return getInstance().formatCallDuration(getContext(), duration)
    }

    fun formatDateChat(date: Long): String? {
        return getInstance().formatDateChat(getContext(), date)
    }

    fun formatDateChat(date: Long, checkYear: Boolean): String? {
        return getInstance().formatDateChat(getContext(), date, checkYear)
    }

    fun formatDate(date: Long): String? {
        return getInstance().formatDate(getContext(), date)
    }

    fun formatDateAudio(date: Long, shortFormat: Boolean): String? {
        return getInstance().formatDateAudio(getContext(), date, shortFormat)
    }

    fun formatDateCallLog(date: Long): String? {
        return getInstance().formatDateCallLog(getContext(), date)
    }

    fun formatLocationUpdateDate(date: Long, timeFromServer: Long): String? {
        return getInstance().formatLocationUpdateDate(getContext(), date, timeFromServer)
    }

    fun formatLocationLeftTime(time: Int): String? {
        return MLang.formatLocationLeftTime(time)
    }

    fun formatDateOnline(date: Long): String? {
        return getInstance().formatDateOnline(getContext(), date)
    }

    fun isRTLCharacter(ch: Char): Boolean {
        return MLang.isRTLCharacter(ch)
    }

    fun formatSectionDate(date: Long): String? {
        return getInstance().formatSectionDate(getContext(), date)
    }

    fun formatDateForBan(date: Long): String? {
        return getInstance().formatDateForBan(getContext(), date)
    }

    fun stringForMessageListDate(date: Long): String? {
        return getInstance().stringForMessageListDate(getContext(), date)
    }

    fun formatShortNumber(number: Int, rounded: IntArray?): String? {
        return MLang.formatShortNumber(number, rounded)
    }

    fun resetImperialSystemType() {
        MLang.resetImperialSystemType()
    }

    fun formatDistance(distance: Float): String? {
        return getInstance().formatDistance(getContext(), distance)
    }
}