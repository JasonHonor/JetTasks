package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import java.util.*

class SaveTask(val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        if (task.status==TaskStatus.COMPLETED) {
            task.completed = Date(Calendar.getInstance().timeInMillis);
        }
        repository.saveTask(task)
    }
}