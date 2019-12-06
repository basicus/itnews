package com.news.it.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import kotlin.random.Random

data class RssRoot(
    @JsonProperty("channel")
    val channel: Channel?
)

data class Channel(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("item")
    val RssItem: List<NewsItem>?
)

data class NewsItem(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("pubDate")
    val pubDate: Date?,
    @JsonProperty("link")
    val link: String?,
    @JsonProperty("dc")
    val creator: String?
) {
    var imageLink: String = ""
    var imageDesc: String = ""
    //todo: temp filed, test for RssStateView
    val read: Boolean = Random.nextBoolean()

    init {
        val split: List<String>? = description?.split("\n")
        imageDesc = split?.get(0) ?: ""
        imageLink = split?.get(1)?.substringAfter("src=&quot;")?.substringBefore("&quot;") ?: ""
    }
}