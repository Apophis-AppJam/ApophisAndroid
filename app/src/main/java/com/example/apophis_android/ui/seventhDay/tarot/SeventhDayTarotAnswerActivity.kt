package com.example.apophis_android.ui.seventhDay.tarot

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import kotlinx.android.synthetic.main.activity_seventh_day_tarot_answer.*
import kotlinx.android.synthetic.main.fragment_seventh_day_tarot_first.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeventhDayTarotAnswerActivity : AppCompatActivity() {

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 131

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_tarot_answer)

        tarot_answer_btn_back.setOnClickListener { onBackPressed() }

        chatDetailsIdx = intent.getIntExtra("Idx", 135)
        Log.d("성림 앤써 서통 제발 좀...", chatDetailsIdx.toString())
        getAponymousChatFromServer(jwt,chatDetailsIdx)


        tarot_answer_complete_btn.setOnClickListener {
            onBackPressed()
            val newIntent = Intent(this, SeventhDayTarotActivity::class.java)
            newIntent.putExtra("Answer", tarot_vp_answer_ed.text)
            Log.d("성림", tarot_vp_answer_ed.text.toString())
            setResult(RESULT_OK)
            finish()
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
                            Log.d("성림 아포가 받은 idx", chatDetailsIdx.toString())

                            val tarot = response.body()!!.data.chat[0].text
                            Log.d("성림 tarot", tarot)

                            tarot_answer_header.text = tarot
                        }
                    }
                }
            })
    }
}