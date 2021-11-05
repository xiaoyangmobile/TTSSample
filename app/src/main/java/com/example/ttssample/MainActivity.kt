package com.example.ttssample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tts: TTSProxy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TTSProxy.getInstance(this)

        val btn1 = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button2)

        btn1.setOnClickListener {
            tts!!.speak("这是一段中文文字请朗读出来这是一段中文文字请朗读出来这是一段中文文字请朗读出来")
        }

        btn2.setOnClickListener {
            tts!!.stop()
            val intent = Intent(this, MainActivity2::class.java)
            this.startActivity(intent)
        }
    }
}