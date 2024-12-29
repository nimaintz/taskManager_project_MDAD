package com.example.taskmanager_project_mdad

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager_project_mdad.databinding.FragmentNewTaskMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTaskMenu(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskMenuBinding
    private lateinit var taskViewModel: TaskView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null){
            binding.taskTitle.text =  "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskNameInput.text = editable.newEditable(taskItem!!.name)
            binding.taskDescInput.text = editable.newEditable(taskItem!!.desc)
        }else{
            binding.taskTitle.text =  "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskView::class.java)
        binding.editCategoryButton.setOnClickListener(){
            saveAction()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name= binding.taskNameInput.text.toString()
        val desc = binding.taskDescInput.text.toString()
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,null,null)
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, null)
        }
        dismiss()
    }
}