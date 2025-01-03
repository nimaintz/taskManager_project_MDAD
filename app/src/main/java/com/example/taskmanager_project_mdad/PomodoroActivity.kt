package com.example.taskmanager_project_mdad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager_project_mdad.databinding.PomodoroMainBinding
import android.os.CountDownTimer
import android.util.Log
import java.util.concurrent.TimeUnit

class PomodoroActivity : AppCompatActivity() {
    private lateinit var binding: PomodoroMainBinding
    private lateinit var dataHelper: DataHelper

    private var timer: CountDownTimer? = null

    private var selectedStudyTime: Long? = 25L
    private var selectedSmallBreakTime: Long? = 5L // Default small break time (5 minutes)
    private var selectedLargeBreakTime: Long? = 15L // Default large break time (15 minutes)
    private var sessionCount: Int = 0 // Number of completed sessions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PomodoroMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataHelper = DataHelper(applicationContext)

        loadSavedPreferences()

        // Listener for study session length selection
        binding.sessionOptions.setOnCheckedChangeListener { _, checkedId ->
            selectedStudyTime = when (checkedId) {
                R.id.radio25 -> 1L
                R.id.radio50 -> 50L
                R.id.radio75 -> 75L
                else -> 1L
            }
            Log.d("PomodoroStuff", "session options clicked ${selectedStudyTime}")
            dataHelper.saveSelectedStudyTime(selectedStudyTime!!)
            if (!dataHelper.isTimerRunning()) updateTimerText(selectedStudyTime!! * 60 * 1000)
        }

        // Listener for small break selection
        binding.smallBreak.setOnCheckedChangeListener { _, checkedId ->
            selectedSmallBreakTime = when (checkedId) {
                R.id.radio5 -> 1L
                R.id.radio10 -> 10L
                else -> 1L
            }
            dataHelper.saveSelectedSmallBreakTime(selectedSmallBreakTime!!)
        }

        // Listener for large break selection
        binding.largeBreak.setOnCheckedChangeListener { _, checkedId ->
            selectedLargeBreakTime = when (checkedId) {
                R.id.radio15 -> 1L
                R.id.radio30 -> 30L
                else -> 1L
            }
            dataHelper.saveSelectedLargeBreakTime(selectedLargeBreakTime!!)
        }

        // Start button logic
        binding.startStopButton.setOnClickListener {

            if (dataHelper.isTimerRunning()) {
                stopTimer()
                binding.statusPomodoro.text= "Stoped"
            } else {
                if (dataHelper.getRemainingTime() > 0L) {
                    resumeTimer()
                    binding.statusPomodoro.text= "Resumed"
                } else {
                    startStudySession()
                    binding.statusPomodoro.text= "Started"
                }
            }
        }

