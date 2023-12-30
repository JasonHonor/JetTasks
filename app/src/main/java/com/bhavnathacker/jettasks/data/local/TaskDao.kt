package com.bhavnathacker.jettasks.data.local

import androidx.room.*
import com.bhavnathacker.jettasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_tbl ORDER BY id DESC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_tbl where id = :id")
    fun getTask(id: Int?): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Query("DELETE FROM task_tbl")
    fun deleteAll()

    @Delete
    fun deleteTask(task: Task)
}