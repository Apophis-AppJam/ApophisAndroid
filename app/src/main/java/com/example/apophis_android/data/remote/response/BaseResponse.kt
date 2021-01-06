package com.example.apophis_android.data.remote.response

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
data class BaseResponse<T>(
        val status: Int,
        val success: Boolean,
        val message: String,
        val data: T
)