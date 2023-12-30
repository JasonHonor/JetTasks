package com.bhavnathacker.jettasks

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.bhavnathacker.jettasks.ui.navigation.TaskNavigation
import com.bhavnathacker.jettasks.ui.theme.JetTasksTheme
import com.bhavnathacker.jettasks.util.MultiLang
import com.timecat.component.locale.MLang
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        MLang.USE_CLOUD_STRINGS = true
        MultiLang.loadRemoteLanguages(this, object : MLang.FinishLoadCallback{
            override fun finishLoad() {
                MultiLang.applyLanguage(this@MainActivity, MultiLang.getInstance().remoteLanguages[1])

                setContent {
                    JetTasksTheme {
                        TaskNavigation()
                    }
                }
            }
        })
        //rebuild()
    }
    private fun rebuild() {
        setContentView(
            buildUi(),
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    private fun buildUi(): ViewGroup {
        val containerLayout = LinearLayout(this)
        containerLayout.setOrientation(LinearLayout.VERTICAL)
        val detail = TextView(this)
        detail.setPadding(20, 20, 20, 20)
        detail.setTextSize(18F)
        detail.setGravity(Gravity.CENTER)
        val detailText = ((("""
    语言详情
    当前语言设置：${MultiLang.loadLanguageKeyInLocal()}
    """.trimIndent() + "\n"
                + "当前语言的英语名：" + MultiLang.getString(
            "LanguageNameInEnglish",
            R.string.LanguageNameInEnglish
        )
                ) + "\n\n本地缺失，云端存在的字符串：\n"
                + MultiLang.getString("remote_string_only", R.string.fallback_string)
                ) + "\n\n本地云端都存在，云端将覆盖本地的字符串：\n"
                + MultiLang.getString("local_string", R.string.local_string))
        detail.setText(detailText)
        containerLayout.addView(detail)
        MLang.USE_CLOUD_STRINGS = true
        MultiLang.loadRemoteLanguages(this, object : MLang.FinishLoadCallback{
            override fun finishLoad() {
                containerLayout.removeAllViews()
                containerLayout.addView(detail)
                for (info in MultiLang.getInstance().remoteLanguages) {
                    val btn = Button(this@MainActivity)
                    btn.setText(info.getSaveString())
                    btn.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            MultiLang.applyLanguage(this@MainActivity, info)
                            rebuild()
                        }
                    })
                    containerLayout.addView(btn)
                }
            }
        })
        return containerLayout
    }
}
