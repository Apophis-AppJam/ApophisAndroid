package com.example.apophis_android.ui.secondDay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import com.example.apophis_android.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_second_day_chat_ending.*

class SecondDayChatEndingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat_ending)

        btn_back_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}