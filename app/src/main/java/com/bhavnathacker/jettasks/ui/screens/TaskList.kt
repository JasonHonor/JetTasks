package com.bhavnathacker.jettasks.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.ui.components.TaskChip
import com.bhavnathacker.jettasks.ui.model.TasksUiModel
import com.bhavnathacker.jettasks.util.*

@ExperimentalComposeUiApi
@Composable
fun TaskList(
    tasksUiModel: TasksUiModel,
    onViewTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onAddTask: () -> Unit,
    showCompletedTasks: (Boolean) -> Unit,
    onSortByPriorityChanged: (Boolean) -> Unit,
    onSortByDeadlineChanged:  (Boolean) -> Unit
) {
    val tasks = tasksUiModel.tasks
    val showCompleted = tasksUiModel.showCompleted
    val sortOrder = tasksUiModel.sortOrder
    val isPrioritySortSelected =
        sortOrder == UserPreferences.SortOrder.BY_PRIORITY || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY
    val isDeadlineSortSelected =
        sortOrder == UserPreferences.SortOrder.BY_DEADLINE || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
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
                        onViewTask = { onViewTask(it) },
                        onDeleteTask = { onDeleteTask(it) })
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
                    text = stringResource(R.string.msg_no_tasks),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = colorResource(id = R.color.light_grey),
                        shape = RectangleShape
                    )
                    .constrainAs(bottomControls) {
                        bottom.linkTo(parent.bottom)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(0.dp)
                        .weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.show_completed_tasks))
                        Switch(
                            checked = showCompleted,
                            onCheckedChange = { showCompletedTasks(it) })
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.sort_by))
                        TaskChip(
                            name = stringResource(id = R.string.priority),
                            isSelected = isPrioritySortSelected,
                            onSelectionChanged = { onSortByPriorityChanged(it)})
                        TaskChip(
                            name = stringResource(id = R.string.deadline),
                            isSelected = isDeadlineSortSelected,
                            onSelectionChanged = { onSortByDeadlineChanged(it)})
                    }
                }
                FloatingActionButton(
                    modifier = Modifier
                        .padding(8.dp), onClick = onAddTask
                ) {
                    Icon(Icons.Filled.Add, stringResource(R.string.add_task))
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
    Surface(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(2.dp)),
        border = BorderStroke(1.dp, color = Color.LightGray),
        elevation = 4.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier
                .width(0.dp)
                .weight(1f)
                .clickable { onViewTask(task) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.Start) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Priority ${task.priority.name}",
                    style = MaterialTheme.typography.caption, fontWeight = FontWeight.SemiBold,
                    color = colorResource(task.priority.color)
                )
                Text(
                    text = task.status.name,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(task.status.color)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.task_deadline)
                    )
                    Text(
                        text = task.deadline.formatDateToString(),
                        style = MaterialTheme.typography.caption
                    )
                }

            }

            Icon(imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_task),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onDeleteTask(task) })

        }
    }
}

