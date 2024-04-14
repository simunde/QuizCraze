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

class SignUpActivity : AppCompatActivity() {
    private lateinit var edtEmailAddress:EditText
    private lateinit var edtPassword:EditText
    private lateinit var edtConfirmPassword:EditText
    private lateinit var btnLogin:TextView
    private lateinit var btnSignUp:Button
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        edtEmailAddress = findViewById(R.id.edtEmailAddress)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        firebaseAuth = FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener {
            signUpUser()
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun signUpUser(){
        val email: String = edtEmailAddress.text.toString()
        val password: String = edtPassword.text.toString()
        val confirmPassword: String = edtConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email & Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword){
            Toast.makeText(this,"Password & Confirm Password don't match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else{
                    Toast.makeText(this,"Error Creating a user",Toast.LENGTH_SHORT).show()
                }
            }



    }
}