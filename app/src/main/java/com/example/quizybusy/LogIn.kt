package com.example.quizybusy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnGetStarted:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val auth=FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            Toast.makeText(this,"Already LogIn",Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
        btnGetStarted=findViewById(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            redirect("LOGIN")
        }
    }
    private fun redirect(name:String){
        val intent =when(name){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN"->Intent(this, MainActivity::class.java)
            else->throw Exception("No Path Exist")
        }
        startActivity(intent)
        finish()
    }

}