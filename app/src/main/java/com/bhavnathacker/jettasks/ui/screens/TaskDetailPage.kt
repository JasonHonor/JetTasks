package com.bhavnathacker.jettasks.ui.screens

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.ui.components.*
import com.bhavnathacker.jettasks.ui.events.TaskDetailEvent
import com.bhavnathacker.jettasks.ui.viewmodels.TaskDetailViewModel
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.TestTags
import com.bhavnathacker.jettasks.util.getDateWithoutTime

@ExperimentalComposeUiApi
@Composable
fun TaskDetail(
    navController: NavController,
    taskId: Int?,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    taskId?.let {
        LaunchedEffect(key1 = taskId) {
            viewModel.fetchTask(taskId)
        }
    }

    val task = viewModel.taskState.value

    val name = task.name
    val selectedDate = task.deadline
    val status = task.status
    var priorityExpanded by remember { mutableStateOf(false) }
    val defaultPriorityIndex = TaskPriority.values().indexOf(task.priority)
    var selectedPriorityIndex = if (defaultPriorityIndex != -1) defaultPriorityIndex else 0
    val tag = task.tag
    val memo = task.memo

    Column {
        TopAppBar(title = {
            Text(text = MultiLang.getString("app_name", R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                TaskInputText(
                    text = name,
                    label = MultiLang.getString("label_add_task", R.string.label_add_task),
                    testTag = TestTags.TASK_NAME,
                    onTextChange = { viewModel.onEvent(TaskDetailEvent.ChangeName(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = MultiLang.getString("task_deadline", R.string.task_deadline),
                color = MaterialTheme.colors.onBackground
            )
            TaskDatePicker(
                selectedDate,
                testTag = TestTags.TASK_DEADLINE
            ) { date ->
                viewModel.onEvent(TaskDetailEvent.ChangeDeadline(date.getDateWithoutTime()))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = MultiLang.getString("task_priority", R.string.task_priority),
                color = MaterialTheme.colors.onBackground
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TaskMenu(
                    menuItems = TaskPriority.getList(),
                    menuExpandedState = priorityExpanded,
                    selectedIndex = selectedPriorityIndex,
                    testTag = TestTags.TASK_PRIORITY,
                    updateMenuExpandStatus = {
                        priorityExpanded = true
                    },
                    onDismissMenuView = {
                        priorityExpanded = false
                    },
                    onMenuItemClick = { index ->
                        selectedPriorityIndex = index
                        priorityExpanded = false
                        viewModel.onEvent(TaskDetailEvent.ChangePriority(TaskPriority.values()[selectedPriorityIndex]))
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TaskSwitch(
                MultiLang.getString("completed", R.string.completed),
                status == TaskStatus.COMPLETED,
                testTag = TestTags.TASK_STATUS,
                onCheckChanged = { isChecked ->
                    viewModel.onEvent(TaskDetailEvent.ChangeStatus(if (isChecked) TaskStatus.COMPLETED else TaskStatus.PENDING))
                })

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = MultiLang.getString("label_tag", R.string.label_tag),
                color = MaterialTheme.colors.onBackground
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                InputText(
                    text = tag,
                    testTag = TestTags.TASK_TAG,
                    onTextChange = { viewModel.onEvent(TaskDetailEvent.ChangeTag(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = MultiLang.getString("label_memo", R.string.label_memo),
                color = MaterialTheme.colors.onBackground
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                InputText(
                    text = memo,
                    testTag = TestTags.TASK_MEMO,
                    minLine = 2,
                    maxLine = 5,
                    onTextChange = { viewModel.onEvent(TaskDetailEvent.ChangeMemo(it)) })
            }

            Spacer(modifier = Modifier.height(20.dp))

            val taskUpdatedMsg = MultiLang.getString("task_updated", R.string.task_updated)
            val taskAddedMsg = MultiLang.getString("task_added", R.string.task_added)
            val addTaskMsg = MultiLang.getString("add_task_first", R.string.add_task_first)

            TaskButton(text = MultiLang.getString("save", R.string.save), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                onClick = {
                    val toastMessage: String
                    if (task.name.isNotEmpty()) {
                        toastMessage = if (task.id != 0) {
                            taskUpdatedMsg
                        } else {
                            taskAddedMsg
                        }
                        viewModel.onEvent(TaskDetailEvent.SaveTask)
                        navController.navigateUp()
                    } else {
                        toastMessage = addTaskMsg
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                })
        }

    }

}
