package com.example.newsinshort.data.api

import com.example.newsinshort.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.Key

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country:String,
        @Query("apiKey") apiKey: String="e09cd8d6f0224f8f907a80217249eee0"
    ):Response<NewsResponse>
}