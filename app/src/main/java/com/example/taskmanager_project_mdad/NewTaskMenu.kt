package com.example.taskmanager_project_mdad

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager_project_mdad.databinding.FragmentNewTaskMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime

class NewTaskMenu(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskMenuBinding
    private lateinit var taskViewModel: TaskView
    private var dueTime: LocalTime? =null
    private var dueDate: LocalDate? =null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null){
            binding.taskTitle.text =  "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskNameInput.text = editable.newEditable(taskItem!!.name)
            binding.taskDescInput.text = editable.newEditable(taskItem!!.desc)

            if(taskItem!!.dueTime != null){
                dueTime = taskItem!!.dueTime!!
                updateTimeButtonText()
            }

            if(taskItem!!.dueDate != null){
                dueDate = taskItem!!.dueDate!!
                updateDateButtonText()
            }

        }else{
            binding.taskTitle.text =  "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskView::class.java)

        binding.saveTaskButton.setOnClickListener(){
            saveAction()
        }

        binding.timeButton.setOnClickListener(){
            openTimePicker()
        }

        binding.dateButton.setOnClickListener(){
            openDatePicker()
        }


    }

    private fun openDatePicker() {
        if(dueDate == null)
            dueDate = LocalDate.now()
        val listener = DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
            dueDate = LocalDate.of(selectedYear, selectedMonth+1, selectedDay)
            updateDateButtonText()
        }
        val dialog = DatePickerDialog(requireContext(), listener, dueDate!!.year, dueDate!!.monthValue, dueDate!!.dayOfMonth)
        dialog.setTitle("Task Date Due")
        dialog.show()
    }

    private fun updateDateButtonText() {
        binding.dateButton.text = String.format("%02d.%02d.%04d", dueDate!!.dayOfMonth, dueDate!!.monthValue, dueDate!!.year)
    }


    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{_,selectedHour,selectedMinute ->
            dueTime = LocalTime.of(selectedHour,selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,true)
        dialog.setTitle("Task Time Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timeButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
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
            val newTask = TaskItem(name,desc,dueTime,null,dueDate)
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, dueTime,dueDate)
        }
        dismiss()
    }
}