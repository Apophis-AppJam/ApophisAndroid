package com.example.apophis_android.ui.firstDay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyCategoryRequest
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.request.ReplyPictureRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.firstDay.adapter.FirstDayChatAdapter
import com.example.apophis_android.ui.main.MainActivity.Companion.countCameraChange
import kotlinx.android.synthetic.main.activity_first_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created By hanjaehyeon
 */

class FirstDayChatActivity : AppCompatActivity() {

    private lateinit var userChatAdapter: FirstDayChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4IjoxMiwiaWF0IjoxNjEwNjUyOTk2LCJleHAiOjE2MTEyNTc3OTYsImlzcyI6ImFwb3BoaXMifQ.dWYb7OFX-mxfQNVvtPL7VomaS6I9yIvTkUROKMAqOVI"
        //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_day_chat)

        initRcv()

        // 1일차 chatDetailsIdx : 1 ~ 22
        getAponymousChatFromServer(jwt, chatDetailsIdx)

        et_first_chat_message.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_first_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initRcv() {
        userChatAdapter =
            FirstDayChatAdapter(
                this
            )
        rcv_first_chat.adapter = userChatAdapter
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
                                tag = 0
                                val nextAction = response.body()!!.data.chat[i].nextAction
                                if (nextAction == "채팅 이미지") {
                                    tag = 1
                                } else if (nextAction == "백그라운드 이미지 - 바다 뷰") {
                                    seaBackground()
                                }

                                Log.d("tag", tag.toString())
                                val aponymousChatData = OurUserChat(
                                    mutableListOf(response.body()!!.data.chat[i].text),
                                    tag
                                )
                                userChatAdapter.addChat(aponymousChatData)
                            }

                            val replyType = response.body()!!.data.postInfo.replyType
                            tag = tagClassification(replyType)

                            var categorySentence = ""
                            var datalist: MutableList<String> = mutableListOf()

