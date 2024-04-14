package com.msid.quizcraze.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.msid.quizcraze.R
import com.msid.quizcraze.adapters.OptionAdapter
import com.msid.quizcraze.models.Question
import com.msid.quizcraze.models.Quiz

class QuestionActivity : AppCompatActivity() {
    private lateinit var description:TextView
    private lateinit var btnPrevious:Button
    private lateinit var btnNext:Button
    private lateinit var btnSubmit:Button

    private lateinit var optionList:RecyclerView

    private var quizzes : MutableList<Quiz>? = null
    private var questions : MutableMap<String, Question>? = null
    var index =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        description= findViewById(R.id.description)
        optionList= findViewById(R.id.optionList)
        btnPrevious= findViewById(R.id.btnPrevious)
        btnNext= findViewById(R.id.btnNext)
        btnSubmit= findViewById(R.id.btnSubmit)
        //bindViews()
        setUpFireStore()
        setUpEventListeners()
    }

    private fun setUpEventListeners() {
        btnNext.setOnClickListener {
            Log.d("Data","Data")

            index++
            bindViews()

        }
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btnSubmit.setOnClickListener {
            Log.d("Data",questions.toString())
            val intent = Intent(this,ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    private fun setUpFireStore() {
        val firebaseFirestore= FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date!=null){
            val collectionReference = firebaseFirestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it!=null && !it.isEmpty){
                        quizzes= it.toObjects(Quiz::class.java)
                        questions= quizzes!!.get(0).questions
                        bindViews()
                    }

                }
        }


    }

    private fun bindViews() {
        btnPrevious.visibility = View.INVISIBLE
        btnNext.visibility = View.INVISIBLE
        btnSubmit.visibility = View.INVISIBLE

        if (index==1){ //first question
            btnNext.visibility = View.VISIBLE

        }
        else if (index== questions!!.size){//first question
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        }
        else{
            btnNext.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE

        }
        val question = questions!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionList.layoutManager=LinearLayoutManager(this)
            optionList.adapter= optionAdapter
            optionList.setHasFixedSize(true)
        }


    }
}