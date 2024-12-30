package com.example.taskmanager_project_mdad

interface TaskItemClickListner {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun deleteTaskItem(taskItem: TaskItem)
}