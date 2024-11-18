package com.jhainusa.testsxperts

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login_page : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        auth=FirebaseAuth.getInstance()
        val loginButton = findViewById<AppCompatButton>(R.id.login_button)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_mobile_input).text.toString()
            val pass = findViewById<EditText>(R.id.password_input).text.toString()
            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this@Login_page,"Please fill all the details",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this,"Sign in successful",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                        else{
                            Toast.makeText(this,"Sign in Failed : ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
            }
        }
    }
}