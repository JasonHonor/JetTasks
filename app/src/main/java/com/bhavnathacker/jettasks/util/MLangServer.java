package com.bhavnathacker.jettasks.util;

import com.timecat.component.locale.model.LangPackDifference;
import com.timecat.component.locale.model.LangPackLanguage;
import com.timecat.component.locale.model.LangPackString;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MLangServer {
    public static ArrayList<LangPackString> englishStrings() {
        ArrayList<LangPackString> list = new ArrayList<>();
        list.add(new LangPackString("LanguageName", "English"));
        list.add(new LangPackString("LanguageNameInEnglish", "English"));
        list.add(new LangPackString("local_string", "remote string in english"));
        list.add(new LangPackString("remote_string_only", "not existing locally, only existing remotely"));
        return list;
    }

    public static ArrayList<LangPackString> chineseStrings() {
        ArrayList<LangPackString> list = new ArrayList<>();

        list.add(new LangPackString("LanguageName", "中文简体"));
        list.add(new LangPackString("LanguageNameInEnglish", "Chinese"));
        list.add(new LangPackString("local_string", "中文的云端字符串"));
        list.add(new LangPackString("remote_string_only", "本地缺失，云端存在的字符串"));
        list.add(new LangPackString("priority", "优先级"));
        list.add(new LangPackString("deadline", "截止日期"));
        list.add(new LangPackString("show_completed_tasks", "显示已完成"));
        list.add(new LangPackString("completed", "已完成"));
        list.add(new LangPackString("app_name", "工作跟踪"));
        list.add(new LangPackString("task_deadline", "截止日期"));
        list.add(new LangPackString("sort_by", "排序"));
        list.add(new LangPackString("add_task", "新任务"));
        list.add(new LangPackString("del_task", "删任务"));
        list.add(new LangPackString("msg_no_tasks", "无任务，点击按钮‘+’建任务"));
        list.add(new LangPackString("total_tasks", "共"));
        list.add(new LangPackString("task_updated", "任务已更新"));
        list.add(new LangPackString("task_added", "任务已添加"));
        list.add(new LangPackString("add_task_first", "在顶部添加"));
        list.add(new LangPackString("save", "保存"));
        list.add(new LangPackString("label_add_task", "添加任务"));
        list.add(new LangPackString("task_deadline", "截止时间"));
        list.add(new LangPackString("task_priority", "优先级"));
        list.add(new LangPackString("label_memo", "描述"));
        list.add(new LangPackString("label_tag", "标签"));

        list.add(new LangPackString("msg_to_login", "请登录"));
        list.add(new LangPackString("label_username", "用户名"));
        list.add(new LangPackString("label_password", "密码"));

        list.add(new LangPackString("btn_login", "立即登录"));

        return list;
    }

    public static LangPackDifference englishPackDifference() {
        LangPackDifference difference = new LangPackDifference();
        difference.lang_code = "en";
        difference.from_version = 0;
        difference.version = 1;
        difference.strings = englishStrings();
        return difference;
    }

    public static LangPackDifference chinesePackDifference() {
        LangPackDifference difference = new LangPackDifference();
        difference.lang_code = "zh";
        difference.from_version = 0;
        difference.version = 1;
        difference.strings = chineseStrings();
        return difference;
    }

    public static LangPackLanguage chineseLanguage() {
        LangPackLanguage langPackLanguage = new LangPackLanguage();
        langPackLanguage.name = "chinese";
        langPackLanguage.native_name = "简体中文";
        langPackLanguage.lang_code = "zh";
        langPackLanguage.base_lang_code = "zh";
        return langPackLanguage;
    }

    public static LangPackLanguage englishLanguage() {
        LangPackLanguage langPackLanguage = new LangPackLanguage();
        langPackLanguage.name = "english";
        langPackLanguage.native_name = "English";
        langPackLanguage.lang_code = "en";
        langPackLanguage.base_lang_code = "en";
        return langPackLanguage;
    }

    public static List<LangPackLanguage> available() {
        List<LangPackLanguage> langPackLanguages = new ArrayList<>();
        langPackLanguages.add(chineseLanguage());
        langPackLanguages.add(englishLanguage());
        return langPackLanguages;
    }

    public static void request_langpack_getDifference(String lang_pack, String lang_code, int from_version, @NonNull final GetDifferenceCallback callback) {
        if ("zh".equals(lang_code)) {
            callback.onNext(chinesePackDifference());
        } else if ("en".equals(lang_code)) {
            callback.onNext(englishPackDifference());
        }
    }

    public static void request_langpack_getLanguages(@NonNull GetLanguagesCallback callback) {
        callback.onNext(available());
    }

    public static void request_langpack_getLangPack(String lang_code, @NonNull GetLangPackCallback callback) {

        if ("zh".equals(lang_code)) {
            callback.onNext(chinesePackDifference());
        }
        else if ("en".equals(lang_code)) {
            callback.onNext(englishPackDifference());
        }
    }

    public interface GetDifferenceCallback {
        void onNext(final LangPackDifference difference);
    }

    public interface GetLanguagesCallback {
        void onNext(final List<LangPackLanguage> languageList);
    }

    public interface GetLangPackCallback {
        void onNext(final LangPackDifference difference);
    }
}