package com.example.apophis_android.ui.firstDay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_first_day_chat_ending.*

class FirstDayChatEndingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_day_chat_ending)

        btn_firstday_back_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}