package com.example.taskmanager_project_mdad
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager_project_mdad.databinding.PomodoroMainBinding
import android.os.CountDownTimer
import android.widget.RadioGroup
import java.util.concurrent.TimeUnit


class PomodoroActivity : AppCompatActivity() {
    private lateinit var binding: PomodoroMainBinding

    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var selectedTimeInMinutes = 25L // Default time is 25 minutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout with ViewBinding
        binding = PomodoroMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener for session length selection
        binding.sessionOptions.setOnCheckedChangeListener { _, checkedId ->
            selectedTimeInMinutes = when (checkedId) {
                R.id.radio25 -> 25L
                R.id.radio50 -> 50L
                R.id.radio75 -> 75L
                else -> 25L
            }
            if (!isTimerRunning) updateTimerText(selectedTimeInMinutes * 60 * 1000)
        }

        // Start/Stop button logic
        binding.startStopButton.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
            } else {
                startTimer(selectedTimeInMinutes * 60 * 1000)
            }
        }
    }

    private fun startTimer(timeInMillis: Long) {
        isTimerRunning = true
        binding.startStopButton.text = "Stop"

        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                isTimerRunning = false
                binding.startStopButton.text = "Start"
                binding.timerText.text = "Done!"
            }
        }.start()
    }

    private fun stopTimer() {
        isTimerRunning = false
        binding.startStopButton.text = "Start"
        timer?.cancel()
        updateTimerText(selectedTimeInMinutes * 60 * 1000)
    }

    private fun updateTimerText(millis: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

}