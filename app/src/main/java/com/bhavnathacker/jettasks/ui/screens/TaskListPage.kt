package com.bhavnathacker.jettasks.ui.screens

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.ui.components.TaskChip
import com.bhavnathacker.jettasks.ui.events.TaskListEvent
import com.bhavnathacker.jettasks.ui.navigation.TaskScreens
import com.bhavnathacker.jettasks.ui.viewmodels.TaskListViewModel
import com.bhavnathacker.jettasks.util.MultiLang
import com.bhavnathacker.jettasks.util.TestTags
import com.bhavnathacker.jettasks.util.formatDateToString
import java.util.*

/**kotlin显示 alertDialog**/
private fun showAlertDialog(ctx: Context,action:OnConfirm) {
    var builder=AlertDialog.Builder(ctx)
    builder.setTitle(MultiLang.getString("title_delete_confirm",R.string.title_delete_confirm))
    builder.setMessage(MultiLang.getString("msg_delete_confirm",R.string.msg_delete_confirm))
    builder.setPositiveButton(MultiLang.getString("btn_confirm_ok",R.string.btn_confirm_ok)){dialog, which ->
        //LogUtil.i("====kotlin确认=====")
        //val b = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        action.Ok()
        //b.setOnClickListener(listener)
        //toast("====kotlin确认=====")
    }
    builder.setNegativeButton(MultiLang.getString("btn_confirm_cancel",R.string.btn_confirm_cancel)){dialog, which ->
        //LogUtil.i("====kotlin取消=====")
        //toast("====kotlin取消=====")
    }

    var dialog:AlertDialog=builder.create()
    if (!dialog.isShowing) {
        dialog.show()
    }
}

public interface OnConfirm{
    fun Ok();
}

@ExperimentalComposeUiApi
@Composable
fun TaskList(
    navController: NavController,
    context:Context,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val taskUiState = viewModel.tasksUiModelStateFlow.collectAsState().value
    val tasks = taskUiState.tasks
    val showCompleted = taskUiState.showCompleted
    val sortOrder = taskUiState.sortOrder

    val isPrioritySortSelected =
        sortOrder == UserPreferences.SortOrder.BY_PRIORITY || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY
    val isDeadlineSortSelected =
        sortOrder == UserPreferences.SortOrder.BY_DEADLINE || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY

    Column {
        TopAppBar(title = {
            Text(text = MultiLang.getString("app_name",R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (list, bottomControls, noTasksText) = createRefs()

            LazyColumn(modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomControls.top)
                    height = Dimension.fillToConstraints
                }) {
                items(tasks) { task ->
                    TaskRow(task = task,
                        onViewTask = { navController.navigate(TaskScreens.DetailScreen.name + "/${it.id}") },
                        onDeleteTask = {
                            showAlertDialog(context, object : OnConfirm {
                                override fun Ok() {
                                    viewModel.onEvent(TaskListEvent.MaskTask(it))
                                }
                            })
                        }
                    )
                }
            }

            if (tasks.isEmpty()) {
                Text(
                    modifier = Modifier
                        .constrainAs(noTasksText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    text = MultiLang.getString("msg_no_tasks",R.string.msg_no_tasks),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(bottomControls) {
                    bottom.linkTo(parent.bottom)
                }) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(MaterialTheme.colors.secondary.copy(0.25f))) {

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(0.dp)
                            .weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = MultiLang.getString("total_tasks",R.string.total_tasks) + " " + String.format("%d",tasks.size))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = MultiLang.getString("show_completed_tasks",R.string.show_completed_tasks))
                            Switch(
                                checked = showCompleted,
                                onCheckedChange = { viewModel.onEvent(TaskListEvent.ShowCompletedTasks(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colors.secondary,
                                    uncheckedThumbColor = MaterialTheme.colors.onBackground.copy(0.5f),
                                    checkedTrackColor = MaterialTheme.colors.secondary.copy(0.5f),
                                    uncheckedTrackColor = MaterialTheme.colors.secondary.copy(0.5f)
                                ), modifier = Modifier.testTag(TestTags.SHOW_COMPLETED_SWITCH))
                        }
                        /*Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(R.string.show_completed_tasks))
                            Switch(
                                checked = showCompleted,
                                onCheckedChange = { viewModel.onEvent(TaskListEvent.ShowCompletedTasks(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colors.secondary,
                                    uncheckedThumbColor = MaterialTheme.colors.onBackground.copy(0.5f),
                                    checkedTrackColor = MaterialTheme.colors.secondary.copy(0.5f),
                                    uncheckedTrackColor = MaterialTheme.colors.secondary.copy(0.5f)
                                ), modifier = Modifier.testTag(TestTags.SHOW_COMPLETED_SWITCH))
                        }*/
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = MultiLang.getString("sort_by",R.string.sort_by))
                            Spacer(modifier = Modifier.width(8.dp))
                            TaskChip(
                                //name = MultiLang.getString( R.string.priority),
                                name = MultiLang.getString("priority",R.string.priority),
                                isSelected = isPrioritySortSelected,
                                onSelectionChanged = { viewModel.onEvent(TaskListEvent.ChangeSortByPriority(it))})
                            TaskChip(
                                name = MultiLang.getString("deadline",R.string.deadline),
                                isSelected = isDeadlineSortSelected,
                                onSelectionChanged = { viewModel.onEvent(TaskListEvent.ChangeSortByDeadline(it))})
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(8.dp), onClick = { navController.navigate(TaskScreens.DetailScreen.name + "/-1") }
                    ) {
                        Icon(Icons.Filled.Add, MultiLang.getString("add_task",R.string.add_task))
                    }
                }
            }
        }
    }
}


@Composable
fun TaskRow(
    modifier: Modifier = Modifier,
    task: Task,
    onViewTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(task.bgColor)) {
            Column(modifier
                .width(0.dp)
                .weight(1f)
                .clickable { onViewTask(task) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.Start) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold,
                    color = task.contentColor
                )
                Text(
                    text = "Priority ${task.priority.name}",
                    style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.SemiBold,
                    color = task.contentColor
                )
                Text(
                    text = task.status.name,
                    style = MaterialTheme.typography.subtitle2,
                    color = task.contentColor
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange, tint = task.contentColor,
                        contentDescription = MultiLang.getString("task_deadline", R.string.task_deadline)
                    )
                    Text(
                        text = task.deadline.formatDateToString(),
                        style = MaterialTheme.typography.body1,
                        color = task.contentColor
                    )
                }

                val c1 = Calendar.getInstance()
                c1[2020, 1, 1, 0, 0] = 8
                val dtStart = c1.time

                if (task.completed.after(dtStart)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check, tint = task.contentColor,
                            contentDescription = MultiLang.getString( "task_deadline",R.string.task_deadline)
                        )
                        Text(
                            text = task.completed.formatDateToString(),
                            style = MaterialTheme.typography.body1,
                            color = task.contentColor
                        )
                    }
                }
            }
            Icon(imageVector = Icons.Default.Delete, tint = task.contentColor,
                contentDescription = MultiLang.getString("del_task",R.string.delete_task),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onDeleteTask(task) })
        }
    }
}

