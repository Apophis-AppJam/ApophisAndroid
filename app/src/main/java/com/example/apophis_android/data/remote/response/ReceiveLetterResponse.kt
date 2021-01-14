package com.example.apophis_android.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created By kimdahyee
 * on 01월 14일, 2020
 */
 
data class ReceiveLetterResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data
) {
    data class Data (
        val LetterIdx: Int,
        val text: String
    )
}