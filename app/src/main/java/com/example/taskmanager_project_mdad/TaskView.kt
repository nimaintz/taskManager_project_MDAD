package com.example.taskmanager_project_mdad

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.lifecycle.asLiveData




class TaskView(private val repository: TaskItemRepository): ViewModel() {
    var taskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()


    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
            repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch{
        repository.updateTaskItem(taskItem)
    }

    fun deleteTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if(!taskItem.isCompleted())
            taskItem.completedDateString = DateTimeFormatter.ISO_DATE.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }



//    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?, dueDate: LocalDate?){
//        val list = taskItems.value
//        val task = list!!.find {it.id == id}!!
//        task.name = name
//        task.desc =desc
//        task.dueTime = dueTime
//        task.dueDate = dueDate
//        taskItems.postValue(list)
//    }

//    fun setCompleted(taskItem: TaskItem){
//        val list = taskItems.value
//        val task = list!!.find {it.id == taskItem.id}!!
//        if (task.completedDate == null)
//            task.completedDate = LocalDate.now()
//        taskItems.postValue(list)
//    }


}

class TaskItemModelFactory(private val repository: TaskItemRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(TaskView::class.java))
            return TaskView(repository) as T
        throw IllegalArgumentException("Unknown Class for View Model")
    }
}