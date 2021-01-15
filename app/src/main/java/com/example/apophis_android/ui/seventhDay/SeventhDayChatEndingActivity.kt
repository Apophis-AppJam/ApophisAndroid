package com.example.apophis_android.ui.seventhDay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import com.example.apophis_android.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_second_day_chat_ending.*
import kotlinx.android.synthetic.main.activity_seventh_day_chat_ending.*

class SeventhDayChatEndingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_chat_ending)

        btn_back_main7.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}