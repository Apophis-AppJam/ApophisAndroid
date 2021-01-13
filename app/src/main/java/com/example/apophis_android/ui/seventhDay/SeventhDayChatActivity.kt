package com.example.apophis_android.ui.seventhDay

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.seventhDay.seventhAdapter.SeventhChatAdapter
import com.example.apophis_android.ui.sixthDay.ScrollLinearLayoutManager
import kotlinx.android.synthetic.main.activity_seventh_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeventhDayChatActivity : AppCompatActivity() {

    private lateinit var seventhUserChatAdapter: SeventhChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 126


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_chat)

        initRcv()

        // 7일차 시작 인덱스 126
        getAponymousChatFromServer(jwt, chatDetailsIdx)

        seventh_et_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                seventh_btn_chat_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        seventhUserChatAdapter.setOnItemClickListener(object : SeventhChatAdapter.OnItemClickListener {
            override fun onItemClick(data: String) {
                // override fun onItemClick(data: MutableList<String>) {

                seventh_et_chat_message.setText(data)
                seventh_et_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                seventh_btn_chat_send.setImageResource(R.drawable.btn_send_act)

                /*for (i in dataList.indices) {
                    et_second_chat_message.setText(dataList[i])
                    et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_chat_send.setImageResource(R.drawable.btn_send_act)
                }*/
            }
        })

    }

    private fun initRcv() {
        seventhUserChatAdapter = SeventhChatAdapter(this)
        seventh_rcv_chat.adapter = seventhUserChatAdapter
        seventh_rcv_chat.layoutManager = ScrollLinearLayoutManager(this, 12)
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
                                Log.d("다다 아포가 받은 idx", chatDetailsIdx.toString())
                                for (i in response.body()!!.data.chat.indices) {

                                    val nextAction = response.body()!!.data.chat[i].nextAction
                                    if (nextAction == "채팅 이미지") {
                                        tag = 1
                                    }

                                    val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                    seventhUserChatAdapter.addChat(aponymousChatData)
                                    seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                }

                                val replyType = response.body()!!.data.postInfo.replyType
                                tag = if (replyType == "단일 보기 버튼" || replyType == "다중 보기 버튼" || replyType == "단일 보기 선택" || replyType == "다중 보기 버튼") {
                                    3
                                } else if (replyType == "단답형 텍스트 입력") {
                                    4
                                } else if (replyType == "타로 방식 시작") {
                                    5
                                } else if (replyType == "기능 액션 버튼 - 두개의 나 ") {
                                    6
                                } else {
                                    2
                                }
                                Log.d("다다 아포에서 보내는 idx", chatDetailsIdx.toString())
                                getChoiceChatFromServer(jwt, chatDetailsIdx, tag)

                                when (tag) {
                                    0, 1, 2, 3 -> {
                                        /* 메세지 전송 버튼 클릭 시 */
                                        seventh_btn_chat_send.setOnClickListener {
                                            seventhUserChatAdapter.removeChat()
                                            val userChoice = seventh_et_chat_message.text.toString()
                                            val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                            /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                            seventhUserChatAdapter.addChat(chatRight)
                                            seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                            seventh_et_chat_message.setText("")
                                            Log.d("다다 여기로 잘 들어왔어", "클릭 리스너, $chatRight")
                                            Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                            postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
                                        }
                                    }
                                    else -> {
                                        /* 메세지 전송 버튼 클릭 시 */
                                        seventh_btn_chat_send.setOnClickListener {
                                            seventhUserChatAdapter.removeChat()
                                            val userChoice = seventh_et_chat_message.text.toString()
                                            val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                            /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                            seventhUserChatAdapter.addChat(chatRight)
                                            seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                            seventh_et_chat_message.setText("")
                                            Log.d("다다 이제 여기서 어쩔거야", "클릭 리스너, $chatRight")
                                            Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                            postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
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
                                seventhUserChatAdapter.addChat(choiceChatData)
                            }
                        }
                    }
                })
    }

    private fun postReplyToServer(jwt: String, chatDetailsIdx: Int, replyNum: Int, replyString: String) {
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
                                Log.d("다다 reply에 들어온 idx", chatDetailsIdx.toString())
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                Log.d("다다 reply에서 보내는 idx", (chatDetailsIdx+1).toString())
                                seventh_btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                                seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                            }
                        }
                    }
                })
    }
}