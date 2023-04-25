package com.example.quizybusy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    //we have to do the find view by ID wherever we're actually defining the function ! Specially in private fun !
    //if we are writing code inside ON_CREATE then we don't have to go inside ,but for private function,we have to define
    //inside the private fun

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var mainDrawer: DrawerLayout
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var adapter: QuizAdapter
    private lateinit var quizRecyclerView: RecyclerView
    private var quizList = mutableListOf<QuizData>()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var btnDatePicker: FloatingActionButton
    private lateinit var readNewsButton: Button
    private lateinit var darkModeSwitch: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readNewsButton = findViewById(R.id.readNewsButton)
        darkModeSwitch = findViewById(R.id.switchDarkMode)
        //below method to switch to DARK MODE using a switch BUTTON.
        darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        readNewsButton.setOnClickListener {
            val btnIntent = Intent(this, NewsActivityMain::class.java)
            startActivity(btnIntent)
        }
//        populateDummyData() //we used this to populate app with some dummy data
        setUpViews()
    }


    //BELOW FUNCTION IS FOR EVERY VIEW IN MAIN LAYOUT
    private fun setUpViews() {
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpFireStore()
        setUpDatePicker()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            //below code to handle different event like, if clicking back button,touched or doesn't touch datePicker
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker was cancelled")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference =
            firestore.collection("quizzes") //"quizzes" here is the name from fire_store database collection named

        //below addSnapShot_listener changes thing according to whatever we'll change in the database
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data !", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            //below,we are fetching data from fire_store,converting that JSON file in java_object and taking ID from QuizData FIle
            Log.d("DATA", value.toObjects(QuizData::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(QuizData::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        quizRecyclerView = findViewById(R.id.quizRecyclerView)
        adapter = QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        topAppBar = findViewById(R.id.topAppBar)
        mainDrawer = findViewById(R.id.mainDrawer)
        navigationView = findViewById(R.id.navigationView)
        setSupportActionBar(topAppBar) //here we're fetching material toolbar by saying it's a custom defined Toolbar
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, mainDrawer, R.string.app_name, R.string.app_name
        ) //In the QuizBusy field ,we can also pass the R.STRING.APP_NAME(lower_case)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            //making drawer menu clickable..
            when(it.itemId){
                R.id.btnProfile->Toast.makeText(applicationContext,"Profile Clicked",Toast.LENGTH_SHORT).show()
                R.id.btnFollowUs->Toast.makeText(applicationContext,"Follow Clicked",Toast.LENGTH_SHORT).show()
                R.id.rateUs->Toast.makeText(applicationContext,"RateUs Clicked",Toast.LENGTH_SHORT).show()
                R.id.logOutDra->startActivity(Intent(this,ProfileActivity::class.java))
            }

            true
        }
    }

    //below is to toggle the action saying that "If the hamburg_icon is selected , that show the drawer."It is happening
    //by using the IF CONDITION.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}