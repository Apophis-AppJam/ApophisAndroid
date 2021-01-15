package com.example.apophis_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import com.example.apophis_android.ui.firstDay.FirstDayChatActivity
import com.example.apophis_android.ui.secondDay.SecondDayChatActivity
import com.example.apophis_android.ui.seventhDay.SeventhDayChatActivity
import com.example.apophis_android.ui.sixthDay.SixthDayChatActivity
import kotlinx.android.synthetic.main.activity_day.*

class DayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        tv_first.setOnClickListener {
            val intent = Intent(this, FirstDayChatActivity::class.java)
            startActivity(intent)
        }

        tv_second.setOnClickListener {
            val intent = Intent(this, SecondDayChatActivity::class.java)
            startActivity(intent)
        }

        tv_sixth.setOnClickListener {
            val intent = Intent(this, SixthDayChatActivity::class.java)
            startActivity(intent)
        }

        tv_seventh.setOnClickListener {
            val intent = Intent(this, SeventhDayChatActivity::class.java)
            startActivity(intent)
        }
    }
}