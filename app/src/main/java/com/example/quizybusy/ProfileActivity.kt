package com.example.quizybusy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var txtEmail: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnLogout = findViewById(R.id.btnLogout)
        txtEmail = findViewById(R.id.txtEmail)
        firebaseAuth = FirebaseAuth.getInstance()
        txtEmail.text =
            firebaseAuth.currentUser?.email //this is to get the email of the user from the fire_store and show in the result activity.
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // this will sign out the user from the app !
            startActivity(Intent(this, LogIn::class.java)) // to redirect user to login activity.

        }
    }
}