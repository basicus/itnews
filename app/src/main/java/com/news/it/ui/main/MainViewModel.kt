package com.news.it.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.it.model.RssRoot
import com.news.it.net.ApiServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _rssData = MutableLiveData<RssRoot>()
    private var rssService = ApiServiceBuilder().getRSSService()

    val rssData: LiveData<RssRoot>
        get() = _rssData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = rssService.getData()
                if (response.isSuccessful) {
                    _rssData.postValue(response.body())
                }
            } catch (ex: Exception) {

            }
        }
    }
}
