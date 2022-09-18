package com.example.memeshare

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientInstance {

    private const val BASE_URL = "https://meme-api.herokuapp.com/"
    val memeInstance: MemeService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        memeInstance = retrofit.create(MemeService::class.java)
    }
}