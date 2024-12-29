package com.example.taskmanager_project_mdad

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskView: ViewModel() {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()
    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?, dueDate: LocalDate?){
        val list = taskItems.value
        val task = list!!.find {it.id == id}!!
        task.name = name
        task.desc =desc
        task.dueTime = dueTime
        task.dueDate = dueDate
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: TaskItem){
        val list = taskItems.value
        val task = list!!.find {it.id == taskItem.id}!!
        if (task.completedDate == null)
            task.completedDate = LocalDate.now()
        taskItems.postValue(list)
    }
}