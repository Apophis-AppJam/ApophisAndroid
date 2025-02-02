package com.example.apophis_android.ui.sixthDay

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
//import com.airbnb.lottie.LottieAnimationView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.sixthDay.adapter.SixthDayChatAdapter
import kotlinx.android.synthetic.main.activity_seventh_day_chat.*
import kotlinx.android.synthetic.main.activity_sixth_day_chat.*
import kotlinx.android.synthetic.main.activity_sixth_day_chat.sixth_btn_chat_send
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SixthDayChatActivity : AppCompatActivity() {

    private lateinit var sixthUserChatAdapter: SixthDayChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 124

    private var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth_day_chat)
        sixth_btn_back.setOnClickListener { onBackPressed() }

        initRcv()

        // 6일차 시작 인덱스 101
        getAponymousChatFromServer(jwt, chatDetailsIdx)

        sixth_et_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sixth_btn_chat_send.setImageResource(R.drawable.btn_send_act)
                sixth_btn_chat_send.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        sixthUserChatAdapter.setOnItemClickListener(object : SixthDayChatAdapter.OnItemClickListener {
            override fun onItemClick(data: String) {
                // override fun onItemClick(data: MutableList<String>) {

                sixth_et_chat_message.setText(data)
                sixth_et_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                sixth_btn_chat_send.setImageResource(R.drawable.btn_send_act)

                /*for (i in dataList.indices) {
                    et_second_chat_message.setText(dataList[i])
                    et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_chat_send.setImageResource(R.drawable.btn_send_act)
                }*/
            }
        })

    }

    private fun initRcv() {
        sixthUserChatAdapter = SixthDayChatAdapter(this)
        sixth_rcv_chat.adapter = sixthUserChatAdapter
        sixth_rcv_chat.layoutManager = ScrollLinearLayoutManager(this, 12)
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

                                if (nextAction == "진동") {
                                    Handler().postDelayed({
                                        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                        vibrator?.vibrate(500)
                                    }, 2000)
                                }

                                if (nextAction == "진동, 찰칵 음성") {
                                    Handler().postDelayed({
                                        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                        vibrator?.vibrate(500)
                                    }, 1500)

                                    Handler().postDelayed({
                                        var shutterPlayer = MediaPlayer.create(this@SixthDayChatActivity, R.raw.shutter)
                                        shutterPlayer.start()
                                    }, 1000)
                                }

                                if (nextAction == "진동, 찰칵 음성, 애니메이션-카메라 조리개") {
                                    Handler().postDelayed({
                                        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                        vibrator?.vibrate(500)
                                    }, 1500)

                                    Handler().postDelayed({
                                        var shutterPlayer = MediaPlayer.create(this@SixthDayChatActivity, R.raw.shutter)
                                        shutterPlayer.start()

                                        val sixthLottieShutter: LottieAnimationView = findViewById(R.id.sixth_lottie_shutter)
                                        sixthLottieShutter.bringToFront()
                                        sixthLottieShutter.setAnimation(R.raw.day6_shutter)
                                        sixthLottieShutter.playAnimation()
                                    }, 1000)
                                }

                                val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                sixthUserChatAdapter.addChat(aponymousChatData)
                                sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                            }

                            val replyType = response.body()!!.data.postInfo.replyType
                            tag = if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택" || replyType == "카테고리 선택") {
                                3
                            } else if (replyType == "단답형 텍스트 입력") {
                                4
                            } else if (replyType == "기능 액션 버튼 - 얼룩 지우기") {
                                5
                            } else if (replyType == "장문형 텍스트 입력") {
                                6
                            } else if (replyType == "일차 종료 (reply 없음)") {
                                10
                            } else {
                                2
                            }
                            Log.d("다다 아포에서 보내는 idx", chatDetailsIdx.toString())

                            when (tag) {
                                0, 1, 2, 3 -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    sixth_btn_chat_send.setOnClickListener {
                                        sixthUserChatAdapter.removeChat()
                                        val userChoice = sixth_et_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        sixthUserChatAdapter.addChat(chatRight)
                                        sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                                        sixth_et_chat_message.setText("")
                                        Log.d("다다 여기로 잘 들어왔어", "클릭 리스너, $chatRight")
                                        Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                        postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                6 -> {
                                    sixth_et_chat_message.hint = "텍스트를 입력하세요"
                                    sixth_btn_chat_send.isEnabled = true
                                    sixth_btn_chat_send.setOnClickListener {
                                        val userChoice = sixth_et_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        sixthUserChatAdapter.addChat(chatRight)
                                        sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                                        sixth_et_chat_message.setText("")
                                        Log.d("다다 여기로 잘 들어왔어", "클릭 리스너, $chatRight")
                                        Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                        postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
                                        sixth_et_chat_message.hint = "선택지를 골라주세요"
                                    }
                                }
                                10 -> {
                                    val user = ""
                                    val chat = OurUserChat(mutableListOf(user), 10)
                                    sixthUserChatAdapter.addChat(chat)
                                    sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                                    sixth_btn_chat_send.setOnClickListener(null)
                                }
                                else -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    sixth_btn_chat_send.setOnClickListener {
                                        sixthUserChatAdapter.removeChat()
                                        val userChoice = sixth_et_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        sixthUserChatAdapter.addChat(chatRight)
                                        sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                                        sixth_et_chat_message.setText("")
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
                            sixthUserChatAdapter.addChat(choiceChatData)
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
                            sixth_btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                            sixth_btn_chat_send.isEnabled = false
                            sixth_rcv_chat.smoothScrollToPosition(sixthUserChatAdapter.itemCount - 1)
                        }
                    }
                }
            })
    }
}