package com.example.ttssample

import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*
import kotlin.collections.HashMap

class TTSProxy private constructor() {
    private var tts: TextToSpeech? = null

    private val map = HashMap<LifecycleOwner, LifecycleBoundObserver>()

    companion object {
        private var instance: TTSProxy? = null
        fun getInstance(context: AppCompatActivity): TTSProxy {
            if (instance == null) {
                instance = TTSProxy()
                instance!!.addObserver(context)
            } else {
                instance!!.addObserver(context)
            }
            return instance!!
        }
    }

    private fun addObserver(context: AppCompatActivity) {
        if (tts == null) {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts!!.setLanguage(Locale.CHINESE)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts = null
                    }
                }
            }
        }
        if (!map.containsKey(context)) {
            val lifecycleBoundObserver = LifecycleBoundObserver(context)
            context.lifecycle.addObserver(lifecycleBoundObserver)
            map[context] = lifecycleBoundObserver
        }
    }



    fun speak(content: String) {
        tts?.speak(content, TextToSpeech.QUEUE_ADD, null, null)
    }

    fun stop() {
        tts?.stop()
    }

    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    inner class LifecycleBoundObserver(lifecycleOwner: LifecycleOwner) : LifecycleEventObserver {
        private val lifecycleOwner: LifecycleOwner = lifecycleOwner
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_PAUSE) {
                stop()
            }
            if (event == Lifecycle.Event.ON_DESTROY) {
                if (map.containsKey(lifecycleOwner)) {
                    map.remove(lifecycleOwner)
                }
                if (map.size == 0) {
                    destroy()
                }
            }
        }
    }

}