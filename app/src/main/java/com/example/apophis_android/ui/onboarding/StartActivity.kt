package com.example.apophis_android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_onboarding_start.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }
    }
}