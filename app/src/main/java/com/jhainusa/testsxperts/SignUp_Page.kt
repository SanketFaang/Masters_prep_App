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
import com.jhainusa.testsxperts.databinding.ActivityCourseTestBinding
import com.jhainusa.testsxperts.databinding.ActivitySignUpPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUp_Page : AppCompatActivity() {
    private  lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivitySignUpPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignUpPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailMobileInput.text.toString()
            val pass = binding.passwordInput.text.toString()
            if(name.isEmpty() || email.isEmpty()|| pass.isEmpty()){
                Toast.makeText(this@SignUp_Page,"Please Fill all the details",Toast.LENGTH_SHORT).show()
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    val editor = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).edit()
                    editor.putString("name",name)
                    editor.putString("email",email)
                    editor.putString("pass",pass)
                    editor.apply()
                }
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        auth.createUserWithEmailAndPassword(email, pass).await()
                        Toast.makeText(this@SignUp_Page,"Registration Successful",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUp_Page,MainActivity::class.java))
                        finish()
                    }
                    catch (e:Exception){
                       Toast.makeText(this@SignUp_Page,"Registration failed : ${e.message}",Toast.LENGTH_SHORT).show()
                    }
                    }
            }
        }
        binding.loginText.setOnClickListener{
            startActivity(Intent(this,Login_page::class.java))
        }

    }
}