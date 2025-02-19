package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.data.repository.FakeTaskRepository
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.Date

class GetTaskTest {
    private lateinit var getTask: GetTask
    private lateinit var fakeTaskRepository: FakeTaskRepository

    @Before
    fun setup() {
        fakeTaskRepository = FakeTaskRepository()
        getTask = GetTask(fakeTaskRepository)

        val tasksToInsert = mutableListOf<Task>()
        tasksToInsert.add(
            Task(
                0,
                name = "Task1",
                deadline = Date(),
                priority = TaskPriority.LOW,
                completed = Date(),
                tag = "tag",
                memo = "memo",
                deleted = Date(),
                status = TaskStatus.PENDING
            )
        )
        tasksToInsert.add(
            Task(
                1,
                name = "Task2",
                deadline = Date(),
                priority = TaskPriority.MEDIUM,
                completed = Date(),
                tag = "tag",
                memo = "memo",
                deleted = Date(),
                status = TaskStatus.COMPLETED
            )
        )
        tasksToInsert.add(
            Task(
                2,
                name = "Task3",
                deadline = Date(),
                priority = TaskPriority.HIGH,
                completed = Date(),
                tag = "tag",
                memo = "memo",
                deleted = Date(),
                status = TaskStatus.PENDING
            )
        )
        tasksToInsert.add(
            Task(
                3,
                name = "Task4",
                deadline = Date(),
                priority = TaskPriority.LOW,
                completed = Date(),
                tag = "tag",
                memo = "memo",
                deleted = Date(),
                status = TaskStatus.COMPLETED
            )
        )

        tasksToInsert.shuffle()
        runBlocking {
            tasksToInsert.forEach { fakeTaskRepository.saveTask(it) }
        }
    }

    @Test
    fun `Get task by id validate name`() = runBlocking {
        val note = getTask(0)

        assertThat(note?.name).isEqualTo("Task1")
    }

    @Test
    fun `Get task by id validate priority`() = runBlocking {
        val note = getTask(1)

        assertThat(note?.priority).isEqualTo(TaskPriority.MEDIUM)
    }


    @Test
    fun `Get task by id validate status`() = runBlocking {
        val note = getTask(2)

        assertThat(note?.status).isEqualTo(TaskStatus.PENDING)
    }
}