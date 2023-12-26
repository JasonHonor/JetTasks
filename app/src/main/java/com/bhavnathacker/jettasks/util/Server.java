package com.bhavnathacker.jettasks.util;

import com.timecat.component.locale.model.LangPackDifference;
import com.timecat.component.locale.model.LangPackLanguage;
import com.timecat.component.locale.model.LangPackString;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Server {
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