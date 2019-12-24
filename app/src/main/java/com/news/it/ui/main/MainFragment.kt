package com.news.it.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.news.it.R
import com.news.it.model.NewsItem
import com.news.it.model.RssRoot
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
        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    private fun observe() {
        with(viewModel) {
            rssData.observe(viewLifecycleOwner, Observer { rss ->
                showData(rss)
            })
            rssLoading.observe(viewLifecycleOwner, Observer { loading ->
                switchProgress(loading)
            })
            loadingError.observe(viewLifecycleOwner, Observer {
                showError()
            })
        }
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(context, this::onNewsClick)
        newsRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        newsRV.adapter = newsAdapter
        newsRV.setHasFixedSize(true)
        newsRV.setItemViewCacheSize(20)
    }

    private fun onNewsClick(i: Int?) {
        val item = if (i == null) null else newsAdapter.getItem(i)
        viewModel.showNewsItem(activity?.baseContext, item)
    }

    private fun switchProgress(loading: Boolean) {
        mainProgressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun showData(rss: RssRoot?) {
        newsAdapter.updateData(rss?.channel?.RssItem as List<NewsItem>)
    }

    private fun showError() {
        view?.let { view ->
            Snackbar.make(
                view,
                getString(R.string.main_frag_load_error),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
