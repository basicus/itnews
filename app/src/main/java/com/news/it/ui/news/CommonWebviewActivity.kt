package com.news.it.ui.news

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.news.it.R
import kotlinx.android.synthetic.main.commom_webview_activity.*

private const val TITLE = "title"
private const val URL = "url"

class CommonWebviewActivity : AppCompatActivity() {

    private var title = ""//getString(R.string.web_content_title)
    private var url = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            title = it.getString(TITLE) ?: ""
            url = it.getString(URL) ?: ""
        }
        setContentView(R.layout.commom_webview_activity)
    }


    override fun onResume() {
        super.onResume()
        initWebView()
        showContent()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        commonWebContentPB.max = 100
        commonWebContentPB.visibility = View.VISIBLE
        commonWebContentWV.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                commonWebContentPB?.run {
                    commonWebContentPB.progress = progress
                    if (progress == 100) {
                        commonWebContentPB.visibility = View.GONE
                    }
                }
            }
        }
        commonWebContentWV.settings.javaScriptEnabled = true
        commonWebContentWV.webViewClient = WebViewClient()
    }

    private fun showContent() {
        if (url.isNotBlank()) {
            commonWebContentWV.loadUrl(url)
        } else {
            showSnack(getString(R.string.web_view_error))
        }
    }

    private fun showSnack(text: String) {
        Snackbar.make(commonWebContentWV, text, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, CommonWebviewActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }
}