package com.example.apophis_android.ui.main.letter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.SendLetterRequest
import com.example.apophis_android.data.remote.response.BaseResponse
import kotlinx.android.synthetic.main.activity_send_letter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendLetterActivity : AppCompatActivity() {

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4IjoxNywiaWF0IjoxNjEwNjU0NTEwLCJleHAiOjE2MTEyNTkzMTAsImlzcyI6ImFwb3BoaXMifQ.0i5NzM5zLDRmN_gW_7yuEY5wDsssoHDT0pDe_CNc4h8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_letter)

        btn_letter_back_to_receive.setOnClickListener { onBackPressed() }

        btn_letter_send.setOnClickListener {
            val text = et_letter_send_content.editableText.toString()
            postSendLetterToServer(jwt, text)
            finish()
        }
    }

    private fun postSendLetterToServer(jwt: String, text: String) {
        apophisService.getInstance()
            .requestSendLetter(
                jwt = jwt,
                body = SendLetterRequest(text)
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
                            Log.d("다혜 편지 전송 성공", text)
                        }
                    }
                }
            })
    }
}