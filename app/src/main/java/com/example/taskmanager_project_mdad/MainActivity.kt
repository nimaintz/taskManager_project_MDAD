package com.example.taskmanager_project_mdad

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager_project_mdad.databinding.ActivityMainBinding
import android.Manifest
import android.content.SharedPreferences
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity(), TaskItemClickListner {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskView by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNotificationPermission()

        //taskAdapter = TaskItemAdapter(mutableListOf(), this)
        binding.pomodoroButton.setOnClickListener {
            val intent = Intent(this, PomodoroActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }


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

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("settingsPrefs", MODE_PRIVATE)

        // Register listener for preference changes
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "textSize") {
                updateRecyclerView()
            }
        }

        setRecycleView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onResume() {
        super.onResume()
        // Register listener for preference changes
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "textSize") {
                updateRecyclerView()
            }
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

    private fun updateRecyclerView() {
        // Notify the RecyclerView to refresh
        binding.todoListRecyclerView.adapter?.notifyDataSetChanged()
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


    //Permissions for post notifications
    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        } else {
            //Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

}
