package com.example.taskmanager_project_mdad

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?,
    @ColumnInfo(name = "completedDateString") var completedDateString: String?,
    @ColumnInfo(name = "dueDateString") var dueDateString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {


    fun completedDate(): LocalDate? = if (completedDateString == null) null
    else LocalDate.parse(completedDateString, DateTimeFormatter.ISO_DATE)

    fun dueDate(): LocalDate? = if (dueDateString == null) null
    else LocalDate.parse(dueDateString, DateTimeFormatter.ISO_DATE)

    fun dueTime(): LocalTime? = if (dueTimeString == null) null
    else LocalTime.parse(dueTimeString, DateTimeFormatter.ISO_TIME)

    fun isCompleted():Boolean {
       return completedDate() != null
    }

    fun imageResource(): Int {
        if(isCompleted())
            return  R.drawable.baseline_check_box_24
        else return R.drawable.check_box_blank
    }


    fun imageColor(context: Context): Int =if(isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)




}