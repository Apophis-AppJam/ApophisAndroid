package com.example.apophis_android.ui.seventhDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_seventh_day_tarot_answer.*

class SeventhDayTarotAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_tarot_answer)

        tarot_answer_btn_back.setOnClickListener { onBackPressed() }

    }
}