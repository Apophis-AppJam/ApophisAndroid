package com.example.apophis_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.ui.secondDay.SecondDayChatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_phone.setOnClickListener {
            val intent = Intent(this, SecondDayChatActivity::class.java)
            startActivity(intent)
        }
    }

    companion object{
        var countCameraChange = false
    }
}