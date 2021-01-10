package com.example.apophis_android.ui.secondDay

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.MergeAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.entity.OurAponymousChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.secondDay.adpater.AponymousChatAdapter
import com.example.apophis_android.ui.secondDay.adpater.UserChatAdapter
import kotlinx.android.synthetic.main.activity_second_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondDayChatActivity : AppCompatActivity() {

    private lateinit var aponymousChatAdapter: AponymousChatAdapter
    private lateinit var userChatAdapter: UserChatAdapter
    private var mergeAdpater: MergeAdapter ?= null

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        getAponymousChatFromServer(jwt, 5)
        getChoiceChatFromServer(jwt, 5, 1)
        /* replyType 별로 tag 값 달리 지정해서 inflate 되는 뷰 지정하는 작업 해야해 */

        et_second_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_chat_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        userChatAdapter.setOnItemClickListener(object : UserChatAdapter.OnItemClickListener {
            override fun onItemClick(dataList: MutableList<String>) {
                for (i in dataList.indices) {
                    et_second_chat_message.setText(dataList[i])
                    et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_chat_send.setImageResource(R.drawable.btn_send_act)
                }
            }
        })

        /* 메세지 전송 버튼 클릭 시 */
        btn_chat_send.setOnClickListener {
            userChatAdapter.removeChat()
            val userChoice = et_second_chat_message.text.toString()
            val chatRight = OurUserChat(mutableListOf(userChoice), 0)
            /* tag == 0 -> user가 보내는 보라색 말풍선 */
            userChatAdapter.addChat(chatRight)
            et_second_chat_message.setText("")
        }

    }

    private fun initRcv() {
        aponymousChatAdapter = AponymousChatAdapter(this)
        userChatAdapter = UserChatAdapter(this)
        mergeAdpater = MergeAdapter(aponymousChatAdapter, userChatAdapter)
        rcv_second_chat.adapter = mergeAdpater
    }

    private fun getAponymousChatFromServer(jwt: String, chatDetailsIdx: Int) {
        //서버한테 받은 replyType 값 tag로 바꿔서 return 해주기
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
                            val replyType = response.body()!!.data.postInfo.replyType
                            for (i in response.body()!!.data.chat.indices) {
                                val nextAction = response.body()!!.data.chat[i].nextAction
                                val aponymousChatData = OurAponymousChat(response.body()!!.data.chat[i].text, 0)

                                if (nextAction == "채팅 이미지") {
                                    aponymousChatData.tag = 1
                                }
                                aponymousChatAdapter.addChat(aponymousChatData)
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
                            /* replyNum에 따라 칩 선택 개수 제한 걸어주는 작업 해야해 */
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
}