package com.example.apophis_android.ui.main

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {
    
    val onOff: Boolean = true //재생

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        mediaplayer = MediaPlayer.create(this, R.raw.apophis_bgm)
        mediaplayer?.start()
        mediaplayer?.isLooping = true

        btn_music_onoff.setOnClickListener {
            mediaplayer?.release()
            btn_music_onoff.setImageResource(R.drawable.btn_music_unselect)
        }

        btn_click.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaplayer?.release()
    }

    companion object {
        var mediaplayer: MediaPlayer? = null
    }
}
