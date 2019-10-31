package com.news.it.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.news.it.R
import com.news.it.ds.BaseRss
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Response

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.rssData.observe(this, Observer { text ->
            showData(text)
        })

        loadBbtn.setOnClickListener{
            viewModel.getData()
        }
    }

    private fun showData(text: Response<BaseRss>?) {
        if(text?.body() != null){
            println(text.body())
        }
    }

}
