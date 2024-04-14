package com.msid.quizcraze.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.msid.quizcraze.R
import java.lang.Exception

class LoginIntro : AppCompatActivity() {
    lateinit var btnGetStarted: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        val auth= FirebaseAuth.getInstance()
        btnGetStarted = findViewById(R.id.btnGetStarted)

        if (auth.currentUser!=null){
            Toast.makeText(this,"User is already logged in!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
        btnGetStarted.setOnClickListener {
            redirect("LOGIN")
        }
    }

    private fun redirect(name: String) {
        val intent:Intent = when(name){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN"  -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no paths Exists")
        }
        startActivity(intent)
        finish()
    }
}