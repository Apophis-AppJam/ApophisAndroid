package com.example.apophis_android.data.remote.response

import com.example.apophis_android.data.entity.AponymousChat

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
data class AponymousChatResponse(
    val chat: ArrayList<AponymousChat>,
    val postInfo: PostInfo
) {
    data class PostInfo(
        val info: String,
        val replyType: String
    )
}