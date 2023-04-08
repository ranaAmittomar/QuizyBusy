package com.example.quizybusy

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var detailWebView: WebView
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        detailWebView = findViewById(R.id.detailWebView)
        progressBar = findViewById(R.id.progressBar)
        val url = intent.getStringExtra("URL")
        if (url != null) {
            detailWebView.settings.javaScriptEnabled = true
            detailWebView.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                    detailWebView.visibility = View.VISIBLE
                }
            }
            detailWebView.loadUrl(url)
        }
    }
}