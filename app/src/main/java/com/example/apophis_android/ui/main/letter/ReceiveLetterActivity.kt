package com.example.apophis_android.ui.main.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ReceiveLetterResponse
import kotlinx.android.synthetic.main.activity_receive_letter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiveLetterActivity : AppCompatActivity() {

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4IjoxNywiaWF0IjoxNjEwNjU0NTEwLCJleHAiOjE2MTEyNTkzMTAsImlzcyI6ImFwb3BoaXMifQ.0i5NzM5zLDRmN_gW_7yuEY5wDsssoHDT0pDe_CNc4h8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_letter)

        getReceiveLetterFromServer(jwt)

        btn_letter_back_to_main.setOnClickListener { onBackPressed() }

        btn_letter_go_to_write.setOnClickListener {
            val intent = Intent(this, SendLetterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun getReceiveLetterFromServer(jwt: String) {
        apophisService.getInstance()
            .requestReceiveLetter(
                jwt = jwt
            ).enqueue(object : Callback<ReceiveLetterResponse> {
                override fun onFailure(
                    call: Call<ReceiveLetterResponse>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<ReceiveLetterResponse>,
                    response: Response<ReceiveLetterResponse>
                ) {
                    //통신 성공
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            tv_letter_receive_content.text = response.body()!!.data.text
                        }
                    }
                }
            })
    }
}