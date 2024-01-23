package com.kkyoungs.news

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kkyoungs.news.databinding.ActivityNewsListBinding
import com.kkyoungs.news.databinding.ActivityNewsViewBinding

class NewsViewActivity : AppCompatActivity(){
    val binding = DataBindingUtil.setContentView<ActivityNewsViewBinding>(this, R.layout.activity_news_view)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val newsUrl = intent.getStringExtra("url")
        binding.webView.apply {
            settings.javaScriptEnabled = true
            url
            webViewClient = MyWebViewClient()
            if (newsUrl != null) {
                loadUrl(newsUrl)
            }
        }



    }

    class MyWebViewClient:WebViewClient(){

    }
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
