package com.news.it.ds

import com.fasterxml.jackson.annotation.JsonProperty

data class BaseRss(
    @JsonProperty("channel")
    val channel: Channel?
)

data class Channel(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("item")
    val item: List<Item>?
)

data class Item(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("link")
    val link: String?
)