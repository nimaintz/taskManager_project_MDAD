package com.example.taskmanager_project_mdad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager_project_mdad.databinding.PomodoroMainBinding
import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class PomodoroActivity : AppCompatActivity() {
    private lateinit var binding: PomodoroMainBinding

    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var remainingTime: Long = 0L
    private var selectedStudyTime = 25L // Default study session time (25 minutes)
    private var selectedSmallBreakTime = 5L // Default small break time (5 minutes)
    private var selectedLargeBreakTime = 15L // Default large break time (15 minutes)
    private var sessionCount = 0 // Number of completed sessions
    private var isBreak = false // Whether the current timer is a break

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PomodoroMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener for study session length selection
        binding.sessionOptions.setOnCheckedChangeListener { _, checkedId ->
            selectedStudyTime = when (checkedId) {
                R.id.radio25 -> 25L
                R.id.radio50 -> 50L
                R.id.radio75 -> 75L
                else -> 25L
            }
            if (!isTimerRunning) updateTimerText(selectedStudyTime * 60 * 1000)
        }

        // Listener for small break selection
        binding.smallBreak.setOnCheckedChangeListener { _, checkedId ->
            selectedSmallBreakTime = when (checkedId) {
                R.id.radio5 -> 5L
                R.id.radio10 -> 10L
                else -> 5L
            }
        }

        // Listener for large break selection
        binding.largeBreak.setOnCheckedChangeListener { _, checkedId ->
            selectedLargeBreakTime = when (checkedId) {
                R.id.radio15 -> 15L
                R.id.radio30 -> 30L
                else -> 15L
            }
        }

        // Start button logic
        binding.startStopButton.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
            } else {
                if (remainingTime > 0L) {
                    resumeTimer()
                } else {
                    startStudySession()
                }
            }
        }

        // Pause button logic
        binding.pauseButton.setOnClickListener {
            pauseTimer()
        }
    }

    private fun startStudySession() {
        sessionCount += 1
        binding.sessionsCount.text = "Sessions Count: $sessionCount"
        isBreak = false
        startTimer(selectedStudyTime * 60 * 1000)
    }

    private fun startSmallBreak() {
        isBreak = true
        startTimer(selectedSmallBreakTime * 60 * 1000)
    }

    private fun startLargeBreak() {
        isBreak = true
        startTimer(selectedLargeBreakTime * 60 * 1000)
    }

    private fun startTimer(timeInMillis: Long) {
        isTimerRunning = true
        remainingTime = timeInMillis
        binding.startStopButton.text = "Stop"
        binding.timerText.text = if (isBreak) "Break Time!" else "Study Time!"

        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                isTimerRunning = false
                binding.startStopButton.text = "Start"

                // Determine the next phase
                if (!isBreak) {
                    if (sessionCount % 4 == 0) {
                        startLargeBreak()
                    } else {
                        startSmallBreak()
                    }
                } else {
                    // After a break, start a new study session
                    startStudySession()
                }
            }
        }.start()
    }

    private fun pauseTimer() {
        if (isTimerRunning) {
            timer?.cancel()
            isTimerRunning = false
            binding.startStopButton.text = "Resume"
        }
    }

    private fun resumeTimer() {
        if (!isTimerRunning && remainingTime > 0L) {
            startTimer(remainingTime)
        }
    }

    private fun stopTimer() {
        isTimerRunning = false
        binding.startStopButton.text = "Start"
        timer?.cancel()
        remainingTime = 0L
        updateTimerText(selectedStudyTime * 60 * 1000)
    }

    private fun updateTimerText(millis: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
    }
}
