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
import com.google.firebase.firestore.FirebaseFirestore

class SignUp_Page : AppCompatActivity() {
    private  lateinit var auth:FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_page)
        auth=FirebaseAuth.getInstance()
        val signupButton = findViewById<AppCompatButton>(R.id.signup_button)
        signupButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text.toString()
            val email = findViewById<EditText>(R.id.email_mobile_input).text.toString()
            val pass = findViewById<EditText>(R.id.password_input).text.toString()
            println(email)
            if(name.isEmpty() || email.isEmpty()|| pass.isEmpty()){
                Toast.makeText(this@SignUp_Page,"Please Fill all the details",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            UploadtoFirebase(name,email,pass)
                            startActivity(Intent(this,Login_page::class.java))
                        }
                        else{
                            Toast.makeText(this,"Registration Failed : ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
    private fun UploadtoFirebase(name : String,email : String,pass:String){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userProfile = hashMapOf(
            "name" to name,
            "email" to email,
            "pass" to pass
        )
        db.collection("Users").document(userId!!)
            .set(userProfile)
            .addOnSuccessListener {
                Toast.makeText(this,"Registration Successfull",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e->
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
    }
}