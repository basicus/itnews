package com.news.it.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.it.R
import com.news.it.model.NewsItem
import com.news.it.model.RssRoot
import com.news.it.net.ApiServiceBuilder
import com.news.it.ui.news.CommonWebviewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var rssService = ApiServiceBuilder().getRSSService()

    private var isRssLoaded: Boolean = false

    private val _rssLoading = MutableLiveData<Boolean>()
    val rssLoading: LiveData<Boolean> = _rssLoading

    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError: LiveData<Boolean> = _loadingError

    private val _rssData = MutableLiveData<RssRoot>()
    val rssData: LiveData<RssRoot> = _rssData

    fun resume() {
        if (!isRssLoaded) {
            // Show loading indicator
            _rssLoading.value = true
            getRss()
        }
    }

    fun showNewsItem(baseContext: Context?, item: NewsItem?) {
        if (item == null) {
            _loadingError.value = true
        } else {
            baseContext?.let {
                CommonWebviewActivity.start(
                    baseContext,
                    item.title ?: baseContext.getString(R.string.web_view_default_title),
                    item.link ?: ""
                )
            }
        }
    }

    private fun getRss() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = rssService.getData()
                if (response.isSuccessful) {
                    _rssData.value = response.body()
                    isRssLoaded = true
                } else {
                    _loadingError.value = true
                }
            } catch (ex: Exception) {
                _loadingError.value = true

            } finally {
                _rssLoading.value = false
            }
        }
    }

}
