package com.example.taskmanager_project_mdad

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_main)


        var textSizeVisual = findViewById<TextView>(R.id.text_size_visual)
        val seekBar1 = findViewById<SeekBar>(R.id.seek_bar)

        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = progress.toFloat()
                textSizeVisual.textSize = size
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