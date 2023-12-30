package com.bhavnathacker.jettasks.ui.screens

import android.widget.Toast
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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.ui.components.InputText
import com.bhavnathacker.jettasks.ui.components.TaskButton
import com.bhavnathacker.jettasks.ui.components.TaskDatePicker
import com.bhavnathacker.jettasks.ui.components.TaskInputText
import com.bhavnathacker.jettasks.ui.components.TaskMenu
import com.bhavnathacker.jettasks.ui.components.TaskSwitch
import com.bhavnathacker.jettasks.ui.events.LoginEvent
import com.bhavnathacker.jettasks.ui.events.TaskDetailEvent
import com.bhavnathacker.jettasks.ui.viewmodels.LoginViewModel
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.TestTags
import com.bhavnathacker.jettasks.util.getDateWithoutTime

@ExperimentalComposeUiApi
@Composable
fun Login(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Column {
        TopAppBar(title = {
            Text(text = MultiLang.getString("app_name", R.string.app_name)+" Login")
        }, backgroundColor = MaterialTheme.colors.primary)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            val task = viewModel.taskState.value
            val username = task.username
            val password = task.password

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

                InputText(
                    text = password,
                    testTag = TestTags.TASK_TAG,
                    onTextChange = { viewModel.onEvent(LoginEvent.ChangePassword(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            TaskButton(text = MultiLang.getString("btn_login", R.string.btn_login), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                onClick = {
                    val toastMessage: String

                    viewModel.onEvent(LoginEvent.preauth(0))
                    //Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                })
        }
    }
}