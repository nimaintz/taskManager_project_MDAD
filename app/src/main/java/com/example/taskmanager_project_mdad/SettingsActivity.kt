package com.example.taskmanager_project_mdad

import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskmanager_project_mdad.databinding.SettingsMainBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: SettingsMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("settingsPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Load saved theme
        val savedTheme = sharedPref.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(savedTheme)

        // Set the correct radio button as checked
        when (savedTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.radioLight.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.radioDark.isChecked = true
        }

        binding.textSizeVisual.textSize = sharedPref.getFloat("textSize", 17f)
        binding.seekBar.progress = sharedPref.getFloat("textSize", 17f).toInt()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = progress.toFloat()
                editor.apply {
                    putFloat("textSize", size)
                    apply()
                }
                binding.textSizeVisual.textSize = sharedPref.getFloat("textSize", 0.0f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user starts interacting with the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user stops interacting with the SeekBar
            }
        })

        // Theme change listener
        binding.themeOptions.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.radioLight -> AppCompatDelegate.MODE_NIGHT_NO
                R.id.radioDark -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(theme)
            editor.putInt("theme", theme)
            editor.apply()
        }
    }
}