package com.msid.quizcraze.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.msid.quizcraze.R
import com.msid.quizcraze.adapters.QuizAdapter
import com.msid.quizcraze.models.Quiz
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

//import kotlinx.android.synthetic.main.activity_main*

class MainActivity : AppCompatActivity() {
    lateinit var appBar: Toolbar
    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    lateinit var mainDrawer : DrawerLayout
    lateinit var adapter: QuizAdapter
    private var quizList= mutableListOf<Quiz>()
    private lateinit var recyclerView: RecyclerView
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var btnDatePicker : FloatingActionButton
    lateinit var navigationView : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBar = findViewById(R.id.appBar)
        mainDrawer = findViewById(R.id.mainDrawer)
        recyclerView = findViewById(R.id.recyclerView)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        navigationView= findViewById(R.id.navigationView)
        //populateDummyData()
        setUpViews()

    }



    private fun setUpViews() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()


    }



    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DATEPICKER")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormat.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText.toString())

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","Date Picker Cancelled")

            }
        }
    }

    private fun setUpFireStore() {
        firebaseFirestore= FirebaseFirestore.getInstance()
        val collectionReference = firebaseFirestore.collection("quizzes")
        collectionReference.addSnapshotListener{ value, error ->
            if (value == null || error!= null){
                safeToast(this,"Error Fetching Data")
                //Toast.makeText(this,"Error Fetching Data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()

        }
    }
    fun safeToast(context: Context, message: String) {
        if (context is Activity && !context.isFinishing) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpRecyclerView() {
        adapter= QuizAdapter(this,quizList)
        recyclerView.layoutManager= GridLayoutManager(this,2)
        recyclerView.adapter= adapter

    }

    private fun setUpDrawerLayout() {
       setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}