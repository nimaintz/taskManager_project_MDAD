package com.example.taskmanager_project_mdad

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager_project_mdad.databinding.SettingsMainBinding

class SettingsActivity : AppCompatActivity() {

lateinit var binding: SettingsMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  SettingsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences("settingsPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        binding.textSizeVisual.textSize = sharedPref.getFloat("textSize", 17f)
        binding.seekBar.progress = sharedPref.getFloat("textSize", 17f).toInt()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = progress.toFloat()
                editor.apply{
                    putFloat("textSize", size)
                    apply()
                }
               binding.textSizeVisual.textSize = sharedPref.getFloat("textSize", 0.0f)
                //binding.textSizeVisual.textSize = size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user starts interacting with the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user stops interacting with the SeekBar
            }
        })


    }


}