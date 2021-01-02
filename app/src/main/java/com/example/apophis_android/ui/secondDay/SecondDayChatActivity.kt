package com.example.apophis_android.ui.secondDay

import android.graphics.Color
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

        val aponis = mutableListOf("정신없지? 시작하기 전에 물 한 잔 마시\n고 오는거 어때?")
        //val aponius = mutableListOf("넌 누구야")
        val aponis2 = mutableListOf("오늘 나는 너에게 가는 길에 눈길을\n밟았어!", "음 그러고보니 너는 여행을 떠날 때\n 어떨지 궁금하다",
        "만약에 여행을 떠난다면 언제 집을\n나서고 싶어?")
        val data = mutableListOf("응, 마셨어.", "딱히, 괜찮아.")

        aponis.forEach {
            Log.d("아포니스가 무슨 말을 할지 보자", it)
            val chatLeft = ChatData(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }

        /*aponius.forEach {
            Log.d("아포니어스는??", it)
            val chatRight = ChatData(mutableListOf(it), 1)
            chatAdapter.addChat(chatRight)
        }*/

        val choice = ChatData(data, 2) //선택지
        chatAdapter.addChat(choice)

        /*aponis2.forEach {
            val chatLeft = ChatData(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }*/


        /* chip click listener 재정의 */
        chatAdapter.setOnChipItemClickListener(object : SecondDayChatAdapter.OnChipClickListener {
            override fun onChipClick(data: String) {
                et_second_chat_message.setText(data)
            }
        })

        /* 메세지 전송 버튼 클릭 시 */
        btn_send.setOnClickListener {
            chatAdapter.removeChat()
            val userChoice = et_second_chat_message.text.toString()
            val chatRight = ChatData(mutableListOf(userChoice), 1)
            chatAdapter.addChat(chatRight)
            et_second_chat_message.setText("")
        }
    }

    private fun initRcv() {
        chatAdapter = SecondDayChatAdapter(this)
        rcv_second_chat.adapter = chatAdapter
    }
}