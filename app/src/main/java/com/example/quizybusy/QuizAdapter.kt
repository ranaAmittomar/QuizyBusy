package com.example.quizybusy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter(private val context: Context, private val quizzes: List<QuizData>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.quizTitle)
        var iconView: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer: CardView = itemView.findViewById(R.id.cardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.quiz_item, parent, false) //remember to write R.Layout not R.id
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int { //in this override , we pass the argument from the main above class ,,which is the List(basically the array of items).
        return quizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text = quizzes[position].title
        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }

}




