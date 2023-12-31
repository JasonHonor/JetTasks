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
                        TaskNavigation(this@MainActivity)
                    }
                }
            }
        })
    }
}