                            when (tag) {
                                0, 1, 2, 5, 6 -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_first_send.setOnClickListener {
                                        userChatAdapter.removeChat()
                                        val userChoice = et_first_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        userChatAdapter.addChat(chatRight)
                                        et_first_chat_message.setText("")
                                        btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                        postReplyToServer(jwt, chatDetailsIdx, 1, userChoice, tag)
                                    }

                                    /* chip click listener 재정의 */
                                    userChatAdapter.setOnItemClickListener(object :
                                        FirstDayChatAdapter.OnItemClickListener {
                                        var indexlist: MutableList<Int?> = mutableListOf(-1)
                                        override fun onItemClick(data: String, index: Int?) {
                                            if (indexlist.contains(index)) {
                                                indexlist.remove(index)
                                                et_first_chat_message.setText("")
                                                btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                            } else {
                                                indexlist.add(index)
                                                et_first_chat_message.setText(data)
                                                btn_first_send.setImageResource(R.drawable.btn_send_act)
                                            }
                                        }
                                    })
                                }
                                8 -> {
                                    btn_first_send.setOnClickListener {
                                        val userChoice = et_first_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        userChatAdapter.addChat(chatRight)
                                        et_first_chat_message.setText("")
                                        btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                        postReplyToServer(jwt, chatDetailsIdx, 1, userChoice, tag)
                                    }
                                }
                                9 -> { // 카테고리
                                    /* 메세지 전송 버튼 클릭 시 */
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_first_send.setOnClickListener {
                                        userChatAdapter.removeChat()
                                        val userChoice = et_first_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        userChatAdapter.addChat(chatRight)
                                        et_first_chat_message.setText("")
                                        btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                        datalist.add(categorySentence)
                                        postCategoryToServer(jwt, chatDetailsIdx, 3, datalist)
                                    }

                                    /* chip click listener 재정의 */
                                    userChatAdapter.setOnItemClickListener(object :
                                        FirstDayChatAdapter.OnItemClickListener {
                                        override fun onItemClick(
                                            categotyData: String,
                                            categoryIndex: Int?
                                        ) {
                                            if (datalist.contains(categotyData)) {
                                                datalist.remove(categotyData)
                                            } else {
                                                datalist.add(categotyData)
                                            }
                                            if (datalist.size == 3) {
                                                categorySentence = getcategorySentence(datalist)
                                                et_first_chat_message.setText(categorySentence)
                                                btn_first_send.setImageResource(R.drawable.btn_send_act)
                                            } else {
                                                et_first_chat_message.setText("")
                                                btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                            }
                                        }
                                    })
                                }
                                    11 -> {
                                        val user = ""
                                        val chat = OurUserChat(mutableListOf(user), 11)
                                        userChatAdapter.addChat(chat)
                                        btn_first_send.setOnClickListener(null)
                                }
                                else -> {
                                    /* 메세지 전송 버튼 클릭 시 */
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_first_send.setOnClickListener {
                                        userChatAdapter.removeChat()
                                        val userChoice = et_first_chat_message.text.toString()
                                        val chatRight = OurUserChat(mutableListOf(userChoice), 2)
                                        /* tag == 2 -> user가 보내는 보라색 말풍선 */
                                        userChatAdapter.addChat(chatRight)
                                        et_first_chat_message.setText("")
                                        btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                        postReplyToServer(jwt, chatDetailsIdx, 1, userChoice, tag)
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
                            userChatAdapter.addChat(choiceChatData)
                        }

                    }
                }
            })
    }

    private fun postReplyToServer(
        jwt: String,
        chatDetailsIdx: Int,
        replyNum: Int,
        replyString: String,
        tag: Int
    ) {
        if (tag == 6) {
            getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
        } else {
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
                                if (chatDetailsIdx < 23) {
                                    Log.i("아포",chatDetailsIdx.toString())
                                    getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                    Log.i("아포",chatDetailsIdx.toString())
                                    btn_first_send.setImageResource(R.drawable.btn_send_unact)
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun postPictureToServer(
        jwt: String,
        chatDetailsIdx: Int,
        replyNum: Int,
        image: File
    ) {
        apophisService.getInstance()
            .requestPictureReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyPictureRequest(image)
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
                            if (chatDetailsIdx < 23) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_first_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun postCategoryToServer(
        jwt: String,
        chatDetailsIdx: Int,
        replyNum: Int,
        reply: MutableList<String>
    ) {
        apophisService.getInstance()
            .requestCategoryReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyCategoryRequest(reply[1], reply[2], reply[3], reply[0])
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
                            if (chatDetailsIdx < 23) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_first_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun tagClassification(replyType: String): Int {
        if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택") {
            return 5
        } else if (replyType == "단답형 텍스트 입력") {
            return 7
        } else if (replyType == "기능 액션 버튼 - 나침반") {
            return 6
        } else if (replyType == "기능 액션 버튼 - 카메라") {
            return 10
        } else if (replyType == "단일 보기 선택") {
            return 2
        } else if (replyType == "일차 종료 (reply 없음)") {
            return 11
        } else if (replyType == "카테고리 선택") {
            return 9
        } else {
            return 8
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == FirstDayChatAdapter.CAMERA_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val uri: Uri? = data?.getParcelableExtra("savedUri")
                userChatAdapter.removeChat()
                val aponymousChatData: OurUserChat
                Log.i("count", countCameraChange.toString())
                if (countCameraChange) {
                    aponymousChatData = OurUserChat(
                        mutableListOf(uri.toString()),
                        3
                    )
                } else {
                    aponymousChatData = OurUserChat(
                        mutableListOf(uri.toString()),
                        4
                    )
                }
                userChatAdapter.addChat(aponymousChatData)
                //postPictureToServer(jwt, chatDetailsIdx, 0, File(uri.toString()))
                if (chatDetailsIdx < 23) {
                    getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                    btn_first_send.setImageResource(R.drawable.btn_send_unact)
                }
            }
        }
    }

    private fun getcategorySentence(categoryData: MutableList<String>) : String {
        var categorySentence = "나는 "
        Log.i("log",categoryData.toString())
        for(i in 0..1) {
            if (categoryData[i].contains("한")) {
                categorySentence += categoryData[i].substring(0, categoryData[i].length - 1) + "하고, "
            } else if (categoryData[i].contains("인")) {
                categorySentence += categoryData[i].substring(0, categoryData[i].length - 1) + "이고, "
            } else if (categoryData[i].contains("는") || categoryData[i].contains("은") ||
                categoryData[i].contains("센")) {
                categorySentence += categoryData[i].substring( 0, categoryData[i].length - 1) + "고, "
            } else if(categoryData[i].contains("운")) {
                categorySentence += categoryData[i].substring( 0, categoryData[i].length - 2) + "럽고, "
            }
        }
        categorySentence += categoryData[2] + " 사람이야."
        return categorySentence
    }

    private fun seaBackground() {
        rcv_first_chat.setBackgroundResource(R.color.transparency00FF)
        cl_first_chat_bottom.setBackgroundResource(R.color.transparency00FF)
        cl_first_chat_header.setBackgroundResource(R.color.transparency00FF)
        constraintLayout_first.setBackgroundResource(R.drawable.img_sea_bg)
        Handler().postDelayed({
            rcv_first_chat.setBackgroundResource(R.color.black262627)
            cl_first_chat_bottom.setBackgroundResource(R.color.black2C2C2D)
            cl_first_chat_header.setBackgroundResource(R.color.black2C2C2D)
            constraintLayout_first.setBackgroundResource(R.color.black262627)
        }, 40000)
    }
}