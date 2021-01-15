package com.example.apophis_android.ui.seventhDay

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.airbnb.lottie.LottieAnimationView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.seventhDay.adapter.SeventhChatAdapter
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity
import com.example.apophis_android.ui.sixthDay.ScrollLinearLayoutManager
import kotlinx.android.synthetic.main.activity_second_day_chat.*
import kotlinx.android.synthetic.main.activity_seventh_day_chat.*
import kotlinx.android.synthetic.main.activity_seventh_day_chat.seventh_btn_chat_send
import kotlinx.android.synthetic.main.item_chat_coin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SeventhDayChatActivity : AppCompatActivity() {

    private lateinit var seventhUserChatAdapter: SeventhChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 130
    private lateinit var VIDEO_PATH: String
    private var randomCoin = ""

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
                seventh_btn_chat_send.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        seventhUserChatAdapter.setOnItemClickListener(object : SeventhChatAdapter.OnItemClickListener {
            override fun onItemClick(data: String) {
                seventh_et_chat_message.setText(data)
                seventh_et_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                seventh_btn_chat_send.setImageResource(R.drawable.btn_send_act)
                seventh_btn_chat_send.isEnabled = true
            }
        })

        /* random coin listener 재정의 */
        seventhUserChatAdapter.setOnRandomCoinListener(object : SeventhChatAdapter.OnRandomCoinListener {
            override fun onRandomCoin(data: String) {
                seventh_videoView.isInvisible = false
                seventh_cl_chat_header.bringToFront()
                seventh_cl_chat_bottom.bringToFront()
                seventh_rcv_chat.bringToFront()
                randomCoin = data

                if (randomCoin == "1") {
                    VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.coin_star
                } else {
                    VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.coin_moon
                }

                var uri: Uri = Uri.parse(VIDEO_PATH)
                seventh_videoView.setVideoURI(uri)
                seventh_videoView.setMediaController(MediaController(this@SeventhDayChatActivity))

                seventh_videoView.setOnPreparedListener { seventh_videoView.start() }

            }
        })

        /* delay click listener 재정의*/
        seventhUserChatAdapter.setOnDelayClickListener(object : SeventhChatAdapter.OnDelayClickListener {
            override fun onDelayClick(data: String) {
                Handler().postDelayed({
                    seventh_et_chat_message.setText(data)
                    seventh_et_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    seventh_btn_chat_send.setImageResource(R.drawable.btn_send_act)
                    seventh_btn_chat_send.isEnabled = true
                }, 11000)
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

                                    if (nextAction == "카운트다운 애니메이션") {
                                        Handler().postDelayed({
                                            var FINAL_VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.countdown_realfin
                                            var uri: Uri = Uri.parse(FINAL_VIDEO_PATH)
                                            seventh_videoView.setVideoURI(uri)
                                            seventh_videoView.bringToFront()
                                            seventh_videoView.setMediaController(MediaController(this@SeventhDayChatActivity))
                                            seventh_videoView.setOnPreparedListener { seventh_videoView.start() }
                                        }, 500)

                                        Handler().postDelayed({
                                            val seventhLottieFinal: LottieAnimationView = findViewById(R.id.seventh_lottie_final)
                                            seventhLottieFinal.bringToFront()
                                            seventhLottieFinal.setAnimation(R.raw.whitelight)
                                            seventhLottieFinal.playAnimation()
                                        }, 12500)

                                        Handler().postDelayed({
                                            seventh_videoView.isInvisible = true
                                            seventh_lottie_final.isInvisible = true
                                        }, 16000)
                                    }

                                    val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                    seventhUserChatAdapter.addChat(aponymousChatData)
                                    seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                }

                                val replyType = response.body()!!.data.postInfo.replyType
                                tag = if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택") {
                                    3
                                } else if (replyType == "다중 보기 선택 - 통신장애") {
                                    4
                                } else if (replyType == "동전") {
                                    5
                                } else if (replyType == "장문형 텍스트 입력") {
                                    7
                                } else if (replyType == "엔딩 뷰") {
                                    8
                                } else if (replyType == "reply 없음") {
                                    9
                                }
                                else {
                                    2
                                }
                                Log.d("다다 아포에서 보내는 idx", chatDetailsIdx.toString())

                                when (tag) {
                                    0, 1, 2, 3, 5 -> {
                                        /* 메세지 전송 버튼 클릭 시 */
                                        getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
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
                                    4 -> {
                                        /* 메세지 전송 버튼 클릭 시 */
                                        getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                        seventh_btn_chat_send.setOnClickListener {
                                            seventhUserChatAdapter.removeChat()
                                            val userChoice = seventh_et_chat_message.text.toString()
                                            val chatRight =
                                                OurUserChat(mutableListOf(userChoice), 6)
                                            /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                            seventhUserChatAdapter.addChat(chatRight)
                                            seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                            seventh_et_chat_message.setText("")
                                            Log.d("다다 여기로 잘 들어왔어", "클릭 리스너, $chatRight")
                                            Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                            postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
                                        }
                                    }
                                    7 -> {
                                        seventh_btn_chat_send.isEnabled = true
                                        seventh_btn_chat_send.setOnClickListener {
                                            val userChoice = seventh_et_chat_message.text.toString()
                                            val chatRight =
                                                OurUserChat(mutableListOf(userChoice), 2)
                                            /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                            seventhUserChatAdapter.addChat(chatRight)
                                            seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                                            seventh_et_chat_message.setText("")
                                            Log.d("다다 여기로 잘 들어왔어", "클릭 리스너, $chatRight")
                                            Log.d("다다 reply로 보내는 idx", chatDetailsIdx.toString())
                                            postReplyToServer(jwt, chatDetailsIdx, 1, userChoice)
                                        }
                                    }
                                    9 -> {
                                        Handler().postDelayed({
                                            val intent = Intent(this@SeventhDayChatActivity, SeventhDayTarotActivity::class.java)
                                            intent.putExtra("chatDetailIdx", chatDetailsIdx.toString())
                                            startActivity(intent)
                                            getAponymousChatFromServer(jwt, chatDetailsIdx + 10)
                                        }, 10000)
                                    }
                                    10 -> {
                                        btn_chat_send.setOnClickListener(null)
                                    }
                                    else -> {
                                        /* 메세지 전송 버튼 클릭 시 */
                                        getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
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
                                if (randomCoin == "0") {getAponymousChatFromServer(jwt, chatDetailsIdx + 2) }
                                else {getAponymousChatFromServer(jwt, chatDetailsIdx + 1)}
                                Log.d("다다 reply에서 보내는 idx", (chatDetailsIdx+1).toString())
                                seventh_btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                                seventh_btn_chat_send.isEnabled = false
                                seventh_rcv_chat.smoothScrollToPosition(seventhUserChatAdapter.itemCount - 1)
                            }
                        }
                    }
                })
    }
}