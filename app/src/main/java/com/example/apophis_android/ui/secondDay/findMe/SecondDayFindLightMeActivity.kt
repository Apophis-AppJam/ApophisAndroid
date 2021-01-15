package com.example.apophis_android.ui.secondDay.findMe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_find_light_me.*

class SecondDayFindLightMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_find_light_me)

        btn_back.setOnClickListener { onBackPressed() }

        btn_find_me_next.setOnClickListener {
            val intent = Intent(this, SecondDayFindDarkMeActivity::class.java)
            intent.putExtra("light", et_find_light_me_content.text)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        et_find_light_me_content.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_find_me_next.setImageResource(R.drawable.boarder_round_rectangle_purple_17dp)
                tv_find_me_next.setTextColor(Color.parseColor("#AB70F5"))
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}