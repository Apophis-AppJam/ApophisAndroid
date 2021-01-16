package com.example.apophis_android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_call.*

/**
 * Created By hanjaehyeon
 */

class CallActivity : AppCompatActivity() {
    private var countPushButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val callfragment = CallFragment()

        iv_answer_call.setOnClickListener {
            if(countPushButton) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container_view_call, callfragment).commit()
            } else {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun changeCallText(){
        tv_answer.text = "끊기"
        countPushButton = false
    }
}