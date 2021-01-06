package com.example.apophis_android.data.remote

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

interface ApophisService {

    companion object {
        const val BASE_URL = "http://52.78.210.107:3000"

        @Volatile
        private var instance: ApophisService ?= null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: provideService(ApophisService::class.java).apply { instance = this }
        }
    }
}