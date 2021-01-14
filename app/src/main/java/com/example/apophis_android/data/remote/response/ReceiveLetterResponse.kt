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
    val data: ArrayList<Data>
) {
    data class Data (
        val text: String
    )
}