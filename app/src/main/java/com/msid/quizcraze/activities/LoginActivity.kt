package com.msid.quizcraze.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.msid.quizcraze.R

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmailAddress:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: TextView
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmailAddress = findViewById(R.id.edtEmailAddress)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun login(){
        val email: String = edtEmailAddress.text.toString()
        val password: String = edtPassword.text.toString()

        if (email.isBlank() || password.isBlank() ){
            Toast.makeText(this,"Email & Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else{
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()

                }
            }
    }
}