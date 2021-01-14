package com.example.apophis_android.ui.main.letter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_receive_letter.*

class ReceiveLetterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_letter)

        btn_letter_back.setOnClickListener { onBackPressed() }
    }
}