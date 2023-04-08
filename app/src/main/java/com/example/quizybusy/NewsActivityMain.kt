package com.example.quizybusy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivityMain : AppCompatActivity() {
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList:RecyclerView
    private var articles = mutableListOf<ArticleData>()
    private var pageNum = 1
    var totalResults = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_main)
        newsList = findViewById(R.id.newsList)
        adapter = NewsAdapter(this@NewsActivityMain,articles)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@NewsActivityMain)
        getNews()

    }

    //so, the process we call is placed in  QUEUE by RETROFIT
    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object:Callback<News>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<News>, response: Response<News>) {//response is of news type bcoj we entered call<News> in NewsService.kt file
                val news = response.body()
                if(news!=null){
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Checking","Error in fetching news",t)

            }
        })
    }
}