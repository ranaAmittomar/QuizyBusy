package com.example.quizybusy

data class ArticleData(
    //we have to make sure the names here are matching with the JSON PROPERTIES from the NEWS_API.
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)
