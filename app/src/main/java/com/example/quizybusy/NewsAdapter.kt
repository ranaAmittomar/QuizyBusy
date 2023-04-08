package com.example.quizybusy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context, private val articles: List<ArticleData>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    //in this inner class we usually define our properties for recycler which we created while making the layout.
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        var newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
        var newsDescription: TextView = itemView.findViewById(R.id.newsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false)
        return ArticleViewHolder(view)

    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description
        //we use Glide Library cause we can't use image view holder directly from the urls.
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,NewsDetailActivity::class.java)
            intent.putExtra("URL",article.url)
            context.startActivity(intent)
        }


    }
}