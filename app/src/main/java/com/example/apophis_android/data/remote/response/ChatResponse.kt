package com.example.apophis_android.data.remote.response

import com.example.apophis_android.data.entity.ChatData

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
data class ChatResponse(
    val chatData: ArrayList<ChatData>,
    val info: String
)