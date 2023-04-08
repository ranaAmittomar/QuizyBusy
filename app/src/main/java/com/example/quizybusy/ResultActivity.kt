package com.example.quizybusy

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    private lateinit var quiz: QuizData
    private lateinit var txtScore: TextView
    private lateinit var txtAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson(quizData, QuizData::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        txtAnswer = findViewById(R.id.txtAnswer)
        val builder = java.lang.StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            //below code is to show the result in the Score/Result activity and to show which answer you've given is correct or wrong
            builder.append("<font color '#18206F'><b>QuestionData: ${question.description}</b><front><br/><br/>") //this <b> "" </b> makes string bold in result activity.😍
            builder.append("<font color = '#009688'>Answer : ${question.answer}</front><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //html class works different on different versions
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
        }
        else{
            txtAnswer.text = Html.fromHtml((builder.toString()))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calculateScore() {
        txtScore = findViewById(R.id.txtScore)
        var score = 0
        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }
        txtScore.text = "Your score is : $score"
    }
}