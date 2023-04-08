package com.example.quizybusy

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
https://newsapi.org/v2/top-headlines?country=in&apiKey=c7f847861d454a8b932a02647290a377
https://newsapi.org/v2/everything?q=apple&from=2023-01-30&to=2023-01-30&sortBy=popularity&apiKey=c7f847861d454a8b932a02647290a377
*/

const val BASE_URL = "https://newsapi.org/" //here , it's the base url which is common in other full url.so,it'll be constant.
const val API_KEY = "c7f847861d454a8b932a02647290a377" //THIS IS THE API key for this NEWS_API website.
interface NewsInterface {

    /*
    we'll define methods here which we'll call from our app to the end points.
    we take pages as Int here,bcoj when API send more data,it generally send in form of page numbers.
    */

    @GET("v2/top-headlines?apiKey=$API_KEY") /* this is the request for API call.which is GET_REQUEST->ALSO,we defining end point */
    fun getHeadlines(@Query("country") country:String, @Query("page") page:Int):Call<News>
    /*
    below is how the actual calling will happen ->behind the scene.
    https://newsapi.org/v2/top-headlines?apiKey=$API_KEY&country=in&page=1
    */
}
//Now,we'll make RETROFIT OBJECT(which would be SINGLETON) ->So,wherever we'll have to call, we'll call this SINGLETON.

object NewsService{
    val newsInstance:NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}