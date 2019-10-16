package com.news.it.net

import com.news.it.ds.BaseRss
import retrofit2.Call
import retrofit2.http.GET

interface RssService {
    @GET("all")
    fun getData(): Call<BaseRss>
}