        // Pause button logic
        binding.pauseButton.setOnClickListener {
            binding.statusPomodoro.text= "Paused"
            pauseTimer()
        }
    }

    private fun loadSavedPreferences() {
        sessionCount = dataHelper.getSessionCount()
        selectedStudyTime = dataHelper.getSelectedStudyTime()
        Log.d("PomodoroStuff", "load preferences${selectedStudyTime}")
        loadStudyTime()
        selectedSmallBreakTime = dataHelper.getSelectedSmallBreakTime()
        loadSmallBreak()
        selectedLargeBreakTime = dataHelper.getSelectedLargeBreakTime()
        loadLargeBreak()

        binding.sessionsCount.text = "Sessions Count: $sessionCount"
        if(dataHelper.isTimerRunning()){
            startingPoint()
        }
        else {
            stoppingPoint()
        }
    }

    private fun loadSmallBreak() {
        if(selectedSmallBreakTime == 1L) //change to 5
            binding.radio5.isChecked = true
        else if(selectedSmallBreakTime == 10L)
            binding.radio10.isChecked = true

    }

    private fun loadStudyTime() {
        if(selectedStudyTime == 1L)//change to 25
            binding.radio25.isChecked = true
        else if(selectedStudyTime == 50L)
            binding.radio50.isChecked = true
        else if(selectedStudyTime == 75L)
            binding.radio75.isChecked = true


    }

    private fun loadLargeBreak() {
        if(selectedLargeBreakTime == 1L) //change to 15
            binding.radio15.isChecked = true
        else if(selectedLargeBreakTime == 30L)
            binding.radio30.isChecked = true
    }



    private fun stoppingPoint() {
        dataHelper.saveTimerRunning(false)
        if(dataHelper.getIsBreak())
        {
            binding.startStopButton.text = "Stop"
            updateTimerText(dataHelper.getRemainingTime())
        }
        if(!dataHelper.getIsBreak() && dataHelper.getRemainingTime() == 0L){
            //it was stopped
            binding.startStopButton.text = "Start"
            updateTimerText(dataHelper.getSelectedStudyTime()* 60 * 1000)
        }
        else {
            binding.startStopButton.text = "Resume"
            updateTimerText(dataHelper.getRemainingTime())
        }
    }

    private fun startingPoint() {
        dataHelper.saveTimerRunning(true)
        binding.startStopButton.text = "Stop"
        updateTimerText(dataHelper.getRemainingTime())
    }

    private fun startStudySession() {
        sessionCount += 1
        dataHelper.saveSessionCount(sessionCount)
        binding.sessionsCount.text = "Sessions Count: $sessionCount"
        dataHelper.saveIsBreak(false)
        startTimer(dataHelper.getSelectedStudyTime() * 60 * 1000)
    }

    private fun startSmallBreak() {
        dataHelper.saveIsBreak(true)
        startTimer(dataHelper.getSelectedSmallBreakTime() * 60 * 1000)
        sendNotification("Small Break Started: ${selectedSmallBreakTime} minutes")
    }

    private fun startLargeBreak() {
        dataHelper.saveIsBreak(true)
        startTimer(dataHelper.getSelectedLargeBreakTime() * 60 * 1000)
        sendNotification("Large Break Started: ${selectedLargeBreakTime} minutes")
    }

    private fun startTimer(timeInMillis: Long) {
        dataHelper.saveTimerRunning(true)
        dataHelper.saveRemainingTime(timeInMillis)
        binding.startStopButton.text = "Stop"
        sendStartingNotification("Pomodoro is now running")


        //binding.timerText.text = if (isBreak) "Break Time!" else "Study Time!"

        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                dataHelper.saveRemainingTime(millisUntilFinished)
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                dataHelper.saveTimerRunning(false)
                binding.startStopButton.text = "Start"

                // Determine the next phase
                if (!dataHelper.getIsBreak()) {
                    if (sessionCount % 4 == 0) {
                        startLargeBreak()
                    } else {
                        startSmallBreak()
                    }
                } else {
                    // After a break, start a new study session
                    sendNotification("Break Over! Time to Study")
                    startStudySession()
                }
            }
        }.start()
    }

    private fun sendNotification(message: String) {
        val intent = Intent(this, PomodoroNotificationService::class.java)
        intent.putExtra(PomodoroNotificationService.EXTRA_MESSAGE, message)
        startService(intent)
    }

    private fun sendStartingNotification(message: String) {
        val intent2 = Intent(this, PomodoroNotificationService::class.java).apply {
            putExtra(PomodoroNotificationService.EXTRA_MESSAGE, message)
            putExtra(PomodoroNotificationService.EXTRA_NOTIFICATION_ID, 3) // ID 3
        }
        startService(intent2)
    }



    private fun pauseTimer() {
        if (dataHelper.isTimerRunning()) {
            timer?.cancel()
            dataHelper.saveTimerRunning(false)
            sendStartingNotification("Pomodoro is now paused")
            binding.startStopButton.text = "Resume"
        }
    }

    private fun resumeTimer() {
        if (!dataHelper.isTimerRunning() && dataHelper.getRemainingTime() > 0L) {
            startTimer(dataHelper.getRemainingTime())
        }
    }

    private fun stopTimer() {
        dataHelper.saveTimerRunning(false)
        binding.startStopButton.text = "Start"
        timer?.cancel()
        dataHelper.saveRemainingTime(0L)
        sendStartingNotification("Pomodoro has stopped")
        updateTimerText(dataHelper.getSelectedStudyTime() * 60 * 1000)
    }

    private fun updateTimerText(millis: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
    }



}