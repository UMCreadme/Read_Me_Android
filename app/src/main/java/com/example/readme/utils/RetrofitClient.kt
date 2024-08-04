package com.example.readme.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    const val BASE_URL = "" //나중에 BASE URL 추가
    //다른 url 사용하고 싶을 때 동적으로 변경 가능. 추가할 일이 있을까 논의?

    fun getClient(): Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}