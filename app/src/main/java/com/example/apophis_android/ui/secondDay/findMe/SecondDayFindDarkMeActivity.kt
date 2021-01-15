package com.example.apophis_android.ui.secondDay.findMe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_find_dark_me.*

class SecondDayFindDarkMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_find_dark_me)

        btn_back.setOnClickListener { onBackPressed() }

        btn_find_me_complete.setOnClickListener {
            finish()
        }

        et_find_dark_me_content.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_find_me_complete.setImageResource(R.drawable.btn_complete_act)
                tv_find_me_complete.setTextColor(Color.parseColor("#FFFFFF"))
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }
}