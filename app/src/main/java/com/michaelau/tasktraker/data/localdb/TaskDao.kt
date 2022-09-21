package com.michaelau.tasktraker.data.localdb

import androidx.room.*
import com.michaelau.tasktraker.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM myTask")
    fun getAllTask(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun delete(movie: Task)

    @Update
    fun update(movie: Task)

}