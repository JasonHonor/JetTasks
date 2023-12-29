package com.bhavnathacker.jettasks.util;

import com.bhavnathacker.jettasks.TaskApplication;
import com.timecat.component.locale.LangAction;
import com.timecat.component.locale.model.LangPackDifference;
import com.timecat.component.locale.model.LangPackLanguage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MultiLangAction implements LangAction {

    public void runOnUIThread(Runnable runnable) {
        TaskApplication.applicationHandler.post(runnable);
    }

    @Override

    public void saveLanguageKeyInLocal(String language) {
        MultiLang.saveLanguageKeyInLocal(language);
    }

    @Nullable
    @Override
    public String loadLanguageKeyInLocal() {
        return MultiLang.loadLanguageKeyInLocal();
    }

    @Override
    public void langpack_getDifference(String lang_pack, String lang_code, int from_version, @NonNull final LangAction.GetDifferenceCallback callback) {
        MLangServer.request_langpack_getDifference(lang_pack, lang_code, from_version, new MLangServer.GetDifferenceCallback() {
            @Override
            public void onNext(final LangPackDifference difference) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoad(difference);
                    }
                });
            }
        });
    }

    @Override
    public void langpack_getLanguages(@NonNull final LangAction.GetLanguagesCallback callback) {
        MLangServer.request_langpack_getLanguages(new MLangServer.GetLanguagesCallback() {
            @Override
            public void onNext(final List<LangPackLanguage> languageList) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoad(languageList);
                    }
                });
            }
        });
    }

    @Override
    public void langpack_getLangPack(String lang_code, @NonNull final LangAction.GetLangPackCallback callback) {
        MLangServer.request_langpack_getLangPack(lang_code, new MLangServer.GetLangPackCallback() {
            @Override
            public void onNext(final LangPackDifference difference) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoad(difference);
                    }
                });
            }
        });
    }
}