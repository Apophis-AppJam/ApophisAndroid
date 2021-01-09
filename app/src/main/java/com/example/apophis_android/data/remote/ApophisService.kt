package com.example.apophis_android.data.remote

import com.example.apophis_android.data.remote.response.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

interface ApophisService {

    @GET("/chat/{chatDetailsIdx}")
    fun requestAponymousChat(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int
    ): Call<BaseResponse<AponymousChatResponse>>

    @GET("/choice/{chatDetailsIdx}")
    fun requestChoiceChat(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int
    ): Call<BaseResponse<ChoiceChatResponse>>

    companion object {
        const val BASE_URL = "http://52.78.210.107:3000"

        @Volatile
        private var instance: ApophisService ?= null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: provideService(ApophisService::class.java).apply { instance = this }
        }
    }
}