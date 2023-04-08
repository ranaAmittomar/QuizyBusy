package com.example.quizybusy

data class News(
    val totalResults:Int,
    val articles:List<ArticleData> //this will point to article data.
)
