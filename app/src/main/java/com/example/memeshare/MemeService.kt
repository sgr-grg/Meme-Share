package com.example.memeshare

import retrofit2.Call
import retrofit2.http.GET

interface MemeService {

    @GET("gimme/")
    fun getMeme(): Call<Meme>
}