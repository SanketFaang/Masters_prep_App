package com.jhainusa.testsxperts.uii

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.databinding.ActivityExamLayoutBinding
import com.jhainusa.testsxperts.databinding.ActivityResultsBinding

class Results_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val answered = intent.getIntExtra("Answered",0)
        val notanswered = intent.getIntExtra("Not_answered",0)
        val tquestion = intent.getIntExtra("Total_Questions",0)
        val correct = intent.getIntExtra("correct",0)
        val incorrect = intent.getIntExtra("incorrect",0)
        binding.tquestion.text=tquestion.toString()
        binding.tCorrect.text=correct.toString()
        binding.tInc.text=incorrect.toString()
        binding.tans.text=answered.toString()
        binding.tnq.text=notanswered.toString()



    }
}