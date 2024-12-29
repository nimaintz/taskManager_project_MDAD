package com.example.taskmanager_project_mdad

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    val textSize1 = findViewById<TextView>(R.id.text)
    val seekBar1 = findViewById<SeekBar>(R.id.seek_bar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_main)

        /*seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Set the text size based on the SeekBar progress
                // Make sure to convert progress to float (and optionally scale it)
                // You can apply a scaling factor to adjust the text size range
                val textSize = progress.toFloat() // Directly use progress for text size
                textSize1.textSize = textSize
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user starts interacting with the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle when the user stops interacting with the SeekBar
            }
        })*/
    }


}