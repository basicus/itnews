package com.news.it.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.it.ds.BaseRss
import com.news.it.net.RssService
import com.news.it.net.ServiceBuilder
import kotlinx.coroutines.*
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _rssData = MutableLiveData<Response<BaseRss>>()

    private var rssService: RssService? = null

    val rssData: LiveData<Response<BaseRss>>
        get() = _rssData

    init {
        rssService = ServiceBuilder().getRSSService()
    }


    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            _rssData.postValue(rssService?.getData()?.execute())
        }
    }
}
