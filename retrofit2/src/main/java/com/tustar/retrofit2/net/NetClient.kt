package com.tustar.retrofit2.net

import com.tustar.retrofit2.data.Repo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class NetClient {

    companion object {
        private const val API_URL = "https://api.github.com"
        val gitHub: GitHub
            get() {
                val retrofit = Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                // Create an instance of our GitHub API interface.
                return retrofit.create(GitHub::class.java)
            }
    }

    interface GitHub {

        @GET("/users/{user}/repos")
        fun repos(@Path("user") user: String): Call<List<Repo>>

    }


}