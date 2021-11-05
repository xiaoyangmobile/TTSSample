package com.example.ttssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ttssample.R

class MainActivity2 : AppCompatActivity() {
    lateinit var ttsProxy: TTSProxy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btn = findViewById<Button>(R.id.button3)
        ttsProxy = TTSProxy.getInstance(this)

        btn.setOnClickListener {
            ttsProxy.speak("这是一段中文文字请朗读出来这是一段中文文字请朗读出来这是一段中文文字请朗读出来")
        }
    }
}