package com.example.apophis_android.ui.sixthDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_sixth_day_dirt.*

class SixthDayDirtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth_day_dirt)

        dirt_btn_back.setOnClickListener { onBackPressed() }
    }
}