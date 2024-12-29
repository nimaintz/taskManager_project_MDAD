package com.example.taskmanager_project_mdad

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
) {

    fun isCompleted():Boolean {
       return completedDate != null
    }
    fun imageResource(): Int {
        if(isCompleted())
            return  R.drawable.baseline_check_box_24
        else return R.drawable.check_box_blank
    }

    //fun imageColor()

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple)
}