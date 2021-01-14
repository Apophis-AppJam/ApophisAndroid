package com.example.apophis_android.data.remote

import com.example.apophis_android.data.remote.request.ReplyCategoryRequest
import com.example.apophis_android.data.remote.request.ReplyFourRequest
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.request.ReplyPictureRequest
import com.example.apophis_android.data.remote.request.SendLetterRequest
import com.example.apophis_android.data.remote.response.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

interface ApophisService {

    /* 채팅 */
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

    @POST("/reply/{chatDetailsIdx}/{replyNum}")
    fun requestOneReply(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int,
        @Path("replyNum") replyNum: Int,
        @Body body: ReplyOneRequest
    ): Call<BaseResponse<Unit>>

    @POST("/reply/{chatDetailsIdx}/{replyNum}")
    fun requestFourReply(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int,
        @Path("replyNum") replyNum: Int,
        @Body body: ReplyFourRequest
    ): Call<BaseResponse<Unit>>

    @POST("/reply/{chatDetailsIdx}/{replyNum}")
    fun requestPictureReply(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int,
        @Path("replyNum") replyNum: Int,
        @Body body: ReplyPictureRequest
    ): Call<BaseResponse<Unit>>

    @POST("/reply/{chatDetailsIdx}/{replyNum}")
    fun requestCategoryReply(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Path("chatDetailsIdx") chatDetailsIdx: Int,
        @Path("replyNum") replyNum: Int,
        @Body body: ReplyCategoryRequest
    ): Call<BaseResponse<Unit>>

    /* 편지 */
    @GET("/letter")
    fun requestReceiveLetter(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String
    ): Call<ReceiveLetterResponse>

    @POST("/letter")
    fun requestSendLetter(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("jwt") jwt: String,
        @Body body: SendLetterRequest
    ): Call<BaseResponse<Unit>>


    companion object {
        const val BASE_URL = "http://52.78.210.107:3000"

        @Volatile
        private var instance: ApophisService ?= null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: provideService(ApophisService::class.java).apply { instance = this }
        }
    }
}