package com.news.it.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.it.R
import com.news.it.model.NewsItem
import com.news.it.model.RssRoot
import com.news.it.ui.news.CommonWebviewActivity
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initNewsAdapter()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.rssData.observe(this, Observer { rss ->
            showData(rss)
        })
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(context, this::onNewsClick)
        newsRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        newsRV.adapter = newsAdapter
        newsRV.setHasFixedSize(true)
        newsRV.setItemViewCacheSize(20)
    }

    private fun onNewsClick(i: Int?) {
        val item = newsAdapter.getItem(i ?: 0)
        activity?.baseContext?.let {
            val intent = CommonWebviewActivity.getIntent(it, "Новость", item.link ?: "")
            startActivity(intent)
        }
    }

    private fun showData(rss: RssRoot?) {
        newsAdapter.updateData(rss?.channel?.RssItem as List<NewsItem>)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
