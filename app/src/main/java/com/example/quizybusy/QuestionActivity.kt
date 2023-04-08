package com.example.quizybusy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    //check HERE FOR ERROR
    private var quizzes: MutableList<QuizData>? = null
    private var questions: MutableMap<String, QuestionData>? = null
    private var index = 1

    private lateinit var description: TextView
    private lateinit var optionList: RecyclerView
    private lateinit var btnPrevious :Button
    private lateinit var btnSubmit : Button
    private lateinit var btnNext : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setupFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        btnPrevious = findViewById(R.id.btnPrevious)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)
        btnPrevious.setOnClickListener {
            index--
            bindView()
        }
        btnNext.setOnClickListener {
            index++
            bindView()
        }
        btnSubmit.setOnClickListener {
            Log.d("Final Quiz",questions.toString())
            val intent = Intent(this,ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    private fun setupFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(QuizData::class.java)
                        questions = quizzes!![0].questions
                        bindView()
                    }
                }
        }

    }

    private fun bindView() {
        description = findViewById(R.id.description)
        optionList = findViewById(R.id.optionList)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)

        btnNext.visibility = View.GONE
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if(index ==1){
            btnNext.visibility = View.VISIBLE
        }
        else if(index== questions!!.size)  {
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility  = View.VISIBLE
        }
        else{
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]
      question?.let {
          description.text = it.description
          val optionAdapter = OptionAdapter(this, it)
          optionList.layoutManager = LinearLayoutManager(this)
          optionList.adapter = optionAdapter
          optionList.setHasFixedSize(true)
      }

    }
}