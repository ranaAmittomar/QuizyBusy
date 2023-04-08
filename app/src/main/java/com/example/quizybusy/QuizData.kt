package com.example.quizybusy

data class QuizData(
    var id: String = "",
    var title: String = "",
    var questions: MutableMap<String, QuestionData> = mutableMapOf() //we took mutable map so that we retrieve the data from FIRESTORE/FIREBASE
)
