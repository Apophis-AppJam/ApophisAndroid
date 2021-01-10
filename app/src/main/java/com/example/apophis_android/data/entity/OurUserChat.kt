package com.example.apophis_android.data.entity

/**
 * Created By kimdahyee
 * on 01월 03일, 2020
 */

/* 리팩토링을 위한 data class */
data class OurUserChat(
    val content: MutableList<String>,
    val tag: Int
)