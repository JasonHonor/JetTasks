package com.bhavnathacker.jettasks.ui.screens

import android.os.Build
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.MainActivity
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.ui.components.InputText
import com.bhavnathacker.jettasks.ui.components.PasswordText
import com.bhavnathacker.jettasks.ui.components.TButton
import com.bhavnathacker.jettasks.ui.components.TaskButton
import com.bhavnathacker.jettasks.ui.components.TaskImageButton
import com.bhavnathacker.jettasks.ui.events.LoginEvent
import com.bhavnathacker.jettasks.ui.navigation.TaskScreens
import com.bhavnathacker.jettasks.ui.viewmodels.LoginViewModel
import com.bhavnathacker.jettasks.util.App
import com.bhavnathacker.jettasks.util.FingerPrint
import com.bhavnathacker.jettasks.util.FingerPrintCallback
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.TestTags
import com.wcz.fingerprintrecognitionmanager.FingerManager
import com.wcz.fingerprintrecognitionmanager.dialog.BaseFingerDialog
import dagger.hilt.android.internal.Contexts.getApplication

@ExperimentalComposeUiApi
@Composable
fun LoginPage(
    ctx: ComponentActivity,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val task = viewModel.taskState.value
    val username = task.username
    val password = task.password
    val (version_name, version_code) = App.get_apk_version(context)

    Column {
        //TopAppBar(title = {
        //    Text(text = MultiLang.getString("app_name", R.string.app_name)+" Login")
        //}, backgroundColor = MaterialTheme.colors.primary)
        Row(
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
        }

        Spacer(modifier = Modifier.height(100.dp))

        Column(
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
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = MultiLang.getString("label_username", R.string.label_username),
                color = MaterialTheme.colors.onBackground
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                InputText(
                    text = username,
                    testTag = TestTags.TASK_MEMO,
                    onTextChange = { viewModel.onEvent(LoginEvent.ChangeUsername(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = MultiLang.getString("label_password", R.string.label_password),
                color = MaterialTheme.colors.onBackground
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PasswordText(
                    text = password,
                    testTag = TestTags.TASK_TAG,
                    onTextChange = { viewModel.onEvent(LoginEvent.ChangePassword(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            TaskButton(text = MultiLang.getString("btn_login", R.string.btn_login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                onClick = {
                    var toastMessage: String =
                        MultiLang.getString("msg_login_ok", R.string.msg_login_ok)
                    //Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                    if (username == "demo") {
                        navController.navigate(TaskScreens.ListScreen.name)
                    } else {
                        toastMessage =
                            MultiLang.getString("msg_login_failed", R.string.msg_login_failed)
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                })

            Spacer(modifier = Modifier.height(20.dp))

            TaskButton(text = MultiLang.getString("finger_login", R.string.btn_finger_login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                onClick = {
                    //
                    FingerPrint().check(ctx,object:FingerPrintCallback{
                        override fun OnSuccess() {
                            navController.navigate(TaskScreens.ListScreen.name)
                        }
                    })
                })
        }
    }
}