package com.example.apophis_android.ui.firstDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import kotlinx.android.synthetic.main.activity_first_day_chat.*

class FirstDayChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: FirstDayChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_day_chat)

        chatAdapter = FirstDayChatAdapter(this)
        rcv_first_chat.adapter = chatAdapter

        val aponymous = mutableListOf("안녕! 미안 방금 정신없었지?",
            "그냥, 방금 뉴스 봤어? 지구가 멸망한\n다길래 아무 번호로나 전화해봤어. 꼭\n한 번쯤 해보고 싶었거든.")

//        val aponius = mutableListOf("넌 누구야")
//        val aponis2 = mutableListOf("다음에 너가 골라줘야해")
        val data = mutableListOf("넌 누구야?", "응.", "별로 믿기지 않아.")

        aponymous.forEach {
            //Log.d("아포니스가 무슨 말을 할지 보자", it)
            val chatLeft = OurUserChat(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }

//        aponius.forEach {
//            Log.d("아포니어스는??", it)
//            val chatRight = ChatData(mutableListOf(it), 1)
//            chatAdapter.addChat(chatRight)
//        }
//
//        aponis2.forEach {
//            val chatLeft = ChatData(mutableListOf(it), 0)
//            chatAdapter.addChat(chatLeft)
//        }

        val choice = OurUserChat(data, 2) //선택지
        chatAdapter.addChat(choice)

        chatAdapter.setOnChipItemClickListener(object : FirstDayChatAdapter.OnChipClickListener {
            override fun onChipClick(data: String) {
                // 아이템 클릭 이벤트를 MainActivity에서 처리.
                et_first_chat_message.setText(data)
            }
        })

        btn_first_send.setOnClickListener {
            val userChoice = et_first_chat_message.text.toString()

            val chatRight = OurUserChat(mutableListOf(userChoice), 1)
            chatAdapter.addChat(chatRight)
            chatAdapter.removeChat()
            Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show()
            et_first_chat_message.setText("")
            a()
        }
    }
    fun a(){
        val aponymous1 = mutableListOf("음.. 내가 누군지는 일단 비밀이라고 해둘게.\n몇가지만 알려주자면", "우선 나는 동쪽 끝에 살아 동쪽이 어딘지 알\n아?")
        val compass = mutableListOf("카메라")

        aponymous1.forEach {
            val chatLeft = OurUserChat(mutableListOf(it), 0)
            chatAdapter.addChat(chatLeft)
        }
        val compass_btn = OurUserChat(compass, 2) //선택지
        chatAdapter.addChat(compass_btn)
    }
//    private fun initRcv() {
//        chatAdapter = FirstDayChatAdapter(this)
//        rcv_first_chat.adapter = chatAdapter
//    }
}