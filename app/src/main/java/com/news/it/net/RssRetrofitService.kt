package com.news.it.net

import com.news.it.model.RssRoot
import retrofit2.Response
import retrofit2.http.GET

interface RssRetrofitService {
    @GET("all")
    suspend fun getData(): Response<RssRoot>
}