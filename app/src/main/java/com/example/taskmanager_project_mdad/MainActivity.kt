package com.example.taskmanager_project_mdad

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager_project_mdad.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), TaskItemClickListner {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskView by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener(){
              NewTaskMenu(null).show(supportFragmentManager, "newTaskTag")
        }


        //Music

        binding.toggleMusic.setOnClickListener {
       if (binding.toggleMusic.isChecked){
           startMusicService()
       }else{
           stopMusicService()
       } }
//        taskViewModel.name.observe(this){
//            binding.taskName.text = String.format("Task Name: %s", it)
//        }
//
//        taskViewModel.desc.observe(this){
//            binding.taskDesc.text = String.format("Task Description: %s", it)
//        }

        setRecycleView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun setRecycleView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskMenu(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }


//Music
    private fun startMusicService() {
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_START
        }
        startService(intent)
    }

    private fun stopMusicService() {
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_STOP
        }
        startService(intent)
    }


}
