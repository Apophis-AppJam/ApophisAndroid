package com.example.apophis_android.data.remote.response

import com.example.apophis_android.data.entity.Choice

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
data class ChoiceResponse(
    val info: String,
    val day: Int,
    val replyNum: Int,
    val choiceWords: ArrayList<Choice>
)