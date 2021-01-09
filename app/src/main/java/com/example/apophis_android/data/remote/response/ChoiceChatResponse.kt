package com.example.apophis_android.data.remote.response

import com.example.apophis_android.data.entity.ChoiceChat
import com.google.gson.annotations.SerializedName

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
data class ChoiceChatResponse(
    val info: String,
    val day: Int,
    val replyNum: Int,
    @SerializedName("ChoiceWords")
    val choiceWords: ArrayList<ChoiceChat>
)