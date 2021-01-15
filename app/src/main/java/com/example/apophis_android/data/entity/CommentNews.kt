package com.example.apophis_android.data.entity

import android.media.Image

/**
 * Created By hanjaehyeon
 */

data class CommentNews(
    val image: Image,
    val time: String,
    val name: String,
    val comment: String
)