package com.example.apophis_android.ui.secondDay

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.MergeAdapter
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.ChatData
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        getAponymousChatFromServer(1)

    }

    private fun initRcv() {
        aponymousChatAdapter = AponymousChatAdapter(this)
        userChatAdapter = UserChatAdapter(this)
        mergeAdpater = MergeAdapter(aponymousChatAdapter, userChatAdapter)
        rcv_second_chat.adapter = mergeAdpater
    }

    private fun getAponymousChatFromServer(chatDetailsIdx: Int) {
        apophisService.getInstance()
            .requestAponymousChat(
                jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw",
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
                            for (i in response.body()!!.data.chat.indices) {
                                Log.d("다혜", response.body()!!.data.chat[i].text)
                                val chatData =
                                    ChatData(mutableListOf(response.body()!!.data.chat[i].text), 0)
                                aponymousChatAdapter.addChat(chatData)
                            }
                        }
                    }
                }
            })
    }
}