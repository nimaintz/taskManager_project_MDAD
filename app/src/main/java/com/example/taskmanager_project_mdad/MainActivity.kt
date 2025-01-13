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
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity(), TaskItemClickListner {
    private lateinit var binding: ActivityMainBinding

    //Broadcast Recievers
    private val callReciver =  CallReciver()
    private val musicStoppedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.taskmanager_project_mdad.MUSIC_STOPPED") {
                runOnUiThread {
                binding.toggleMusic.isChecked = false
                }
            }
        }
    }


    private val taskViewModel: TaskView by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()




        val filter = IntentFilter("com.example.taskmanager_project_mdad.MUSIC_STOPPED")
        registerReceiver(musicStoppedReceiver, filter, Context.RECEIVER_EXPORTED)

        registerReceiver(
            callReciver,
            android.content.IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        )

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

        // SharedPreferences
        sharedPreferences = getSharedPreferences("settingsPrefs", MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "textSize") {
                updateRecyclerView()
            }
        }

        val savedTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(savedTheme)

        setRecycleView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onResume() {
        super.onResume()
        //listener for preference changes
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
        // Notify the RecyclerView to refresh when text sixe changes
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
    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                123
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReciver)
        unregisterReceiver(musicStoppedReceiver)
    }

}
