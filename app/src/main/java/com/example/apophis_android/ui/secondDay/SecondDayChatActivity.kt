package com.example.apophis_android.ui.secondDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.ChatData
import kotlinx.android.synthetic.main.activity_second_day_chat.*

class SecondDayChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: SecondDayChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        val aponis = mutableListOf("안녕! 미안 방금 정신없었지?", "그냥, 방금 뉴스 봤어?")
        val aponius = mutableListOf("넌 누구야")
        val aponis2 = mutableListOf("다음에 너가 골라줘야해")
        val data
                = mutableListOf("응, 아니야", "빡치네", "내 맘대로 할거야")

        aponis.forEach {
            Log.d("아포니스가 무슨 말을 할지 보자", it)
            val chatLeft = ChatData(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }

        aponius.forEach {
            Log.d("아포니어스는??", it)
            val chatRight = ChatData(mutableListOf(it), 1)
            chatAdapter.addChat(chatRight)
        }

        aponis2.forEach {
            val chatLeft = ChatData(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }

        val choice = ChatData(data, 2) //선택지
        chatAdapter.addChat(choice)
    }

    fun initRcv() {
        chatAdapter = SecondDayChatAdapter(this)
        rcv_second_chat.adapter = chatAdapter
    }
}