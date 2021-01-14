package com.example.apophis_android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.ui.main.letter.ReceiveLetterActivity
import com.example.apophis_android.ui.secondDay.SecondDayChatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_apophis.setOnClickListener {
            val intent = Intent(this, ApophisActivity::class.java)
            startActivity(intent)
        }

        view_message.setOnClickListener {
            val intent = Intent(this, ReceiveLetterActivity::class.java)
            startActivity(intent)
        }

        view_phone.setOnClickListener {
            val intent = Intent(this, SecondDayChatActivity::class.java)
            startActivity(intent)
        }

        view_setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}