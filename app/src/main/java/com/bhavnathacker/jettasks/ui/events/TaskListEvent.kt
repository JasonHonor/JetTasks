package com.bhavnathacker.jettasks.ui.events

import com.bhavnathacker.jettasks.domain.model.Task

sealed class TaskListEvent {
    data class DeleteTask(val task: Task): TaskListEvent()

    data class MaskTask(val task: Task): TaskListEvent()

    data class ShowCompletedTasks(val show: Boolean): TaskListEvent()
    data class ChangeSortByPriority(val enable: Boolean): TaskListEvent()
    data class ChangeSortByDeadline(val enable: Boolean): TaskListEvent()
}