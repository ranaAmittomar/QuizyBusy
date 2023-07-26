package com.example.quizybusy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var etEmailAddress:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth=FirebaseAuth.getInstance()
        etEmailAddress=findViewById(R.id.etEmailAddress)
        etPassword=findViewById(R.id.etPassword)

        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.btnSignUp)
        btnLogin.setOnClickListener{
            login()
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login(){
        val email = etEmailAddress.text.toString()
        val password = etPassword.text.toString()

        if(email.isBlank()||password.isBlank()){
            Toast.makeText(this,"Fields Can't be Empty !",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                //SEARCH CONDITION FOR IF USER DOESN'T EXIST
            }
            else{
                Toast.makeText(this,"Password or Email Do not match !",Toast.LENGTH_SHORT).show()
            }
        }

    }
}