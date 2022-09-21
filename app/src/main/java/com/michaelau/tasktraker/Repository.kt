package com.michaelau.tasktraker

import com.michaelau.tasktraker.data.localdb.TaskDao
import com.michaelau.tasktraker.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val taskDao: TaskDao
) {

    suspend fun insert(task: Task) {
        try {
            withContext(Dispatchers.IO){
                taskDao.insertTask(task)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    suspend fun getAllSavedTask(): Flow<List<Task>>{
        return flow {
            try {
                emit(taskDao.getAllTask())
            }catch (e:Exception){
                e.printStackTrace()
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun delete(task: Task) {
        try {
            withContext(Dispatchers.IO) {
                taskDao.delete(task)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    suspend fun update(task: Task) {
        try {
            withContext(Dispatchers.IO) {
                taskDao.update(task)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

/*
    private val content: String get() = "I would like to take this opportunity " +
            "to thank you for providing me with this golden"

    var dummyTask= arrayListOf(
        Task(1,title="Contact the CEO of Decagon.",
            content = content,
            colorTheme = R.color.grey_light),
        Task(2,title="Design the onboarding session of " +
                "Task Tracker App.",
            content =content,
            colorTheme = R.color.purple_fade),
        Task(3,title="Remind the technical team to include " +
                "the micro-interactions delivered..",
            content = content,
            colorTheme = R.color.red_fade),
    )*/
}