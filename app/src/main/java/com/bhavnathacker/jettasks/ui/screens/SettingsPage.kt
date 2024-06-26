package com.bhavnathacker.jettasks.ui.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.ui.components.InputText2
import com.bhavnathacker.jettasks.ui.components.TaskButton
import com.bhavnathacker.jettasks.ui.events.SettingsEvent
import com.bhavnathacker.jettasks.ui.viewmodels.SettingsViewModel
import com.bhavnathacker.jettasks.util.App
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.TestTags
import java.io.File

@ExperimentalComposeUiApi
@Composable
fun SettingPage(
    ctx: ComponentActivity,
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val taskUiState = viewModel.tasksUiModelStateFlow.collectAsState().value
    val (version_name, version_code) = App.get_apk_version(context)
    val task = viewModel.taskState.value
    task.server =taskUiState.server

    var server =task.server

    var textFieldValueState =
            TextFieldValue(
            text = server,
            selection = TextRange(server.length)
        )


    Column {
        TopAppBar(title = {
            Text(text = MultiLang.getString("app_name", R.string.app_name)+"Settings")
        }, backgroundColor = MaterialTheme.colors.primary)
        /*Row(
            modifier = Modifier
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.End,
        ) {
            TButton(text = MultiLang.getString("btn_settings", R.string.btn_settings),
                modifier = Modifier
                    .width(80.dp)
                    .padding(0.dp)
                    .height(40.dp),
                onClick = {
                    navController.navigate(TaskScreens.SettingScreen.name)
                })
        }*/

        Spacer(modifier = Modifier.height(30.dp))

        /*Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = MultiLang.getString(
                    "app_name",
                    R.string.app_name
                ) + "(v" + version_name + "-" + String.format("%d", version_code) + ")",
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            )
        }*/

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = MultiLang.getString("label_server", R.string.label_server),
                color = MaterialTheme.colors.onBackground
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                InputText2(
                    text = textFieldValueState,
                    testTag = TestTags.TASK_MEMO,
                    modifier = Modifier.onFocusChanged {
                        when {
                            it.isFocused -> {
                                // 我被聚焦了
                                System.out.println("isFoused")
                            }
                            //it.hasFocused -> // 我的子项被聚焦了
                            it.isCaptured -> {
                                // 是否处于捕获状态
                                System.out.println("isCaptured")
                            }
                        }
                    },
                    onTextChange = {
                        viewModel.onEvent(SettingsEvent.ChangeServer(it.text))
                        textFieldValueState = it.copy(text = it.text, selection = TextRange(it.text.length), composition = it.composition)
                    })
            }

            Spacer(modifier = Modifier.height(20.dp))

            TaskButton(text = MultiLang.getString("clean_langpack", R.string.clean_langpack),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                onClick = {
                    //
                    val filedir = context.filesDir;
                    var lang_file = "remote_zh.xml"
                    if(!context.deleteFile(lang_file)) {
                        Toast.makeText(context, "LangPack delete failed!", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(context, "LangPack delete ok!", Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }
}