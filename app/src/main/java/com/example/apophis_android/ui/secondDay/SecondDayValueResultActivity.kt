package com.example.apophis_android.ui.secondDay

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_value_result.*

class SecondDayValueResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_value_result)

        value_result_btn_back.setOnClickListener {
            onBackPressed()
        }

        var printingValue = intent.getStringExtra("printingValue")
        value_result_tv_result.text = printingValue
    }
}