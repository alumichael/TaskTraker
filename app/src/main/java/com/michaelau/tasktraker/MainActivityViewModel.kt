package com.michaelau.tasktraker

import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelau.tasktraker.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: Repository
): ViewModel() {

    //task list livedata
    private val _taskList= MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> get() = _taskList


    fun getAllTask() {
        viewModelScope.launch {
            userRepository.getAllSavedTask().collect {
                _taskList.value = it
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            userRepository.delete(task)
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            userRepository.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            userRepository.update(task)
        }
    }

}