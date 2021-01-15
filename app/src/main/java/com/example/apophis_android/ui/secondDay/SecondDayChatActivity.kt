package com.example.apophis_android.ui.secondDay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyFourRequest
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.secondDay.adapter.SecondDayChatAdapter
import kotlinx.android.synthetic.main.activity_second_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondDayChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: SecondDayChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 23 // 2일차 시작 인덱스 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        getAponymousChatFromServer(jwt, chatDetailsIdx)

        btn_second_back.setOnClickListener { onBackPressed() }

        constraintLayout_second.setOnClickListener { hideKeyboard() }

        et_second_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_chat_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        chatAdapter.setOnItemClickListener(object : SecondDayChatAdapter.OnItemClickListener {
            override fun onItemClick(data: String) {
                // override fun onItemClick(data: MutableList<String>) {

                et_second_chat_message.setText(data)
                et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                btn_chat_send.setImageResource(R.drawable.btn_send_act)

                /*for (i in dataList.indices) {
                    et_second_chat_message.setText(dataList[i])
                    et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_chat_send.setImageResource(R.drawable.btn_send_act)
                }*/
            }
        })

    }

    private fun initRcv() {
        chatAdapter = SecondDayChatAdapter(this)
        rcv_second_chat.adapter = chatAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SecondDayChatAdapter.TIMER_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val text = data?.getStringExtra("text")
                et_second_chat_message.setText(text)
            }
        }
    }

    private fun getAponymousChatFromServer(jwt: String, chatDetailsIdx: Int) {
        apophisService.getInstance()
            .requestAponymousChat(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx
            ).enqueue(object : Callback<BaseResponse<AponymousChatResponse>> {
                override fun onFailure(
                    call: Call<BaseResponse<AponymousChatResponse>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<AponymousChatResponse>>,
                    response: Response<BaseResponse<AponymousChatResponse>>
                ) {
                    //통신 성공
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            var tag: Int
                            for (i in response.body()!!.data.chat.indices) {
                                tag = 0

                                val nextAction = response.body()!!.data.chat[i].nextAction
                                if (nextAction == "채팅 이미지") {
                                    tag = 1
                                } else if (nextAction == "백그라운드 이미지 - 눈길 뷰"){
                                    snowBackground()
                                } else if(nextAction == "음성송출1"){
                                    tag = 2
                                }

                                val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                chatAdapter.addChat(aponymousChatData)
                            }

                            val replyType = response.body()!!.data.postInfo.replyType
                            tag = if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택" || replyType == "카테고리 선택") { //chip_choice
                                4
                            } else if (replyType == "단답형 텍스트 입력") { //short answer
                                5
                            } else if (replyType == "기능 액션 버튼 - 시간대 설정") { //timer
                                6
                            } else if (replyType == "기능 액션 버튼 - 두개의 나 ") { //find_me
                                7
                            } else if (replyType == "기능 액션 버튼 - 가치 선택") { //value
                                8
                            } else if(replyType == "장문형 텍스트 입력") { // long answer
                                9
                            } else if (replyType == "일차 종료 (reply 없음)") { //end
                                10
                            } else {
                                3 //chat_user
                            }

                            when (tag) {
                                0, 1, 3, 4 -> {
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                5 -> { //short answer
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener(null)
                                    chatAdapter.setCallbackListener(object : SecondDayChatAdapter.CallbackListener{
                                        override fun callBack(inputTextList: MutableList<String>) {
                                            postReplyFourToServer(jwt, chatDetailsIdx, 4, inputTextList)
                                        }
                                    })
                                }
                                9 -> { //long answer
                                    btn_chat_send.setOnClickListener {
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                10 -> {
                                    val user = ""
                                    val chat = OurUserChat(mutableListOf(user), 10)
                                    chatAdapter.addChat(chat)
                                    btn_chat_send.setOnClickListener(null)
                                }
                                else -> {
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }

    private fun getChoiceChatFromServer(jwt: String, chatDetailsIdx: Int, tag: Int) {
        apophisService.getInstance()
            .requestChoiceChat(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx
            ).enqueue(object : Callback<BaseResponse<ChoiceChatResponse>> {
                override fun onFailure(
                    call: Call<BaseResponse<ChoiceChatResponse>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<ChoiceChatResponse>>,
                    response: Response<BaseResponse<ChoiceChatResponse>>
                ) {
                    //통신 성공
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val replyNum = response.body()!!.data.replyNum
                            val list = mutableListOf<String>()
                            for (i in response.body()!!.data.choiceWords.indices) {
                                list.add(response.body()!!.data.choiceWords[i].choiceWords)
                            }
                            val choiceChatData = OurUserChat(list, tag)
                            chatAdapter.addChat(choiceChatData)
                        }

                    }
                }
            })
    }

    private fun postReplyOneToServer(jwt: String, chatDetailsIdx: Int, replyNum: Int, replyString: String) {
        apophisService.getInstance()
            .requestOneReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyOneRequest(replyString)
            ).enqueue(object : Callback<BaseResponse<Unit>> {
                override fun onFailure(
                    call: Call<BaseResponse<Unit>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            if (chatDetailsIdx < 41) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun postReplyFourToServer(jwt: String, chatDetailsIdx: Int, replyNum: Int, reply: MutableList<String>) {
        apophisService.getInstance()
            .requestFourReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyFourRequest(reply[0], reply[1], reply[2], reply[3])
            ) .enqueue(object : Callback<BaseResponse<Unit>> {
                override fun onFailure(
                    call: Call<BaseResponse<Unit>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            if (chatDetailsIdx < 41) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_second_chat_message.windowToken, 0)
    }

    private fun snowBackground() {
        rcv_second_chat.setBackgroundResource(R.color.transparency00FF)
        cl_second_chat_bottom.setBackgroundResource(R.color.transparency00FF)
        cl_second_chat_header.setBackgroundResource(R.color.transparency00FF)
        constraintLayout_second.setBackgroundResource(R.drawable.bg_snowroadxx)
        Handler().postDelayed({
            rcv_second_chat.setBackgroundResource(R.color.black262627)
            cl_second_chat_bottom.setBackgroundResource(R.color.black2C2C2D)
            cl_second_chat_header.setBackgroundResource(R.color.black2C2C2D)
            constraintLayout_second.setBackgroundResource(R.color.black262627)
        }, 40000)
    }
}