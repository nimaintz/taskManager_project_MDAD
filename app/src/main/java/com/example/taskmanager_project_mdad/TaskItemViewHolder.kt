package com.example.taskmanager_project_mdad

import android.content.Context
import android.graphics.Paint
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager_project_mdad.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListner
):RecyclerView.ViewHolder(binding.root) {

    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    fun bindTaskItem(taskItem: TaskItem){

        binding.name.text = taskItem.name

        if (taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnLongClickListener{
            clickListener.deleteTaskItem(taskItem)
            Toast.makeText(context, "Item deleted :)", Toast.LENGTH_SHORT).show()
            true

        }



        if (taskItem.dueTime() != null){
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        }
        else binding.dueTime.text = ""

        if (taskItem.dueDate() != null){
            binding.dueDate.text = dateFormat.format(taskItem.dueDate())
        }
        else binding.dueDate.text = ""

    }
}