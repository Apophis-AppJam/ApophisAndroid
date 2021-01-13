package com.example.apophis_android.ui.secondDay

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyFourRequest
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.secondDay.adpater.ChatAdapter
import kotlinx.android.synthetic.main.activity_second_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondDayChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        // 2일차 시작 인덱스 23
        getAponymousChatFromServer(jwt, chatDetailsIdx)

        et_second_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_chat_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        chatAdapter.setOnItemClickListener(object : ChatAdapter.OnItemClickListener {
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
        chatAdapter = ChatAdapter(this)
        rcv_second_chat.adapter = chatAdapter
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
                            var tag = 0
                            for (i in response.body()!!.data.chat.indices) {

                                when (response.body()!!.data.chat[i].nextAction) {
                                    "채팅 이미지" -> {
                                        tag = 1
                                    }
                                }

                                val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                chatAdapter.addChat(aponymousChatData)
                            }

                            val replyType = response.body()!!.data.postInfo.replyType
                            tag = if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택" || replyType == "카테고리 선택") {
                                4
                            } else if (replyType == "단답형 텍스트 입력") {
                                5
                            } else if (replyType == "기능 액션 버튼 - 시간대 설정") {
                                6
                            } else if (replyType == "기능 액션 버튼 - 두개의 나 ") {
                                7
                            } else if (replyType == "기능 액션 버튼 - 가치 선택") {
                                8
                            } else {
                                3
                            }
                            Log.d("다다 아포에서 보내는 idx", chatDetailsIdx.toString())
                            getChoiceChatFromServer(jwt, chatDetailsIdx, tag)

                            when (tag) {
                                0, 1, 3, 4 -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chatRight)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                5 -> { //short answer
                                    chatAdapter.setCallbackListener(object : ChatAdapter.CallbackListener{
                                        override fun callBack(inputTextList: MutableList<String>) {
                                            postReplyFourToServer(jwt, chatDetailsIdx, 4, inputTextList)
                                        }

                                    })
                                }
                                else -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chatRight)
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
                            Log.d("다다 choice가 받은 idx", chatDetailsIdx.toString())
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
                            getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                            btn_chat_send.setImageResource(R.drawable.btn_send_unact)
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
                            getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                            btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                        }
                    }
                }
            })
    }
}