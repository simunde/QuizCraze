package com.msid.quizcraze.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.google.gson.Gson
import com.msid.quizcraze.R
import com.msid.quizcraze.models.Quiz

class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    lateinit var tvScore: TextView
    lateinit var tvAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        tvScore = findViewById(R.id.tvScore)
        tvAnswer = findViewById(R.id.tvAnswer)

        setUpViews()

    }

    private fun setUpViews() {
        val quizData : String? = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData,Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun calculateScore() {
        var score=0
        for (entry in quiz.questions.entries){
            val question = entry.value
            if (question.answer == question.useranswer){
                score+=10
            }
        }
        tvScore.text= "Your score is : $score"

    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            tvAnswer.text = Html.fromHtml(builder.toString());
        }
    }
}