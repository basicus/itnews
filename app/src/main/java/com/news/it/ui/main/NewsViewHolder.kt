package com.news.it.ui.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.news.it.model.NewsItem
import kotlinx.android.synthetic.main.news_item_layout.view.*

class NewsViewHolder(view_: View) : RecyclerView.ViewHolder(view_) {
    val view = view_
    private val titleTV: TextView = view_.newsTitle
    private val descriptionTV: TextView = view_.newsDescription
    private val creatorTV: TextView = view_.newsCreator

    fun bind(
        news: NewsItem,
        onClick: (Int?) -> Unit
    ) {
        titleTV.text = news.title
        descriptionTV.text = news.imageDesc
        val author = "Автор: ${news.creator}"
        creatorTV.text = author
        view.setOnClickListener { onClick(0) }
    }
}